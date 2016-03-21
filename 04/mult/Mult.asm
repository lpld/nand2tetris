// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

@R1
D=M
@i
M=D // i = R1

@R2
M=0 // R2 (sum) = 0

(LOOP)

    @i
    D=M
    @END
    D;JLE // if i <= 0, goto END

    @R0
    D=M // read R0

    @R2
    M=D+M // R2(sum) = sum + R0

    @i
    M=M-1 // i = i - 1

    @LOOP
    0;JMP // repeat: goto LOOP

(END)
    @END
    0;JMP
