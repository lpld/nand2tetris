#!/bin/sh

if [ $# -ne 1 ]
    then echo "Bad number or arguments, expected: 1"
    exit 1
fi

scala -cp target/scala*/assembler*.jar com.github.lpld.nand2tetris.asm.Assembler $1
