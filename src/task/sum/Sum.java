/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.sum;

import task.sum.SumWorker;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import task.Common;

/**
 *
 * @author bohdan
 */
public class Sum {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        String fileName = args[0];
        System.out.println(fileName);
        long sizeOfFile = 0;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            sizeOfFile = fis.getChannel().size();
            System.out.println(sizeOfFile);
            long nR = 0;
            if (sizeOfFile % Common.SIZE_OF_INT == 0) {
                nR = sizeOfFile / Common.SIZE_OF_INT;
            } else {
                System.out.println("Malformed Input");
                System.exit(0);
            }
            fis.close();
        } catch (IOException e) {
            System.out.println("File not found or failed to read");
            System.exit(0);
        }
        Collection<Callable<BigInteger>> tasks = new LinkedList<>();
        //Decide amount of workers -- 1 worker for 10k inputs
        long workerNumber = sizeOfFile / (Common.WORKER_LOAD * Common.SIZE_OF_INT)
                + ((Common.WORKER_LOAD % 10000 != 0) ? 0 : 1);

        System.out.println("Worker Number - " + workerNumber);
        for (int i = 0; i < workerNumber; i++) {
            tasks.add(new SumWorker(fileName, i));
        }
        ExecutorService pool = Executors.newFixedThreadPool((int) workerNumber);
        List<Future<BigInteger>> results = pool.invokeAll(tasks);
        pool.shutdown();
        BigInteger t = BigInteger.ZERO;
        for (Future<BigInteger> result : results) {
            t = t.add(result.get());
        }
        System.out.println(t);
    }
}
