package no.knowit.squeryl

import org.scalatest.FlatSpec
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import org.squeryl.PrimitiveTypeMode._

import no.knowit.squeryl.Library._

@RunWith(classOf[JUnitRunner])
class LibraryTest extends FlatSpec with ShouldMatchers {

  Library.init

  "A library" should "register authors" in {
    transaction {
      authors.insert(new Author(1, "Fredrik", "Testesen", Some("fvr@knowit.no")))
      authors.insert(new Author(2, "Johannes Stamnes", "Vraalsen", None))
      authors.insert(new Author(3, "Wil", "Wheaton", Some("wil@wilwheaton.net")))
      authors.insert(new Author(4, "Neil", "Gaiman", None))
    }
  }

  it should "register books to authors" in {
    transaction {
      books.insert(new Book(1, "Memories of the Future", 3))
      books.insert(new Book(2, "Coraline", 4))
      books.insert(new Book(3, "American Gods", 4))
    }
  }

  it should "not register multiple books with the same title" in {
    evaluating {
      transaction {
        books.insert(new Book(0, "Coraline", 0))
      }
    } should produce[Exception]
  }

  it should "not register books to authors that do not exist" in {
    evaluating {
      transaction {
        books.insert(new Book(0, "Test", 0))
      }
    } should produce[Exception]
  }

  it should "not find authors that do not exist" in {
    transaction {
      findAuthorByEmail("fredrik@vraalsen.no") should equal(None)
      findAuthorByName("Fredrik", "Vraalsen") should equal(None)
    }
  }

  it should "find author by e-mail" in {
    transaction {
      val a = findAuthorByEmail("wil@wilwheaton.net")
      a should not equal(None)
      a.get.firstName should equal("Wil")
      a.get.lastName should equal("Wheaton")
      a.get.email should equal(Some("wil@wilwheaton.net"))
    }
  }

  it should "find author by name" in {
    transaction {
      val a = findAuthorByName("Neil", "Gaiman")
      a should not be (None)
      a.get.firstName should equal("Neil")
      a.get.lastName should equal("Gaiman")
      a.get.email should equal(None)
    }
  }

}