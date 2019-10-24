package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class VxEqualsOperation implements Operation {

    private byte register;
    private byte value;

    public VxEqualsOperation(byte register, byte value) {
        setRegister(register);
        setValue(value);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte variableValue = chip8.getVDataRegisters()[getRegisterInt()];
        if (variableValue == getValue()) {
            chip8.incProgramCounter(4);
        } else {
            chip8.incProgramCounter(2);
        }

    }

    public byte getRegister() {
        return register;
    }
    public int getRegisterInt() {
        return Byte.toUnsignedInt(register);
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
