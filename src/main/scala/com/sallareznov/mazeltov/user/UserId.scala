package com.sallareznov.mazeltov.user

final case class UserId(value: Int) extends AnyVal

object UserId {

  def apply(value: String): UserId = new UserId(value.toInt)

}
