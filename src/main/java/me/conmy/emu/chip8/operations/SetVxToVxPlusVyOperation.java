package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetVxToVxPlusVyOperation implements Operation {

    private byte vxReg;
    private byte vyReg;

    public SetVxToVxPlusVyOperation(byte vxReg, byte vyReg) {
        this.vxReg = vxReg;
        this.vyReg = vyReg;
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte[] registers = chip8.getVDataRegisters();
        int vxValue = Byte.toUnsignedInt(registers[getVxReg()]);
        int vyValue = Byte.toUnsignedInt(registers[getVyReg()]);

        int result = vxValue + vyValue;
        // Set carry flag if there is a carry.
        if (result > 0x0ff) {
            registers[0x0f] = 0x01;
        } else {
            registers[0x0f] = 0x00;
        }
        registers[getVxReg()] = (byte) (result & 0x0ff);

        chip8.incProgramCounter(2);
    }

    public byte getVxReg() {
        return vxReg;
    }

    public byte getVyReg() {
        return vyReg;
    }

    public String toString() {
        return String.format("SET: V[%d] = V[%d] + V[%d]", getVxReg(), getVxReg(), getVyReg());
    }
}
