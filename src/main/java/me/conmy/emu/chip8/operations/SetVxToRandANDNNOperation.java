package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetVxToRandANDNNOperation implements Operation {

    private byte vxReg;
    private byte maskNumber;

    public SetVxToRandANDNNOperation(byte vxReg, byte maskNumber) {
        setVxReg(vxReg);
        setMaskNumber(maskNumber);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        int randomValue = (int) (Math.random() * 255 + 1);
        byte[] registers = chip8.getVDataRegisters();

        byte newValue = (byte) (getMaskNumber() & (byte) (randomValue & 0x0ff));

        registers[getVxReg()] = newValue;

        chip8.incProgramCounter(2);

    }

    public byte getVxReg() {
        return vxReg;
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public byte getMaskNumber() {
        return maskNumber;
    }

    private void setMaskNumber(byte maskNumber) {
        this.maskNumber = maskNumber;
    }

    public String toString() {
        return String.format("SET: V[%d] = rand(%02X)", getVxReg(), Byte.toUnsignedInt(getMaskNumber()));
    }
}
