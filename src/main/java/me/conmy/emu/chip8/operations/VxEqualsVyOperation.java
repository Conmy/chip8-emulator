package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class VxEqualsVyOperation implements Operation {

    private byte vxRegister;
    private byte vyRegister;

    public VxEqualsVyOperation(byte vxReg, byte vyReg) {
        setVxRegister(vxReg);
        setVyRegister(vyReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte[] registers = chip8.getVDataRegisters();
        byte vxValue = registers[getVxRegister()];
        byte vyValue = registers[getVyRegister()];

        if (vxValue == vyValue) {
            chip8.incProgramCounter(4);
        } else {
            chip8.incProgramCounter(2);
        }
    }

    public byte getVxRegister() {
        return vxRegister;
    }

    private void setVxRegister(byte vxReg) {
        this.vxRegister = vxReg;
    }

    public byte getVyRegister() {
        return vyRegister;
    }

    private void setVyRegister(byte vyReg) {
        this.vyRegister = vyReg;
    }
}
