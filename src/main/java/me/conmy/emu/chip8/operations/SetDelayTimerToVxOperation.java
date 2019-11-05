package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetDelayTimerToVxOperation implements Operation {

    private byte vxReg;

    public SetDelayTimerToVxOperation(byte vxReg) {
        setVxReg(vxReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte[] registers = chip8.getVDataRegisters();
        byte value = registers[getVxReg()];
        chip8.setDelayTimer(value);

        chip8.incProgramCounter(2);
    }

    public byte getVxReg() {
        return vxReg;
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public String toString() {
        return String.format("SET: DelayTimer =  V[%d]", getVxReg());
    }
}
