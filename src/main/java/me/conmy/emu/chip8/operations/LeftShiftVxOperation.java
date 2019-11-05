package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class LeftShiftVxOperation implements Operation {

    private byte vxReg;

    public LeftShiftVxOperation(byte vxReg) {
        setVxReg(vxReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte[] registers = chip8.getVDataRegisters();
        byte vxValue = registers[getVxReg()];
        byte carry = (byte) ((vxValue & 0x80) >> 7);

        registers[getVxReg()] = (byte) (vxValue << 1);
        registers[0x0f] = carry;

        chip8.incProgramCounter(2);
    }

    public byte getVxReg() {
        return vxReg;
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public String toString() {
        return String.format("SET: V[%d] = V[%d] << 1", getVxReg(), getVxReg());
    }
}
