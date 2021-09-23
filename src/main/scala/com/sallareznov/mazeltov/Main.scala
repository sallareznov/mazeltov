package com.sallareznov.mazeltov

import akka.actor.{ActorSystem, Props}
import com.sallareznov.mazeltov.event.{EventListener, EventRepository}
import com.sallareznov.mazeltov.user.{UserListener, UserRepository}

object Main extends App {

  implicit final val system: ActorSystem = ActorSystem("mazeltov")

  final val userRepository  = UserRepository.empty
  final val eventRepository = EventRepository.empty

  system.actorOf(Props(new UserListener(9099, userRepository)))
  system.actorOf(Props(new EventListener(9090, userRepository, eventRepository)))

}
