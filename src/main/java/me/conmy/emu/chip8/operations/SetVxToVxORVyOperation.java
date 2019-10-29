package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetVxToVxORVyOperation implements Operation {

    private byte vxReg;
    private byte vyReg;

    public SetVxToVxORVyOperation(byte vxReg, byte vyReg) {
        setVxReg(vxReg);
        setVyReg(vyReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte[] registers = chip8.getVDataRegisters();
        byte vxValue = registers[getVxReg()];
        byte vyValue = registers[getVyReg()];
        registers[getVxReg()] = (byte) (( vxValue | vyValue) & 0x0ff);

        chip8.incProgramCounter(2);
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
