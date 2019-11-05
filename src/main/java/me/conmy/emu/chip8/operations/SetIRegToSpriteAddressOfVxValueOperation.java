package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class SetIRegToSpriteAddressOfVxValueOperation implements Operation {

    private byte vxReg;

    public SetIRegToSpriteAddressOfVxValueOperation(byte vxReg) {
        setVxReg(vxReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte vxValue = chip8.getVDataRegisters()[getVxReg()];
        char addressLoc = Chip8.getSpriteRegAddress(vxValue);

        chip8.setIAddressRegister(addressLoc);

        chip8.incProgramCounter(2);
    }

    public byte getVxReg() {
        return vxReg;
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public String toString() {
        return String.format("SET: I = sprite_address(V[%d])", getVxReg());
    }
}
