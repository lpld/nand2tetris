// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.

// last screen register
@8191
D=A
@SCREEN
D=D+A
@last
M=D

// main loop
(MAIN)

    @fill
    M=0 // reset the "fill"

    @SCREEN
    D=A
    @i
    M=D // set i to beginnig of the screen memory area

    @KBD
    D=M // read keyboard

    @FILL
    D;JEQ // if nothing pressed, goto fill (with fill=0)

    @fill
    M=-1 // if any key was pressed, set fill to -1 (all ones)

    (FILL) // fill loop

        // termination check
        @i
        D=M
        @last
        D=M-D // D = last - i
        @ENDLOOP // if i > last, goto ENDLOOP (if last - i < 0)
        D;JLT

        @fill // 0(white) or -1 (black)
        D=M

        @i
        A=M // move pointer to i
        M=D // apply the 'fill'

        @i // inc i
        M=M+1

        @FILL // repeat
        0;JMP

    (ENDLOOP)

@MAIN
0;JMP
