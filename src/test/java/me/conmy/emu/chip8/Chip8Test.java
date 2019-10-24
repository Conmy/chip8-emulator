package me.conmy.emu.chip8;

import me.conmy.emu.chip8.operations.OperationFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;


public class Chip8Test {

    private Chip8 chip8;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUpChip8() {
        chip8 = new Chip8();
    }

    @Test
    public void chip8IsInitializedWhenConstructorIsCalled() {

        Assert.assertEquals("Memory not initialized correctly", 4096, chip8.getMemory().length);
        Assert.assertEquals("Program counter not set correctly", 512, chip8.getProgramCounter());
        Assert.assertEquals("Stack size not intialized correctly", 48, chip8.getStack().length);
        Assert.assertEquals("Data Register size not initialized correctly", 16, chip8.getVDataRegisters().length);
        Assert.assertEquals("Delay Timer is not 0 after initialization", 0, chip8.getDelayTimer());
        Assert.assertEquals("Sound Timer is not 0 after initialization", 0, chip8.getSoundTimer());
    }

    @Test
    public void loadApplicationPlacesHexValuesInTheCorrectLocations() {

        byte[] applicationCodes = new byte[] { 0x42, 0x0a, 0x1b, 0x0f, 0x11 };
        chip8.loadApplication(applicationCodes);
        int offset = chip8.getProgramCounter();
        byte[] memory = chip8.getMemory();
        for (int i=0; i < applicationCodes.length; i++) {
            Assert.assertEquals("Unexpected value at memory location:" + (offset+i) , applicationCodes[i], memory[offset + i]);
        }
    }

    @Test
    public void loadApplicationThrowsRuntimeExceptionIfApplicationIsTooLarge() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Application Code is too large for Chip8 to store");
        byte[] applicationCode = new byte[4096];
        chip8.loadApplication(applicationCode);
    }

    @Test
    public void emulateChipCycleCallsExpectedMethodsInTheCorrectOrder() {

        Chip8 spyChip8 = Mockito.spy(chip8);
        spyChip8.emulateChipCycle();
        Mockito.verify(spyChip8).getCurrentOpCode();
        Mockito.verify(spyChip8).executeOpCode(Mockito.anyChar());
        Mockito.verify(spyChip8).updateTimers();
    }

    @Test
    public void emulateChipCyclePerformsFullOperationAtProgramCounter() {
        // Using Jump Operation as test
        byte[] memory = chip8.getMemory();
        memory[0] = 0x001f;
        memory[1] = (byte) 0x00ff;
        chip8.setProgramCounter(0);
        chip8.emulateChipCycle();

        Assert.assertEquals(0xfff, chip8.getProgramCounter());
    }

    @Test
    public void getCurrentOpCodeReturnsOpCodeAtProgramCounter() {
        byte[] memory = chip8.getMemory();
        memory[0] = 0x45;
        memory[1] = 0x1a;
        chip8.setProgramCounter(0);
        char opCode = chip8.getCurrentOpCode();

        Assert.assertEquals(0x451a, opCode);
    }

}
