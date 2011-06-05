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
  
}