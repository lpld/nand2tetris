package com.github.lpld.nand2tetris.asm

import scala.util.Failure

/**
  * @author leopold
  * @since 27/03/16
  */
class ParserTest extends AsmTest {

  test("correctly parse input file") {

    val source = sourceFile("Fill.asm")

    // run parser
    val parsedLines = new Parser(source).parseCommands

    // get original lines from file
    val origLines = source.lines()

    // compare
    parsedLines.zip(origLines)
      .foreach { case (cmd, line) =>
        cmd.isSuccess shouldBe true
        cmd.get.toString should equal(line)
        println(s"MATCH: $line")
      }
  }

  test("fail on bad input") {
    val source = sourceFile("Fill_bad.asm")
    val parseResult = new Parser(source).parseCommands

    val badLine = parseResult.find(_.isFailure)

    badLine.isDefined shouldBe true
    badLine.get match {
      case Failure(ex) =>
        ex shouldBe a[ParserException]
        ex.getMessage should include("(FILL)(blabla)")
    }
  }


}
