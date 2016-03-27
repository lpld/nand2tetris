package com.github.lpld.nand2tetris.asm

import java.nio.file.{Files, Paths, StandardOpenOption}

import scala.collection.JavaConversions._

/**
  * @author leopold
  * @since 27/03/16
  */
trait HackDest {
  def write(lines: Stream[String])

}

object HackDest {
  def forFile(fileName: String): HackDest = new HackDest {

    override def write(lines: Stream[String]): Unit =
      Files.write(Paths.get(fileName), lines, StandardOpenOption.WRITE, StandardOpenOption.CREATE)


  }
}
