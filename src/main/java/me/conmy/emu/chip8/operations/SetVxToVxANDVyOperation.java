package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetVxToVxANDVyOperation implements Operation {

    private byte vxReg;
    private byte vyReg;

    public SetVxToVxANDVyOperation(byte vxReg, byte vyReg) {
        setVxReg(vxReg);
        setVyReg(vyReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte[] registers = chip8.getVDataRegisters();
        byte vxValue = registers[getVxReg()];
        byte vyValue = registers[getVyReg()];

        registers[getVxReg()] = (byte) (vyValue & vyValue);
    }

    public byte getVxReg() {
        return vxReg;
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public byte getVyReg() {
        return vyReg;
    }

    private void setVyReg(byte vyReg) {
        this.vyReg = vyReg;
    }
}
