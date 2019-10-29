package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class VxNotEqualsNNOperation implements Operation {

    private byte vxRegister;
    private byte value;

    public VxNotEqualsNNOperation(byte register, byte value) {
        setVxRegister(register);
        setValue(value);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte registerValue = chip8.getVDataRegisters()[getVxRegister()];

        if (registerValue != getValue()) {
            chip8.incProgramCounter(4);
        } else {
            chip8.incProgramCounter(2);
        }
    }

    public byte getVxRegister() {
        return vxRegister;
    }

    private void setVxRegister(byte vxRegister) {
        this.vxRegister = vxRegister;
    }

    public byte getValue() {
        return value;
    }

    private void setValue(byte value) {
        this.value = value;
    }
}
