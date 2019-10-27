package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class CallSubroutineOperation implements Operation {

    private char address;

    public CallSubroutineOperation(char address) {
        setAddress(address);
    }

    public void doOperation(Chip8 chip8) {
        int pc = chip8.getProgramCounter();
        chip8.getStack().push(new Character((char) pc));
        int newAddress = getAddress();
        chip8.setProgramCounter(newAddress);
    }

    public char getAddress() {
        return address;
    }

    private void setAddress(char address) {
        if (((int) address) > 0xfff ) {
            throw new RuntimeException("Tried to set an address value greater than the max memory size");
        }
        this.address = address;
    }
}
