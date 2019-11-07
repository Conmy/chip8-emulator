package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import me.conmy.emu.chip8.Chip8Display;

public class DrawSpriteOperation implements Operation {

    private byte vxReg;
    private byte vyReg;
    private int height;

    public DrawSpriteOperation(byte vxReg, byte vyReg, int height) {
        setVxReg(vxReg);
        setVyReg(vyReg);
        setHeight(height);
    }

    @Override
    public void doOperation(Chip8 chip8) {
        char iAddressLoc = chip8.getIAddressRegister();
        int addressLocationInt = (iAddressLoc & 0x0ffff);
        byte[] memory = chip8.getMemory();
        Chip8Display display = chip8.getScreenDisplay();
        byte[] registers = chip8.getVDataRegisters();
        int x = Byte.toUnsignedInt(registers[getVxReg()]);
        int y = Byte.toUnsignedInt(registers[getVyReg()]);

        byte[] sprite = new byte[getHeight()];
        if (getHeight() >= 0) System.arraycopy(memory, addressLocationInt + 0, sprite, 0, getHeight());

        // Needs to be cleared before a draw transaction to detect a collision.
        display.clearCollisionDetected();
        display.writeSprite(x, y, sprite);

        if (display.isCollisionDetected()) {
            registers[0x0f] = 0x01;
        } else {
            registers[0x0f] = 0x00;
        }
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

    public int getHeight() {
        return height;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    public String toString() {
        return String.format("DRAW: Display(V[%d], V[%d]) = Sprite(I[0-%d])", getVxReg(), getVyReg(), getHeight());
    }
}
