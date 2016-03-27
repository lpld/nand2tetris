package com.github.lpld.nand2tetris.asm

/**
  * @author leopold
  * @since 27/03/16
  */
class AsmWorker(source: SourceFile) {

  val parser = new Parser(source)
  val symbolTable = SymbolTable.initNewTable()

  def assemble(): Unit = {
    populateLabels()
    translate()
  }

  // first pass. will iterate and populate symbols table with labels definitions
  private def populateLabels(): Unit = {
    var idx = 0

    // first pass: searching for all label definitions and populating symbols table
    parser.parseCommands
      .map(_.get)
      .foreach {
        case LCommand(label) => symbolTable.addSymbol(label, idx)
        case _ => idx = idx + 1
      }
  }

  private def translate(): Unit = {

    val commands = parser.parseCommands.map(_.get)
    val translated = new Translator(commands, symbolTable).translateCommands

    translated.map(_.get).foreach(println)
  }
}
