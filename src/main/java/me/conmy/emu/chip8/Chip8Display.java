package me.conmy.emu.chip8;

import me.conmy.emu.utils.ByteConverter;

public class Chip8Display {

    public static final int DISPLAY_HEIGHT = 32;
    public static final int DISPLAY_WIDTH = 64;

    private boolean[][] bitMatrix;
    private boolean collisionDetected;

    public Chip8Display() {
        bitMatrix = new boolean[DISPLAY_HEIGHT][DISPLAY_WIDTH];
    }

    public void writeByte(int x, int y, byte displayByte) {
//        if ((x + 8) >= DISPLAY_WIDTH | y >= DISPLAY_HEIGHT) {
//            throw new IllegalArgumentException(
//                    String.format("Cannot draw sprite. Byte position overflows display width in pos range [(%d, %d) -> (%d, %d)]", x, y, x+8, y));
//        }
        boolean[] row = getDisplayRow(y);
        boolean[] sprite = ByteConverter.byteToBooleanArray(displayByte);
        for (int i=0; i < 8; i++) {
            if (x+i < row.length) {
                // Determine if the bit will be flipped
                boolean flipped = (row[x + i] && sprite[i]);
                // Set the new pixel value
                row[x + i] = row[x + i] ^ sprite[i];
                // Set collision detection if bit was flipped
                if (flipped) {
                    setCollisionDetected(true);
                }
            }
        }
    }

    public void writeSprite(int x, int y, byte[] displaySprite) {
        int height = 0;
        for (byte spriteRow: displaySprite) {
            writeByte(x, y + height, spriteRow);
            height++;
        }
    }

    public void clearScreen() {
        boolean[][] bitMatrix = getBitMatrix();
        for (int i=0; i < bitMatrix.length; i++) {
            for (int j=0; j<bitMatrix[i].length; j++) {
                bitMatrix[i][j] = false;
            }
        }
    }

    // ==========================================
    // Getters and Setters
    // ==========================================

    public boolean[] getDisplayRow(int row) {
        if (row < 0 || row >= DISPLAY_HEIGHT) {
            throw new IllegalArgumentException(String.format("Cannot access display row of index %d", row));
        }
        return getBitMatrix()[row];
    }

    public boolean[][] getBitMatrix() {
        return bitMatrix;
    }

    public void setBitMatrix(boolean[][] bitMatrix) {
        this.bitMatrix = bitMatrix;
    }

    public boolean isCollisionDetected() {
        return collisionDetected;
    }

    public void clearCollisionDetected() {
        this.collisionDetected = false;
    }

    private void setCollisionDetected(boolean collisionDetected) {
        this.collisionDetected = collisionDetected;
    }
}
