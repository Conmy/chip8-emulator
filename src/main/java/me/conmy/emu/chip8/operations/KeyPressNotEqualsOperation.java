package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class KeyPressNotEqualsOperation implements Operation {

    private byte vxReg;

    public KeyPressNotEqualsOperation(byte vxReg) {
        setVxReg(vxReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte vxRegValue = chip8.getVDataRegisters()[getVxReg()];

        if (! chip8.getPressedKeys().contains(vxRegValue)) {
            chip8.incProgramCounter(4);
        } else {
            chip8.incProgramCounter(2);
        }
    }

    public byte getVxReg() {
        return vxReg;
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public String toString() {
        return String.format("IF: key() != V[%d]", getVxReg());
    }
}
