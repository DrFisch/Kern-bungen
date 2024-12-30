package Uebungseinheit3.Aufgabe5CountDownLatch;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class CalcCountDownLatch2 {
    public void run() {
        var vector = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(vector.length);

        for (int i = 0; i < vector.length; ++i) // create and start threads
            new Thread(new IncrementWorker(vector, i, startSignal, doneSignal)).start();

        doSomethingElse();            // don't let run yet
        startSignal.countDown();      // let all threads proceed
        doSomethingElse();
        try {
            doneSignal.await();           // wait for all to finish

            System.out.println(Arrays.toString(vector));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void doSomethingElse() {
        System.out.println("doSomethingElse");
    }

    record IncrementWorker(Double[] vector, int index, CountDownLatch startSignal, CountDownLatch doneSignal) implements Runnable {
      public void run() {
            try {
                startSignal.await();
                doWork();
                doneSignal.countDown();
            } catch (InterruptedException ex) {} // return;
        }

        void doWork() { vector[index]++; }
    }
}
