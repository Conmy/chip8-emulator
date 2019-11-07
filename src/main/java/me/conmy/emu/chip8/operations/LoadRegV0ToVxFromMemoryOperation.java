package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class LoadRegV0ToVxFromMemoryOperation implements Operation {

    private byte vxReg;

    public LoadRegV0ToVxFromMemoryOperation(byte vxReg) {
        setVxReg(vxReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        byte[] memory = chip8.getMemory();
        byte[] registers = chip8.getVDataRegisters();

        char iRegAddress = chip8.getIAddressRegister();
        int iRegAddressInt = iRegAddress & 0x0ffff;

        int vxRegInt = getVxRegInt();
        if (vxRegInt + 1 >= 0) System.arraycopy(memory, iRegAddressInt, registers, 0, vxRegInt + 1);

        chip8.incProgramCounter(2);
    }

    public byte getVxReg() {
        return vxReg;
    }

    private int getVxRegInt() {
        return Byte.toUnsignedInt(getVxReg());
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public String toString() {
        return String.format("LOAD: V[0-%d] = Mem(I[0-%d])", getVxReg(), getVxReg());
    }
}
