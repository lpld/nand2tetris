package com.github.lpld.nand2tetris.asm

/**
  * args[0] - file path
  *
  * @author leopold
  * @since 26/03/16
  */
object Assembler extends App {

  val source = args.headOption match {
    case Some(fileName) => SourceFile.fromFile(fileName)
    case None => throw new IllegalArgumentException("No filename provided")
  }


}
