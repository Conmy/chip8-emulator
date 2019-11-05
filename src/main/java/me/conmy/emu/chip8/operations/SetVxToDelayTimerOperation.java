package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetVxToDelayTimerOperation implements Operation {

    private byte vxReg;

    public SetVxToDelayTimerOperation(byte vxReg) {
        setVxReg(vxReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte[] registers = chip8.getVDataRegisters();
        byte delayValue = chip8.getDelayTimer();

        registers[getVxReg()] = delayValue;

        chip8.incProgramCounter(2);
    }

    public byte getVxReg() {
        return vxReg;
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public String toString() {
        return String.format("SET: V[%d] = DelayTimer()", getVxReg());
    }
}
