package me.conmy.emu.chip8;

import javafx.scene.input.KeyCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.security.Key;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;

public class Chip8KeyMapperTest {

    private Chip8 chip8;
    private Chip8KeyMapper keyMapper;

    @Before
    public void setUp() {
        chip8 = new Chip8();
        keyMapper = new Chip8KeyMapper(chip8);
    }

    @Test
    public void keyPressedSetsKeyPressInChip8() {
        JFrame frame = new JFrame("Test");
        keyMapper.keyPressed(new KeyEvent(frame, 1, 1000, 0, KeyEvent.VK_Q, 'Q'));

        Assert.assertThat(chip8.getPressedKeys(), contains((byte) 0x04));
    }

    @Test
    public void keyReleasedRemovesAKeyPressInChip8() {
        JFrame frame = new JFrame("Test");
        KeyEvent event = new KeyEvent(frame, 1, 1000, 0, KeyEvent.VK_Q, 'Q');
        keyMapper.keyPressed(event);
        Assert.assertThat(chip8.getPressedKeys(), contains((byte) 0x04));

        keyMapper.keyReleased(event);
        Assert.assertThat(chip8.getPressedKeys(), not(contains((byte) 0x04)));
    }

    @Test
    public void keyReleasedThrowsNoErrorsEventIfKeyWasntPressedInChip8() {
        JFrame frame = new JFrame("Test");
        KeyEvent event = new KeyEvent(frame, 1, 1000, 0, KeyEvent.VK_Q, 'Q');
        keyMapper.keyReleased(event);
        Assert.assertThat(chip8.getPressedKeys(), not(contains((byte) 0x04)));
    }
}
