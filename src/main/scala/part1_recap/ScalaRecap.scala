package part1_recap

import scala.concurrent.Future
import scala.util.{Failure, Success}

object ScalaRecap extends App {
  val aCondition: Boolean = false

  def myFunction(x: Int) = {
    // code
    if (x > 4) 42 else 65
  }
  // instruction vs expression
  // types vs type inference

  // OO features of Scala
  class Animal
  trait Carnivore {
    def eat(a: Animal): Unit
  }
  object Carnivore

  // generics
  abstract class MyList[+A]
  // FP
  val anIncrementer: Int => Int = (x: Int) => x + 1
  anIncrementer(1)
}
