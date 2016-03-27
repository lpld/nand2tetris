package com.github.lpld.nand2tetris.asm

import org.scalatest.{FunSuite, Matchers}

import scala.io.Source

/**
  * @author leopold
  * @since 27/03/16
  */
trait AsmTest extends FunSuite with Matchers {

  def sourceFile(name: String): HackSource = new HackSource {
    override def rawLines = Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(name)).getLines()
  }

}
