package me.conmy.emu.chip8;

import me.conmy.emu.chip8.operations.Operation;
import me.conmy.emu.chip8.operations.OperationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Chip8 {

    private static final int MEMORY_SIZE = 4096;
    private static final int DATA_REGISTER_SIZE = 16;
    private static final int STACK_SIZE = 48;
    private static final int PROGRAM_COUNTER_START_LOCATION = 512;

    public static final int SPRITE_FONT_START_LOCATION = 0;
    public static final byte[] SPRITE_FONT_VALUES = {
            (byte) 0xF0, (byte) 0x90, (byte) 0x90, (byte) 0x90, (byte) 0xF0, // 0
            (byte) 0x20, (byte) 0x60, (byte) 0x20, (byte) 0x20, (byte) 0x70, // 1
            (byte) 0xF0, (byte) 0x10, (byte) 0xF0, (byte) 0x80, (byte) 0xF0, // 2
            (byte) 0xF0, (byte) 0x10, (byte) 0xF0, (byte) 0x10, (byte) 0xF0, // 3
            (byte) 0x90, (byte) 0x90, (byte) 0xF0, (byte) 0x10, (byte) 0x10, // 4
            (byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x10, (byte) 0xF0, // 5
            (byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x90, (byte) 0xF0, // 6
            (byte) 0xF0, (byte) 0x10, (byte) 0x20, (byte) 0x40, (byte) 0x40, // 7
            (byte) 0xF0, (byte) 0x90, (byte) 0xF0, (byte) 0x90, (byte) 0xF0, // 8
            (byte) 0xF0, (byte) 0x90, (byte) 0xF0, (byte) 0x10, (byte) 0xF0, // 9
            (byte) 0xF0, (byte) 0x90, (byte) 0xF0, (byte) 0x90, (byte) 0x90, // A
            (byte) 0xE0, (byte) 0x90, (byte) 0xE0, (byte) 0x90, (byte) 0xE0, // B
            (byte) 0xF0, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0xF0, // C
            (byte) 0xE0, (byte) 0x90, (byte) 0x90, (byte) 0x90, (byte) 0xE0, // D
            (byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x80, (byte) 0xF0, // E
            (byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x80, (byte) 0x80  // F
    };

    private byte[] memory;
    private int programCounter;

    private byte[] VDataRegisters;

    private char IAddressRegister;
    private Stack<Character> stack;

    private byte delayTimer;
    private byte soundTimer;
    private Chip8Display screenDisplay;
    private boolean debug;

    private List<Byte> pressedKeys;
    private int timerTickRemainder;
    private boolean applicationLoaded;

    public Chip8() {
        memory = new byte[MEMORY_SIZE];
        loadFontInToMemory();
        programCounter = PROGRAM_COUNTER_START_LOCATION;
        VDataRegisters = new byte[DATA_REGISTER_SIZE];
        IAddressRegister = 0x0;
        stack = new Stack<>();
        stack.ensureCapacity(STACK_SIZE);
        delayTimer = 0;
        soundTimer = 0;
        screenDisplay = new Chip8Display();
        pressedKeys = new ArrayList<Byte>();
        debug = false;
        timerTickRemainder = 0;
    }

    public void loadApplication(byte[] applicationCode) {
        if (applicationCode.length > (MEMORY_SIZE - PROGRAM_COUNTER_START_LOCATION)) {
            throw new RuntimeException("Application Code is too large for Chip8 to store");
        }
        int offsetStart = getProgramCounter();
        for (int i=0; i < applicationCode.length; i++) {
            getMemory()[offsetStart + i] = applicationCode[i];
        }
        applicationLoaded = true;
    }

    public void emulateChipCycle(long elapsedTime) {
        char opCode = getCurrentOpCode();
        executeOpCode(opCode);
        updateTimers(elapsedTime);
    }

    public char getCurrentOpCode() {
        int counter = getProgramCounter();
        byte memory0 = getMemory()[counter];
        byte memory1 = getMemory()[counter+1];
        char opCode = (char) (memory0 << 8);
        opCode = (char) (opCode | (memory1 & 0x00ff));
        return opCode;
    }

    public void executeOpCode(char opCode) {
        Operation op = OperationFactory.decodeOpCodeToOperation(opCode);
        if (isDebug()) {
            System.out.println(String.format("PC@%d:\tOpCode %04X\t%s", getProgramCounter(), (opCode & 0x0ffff), op.toString()));
        }
        op.doOperation(this);
    }

    public void updateTimers(long timeElapsed) {

        int ticksElapsed = (int) ((timeElapsed+timerTickRemainder) / 16);
        timerTickRemainder = (int) ((timeElapsed+timerTickRemainder) % 16);
        updateDelayTimer(ticksElapsed);
        updateSoundTimer(ticksElapsed);
    }

    public void updateDelayTimer(int ticksElapsed) {
        byte delayTimerValue = getDelayTimer();
        int newDelayTimerValue = Byte.toUnsignedInt(delayTimerValue) - ticksElapsed;
        if (newDelayTimerValue <= 0) {
            setDelayTimer((byte) 0x00);
        } else {
            setDelayTimer((byte) (newDelayTimerValue & 0x0ff));
        }
    }

    public void updateSoundTimer(int ticksElapsed) {
        byte soundTimerValue = getSoundTimer();
        int newSoundTimerValue = Byte.toUnsignedInt(soundTimerValue) - ticksElapsed;
        if (newSoundTimerValue <= 0) {
            setSoundTimer((byte) 0x00);
        } else {
            setSoundTimer((byte) (newSoundTimerValue & 0x0ff));
        }
    }

    public void resetChip8() {
        setProgramCounter(PROGRAM_COUNTER_START_LOCATION);
        setMemory(new byte[MEMORY_SIZE]);
        loadFontInToMemory();

        setDelayTimer((byte) 0x00);
        setSoundTimer((byte) 0x00);

        for (int i=0; i<getVDataRegisters().length; i++) {
            getVDataRegisters()[i] = (byte) 0x00;
        }
        setIAddressRegister((char) 0x000);

        getStack().clear();
        setTimerTickRemainder(0);
        getScreenDisplay().clearScreen();
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
    public void incProgramCounter(int value) {
        programCounter += value;
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

    public Stack<Character> getStack() {
        return stack;
    }

    private void setStack(Stack<Character> stack) {
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

    public static char getSpriteRegAddress(byte nibble) {
        return (char) ((SPRITE_FONT_START_LOCATION + (5 * Byte.toUnsignedInt(nibble))) & 0x0ffff);
    }

    private void loadFontInToMemory() {
        for (int i=0; i < SPRITE_FONT_VALUES.length; i++) {
            getMemory()[SPRITE_FONT_START_LOCATION + i] = SPRITE_FONT_VALUES[i];
        }
    }

    public Chip8Display getScreenDisplay() {
        return screenDisplay;
    }

    public void setKeyPressed(byte keyValue) {
        if (keyValue > 0x0f) {
            throw new IllegalArgumentException(String.format("Illegal KeyPress: Tried to set a key press of 0x%02X", keyValue));
        }
        if (! this.pressedKeys.contains(keyValue)) {
            pressedKeys.add(keyValue);
        }
    }

    public void clearPressedKeys() {
        this.pressedKeys.clear();
    }

    public List<Byte> getPressedKeys() {
        return this.pressedKeys;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getTimerTickRemainder() {
        return timerTickRemainder;
    }

    public void setTimerTickRemainder(int timerTickRemainder) {
        this.timerTickRemainder = timerTickRemainder;
    }

    public boolean isApplicationLoaded() {
        return applicationLoaded;
    }

    public void setApplicationLoaded(boolean applicationLoaded) {
        this.applicationLoaded = applicationLoaded;
    }
}
