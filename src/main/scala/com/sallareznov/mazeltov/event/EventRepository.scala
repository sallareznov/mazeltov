package com.sallareznov.mazeltov.event

import scala.collection.mutable.ListBuffer

final class EventRepository(events: ListBuffer[String]) {

  def addEvent(event: String): Unit    = events += event
  def removeEvent(event: String): Unit = events -= event
  def getEvents: ListBuffer[String]    = events

}

object EventRepository {

  def empty: EventRepository = new EventRepository(ListBuffer.empty)

}
