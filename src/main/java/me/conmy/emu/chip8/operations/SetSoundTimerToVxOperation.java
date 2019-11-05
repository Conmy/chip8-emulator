package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetSoundTimerToVxOperation implements Operation {

    private byte vxReg;

    public SetSoundTimerToVxOperation(byte vxReg) {
        setVxReg(vxReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte value = chip8.getVDataRegisters()[getVxReg()];
        chip8.setSoundTimer(value);

        chip8.incProgramCounter(2);
    }

    public byte getVxReg() {
        return vxReg;
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public String toString() {
        return String.format("SET: SoundTimer = V[%d]", getVxReg());
    }
}
