package no.knowit.squeryl

import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.DerbyAdapter
import org.squeryl.annotations.Column
import org.squeryl.dsl.OneToMany
import org.squeryl.dsl.ManyToOne

class Author(val id: Long,
  val firstName: String,
  val lastName: String,
  val email: Option[String]) extends KeyedEntity[Long] {

  // no-arg constructor required for classes with Option[] fields
  def this() = this(0, "", "", Some(""))
}

class Book(val id: Long,
  var title: String,
  @Column(name = "AUTHOR_ID") var authorId: Long) extends KeyedEntity[Long] {
}

object Library extends Schema {

  val authors = table[Author]("AUTHORS")

  val books = table[Book]("BOOKS")

  on(authors)(a => declare(
    a.email is (indexed("idxEmailAddresses")),
    a.firstName is (indexed),
    a.lastName is (indexed),
    columns(a.firstName, a.lastName) are (indexed)))

  on(books)(b => declare(
    b.title is (unique, indexed("idxBookTitles"), dbType("varchar(255)"))))

  val authorToBooks = oneToManyRelation(authors, books).
    via((a, b) => a.id === b.authorId)

  def findAuthorByName(firstName: String, lastName: String): Option[Author] =
    try {
      from(authors)(a =>
        where(a.firstName === firstName and a.lastName === lastName)
          select (Some(a))).single
    } catch {
      case _ => None
    }

  def findAuthorByEmail(email: String): Option[Author] =
    try {
      from(authors)(a =>
        where(a.email === Some(email))
          select (Some(a))).single
    } catch {
      case _ => None
    }

  def findBooksByAuthor(author: Author): List[Book] =
    books.where(b => b.authorId === author.id).toList

  def init = {
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver")

    SessionFactory.concreteFactory = Some(() =>
      Session.create(
        java.sql.DriverManager.getConnection("jdbc:derby:memory:library;create=true"),
        new DerbyAdapter()))

    transaction {
      // Library.printDdl
      Library.create
    }
  }

}
