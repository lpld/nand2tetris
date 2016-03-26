package com.github.lpld.nand2tetris

import scala.util.matching.Regex

/**
  * @author leopold
  * @since 27/03/16
  */
trait Command

case class ACommand(value: String) extends Command

case class CCommand(dest: String, comp: String, jump: String) extends Command

case class LCommand(label: String) extends Command

