package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetIRegToIPlusVxOperation implements Operation {

    private byte vxReg;

    public SetIRegToIPlusVxOperation(byte vxReg) {
        setVxReg(vxReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        char iAddressValue = chip8.getIAddressRegister();
        byte vxValue = chip8.getVDataRegisters()[getVxReg()];
        int sum = (iAddressValue & 0xffff) + (vxValue & 0xff);

        chip8.setIAddressRegister((char) (sum & 0x0ffff));
        chip8.incProgramCounter(2);
    }

    public byte getVxReg() {
        return vxReg;
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public String toString() {
        return String.format("SET: I = I + V[%d]", getVxReg());
    }
}
