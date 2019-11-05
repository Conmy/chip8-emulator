package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

import java.util.Stack;

public class ReturnFromSubroutineOperation implements Operation {

    public ReturnFromSubroutineOperation() {
    }

    @Override
    public void doOperation(Chip8 chip8) {
        Stack<Character> stack = chip8.getStack();
        char topStackAddress = stack.pop();
        int topStackAddressInt = topStackAddress & 0x0ffff;

        chip8.setProgramCounter(topStackAddressInt);
    }

    public String toString() {
        return "RETURN";
    }
}
