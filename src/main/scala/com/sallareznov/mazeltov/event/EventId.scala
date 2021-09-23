package com.sallareznov.mazeltov.event

final case class EventId(value: Long) extends AnyVal

object EventId {

  def apply(value: String): EventId = new EventId(value.toLong)

}
