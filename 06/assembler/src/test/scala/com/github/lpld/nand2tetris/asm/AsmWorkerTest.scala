package com.github.lpld.nand2tetris.asm

/**
  * @author leopold
  * @since 27/03/16
  */
class AsmWorkerTest extends AsmTest {

  test("assemble") {
    new AsmWorker(sourceFile("Fill.asm")).assemble()
  }


}
