/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.integerGenerator;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import task.Common;

/**
 *
 * @author bohdan
 */
public class DataGenerator {
     public static void main(String[] args) throws IOException {
         try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("sample.data"))) {
             for (int i = 0; i < 1001; i++) {
                 int j = ThreadLocalRandom.current().nextInt(1, 2 + 1);
                 outputStream.write(Common.my_int_to_bb_le(j));
             }}

    }
}
