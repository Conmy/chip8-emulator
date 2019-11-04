package me.conmy.emu.chip8;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;


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
        Assert.assertEquals("Stack size not initialized correctly", 48, chip8.getStack().capacity());
        Assert.assertEquals("Data Register size not initialized correctly", 16, chip8.getVDataRegisters().length);
        Assert.assertEquals("Delay Timer is not 0 after initialization", 0, chip8.getDelayTimer());
        Assert.assertEquals("Sound Timer is not 0 after initialization", 0, chip8.getSoundTimer());
        // TODO: Missing properties that should be initialized
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
        byte[] memory = chip8.getMemory();
        int pc = chip8.getProgramCounter();
        memory[pc] = 0x60;
        memory[pc+1] = 0x00;
        Chip8 spyChip8 = Mockito.spy(chip8);
        long timeElapsed = 0;

        spyChip8.emulateChipCycle(timeElapsed);

        Mockito.verify(spyChip8).getCurrentOpCode();
        Mockito.verify(spyChip8).executeOpCode(Mockito.anyChar());
        Mockito.verify(spyChip8).updateTimers(timeElapsed);
    }

    @Test
    public void emulateChipCyclePerformsFullOperationAtProgramCounter() {
        // Using Jump Operation as test
        byte[] memory = chip8.getMemory();
        memory[0] = 0x001f;
        memory[1] = (byte) 0x00ff;
        chip8.setProgramCounter(0);
        long timeElapsed = 0;

        chip8.emulateChipCycle(timeElapsed);

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

    @Test
    public void getSpriteRegAddressReturnsTheRegAddressLocationOfSpriteInformationForTheValuePassed() {
        char[] expectedAddress = {
                0x00, 0x05, 0x0a, 0x0f, 0x14, 0x19, 0x1e, 0x23, 0x28, 0x2d, 0x32, 0x37, 0x3c, 0x41, 0x46, 0x4b};

        for (int i=0; i < 0x10; i++) {
            char addressLoc = Chip8.getSpriteRegAddress((byte) i);
            Assert.assertEquals(expectedAddress[i], addressLoc);
        }
    }

    @Test
    public void chip8ContainsAStoreForTheCurrentScreenOutput() {
        Chip8Display screenDisplay = chip8.getScreenDisplay();

        Assert.assertEquals(32, screenDisplay.getBitMatrix().length);
        for (int i=0; i < 32; i++) {
            Assert.assertEquals(64, screenDisplay.getBitMatrix()[i].length);
        }
    }

    @Test
    public void setKeyPressedAddsTheKeyValueToPressedKeys() {
        chip8.setKeyPressed((byte) 0x01);

        Assert.assertThat(chip8.getPressedKeys(), contains((byte) 0x01));
    }

    @Test
    public void clearPressedKeysRemovesAllKeysFromPressedKeys() {
        chip8.setKeyPressed((byte) 0x0f);
        Assert.assertThat(chip8.getPressedKeys(), contains((byte) 0x0f));

        chip8.clearPressedKeys();
        Assert.assertThat(chip8.getPressedKeys(), not(contains(0x0f)));
    }

    @Test
    public void setKeyPressedAcceptsKeyValuesUpTo0x0f() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Illegal KeyPress: ");
        chip8.setKeyPressed((byte) 0x10);
    }

    @Test
    public void updateTimersCountsDownDelayTimerPassingANumberOfTickOf60HertzThatHasPassed() {
        Chip8 chip8Spy = Mockito.spy(chip8);

        long timeElapsedInMillis = 32;
        chip8Spy.updateTimers(timeElapsedInMillis);
        Mockito.verify(chip8Spy).updateDelayTimer(2);

        timeElapsedInMillis = 48;
        chip8Spy.updateTimers(timeElapsedInMillis);
        Mockito.verify(chip8Spy).updateDelayTimer(3);
    }

    @Test
    public void updateTimersCountsDownSoundTimerPassingANumberOfTickOf60HertzThatHasPassed() {
        Chip8 chip8Spy = Mockito.spy(chip8);

        long timeElapsedInMillis = 16;
        chip8Spy.updateTimers(timeElapsedInMillis);
        Mockito.verify(chip8Spy).updateSoundTimer(1);

        timeElapsedInMillis = 64;
        chip8Spy.updateTimers(timeElapsedInMillis);
        Mockito.verify(chip8Spy).updateSoundTimer(4);
    }

    @Test
    public void updateSoundTimerCountsDownByNumberOfTicksToZero() {
        chip8.setSoundTimer((byte) 0x8);
        chip8.updateSoundTimer(4);
        Assert.assertEquals(0x4, chip8.getSoundTimer());

        chip8.setSoundTimer((byte) 0x16);
        chip8.updateSoundTimer(2);
        Assert.assertEquals(0x14, chip8.getSoundTimer());

        chip8.setSoundTimer((byte) 0xff);
        chip8.updateSoundTimer(2);
        Assert.assertEquals((byte) 0xfd, chip8.getSoundTimer());

        chip8.setSoundTimer((byte) 0x8);
        chip8.updateSoundTimer(18);
        Assert.assertEquals(0x00, chip8.getSoundTimer());

        chip8.setSoundTimer((byte) 0x8);
        chip8.updateSoundTimer(8);
        Assert.assertEquals(0x00, chip8.getSoundTimer());

    }

    @Test
    public void updateDelayTimerCountsDownByNumberOfTicksToZero() {
        chip8.setDelayTimer((byte) 0x01);
        chip8.updateDelayTimer(4);
        Assert.assertEquals(0x00, chip8.getDelayTimer());

        chip8.setDelayTimer((byte) 0x16);
        chip8.updateDelayTimer(2);
        Assert.assertEquals(0x14, chip8.getDelayTimer());

        chip8.setDelayTimer((byte) 0xff);
        chip8.updateDelayTimer(8);
        Assert.assertEquals((byte) 0xf7, chip8.getDelayTimer());

        chip8.setDelayTimer((byte) 0xff);
        chip8.updateDelayTimer(255);
        Assert.assertEquals((byte) 0x00, chip8.getDelayTimer());

    }
}
