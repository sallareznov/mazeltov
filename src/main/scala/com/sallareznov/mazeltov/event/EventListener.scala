package com.sallareznov.mazeltov.event

import akka.actor.Actor
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.{ByteString, CompactByteString}
import com.sallareznov.mazeltov.Regexes
import com.sallareznov.mazeltov.user.{UserId, UserRepository}

import java.net.InetSocketAddress
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.language.postfixOps

final class EventListener(port: Int, userRepository: UserRepository, eventRepository: EventRepository) extends Actor {

  import context.system

  implicit val executionContext: ExecutionContextExecutor = ExecutionContext.global

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", port))

  val runnable: Runnable = () => {
    val currentEvents = eventRepository.getEvents.toList
    currentEvents
      .sortBy(eventId(_).getOrElse(Long.MaxValue))
      .foreach { event =>
        handleEvent(event)
        eventRepository.removeEvent(event)
      }

  }

  system.scheduler.scheduleAtFixedRate(1 seconds, 2 seconds)(runnable)

  def receive: Receive = {
    case b: Bound               => context.parent ! b
    case CommandFailed(_: Bind) => context.stop(self)
    case _: Connected           => sender() ! Register(self)
    case Received(data)         => handleReceivedData(data)
    case Closed                 => context.stop(self)
  }

  private def handleReceivedData(data: ByteString): Unit = data.utf8String.split("\n").foreach(eventRepository.addEvent)

  private def handleEvent: String => Unit = {
    case message @ Regexes.follow(_, from, to)   => follow(message, from, to)
    case Regexes.unfollow(_, from, to)           => unfollow(from, to)
    case message @ Regexes.broadcast(_)          => broadcast(message)
    case message @ Regexes.privateMsg(_, _, to)  => privateMsg(message, to)
    case message @ Regexes.statusUpdate(_, from) => statusUpdate(message, from)
  }

  private def eventId: String => Option[Long] = {
    case Regexes.follow(id, _, _)     => Some(id.toLong)
    case Regexes.unfollow(id, _, _)   => Some(id.toLong)
    case Regexes.broadcast(id)        => Some(id.toLong)
    case Regexes.privateMsg(id, _, _) => Some(id.toLong)
    case Regexes.statusUpdate(id, _)  => Some(id.toLong)
    case _                            => None
  }

  private def follow(message: String, from: String, to: String): Unit = {
    userRepository.addFollower(UserId(from), UserId(to))
    userRepository.getUserAddress(UserId(to)).foreach(_ ! Write(CompactByteString(s"$message\n")))
  }

  private def unfollow(from: String, to: String): Unit = userRepository.removeFollower(UserId(from), UserId(to))

  private def broadcast(message: String): Unit = userRepository.getUsers.foreach(_._2 ! Write(CompactByteString(s"$message\n")))

  private def privateMsg(message: String, to: String): Unit =
    userRepository.getUserAddress(UserId(to)).foreach(_ ! Write(CompactByteString(s"$message\n")))

  private def statusUpdate(message: String, from: String): Unit =
    userRepository
      .getFollowers(UserId(from))
      .flatMap(userRepository.getUserAddress)
      .foreach(_ ! Write(CompactByteString(s"$message\n")))

}
