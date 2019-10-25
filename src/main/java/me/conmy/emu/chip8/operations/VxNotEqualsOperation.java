package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class VxNotEqualsOperation implements Operation {

    private byte register;
    private byte value;

    public VxNotEqualsOperation(byte register, byte value) {
        setRegister(register);
        setValue(value);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte registerValue = chip8.getVDataRegisters()[getRegister()];

        if (registerValue != getValue()) {
            chip8.incProgramCounter(4);
        } else {
            chip8.incProgramCounter(2);
        }
    }

    public byte getRegister() {
        return register;
    }

    private void setRegister(byte register) {
        this.register = register;
    }

    public byte getValue() {
        return value;
    }

    private void setValue(byte value) {
        this.value = value;
    }
}
