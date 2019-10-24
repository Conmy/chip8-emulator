package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class JumpOperation implements Operation {

    private char addressNNN;

    public JumpOperation(char addressNNN) {
        setAddressNNN(addressNNN);
    }

    public void doOperation(Chip8 chip8) {
        chip8.setProgramCounter(getAddressNNN());
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
