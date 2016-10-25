/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.sum;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.Callable;
import task.Common;

/**
 *
 * @author bohdan
 */
public final class SumWorker implements Callable<BigInteger> {

    private String fileName;
    private int off;

    public SumWorker(String fileName, int off) {
        this.setFileName(fileName);
        this.setOff(off);
    }

    @Override
    public BigInteger call() throws InterruptedException, IOException {

        BigInteger result;
        try (FileInputStream fis = new FileInputStream(this.fileName);
                BufferedInputStream bis = new BufferedInputStream(fis)) {
            bis.skip(off * Common.WORKER_LOAD * Common.SIZE_OF_INT);
            result = BigInteger.ZERO;
            int endOfPart = 0;
            byte[] barray = new byte[Common.SIZE_OF_INT];
            while (bis.read(barray, 0, Common.SIZE_OF_INT) != -1 && endOfPart < Common.WORKER_LOAD) {
                int gettedData = Common.my_bb_to_int_le(barray);
                result = result.add(BigInteger.valueOf(gettedData));
                endOfPart++;
            }
        }
        return result;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setOff(int off) {
        this.off = off;
    }

}
