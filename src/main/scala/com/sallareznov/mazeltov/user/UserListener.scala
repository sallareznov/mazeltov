package com.sallareznov.mazeltov.user

import akka.actor.Actor
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.ByteString
import com.sallareznov.mazeltov.Regexes._

import java.net.InetSocketAddress

final class UserListener(port: Int, userRepository: UserRepository) extends Actor {

  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", port))

  override def receive: Receive = {
    case b: Bound               => context.parent ! b
    case CommandFailed(_: Bind) => context.stop(self)
    case _: Connected           => sender() ! Register(self)
    case Received(data)         => handleData(data)
    case Closed                 => context.stop(self)
  }

  private def handleData(data: ByteString): Unit =
    data.utf8String match {
      case userId(id) => userRepository.addUser(id.toInt, sender())
      case _          => ()
    }

}
