package com.github.lpld.nand2tetris.asm

import scala.io.Source

/**
  * @author leopold
  * @since 26/03/16
  */
trait HackSource {

  protected def rawLines: Iterator[String]

  def lines(): Stream[String] = rawLines
    .map(HackSource.whitespaces.replaceAllIn(_, "")) // remove all whitespaces
    .map(l => HackSource.trimLime.findAllIn(l).matchData) // regex match
//    .filter(_.hasNext) // filter only matched lines, this is probably redundant
    .map(_.next().group(1)) // get effective string
    .filterNot(_.isEmpty) // filter out blank and comment lines
    .toStream
}

object HackSource {

  // regex to trim whitespaces and comments
  val whitespaces = "\\s".r
  val trimLime = "([\\w\\@\\=\\(\\)\\;\\+\\-\\.\\|\\&\\!\\$]*)(//.*)?".r

  def forFile(fileName: String) = new HackSource {
    override def rawLines = Source.fromFile(fileName).getLines()
  }
}
