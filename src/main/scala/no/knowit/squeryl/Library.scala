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
    b.title is (indexed("idxBookTitles"), dbType("varchar(255)"))))

  // TODO: Sett opp relasjon mellom bok og forfatter

  // TODO: Sett opp søk på forfatter
  def findAuthorByName(firstName: String, lastName: String): Option[Author] = None

  def findAuthorByEmail(email: String): Option[Author] = None

  // TODO: Sett opp søk på bøker
  def findBooksByAuthor(author: Author): List[Book] = Nil

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
