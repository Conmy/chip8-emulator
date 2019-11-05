package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;

public class GetKeyPressOperation implements Operation {

    private byte vxReg;

    public GetKeyPressOperation(byte vxReg) {
        setVxReg(vxReg);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        if (chip8.getPressedKeys().size() > 0) {
            byte pressedValue = chip8.getPressedKeys().get(0);
            byte[] registers = chip8.getVDataRegisters();
            registers[getVxReg()] = pressedValue;

            chip8.incProgramCounter(2);
        }
    }

    public byte getVxReg() {
        return vxReg;
    }

    private void setVxReg(byte vxReg) {
        this.vxReg = vxReg;
    }

    public String toString() {
        return String.format("SET: V[%d] = KeyPressed()", getVxReg());
    }
}
