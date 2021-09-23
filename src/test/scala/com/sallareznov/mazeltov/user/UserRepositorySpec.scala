package com.sallareznov.mazeltov.user

import akka.actor.ActorRef
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers

import scala.collection.concurrent.TrieMap

final class UserRepositorySpec extends AnyFunSuiteLike with Matchers {

  test("add user") {
    val repository = UserRepository.empty

    repository.addUser(1, ActorRef.noSender)
    repository.getUsers shouldBe TrieMap.from(List(UserId(1) -> ActorRef.noSender))
    repository.addUser(84, ActorRef.noSender)
    repository.getUsers shouldBe TrieMap.from(List(UserId(1) -> ActorRef.noSender, UserId(84) -> ActorRef.noSender))
  }

  test("get user address") {
    val repository =
      new UserRepository(
        TrieMap.from(List(UserId(1) -> ActorRef.noSender, UserId(84) -> ActorRef.noSender)),
        TrieMap.empty
      )

    repository.getUserAddress(UserId(84)) should contain(ActorRef.noSender)
    repository.getUserAddress(UserId(43)) shouldBe empty
  }

  test("add follower") {
    val repository = UserRepository.empty

    repository.addFollower(UserId(10), UserId(20))
    repository.getFollowers(UserId(20)) shouldBe Vector(UserId(10))
    repository.addFollower(UserId(30), UserId(40))
    repository.getFollowers(UserId(40)) shouldBe Vector(UserId(30))
    repository.addFollower(UserId(50), UserId(20))
    repository.getFollowers(UserId(20)) shouldBe Vector(UserId(10), UserId(50))
  }

  test("remove follower") {
    val repository =
      new UserRepository(
        TrieMap.empty,
        TrieMap.from(List(UserId(20) -> Vector(UserId(10), UserId(50)), UserId(40) -> Vector(UserId(30))))
      )

    repository.removeFollower(UserId(50), UserId(20))
    repository.getFollowers(UserId(20)) shouldBe Vector(UserId(10))
    repository.removeFollower(UserId(10), UserId(20))
    repository.getFollowers(UserId(20)) shouldBe Vector.empty
  }

}
