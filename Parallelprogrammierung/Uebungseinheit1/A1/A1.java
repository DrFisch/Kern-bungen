package Uebungseinheit1.A1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.List;

// Im Folgenden sollen verschiedene Ansätze zur Synchronisation anhand eines einheitlichen Beispiels
// implementiert werden. Setzen Sie dazu zunächst den Code der Testklasse PointSynchronizedObject um.
// Diese kann von einer Methode main dann durch „new PointSynchronizedObject ().run()“ genutzt werden.

public class A1 {
    public static void main(String[] args) {
        new PointSynchronizedObject().run();
    }
    
}

// Basisklasse Point
abstract class Point {
    protected double x, y;
    public abstract void move(double deltaX, double deltaY);
    public synchronized String getPosition() {
        return String.format("(%.2f, %.2f)", x, y);
    }
}

// Implementierung ohne Synchronisation
class PointWithNoSynchronization extends Point {
    public void move(double deltaX, double deltaY) {
        x += deltaX;
        y += deltaY;
    }
}

// Implementierung mit synchronisierter Methode
class PointWithSynchronizedMethod extends Point {
    public synchronized void move(double deltaX, double deltaY) {
        x += deltaX;
        y += deltaY;
    }
}

// Implementierung mit synchronisiertem Block
class PointWithSynchronizedBlock extends Point {
    public void move(double deltaX, double deltaY) {
        synchronized (this) {
            x += deltaX;
            y += deltaY;
        }
    }
}

// Implementierung mit ReentrantLock
class PointWithReentrantLock extends Point {
    private final Lock lock = new ReentrantLock();

    public void move(double deltaX, double deltaY) {
        lock.lock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            lock.unlock();
        }
    }
}

// Klasse CalcServer, die den Punkt bewegt
class CalcServer extends Thread {
    private final Point sharedState;
    public boolean succeeded = true;

    public CalcServer(Point sharedState) {
        this.sharedState = sharedState;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 1000; i++) {
                sharedState.move(1, 1);
                sharedState.move(-1, -1);
            }
        } catch (Exception e) {
            succeeded = false;
        }
    }
}


// Testklasse PointSynchronizedObject
class PointSynchronizedObject {
    boolean logging = false;
    boolean moveMethods = false;

    public void run() {
        final var sharedStates = new Point[]{
                new PointWithNoSynchronization(),
                new PointWithSynchronizedMethod(),
                new PointWithSynchronizedBlock(),
                new PointWithReentrantLock()
        };
        runCalcServers(sharedStates, false, false);
        runCalcServers(sharedStates, true, false);
        runCalcServers(sharedStates, false, true);
    }

    private void runCalcServers(Point[] sharedStates, boolean loggingEnabled,
                                boolean moveMethodsEnabled) {
        logging = loggingEnabled;
        moveMethods = moveMethodsEnabled;
        System.out.println("Run with logging = " + logging
                + ", moveMethods = " + moveMethods);
        for (var sharedState : sharedStates)
            runCalcServer(sharedState);
    }

    private void runCalcServer(Point sharedState) {
        System.out.print("- " + sharedState.getClass().getSimpleName() + ": ");
        List<CalcServer> calcServerThreads = new ArrayList<>();
        double start = System.nanoTime();
        for (int i = 0; i < 10; i++) calcServerThreads.add(new CalcServer(sharedState));
        calcServerThreads.forEach(Thread::start);
        calcServerThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        double end = System.nanoTime();
        System.out.print(String.format("%.2f ms", (end - start) / 1_000_000));
        
        // Prüfen, ob alle Threads erfolgreich waren
        if (calcServerThreads.stream().allMatch(thread -> thread.succeeded))
            System.out.println(" (succeeded)");
        else
            System.out.println(" (failed)");

        checkFinalPosition(sharedState);
    }
    private void checkFinalPosition(Point sharedState) {
        String position = sharedState.getPosition();
        System.out.println("    - Final position: " + position);
        if ("(0,00, 0,00)".equals(position)) {
            System.out.println("    - Point is at the original position (0, 0).");
        } else {
            System.out.println("    - Point has moved from the original position.");
        }
    }
    
}
