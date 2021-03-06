package com.github.lpld.nand2tetris.asm

/**
  * args[0] - file path
  *
  * @author leopold
  * @since 26/03/16
  */
object Assembler extends App {

  val fileNameNoExt = args.headOption
    .map(stripExtension)
    .getOrElse(throw new IllegalArgumentException("No filename provided"))

  val sourceFileName = fileNameNoExt + ".asm"
  val destFileName = fileNameNoExt + ".hack"

  val source = HackSource.forFile(sourceFileName)
  val dest = HackDest.forFile(destFileName)

  new AsmWorker(source, dest).assemble()

  def stripExtension(fileName: String) =
    if (fileName.endsWith(".asm")) fileName.dropRight(4)
    else fileName
}
