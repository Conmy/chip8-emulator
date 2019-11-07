package me.conmy.emu.chip8;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Properties;

public class Chip8KeyMapper implements KeyListener {

    Properties keyProperties;
    Chip8 chip8;

    public Chip8KeyMapper(Chip8 chip8) {
        this.chip8 = chip8;

        keyProperties = new Properties();
        keyProperties.put(KeyEvent.VK_1, (byte) 0x01);
        keyProperties.put(KeyEvent.VK_2, (byte) 0x02);
        keyProperties.put(KeyEvent.VK_3, (byte) 0x03);
        keyProperties.put(KeyEvent.VK_Q, (byte) 0x04);
        keyProperties.put(KeyEvent.VK_W, (byte) 0x05);
        keyProperties.put(KeyEvent.VK_E, (byte) 0x06);
        keyProperties.put(KeyEvent.VK_A, (byte) 0x07);
        keyProperties.put(KeyEvent.VK_S, (byte) 0x08);
        keyProperties.put(KeyEvent.VK_D, (byte) 0x09);
        keyProperties.put(KeyEvent.VK_Z, (byte) 0x00);
        keyProperties.put(KeyEvent.VK_X, (byte) 0x0a);
        keyProperties.put(KeyEvent.VK_C, (byte) 0x0b);
        keyProperties.put(KeyEvent.VK_V, (byte) 0x0c);
        keyProperties.put(KeyEvent.VK_F, (byte) 0x0d);
        keyProperties.put(KeyEvent.VK_R, (byte) 0x0e);
        keyProperties.put(KeyEvent.VK_4, (byte) 0x0f);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (keyProperties.containsKey(e.getKeyCode())) {
            chip8.setKeyPressed((byte) keyProperties.get(e.getKeyCode()));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (keyProperties.containsKey(e.getKeyCode())) {
            byte value = (byte) keyProperties.get(e.getKeyCode());
            chip8.getPressedKeys().remove(Byte.valueOf(value));
        }
    }
}
