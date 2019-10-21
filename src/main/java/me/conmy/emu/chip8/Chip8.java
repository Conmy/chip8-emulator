package me.conmy.emu.chip8;

public class Chip8 {

    private static final int MEMORY_SIZE = 4096;
    private static final int DATA_REGISTER_SIZE = 16;
    private static final int STACK_SIZE = 48;
    private static final int PROGRAM_COUNTER_START_LOCATION = 512;

    private byte[] memory;
    private int programCounter;

    private byte[] VDataRegisters;

    private char IAddressRegister;
    private byte[] stack;

    private byte delayTimer;
    private byte soundTimer;

    public Chip8() {
        memory = new byte[MEMORY_SIZE];
        programCounter = PROGRAM_COUNTER_START_LOCATION;
        VDataRegisters = new byte[DATA_REGISTER_SIZE];
        IAddressRegister = 0x0;
        stack = new byte[STACK_SIZE];
        delayTimer = 0;
        soundTimer = 0;
    }

    public void loadApplication(byte[] applicationCode) {
        if (applicationCode.length > (MEMORY_SIZE - PROGRAM_COUNTER_START_LOCATION)) {
            throw new RuntimeException("Application Code is too large for Chip8 to store");
        }
        int offsetStart = getProgramCounter();
        for (int i=0; i < applicationCode.length; i++) {
            getMemory()[offsetStart + i] = applicationCode[i];
        }
    }

    public byte[] getMemory() {
        return memory;
    }

    public void setMemory(byte[] memory) {
        this.memory = memory;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public byte[] getVDataRegisters() {
        return VDataRegisters;
    }

    public void setVDataRegisters(byte[] VDataRegisters) {
        this.VDataRegisters = VDataRegisters;
    }

    public char getIAddressRegister() {
        return IAddressRegister;
    }

    public void setIAddressRegister(char IAddressRegister) {
        this.IAddressRegister = IAddressRegister;
    }

    public byte[] getStack() {
        return stack;
    }

    public void setStack(byte[] stack) {
        this.stack = stack;
    }

    public byte getDelayTimer() {
        return delayTimer;
    }

    public void setDelayTimer(byte delayTimer) {
        this.delayTimer = delayTimer;
    }

    public byte getSoundTimer() {
        return soundTimer;
    }

    public void setSoundTimer(byte soundTimer) {
        this.soundTimer = soundTimer;
    }
}
