package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetVxToVxMinusVyOperation implements Operation {

    private byte vxReg;
    private byte vyReg;

    public SetVxToVxMinusVyOperation(byte vxReg, byte vyReg) {
        setVxReg(vxReg);
        setVyReg(vyReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte[] registers = chip8.getVDataRegisters();
        int vxValue = Byte.toUnsignedInt(registers[getVxReg()]);
        int vyValue = Byte.toUnsignedInt(registers[getVyReg()]);

        if (vxValue > vyValue) {
            registers[0x0f] = 0x01;
        } else {
            registers[0x0f] = 0x00;
        }

        int result = vxValue - vyValue;
        registers[getVxReg()] = (byte) (result & 0x0ff);

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

    public String toString() {
        return String.format("SET: V[%d] = V[%d] - V[%d]", getVxReg(), getVxReg(), getVyReg());
    }
}
