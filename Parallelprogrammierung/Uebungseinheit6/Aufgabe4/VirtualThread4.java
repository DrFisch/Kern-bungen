package Uebungseinheit6.Aufgabe4;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VirtualThread4 {
    public void run() {
//        test1();
        test2();
    }

    void test1() {
        ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();
        List<Callable<String>> tasks = IntStream.range(0, 4)
                .mapToObj(index -> createCallable(index+1))
                .collect(Collectors.toList());

        try {
            List<Future<String>> allFuturesCompleted = exec.invokeAll(tasks);

            allFuturesCompleted.forEach(f -> System.out.println(f.resultNow()));
            // Task 1 finished
            // Task 2 finished
            // Task 3 finished
            // Task 4 finished
        } catch (Exception e) { }
    }

    void test2() {
        ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();
        List<Callable<String>> tasks = IntStream.range(0, 4)
                .mapToObj(index -> createCallable(index+1))
                .collect(Collectors.toList());

        try {
            String futureResult = exec.invokeAny(tasks);

            System.out.println(futureResult);
            // Task 3 finished
            // Task 1 cancelled!
            // Task 4 cancelled!
            // Task 2 cancelled!
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Callable<String> createCallable(int t) {
        return () -> {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(0, 1000));
                return "Task " + t + " finished";
            } catch (InterruptedException e) {
                System.out.println("Task " + t + " cancelled!");
                return null;
            }
        };
    }
}
