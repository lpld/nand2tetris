package com.github.lpld.nand2tetris.asm

/**
  * @author leopold
  * @since 27/03/16
  */
trait Command

case class ACommand(value: String) extends Command {
  override def toString = s"@$value"
}

case class CCommand(dest: Option[String], comp: String, jump: Option[String]) extends Command {
  override def toString = destToString + compToString + jumpToString

  private def destToString = dest.map(_ + "=").getOrElse("")

  private def compToString = comp

  private def jumpToString = jump.map(";" + _).getOrElse("")
}

case class LCommand(label: String) extends Command {
  override def toString = "(" + label + ")"
}

