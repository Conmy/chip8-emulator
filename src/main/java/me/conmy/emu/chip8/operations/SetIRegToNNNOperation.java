package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetIRegToNNNOperation implements Operation {

    private char address;

    public SetIRegToNNNOperation(char address) {
        setAddress(address);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        chip8.setIAddressRegister(getAddress());
        chip8.incProgramCounter(2);
    }

    public char getAddress() {
        return address;
    }

    private void setAddress(char address) {
        this.address = address;
    }

    public String toString() {
        return String.format("SET: I = %04X", (getAddress() & 0x0ffff));
    }
}
