package me.conmy.emu.utils;

import org.junit.Assert;
import org.junit.Test;

public class ByteConverterTest {

    @Test
    public void convertToBooleanArrayReturnsABooleanArrayOfTheInputByte() {
        byte[] bytes = new byte[] { 0x01, 0x06, 0x22, 0x0a, (byte)0xff, 0x00, (byte) 0xfc};
        //byte inByte = 0x01;
        boolean[][] expectedResults = new boolean[][] {
                new boolean[] {false, false, false, false, false, false, false, true}, // 01
                new boolean[] {false, false, false, false, false, true, true, false}, // 06
                new boolean[] {false, false, true, false, false, false, true, false}, // 22
                new boolean[] {false, false, false, false, true, false, true, false}, // 0a
                new boolean[] {true, true, true, true, true, true, true, true}, // ff
                new boolean[] {false, false, false, false, false, false, false, false}, // 00
                new boolean[] {true, true, true, true, true, true, false, false} // fc
        };

        for (int x=0; x < bytes.length; x++){
            byte inByte = bytes[x];
            boolean[] expectedResult = expectedResults[x];
            boolean[] actualResult = ByteConverter.byteToBooleanArray(inByte);
            Assert.assertEquals(8, actualResult.length);
            for (int i = 0; i < actualResult.length; i++) {
                boolean expected = expectedResult[i];
                boolean actual = actualResult[i];
                Assert.assertEquals(String.format("Incorrect value in pos %d of answerArray %d", i, x), expected, actual);
            }
        }
    }
}
