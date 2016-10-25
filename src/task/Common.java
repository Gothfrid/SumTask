/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author bohdan
 */
public class Common {

    public static byte SIZE_OF_INT = 4;
    public static long WORKER_LOAD = 500000;

    public static byte[] my_int_to_bb_le(int integerValue) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(integerValue).array();
    }

    public static int my_bb_to_int_le(byte[] byteBarray) {
        return ByteBuffer.wrap(byteBarray).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

}
