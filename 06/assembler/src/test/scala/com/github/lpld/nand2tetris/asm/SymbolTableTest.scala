package com.github.lpld.nand2tetris.asm

/**
  * @author leopold
  * @since 27/03/16
  */
class SymbolTableTest extends AsmTest {

  test("populate symbol table with label definitions") {
    val source = sourceFile("Fill.asm")

    var i = 0

    new Parser(source).parseCommands
      .map(_.get)
      .foreach {
        case (LCommand(label)) => println(label + " " + i)
        case _ => i = i + 1
      }

  }

}
