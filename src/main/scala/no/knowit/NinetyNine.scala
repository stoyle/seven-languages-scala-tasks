package no.knowit

import annotation.tailrec

object NinetyNine {

  @tailrec
  def last[T](l: List[T]): T = l match {
    case head :: Nil => head
    case head :: tail => last(tail)
    case Nil => throw new RuntimeException("Empty list")
  }

  @tailrec
  def penultimate[T](l: List[T]): T = l match {
    case List(_, e, _) => e
    case head :: tail => penultimate(tail)
    case Nil => throw new RuntimeException("Empty list")
  }

  @tailrec
  def nth[T](num: Int, l: List[T]): T = (l, num) match {
    case (head :: tail, cur) if cur == 0 => head
    case (head :: tail, cur) => nth(cur - 1, tail)
    case (Nil, _) => throw new RuntimeException(num + " is too large for list")
  }


  def length[T](l: List[T]): Int = {
    @tailrec
    def recurseLength[T](acc: Int, l: List[T]): Int = l match {
      case head :: tail => recurseLength(acc + 1, tail)
      case Nil => acc
    }
    recurseLength(0, l)
  }

  def reverse[T](l: List[T]): List[T] = l.foldLeft(List[T]()) {(l, e) => e :: l}

  def isPalindrome[T](l: List[T]) = l == reverse(l)

}