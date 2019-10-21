package me.conmy.emu.chip8;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

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

    }

    @Test
    public void chip8LoadApplicationPlacesHexValuesInTheCorrectLocations() {

        byte[] applicationCodes = new byte[] { 0x42, 0x0a, 0x1b, 0x0f, 0x11 };
        chip8.loadApplication(applicationCodes);
        int offset = chip8.getProgramCounter();
        byte[] memory = chip8.getMemory();
        for (int i=0; i < applicationCodes.length; i++) {
            Assert.assertEquals("Unexpected value at memory location:" + (offset+i) , applicationCodes[i], memory[offset + i]);
        }
    }

    @Test
    public void chip8LoadApplicationThrowsRuntimeExceptionIfApplicationIsTooLarge() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Application Code is too large for Chip8 to store");
        byte[] applicationCode = new byte[4096];
        chip8.loadApplication(applicationCode);
    }

    @Test
    public void chip8EmulateChipCycleCallsExpectedMethodsInTheCorrectOrder() {

        Chip8 spyChip8 = spy(chip8);
        spyChip8.emulateChipCycle();
        Mockito.verify(spyChip8).getCurrentOpCode();
        Mockito.verify(spyChip8).executeOpCode(any());
        Mockito.verify(spyChip8).updateTimers();
    }
}
