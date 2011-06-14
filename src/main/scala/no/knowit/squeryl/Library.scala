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
  val email: Option[String]) {

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

  on(authors)(s => declare(
    s.email is (unique, indexed("idxEmailAddresses"), dbType("varchar(255)")),
    s.firstName is (indexed),
    s.lastName is (indexed),
    columns(s.firstName, s.lastName) are (indexed)))

  def main(args: Array[String]) = {
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver")

    SessionFactory.concreteFactory = Some(() =>
      Session.create(
        java.sql.DriverManager.getConnection("jdbc:derby:memory:library;create=true"),
        new DerbyAdapter()))

    transaction {
      Library.create
    }

    transaction {
      authors.insert(new Author(1, "Neil", "Gaiman", None))

      val res = from(authors) { a =>
        where(a.lastName === "Gaiman")
        select(a)
      }

      for (a <- res)
        println(a.firstName + " " + a.lastName)
    }
  }

}
