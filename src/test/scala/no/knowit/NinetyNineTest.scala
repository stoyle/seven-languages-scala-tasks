package no.knowit

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import no.knowit.NinetyNine._

@RunWith(classOf[JUnitRunner])
class NinetyNineTest extends FunSuite {

  test("1. Find the last element of a list.") {
    assert(8 === last(List(1, 1, 2, 3, 5, 8)))
  }

  test("2. Find the last but one element of a list.") {
    assert(5 === penultimate(List(1, 1, 2, 3, 5, 8)))
  }

  test("3. Find the Kth element of a list.") {
    assert(2 === nth(2, List(1, 1, 2, 3, 5, 8)))
  }

  test("4. def penultimate[T](l: List[T]) = 5") {
    assert(6 === length(List(1, 1, 2, 3, 5, 8)))
  }

  test("5. Reverse a list.") {
    assert(List(8, 5, 3, 2, 1, 1) === reverse(List(1, 1, 2, 3, 5, 8)))
  }

  test("6. Find out whether a list is a palindrome.") {
    assert(true === isPalindrome(List(1, 2, 3, 2, 1)))
  }

  test("7. Flatten a nested list structure.") {
    assert(List(1, 1, 2, 3, 5, 8) === flatten(List(List(1, 1), 2, List(3, List(5, 8)))))
  }

  test("8. Eliminate consecutive duplicates of list elements.") {
    assert(List('a, 'b, 'c, 'a, 'd, 'e) === compress(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)))
  }

  test("9. Pack consecutive duplicates of list elements into sublists.") {
    assert(List(List('a, 'a, 'a, 'a), List('b), List('c, 'c), List('a, 'a), List('d), List('e, 'e, 'e, 'e))
      ===  pack(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)))
  }

  test("10. Run-length encoding of a list.") {
    assert(List((4,'a), (1,'b), (2,'c), (2,'a), (1,'d), (4,'e)) === encode(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)))
  }
}