package me.conmy.emu.chip8;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;

public class Chip8PanelTest {

    private Chip8Panel panel;

    @Before
    public void setUp() throws Exception {
        panel = new Chip8Panel();
    }

    @Test
    public void constructorCreatesAnInstanceOfChip8() {
        Assert.assertNotNull(panel.getChip8());
        Assert.assertThat(panel.getChip8(), instanceOf(Chip8.class));
    }

    @Test
    public void chip8CycleIsNotStartedAfterConstruction() {
        Assert.assertFalse(panel.isStarted());
    }

    @Test
    public void callingStartChipCycleStartsTheChipCycle() {
        panel.startChipCycle();
        Assert.assertTrue(panel.isStarted());
    }

    @Test
    public void isRunningIsTrueWhenChipCycleIsBeingCalled() {
        Assert.assertFalse(panel.isRunning());
    }

    @Test
    public void stopChipCycleStopsTheChipCycleFromRunning() {
        Assert.assertFalse(panel.isRunning());
        panel.startChipCycle();
        Assert.assertTrue(panel.isRunning());
        panel.stopChipCycle();
        Assert.assertFalse(panel.isRunning());
    }

    @Test
    public void restartChipCycleStartsTheEmulatorCycleAgainAfterAStop() {
        Assert.assertFalse(panel.isRunning());
        panel.startChipCycle();
        Assert.assertTrue(panel.isRunning());
        panel.stopChipCycle();
        Assert.assertFalse(panel.isRunning());
        panel.restartChipCycle();
        Assert.assertTrue(panel.isRunning());
    }

    @Test
    public void whenIsRunningIsTrueTheChipCycleExecuteIsBeingCalledOnChip8() {
        // TODO: Implement test

//        Chip8 chip8Spy = Mockito.spy(panel.getChip8());
//
//        int delay = panel.getChipCycleTimer().getDelay();
//        panel.startChipCycle();
//
//        Assert.assertTrue(panel.isRunning());
//
//        Mockito.verify(chip8Spy).executeOpCode(any());

    }
}
