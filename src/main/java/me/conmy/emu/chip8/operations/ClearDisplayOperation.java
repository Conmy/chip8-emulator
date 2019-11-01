package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import me.conmy.emu.chip8.Chip8Display;

public class ClearDisplayOperation implements Operation {

    public ClearDisplayOperation() {
    }

    @Override
    public void doOperation(Chip8 chip8) {
        Chip8Display display = chip8.getScreenDisplay();
        display.clearScreen();

        chip8.incProgramCounter(2);
    }
}
