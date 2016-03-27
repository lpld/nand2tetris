package com.github.lpld.nand2tetris.asm

import scala.io.Source

/**
  * @author leopold
  * @since 26/03/16
  */
trait SourceFile {

  protected def rawLines: Iterator[String]

  def lines(): Stream[String] = rawLines
    .map(SourceFile.whitespaces.replaceAllIn(_, "")) // remove all whitespaces
    .map(l => SourceFile.trimLime.findAllIn(l).matchData) // regex match
    .filter(_.hasNext) // filter only matched lines, this is probably redundant
    .map(_.next().group(1)) // get effective string
    .filterNot(_.isEmpty) // filter out blank and comment lines
    .toStream
}

object SourceFile {

  // regex to trim whitespaces and comments
  val whitespaces = "\\s".r
  val trimLime = "([\\w@=();+-]*)(//.*)?".r

  def fromFile(fileName: String) = new SourceFile {
    override def rawLines = Source.fromFile(fileName).getLines()
  }
}
