package com.github.lpld.nand2tetris

import scala.io.Source

/**
  * args[0] - file path
  *
  * @author leopold
  * @since 26/03/16
  */
object Assembler extends App {

  //  val source = args.headOption match {
  //    case Some(fileName) => new SourceFile(fileName)
  //    case None => throw new IllegalArgumentException("No filename provided")
  //  }

  val parser = new Parser

  new SourceFile("123") {
    override def source: Source =
      Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("Fill.asm"))
  }
    .lines()
    .foreach(line => {
      val c = parser.parseCommand(line)
      println(s"$line : ${c.get}")
    })
}
