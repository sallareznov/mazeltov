package com.sallareznov.mazeltov.user

import akka.actor.ActorRef

import scala.collection.concurrent.TrieMap

final class UserRepository(users: TrieMap[UserId, ActorRef], followers: TrieMap[UserId, Vector[UserId]]) {

  def addUser(key: Int, value: ActorRef): Unit = users += UserId(key) -> value

  def getUserAddress(id: UserId): Option[ActorRef] = users.get(id)

  def getUsers: TrieMap[UserId, ActorRef] = users

  def addFollower(from: UserId, to: UserId): Unit = followers += to -> (getFollowers(to) :+ from)

  def removeFollower(from: UserId, to: UserId): Unit = followers += to -> getFollowers(to).filterNot(_ == from)

  def getFollowers(user: UserId): Vector[UserId] = followers.getOrElse(user, Vector.empty)

}

object UserRepository {

  def empty: UserRepository = new UserRepository(new TrieMap, new TrieMap)

}
