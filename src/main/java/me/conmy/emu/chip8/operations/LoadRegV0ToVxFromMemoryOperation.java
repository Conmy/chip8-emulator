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
        for (int i=0; i <= vxRegInt; i++) {
            registers[i] = memory[iRegAddressInt + i];
        }

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
}
