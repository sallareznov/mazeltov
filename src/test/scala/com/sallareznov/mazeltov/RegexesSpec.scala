package com.sallareznov.mazeltov

import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers
import Regexes._

import scala.util.matching.Regex

final class RegexesSpec extends AnyFunSuiteLike with Matchers {

  def matching(regex: Regex): List[Boolean] = {
    val inputs = List("2932\r\n", "666|F|60|50", "1|U|12|9", "542532|B", "43|P|32|56", "634|S|32", "46373E2")
    inputs.map(regex.matches)
  }

  test("regexes") {
    matching(userId) shouldBe List(true, false, false, false, false, false, false)
    matching(follow) shouldBe List(false, true, false, false, false, false, false)
    matching(unfollow) shouldBe List(false, false, true, false, false, false, false)
    matching(broadcast) shouldBe List(false, false, false, true, false, false, false)
    matching(privateMsg) shouldBe List(false, false, false, false, true, false, false)
    matching(statusUpdate) shouldBe List(false, false, false, false, false, true, false)
  }

}
