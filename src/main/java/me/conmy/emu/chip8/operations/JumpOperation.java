package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class JumpOperation implements Operation {

    private char address;

    public JumpOperation(char address) {
        setAddress(address);
    }

    public void doOperation(Chip8 chip8) {
        chip8.setProgramCounter(getAddress());
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

    public String toString() {
        return String.format("JUMP: PC = %d", (getAddress() & 0x0ffff));
    }
}
