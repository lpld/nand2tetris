package com.github.lpld.nand2tetris.asm

import scala.util.Try

/**
  * @author leopold
  * @since 27/03/16
  */
class Translator(commands: Stream[Command], symbolTable: SymbolTable) {

  def translateCommands: Stream[Try[String]] = commands
    .zipWithIndex
    .collect {
      case (a@ACommand(_), _) => Try(translateACommand(a))
      case (c@CCommand(_, _, _), idx) => Try(translateCCommand(c, idx))
      // and ignore LCommand's, because we processed them earlier
    }

  private def translateACommand(aCommand: ACommand): String = {
    val value = aCommand.value
    val address: Int =
      if (value forall Character.isDigit) value.toInt // if this is a real number, use it
      else symbolTable.getOrAddVariable(value) // otherwise treat it as a variable

    String.format("%16s", Integer.toBinaryString(address)).replace(' ', '0')
  }

  private def translateCCommand(cCommand: CCommand, lineNumber: Int): String = {
    // three leading "1"s
    val cmdBuilder = new StringBuilder("111")

    // a-flag and computation flags
    val (compFlags, aFlag) = Constants.aInputs.flatMap { case (input, flag) =>
      val repl = cCommand.comp.replace(input, "Z")
      Constants.compFlags.get(repl).map((_, flag))
    }
      .headOption
      .getOrElse(throw new TranslationException(s"Bad computation on line $lineNumber: $cCommand"))

    cmdBuilder.append(aFlag).append(compFlags)

    // destination flags
    val dest = cCommand.dest.getOrElse("")
    cmdBuilder.append(boolToStr(dest.contains("A")))
    cmdBuilder.append(boolToStr(dest.contains("D")))
    cmdBuilder.append(boolToStr(dest.contains("M")))

    // validate destination:
    if (dest.replaceAll("[AMD]", "") != "") {
      throw new TranslationException(s"Bad destination on line $lineNumber: $cCommand")
    }

    // jump flags
    val jmp = cCommand.jump.getOrElse("")
    val jmpFlags = Constants.jmpFlags.getOrElse(
      jmp,
      throw new TranslationException(s"Bad jump on line $lineNumber: $cCommand")
    )

    cmdBuilder.append(jmpFlags)

    cmdBuilder.toString()
  }

  private def boolToStr(boolean: Boolean) = if (boolean) "1" else "0"
}

class TranslationException(message: String) extends Exception(message)

private object Constants {
  val address = "%015d"

  val aInputs = Stream(
    "A" -> "0",
    "M" -> "1"
  )

  val compFlags = Map(
    "0" -> "101010",
    "1" -> "111111",
    "-1" -> "111010",
    "D" -> "001100",
    "Z" -> "110000",
    "!D" -> "001101",
    "!Z" -> "110001",
    "-D" -> "001111",
    "-Z" -> "110011",
    "D+1" -> "011111",
    "Z+1" -> "110111",
    "D-1" -> "001110",
    "Z-1" -> "110010",
    "D+Z" -> "000010",
    "D-Z" -> "010011",
    "Z-D" -> "000111",
    "D&Z" -> "000000",
    "D|Z" -> "010101"
  )

  val jmpFlags = Map(
    "" -> "000",
    "JGT" -> "001",
    "JEQ" -> "010",
    "JGE" -> "011",
    "JLT" -> "100",
    "JNE" -> "101",
    "JLE" -> "110",
    "JMP" -> "111"
  )
}
