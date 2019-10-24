package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class CallSubroutineOperation implements Operation {

    private char addressNNN;

    public CallSubroutineOperation(char addressNNN) {
        setAddressNNN(addressNNN);
    }

    public void doOperation(Chip8 chip8) {
        int pc = chip8.getProgramCounter();
        chip8.getStack().push(new Character((char) pc));
        int newAddress = getAddressNNN();
        chip8.setProgramCounter(newAddress);
    }

    public char getAddressNNN() {
        return addressNNN;
    }

    private void setAddressNNN(char addressNNN) {
        if (((int) addressNNN) > 0xfff ) {
            throw new RuntimeException("Tried to set an address value greater than the max memory size");
        }
        this.addressNNN = addressNNN;
    }
}
