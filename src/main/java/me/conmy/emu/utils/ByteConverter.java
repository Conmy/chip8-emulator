package me.conmy.emu.utils;

public class ByteConverter {

    public static boolean[] byteToBooleanArray(byte bite) {
        boolean[] returnArray = new boolean[8];
        returnArray[7] = ((bite & 0x01) != 0);
        returnArray[6] = ((bite & 0x02) != 0);
        returnArray[5] = ((bite & 0x04) != 0);
        returnArray[4] = ((bite & 0x08) != 0);

        returnArray[3] = ((bite & 0x10) != 0);
        returnArray[2] = ((bite & 0x20) != 0);
        returnArray[1] = ((bite & 0x40) != 0);
        returnArray[0] = ((bite & 0x80) != 0);
        return returnArray;
    }
}
