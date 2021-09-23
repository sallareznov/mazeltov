package com.sallareznov.mazeltov

import scala.util.matching.Regex

object Regexes {

  final val userId: Regex       = raw"(\d+)\s+".r
  final val follow: Regex       = raw"(\d+)[|]F[|](\d+)[|](\d+)".r
  final val unfollow: Regex     = raw"(\d+)[|]U[|](\d+)[|](\d+)".r
  final val broadcast: Regex    = raw"(\d+)[|]B".r
  final val privateMsg: Regex   = raw"(\d+)[|]P[|](\d+)[|](\d+)".r
  final val statusUpdate: Regex = raw"(\d+)[|]S[|](\d+)".r

}
