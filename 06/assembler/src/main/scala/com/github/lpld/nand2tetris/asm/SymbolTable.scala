package com.github.lpld.nand2tetris.asm

import scala.collection.mutable


/**
  * @author leopold
  * @since 27/03/16
  */
class SymbolTable {
  val symbols = new mutable.HashMap[String, Int]
  var availableAddress = 0

  def addSymbol(symbol: String, value: Int): Unit = {
    if (symbols contains symbol) throw new IllegalArgumentException
    else symbols.put(symbol, value)
  }

  def getOrAddVariable(symbol: String): Int = {
    symbols.getOrElseUpdate(symbol, nextAvailableAddress)
  }

  def getSymbol(symbol: String): Option[Int] = symbols get symbol

  private def nextAvailableAddress: Int = {
    val addr = availableAddress
    availableAddress = availableAddress + 1
    addr
  }
}

object SymbolTable {
  /**
    * Initialize new symbols table with default Hack symbols
    */
  def initNewTable(): SymbolTable = {
    val table = new SymbolTable
    table.symbols ++= Seq(
      "SP" -> 0,
      "LCL" -> 1,
      "ARG" -> 2,
      "THIS" -> 3,
      "THAT" -> 4,
      "R0" -> 0,
      "R1" -> 1,
      "R2" -> 2,
      "R3" -> 3,
      "R4" -> 4,
      "R5" -> 5,
      "R6" -> 6,
      "R7" -> 7,
      "R8" -> 8,
      "R9" -> 9,
      "R10" -> 10,
      "R11" -> 11,
      "R12" -> 12,
      "R13" -> 13,
      "R14" -> 14,
      "R15" -> 15,
      "SCREEN" -> 16384,
      "KBD" -> 24576
    )
    table.availableAddress = 16
    table
  }
}
