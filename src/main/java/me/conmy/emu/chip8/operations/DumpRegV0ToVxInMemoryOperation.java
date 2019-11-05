package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class DumpRegV0ToVxInMemoryOperation implements Operation {

    private byte vxReg;

    public DumpRegV0ToVxInMemoryOperation(byte vxReg) {
        setVxReg(vxReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        int iRegAddress = chip8.getIAddressRegister() & 0x0ffff;
        byte[] memory = chip8.getMemory();
        byte[] registers = chip8.getVDataRegisters();

        for (int i=0; i <= getVxRegAsInt(); i++) {
            memory[iRegAddress + i] = registers[i];
        }

        chip8.incProgramCounter(2);
    }

    public byte getVxReg() {
        return vxReg;
    }

    private int getVxRegAsInt() {
        return Byte.toUnsignedInt(getVxReg());
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public String toString() {
        return String.format("LOAD: Mem(I[0-%d]) = V[0-%d]", getVxReg(), getVxReg());
    }
}
