package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetVxToVxPlusNNOperation implements Operation {

    private byte vxReg;
    private byte value;

    public SetVxToVxPlusNNOperation(byte vxReg, byte value) {
        setVxReg(vxReg);
        setValue(value);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        int vxValue = chip8.getVDataRegisters()[getVxReg()] & 0x00ff;

        vxValue = vxValue + (value & 0x00ff);
        chip8.getVDataRegisters()[getVxReg()] = (byte) (vxValue & 0x0ff);

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

    public String toString() {
        return String.format("SET: V[%d] = V[%d] + %d", getVxReg(), getVxReg(), Byte.toUnsignedInt(getValue()));
    }
}
