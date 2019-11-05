package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class JumpToV0PlusNNNOperation implements Operation {

    private char address;

    public JumpToV0PlusNNNOperation(char address) {
        setAddress(address);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte[] registers = chip8.getVDataRegisters();
        int v0Value = Byte.toUnsignedInt(registers[0x00]);
        int nnnAddressValue = getAddress() & 0x0fff;

        int newLocation = v0Value + nnnAddressValue;

        if (newLocation < chip8.getMemory().length) {
            chip8.setProgramCounter(newLocation);
        } else {
            throw new RuntimeException("Adding NNN to program counter exceeds max memory allocation");
        }
    }

    public char getAddress() {
        return address;
    }

    private void setAddress(char address) {
        this.address = address;
    }

    public String toString() {
        return String.format("JUMP: PC = V[0] + %d", (getAddress() & 0x0ffff));
    }
}
