package me.conmy.emu.chip8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chip8Panel extends JPanel {

    public static final int PIXEL_WIDTH = 10;
    public static final int PIXEL_HEIGHT = 10;

    Timer chipCycleTimer;
    Chip8 chip8;
    private boolean started;

    public Chip8Panel() {
        chip8 = new Chip8();
        chipCycleTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chip8.emulateChipCycle(15);
                repaint();
            }
        });
        started = false;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Chip8Display.DISPLAY_WIDTH * PIXEL_WIDTH, Chip8Display.DISPLAY_HEIGHT * PIXEL_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Chip8Display chip8Display = chip8.getScreenDisplay();
        if (chip8Display != null) {
            boolean[][] bitMatrix = chip8Display.getBitMatrix();
            for (int y = 0; y < bitMatrix.length; y++) {
                for (int x = 0; x < bitMatrix[y].length; x++) {
                    if (bitMatrix[y][x]) {
                        g2d.fillRect(x * PIXEL_WIDTH, y * PIXEL_HEIGHT, PIXEL_WIDTH, PIXEL_HEIGHT);
                    }
                }
            }
        }
        g2d.dispose();
    }

    public Timer getChipCycleTimer() {
        return chipCycleTimer;
    }

    public void setChipCycleTimer(Timer chipCycleTimer) {
        this.chipCycleTimer = chipCycleTimer;
    }

    public Chip8 getChip8() {
        return chip8;
    }

    public void setChip8(Chip8 chip8) {
        this.chip8 = chip8;
    }

    public boolean isRunning() {
        return chipCycleTimer.isRunning();
    }

    public boolean isStarted() {
        return started;
    }

    public void startChipCycle() {
        chipCycleTimer.start();
        started = true;
    }

    public void stopChipCycle() {
        chipCycleTimer.stop();
    }

    public void restartChipCycle() {
        chipCycleTimer.restart();
    }
}
