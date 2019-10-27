package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetVxToNNOperation implements Operation {

    private byte vxReg;
    private byte value;

    public SetVxToNNOperation(byte vxReg, byte value) {
        setVxReg(vxReg);
        setValue(value);
    }

    public void doOperation(Chip8 chip8) {
        chip8.getVDataRegisters()[vxReg] = value;
        chip8.incProgramCounter(2);
    }

    public byte getVxReg() {
        return vxReg;
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public byte getValue() {
        return value;
    }

    private void setValue(byte value) {
        this.value = value;
    }
}
