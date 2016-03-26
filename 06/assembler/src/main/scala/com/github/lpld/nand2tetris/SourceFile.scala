package com.github.lpld.nand2tetris

import scala.io.Source

/**
  * @author leopold
  * @since 26/03/16
  */
class SourceFile(fileName: String) {

  def source: Source = Source.fromFile(fileName)

  def lines(): TraversableOnce[String] = source.getLines()
    .map(SourceFile.trimLime.findAllIn(_).matchData) // regex match
    .filter(_.hasNext) // filter only matched lines, this is probably redundant
    .map(_.next().group(1)) // get effective string
    .filterNot(_.isEmpty) // filter out blank and comment lines
}

object SourceFile {

  // regex to trim whitespaces and comments
  val trimLime = "\\s*([\\w@=();+-]*)\\s*(//)*.*".r
}
