package com.sallareznov.mazeltov.event

import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable.ListBuffer

final class EventRepositorySpec extends AnyFunSuiteLike with Matchers {

  test("add event") {
    val repository = EventRepository.empty

    repository.getEvents.toList shouldBe Nil
    repository.addEvent("666|F|60|50")
    repository.getEvents.toList shouldBe List("666|F|60|50")
    repository.addEvent("1|U|12|9")
    repository.getEvents.toList shouldBe List("666|F|60|50", "1|U|12|9")
  }

  test("remove event") {
    val repository = new EventRepository(ListBuffer("666|F|60|50", "1|U|12|9", "542532|B"))

    repository.removeEvent("1|U|12|9")
    repository.getEvents.toList shouldBe List("666|F|60|50", "542532|B")
    repository.removeEvent("43|P|32|56")
    repository.getEvents.toList shouldBe List("666|F|60|50", "542532|B")
  }

}
