package com.github.lpld.nand2tetris

import scala.util.matching.Regex
import scala.util.matching.Regex.Match

/**
  * @author leopold
  * @since 27/03/16
  */
class Parser {

  def parseCommand(line: String): Option[Command] = {
    // values is a Stream, so computation will be done only until we successfully parse a command
    CommandType.values
      .flatMap(_.tryParse(line)) // tryParse will return None, if the command doesn't match
      .headOption // getting the first parsed command
  }
}

sealed abstract class CommandType(val name: String, val regex: Regex) {

  // try parse the command. if command doesn't match to the type, return None
  def tryParse(line: String): Option[Command] =
    regex.findAllIn(line).matchData // match regex
      .toStream.headOption // if matched - take the first match
      .map(parseMatched) // and parse it

  // parse a matched command
  def parseMatched(matched: Match): Command
}

object CommandType {

  // example: @8192 or @sum
  case object A extends CommandType("A", "^@(\\w+)$".r) {
    def parseMatched(matched: Match) = ACommand(matched.group(1))
  }

  // example: (LOOP)
  case object L extends CommandType("L", "^[(](\\w+)[)]$".r) {
    def parseMatched(matched: Match) = LCommand(matched.group(1))
  }

  // example: D=D+A or D;JEQ
  case object C extends CommandType("C", "^((\\w+)=)?(\\w+)(;(\\w+))?".r) {

    def parseMatched(matched: Match) = CCommand(
      dest = matched.group(2),
      comp = matched.group(3),
      jump = matched.group(5)
    )
  }

  val values = Stream(A, C, L)
}
