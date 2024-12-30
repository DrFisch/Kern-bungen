package Musterlösung;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.function.BiFunction;



public class Musterloesung{
    public static void main(String[] args) {
        new PointSynchronizedObject().run();
    }
}

class PointSynchronizedObject {
    boolean logging = false;
    boolean moveMethods = false;

    public void run() {
        final var sharedStates = new Point[]{
                new PointWithNoSynchronization(),
                new PointWithMethodSynchronization(),
                // new PointWithClassSynchronization(),
                // new PointWithObjectSynchronization(),
                // new PointWithMultipleObjectsSynchronization(),
                new PointWithMutex(),
                new PointWithSemaphore(),
                // new PointWithReentrantLock(),
                // new PointWithReentrantReadWriteLock(),
                // new PointWithStampedLock(),
                new PointWithAtomicSynchronization()
        };

        runCalcServers(sharedStates, false, false);
        runCalcServers(sharedStates, true, false);
        runCalcServers(sharedStates, false, true);
    }

    private void runCalcServers(Point[] sharedStates, boolean loggingEnabled, boolean moveMethodsEnabled) {
        logging = loggingEnabled;
        moveMethods = moveMethodsEnabled;

        System.out.println("Run with logging = " + logging + ", moveMethods = " + moveMethods);
        for (var sharedState : sharedStates)
            runCalcServer(sharedState);
    }

    private void runCalcServer(Point sharedState) {
        System.out.print("- " + sharedState.getClass().getSimpleName() + ": ");

        List<CalcServer> calcServerThreads = new ArrayList<CalcServer>();

        double start = System.nanoTime();

        // Create CalcServer threads
        for (int i=0; i<10; i++)
            calcServerThreads.add(new CalcServer(sharedState));

        // Start CalcServer thrads
        calcServerThreads.forEach( thread -> thread.start());

        // Wait for CalcServer threads to finish
        calcServerThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        double end = System.nanoTime();

        System.out.print(String.format("%.2f ms", (end-start) / 1000 / 1000));
        if (calcServerThreads.stream().allMatch(thread -> thread.succeeded))
            System.out.println(" (succeeded)");
        else
            System.out.println(" (failed)");
    }

    class CalcServer extends Thread {
        private Point state;

        private boolean succeeded = false;
        public CalcServer(Point state) {
            this.state = state;
        }

        @Override
        public void run() {
            succeeded = true;

            for (int i=0; i<4; i++) {
                final var value = ThreadLocalRandom.current().nextDouble(1000);

                if (!moveMethods)
                    executeBasicMethods(value);
                else
                    executeExtendedMethods(value);

                // Da gleicher value für x und y übergeben wird, müssen bei korrekter
                // synchronisierter Umsetzung beide Koordinaten auf identischen Wert gesetzt sein
                final var newCoords = state.getCoords();
                if (newCoords[0] != newCoords[1])
                    succeeded = false;
            }
        }

        void executeBasicMethods(double value) {
            for (int j=0; j<10; j++)
                state.getCoords();

            state.setCoords(value, value);
        }

        void executeExtendedMethods(double value) {
            state.move(value, value);

            state.moveIfCondition(0, 0, (x, y) -> x + y == 0);
        }
    }

    abstract class Point {
        private double x, y;
        private List<String> actionsLog = new ArrayList<>();
        private List<Object[]> paramsLog = new ArrayList<>();

        protected double getX() {
            delay();
            return x;
        }

        protected void setX(double x) {
            delay();
            this.x = x;
        }

        protected double getY() {
            delay();
            return y;
        }

        protected void setY(double y) {
            delay();
            this.y = y;
        }

        protected void addToActionLog(String action) {
            delay();
            this.actionsLog.add(action);
        }

        protected void addToParamsLog(Object[] params) {
            delay();
            this.paramsLog.add(params);
        }

        // Demonstriert zu synchronisierenden Schreibzugriff auf gleiche Variablen
        public abstract void setCoords(double x, double y);

        // Demonstriert zu synchronisierenden Lesezugriff auf gleiche Variablen
        public abstract double[] getCoords();

        // Demonstriert zu synchronisierenden Schreibzugriff auf unabhängige Variablen
        protected abstract void addToLog(String action, Object ...params);

        // Demonstriert zu synchronisierenden Lese- und Schreibzugriff
        public abstract void move(double deltaX, double deltaY);

        // Demonstriert zu synchronisierenden Lese- und bedingten Schreibzugriff
        public abstract void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition);

        protected void logAction(String action, Object ...params) {
            delay();

            if (logging)
                addToLog(action, params);
        }

        protected void delay() {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(5));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class PointWithNoSynchronization extends Point {
        public void setCoords(double x, double y) {
            // Aufruf von logAction ist hier wichtig, da dies aufwendigen Teil repräsentiert, der nicht
            // in einer Synchronisierung berücksichtigt werden muss
            logAction("setCoords", x, y);
            setX(x);
            setY(y);
        }

        public double[] getCoords() {
            // Aufruf von logAction ist hier wichtig, da dies aufwendigen Teil repräsentiert, der nicht
            // in einer Synchronisierung berücksichtigt werden muss
            logAction("getCoords");

            return new double[]{getX(), getY()};
        }

        protected void addToLog(String action, Object ...params) {
            addToActionLog(action);
            addToParamsLog(params);
        }

        public void move(double deltaX, double deltaY) {
            // Aufruf von logAction ist hier wichtig, da dies aufwendigen Teil repräsentiert, der nicht
            // in einer Synchronisierung berücksichtigt werden muss
            logAction("move", deltaX, deltaY);
            setX(getX() + deltaX);
            setY(getY() + deltaY);
        }

        public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
            logAction("moveIfCondition", newX, newY, condition);
            final var coords = getCoords(); // getCoords() statt getX() und getY() nutzen, um verschachteltes Locking zu testen
            if (condition.apply(coords[0], coords[1])) {
                setX(newX);
                setY(newY);
            }
        }
    }
    class PointWithMethodSynchronization extends Point {
        public synchronized void setCoords(double x, double y) {
            // Aufruf von logAction ist hier wichtig, da dies aufwendigen Teil repräsentiert, der nicht
            // in einer Synchronisierung berücksichtigt werden muss
            logAction("setCoords", x, y);
            setX(x);
            setY(y);
        }

        public synchronized double[] getCoords() {
            // Aufruf von logAction ist hier wichtig, da dies aufwendigen Teil repräsentiert, der nicht
            // in einer Synchronisierung berücksichtigt werden muss
            logAction("getCoords");

            return new double[]{getX(), getY()};
        }

        protected synchronized void addToLog(String action, Object ...params) {
            addToActionLog(action);
            addToParamsLog(params);
        }

        public synchronized void move(double deltaX, double deltaY) {
            // Aufruf von logAction ist hier wichtig, da dies aufwendigen Teil repräsentiert, der nicht
            // in einer Synchronisierung berücksichtigt werden muss
            logAction("move", deltaX, deltaY);
            setX(getX() + deltaX);
            setY(getY() + deltaY);
        }

        public synchronized void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
            logAction("moveIfCondition", newX, newY, condition);
            final var coords = getCoords(); // getCoords() statt getX() und getY() nutzen, um verschachteltes Locking zu testen
            if (condition.apply(coords[0], coords[1])) {
                setX(newX);
                setY(newY);
            }
        }
    }
    class PointWithClassSynchronization extends Point {
        public  void setCoords(double x, double y) {
            // Aufruf von logAction ist hier wichtig, da dies aufwendigen Teil repräsentiert, der nicht
            // in einer Synchronisierung berücksichtigt werden muss
            logAction("setCoords", x, y);
            synchronized(this){
            setX(x);
            setY(y);}
        }

        public double[] getCoords() {
            // Aufruf von logAction ist hier wichtig, da dies aufwendigen Teil repräsentiert, der nicht
            // in einer Synchronisierung berücksichtigt werden muss
            logAction("getCoords");
            synchronized(this){
            return new double[]{getX(), getY()};
            }
        }

        protected void addToLog(String action, Object ...params) {
            addToActionLog(action);
            addToParamsLog(params);
        }

        public void move(double deltaX, double deltaY) {
            // Aufruf von logAction ist hier wichtig, da dies aufwendigen Teil repräsentiert, der nicht
            // in einer Synchronisierung berücksichtigt werden muss
            logAction("move", deltaX, deltaY);
            synchronized(this){
                setX(getX() + deltaX);
                setY(getY() + deltaY);
            }
        }

        public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
            logAction("moveIfCondition", newX, newY, condition);
            synchronized (this) {
                final var coords = getCoords();
                if (condition.apply(coords[0], coords[1])) {
                    setX(newX);
                    setY(newY);
                }
            }
        }
    }
    class PointWithObjectSynchronization extends Point {
        private final Object lock = new Object();
    
        public void setCoords(double x, double y) {
            logAction("setCoords", x, y);
            synchronized (lock) {
                setX(x);
                setY(y);
            }
        }
    
        public double[] getCoords() {
            logAction("getCoords");
            synchronized (lock) {
                return new double[]{getX(), getY()};
            }
        }
    
        protected void addToLog(String action, Object... params) {
            synchronized (lock) {
                addToActionLog(action);
                addToParamsLog(params);
            }
        }
    
        public void move(double deltaX, double deltaY) {
            logAction("move", deltaX, deltaY);
            synchronized (lock) {
                setX(getX() + deltaX);
                setY(getY() + deltaY);
            }
        }
    
        public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
            logAction("moveIfCondition", newX, newY, condition);
            synchronized (lock) {
                final var coords = getCoords();
                if (condition.apply(coords[0], coords[1])) {
                    setX(newX);
                    setY(newY);
                }
            }
        }
    }
    class PointWithMultipleObjectsSynchronization extends Point {
        private final Object lockX = new Object();
        private final Object lockY = new Object();
    
        public void setCoords(double x, double y) {
            logAction("setCoords", x, y);
            synchronized (lockX) {
                synchronized (lockY) {
                    setX(x);
                    setY(y);
                }
            }
        }
    
        public double[] getCoords() {
            logAction("getCoords");
            synchronized (lockX) {
                synchronized (lockY) {
                    return new double[]{getX(), getY()};
                }
            }
        }
    
        protected void addToLog(String action, Object... params) {
            synchronized (lockX) {
                synchronized (lockY) {
                    addToActionLog(action);
                    addToParamsLog(params);
                }
            }
        }
    
        public void move(double deltaX, double deltaY) {
            logAction("move", deltaX, deltaY);
            synchronized (lockX) {
                synchronized (lockY) {
                    setX(getX() + deltaX);
                    setY(getY() + deltaY);
                }
            }
        }
    
        public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
            logAction("moveIfCondition", newX, newY, condition);
            synchronized (lockX) {
                synchronized (lockY) {
                    final var coords = getCoords();
                    if (condition.apply(coords[0], coords[1])) {
                        setX(newX);
                        setY(newY);
                    }
                }
            }
        }
    }
    class PointWithReentrantLock extends Point {

        private final Lock coordslock = new ReentrantLock();
        private final Lock logLock = new ReentrantLock();

        public void setCoords(double x, double y) {
            coordslock.lock();
            try {
                setX(x);
                setY(y);
            } finally {
                coordslock.unlock();
            }
        }

        public double[] getCoords() {
            // Aufruf von logAction ist hier wichtig, da dies aufwendigen Teil repräsentiert, der nicht
            // in einer Synchronisierung berücksichtigt werden muss
            logAction("getCoords");
            coordslock.lock();
            try {
                return new double[]{getX(), getY()};
            } finally {
                coordslock.unlock();
            }
        }

        protected void addToLog(String action, Object ...params) {
            logLock.lock();
            addToActionLog(action);
            addToParamsLog(params);
            logLock.unlock();
        }

        public void move(double deltaX, double deltaY) {
            // Aufruf von logAction ist hier wichtig, da dies aufwendigen Teil repräsentiert, der nicht
            // in einer Synchronisierung berücksichtigt werden muss
            logAction("move", deltaX, deltaY);

            coordslock.lock();
            try{
                setX(getX() + deltaX);
                setY(getY() + deltaY);
            }
            finally{
                coordslock.unlock();
            }
        }

        public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
            logAction("moveIfCondition", newX, newY, condition);
            
            coordslock.lock();
            try {
            final var coords = new double[]{getX(), getY()};
            if (condition.apply(coords[0], coords[1])) {
                setX(newX);
                setY(newY);
            }
            } finally {
                coordslock.unlock();
            }
        }
    }
    // class PointWithReentrantReadWriteLock extends Point {
    //     private ReentrantReadWriteLock coordslock = new ReentrantReadWriteLock();
    //     private ReentrantReadWriteLock loglock = new ReentrantReadWriteLock();

    //     public void setCoords(double x, double y) {
           
    //         logAction("setCoords", x, y);
    //         coordslock.writeLock().lock();
    //         setX(x);
    //         setY(y);
    //         coordslock.writeLock().unlock();
    //     }

    //     public double[] getCoords() {
            
    //         logAction("getCoords");
    //         try{
    //             coordslock.readLock().lock();
    //             return new double[]{getX(), getY()};
    //         }finally{
    //             coordslock.readLock().unlock();
    //         }
    //     }

    //     protected void addToLog(String action, Object ...params) {
    //         loglock.writeLock().lock();
    //         addToActionLog(action);
    //         addToParamsLog(params);
    //         loglock.writeLock().unlock();
    //     }

    //     public void move(double deltaX, double deltaY) {
            
    //         logAction("move", deltaX, deltaY);
    //         coordslock.writeLock().lock();
    //         setX(getX() + deltaX);
    //         setY(getY() + deltaY);
    //         coordslock.writeLock().unlock();
    //     }

    //     public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
    //         logAction("moveIfCondition", newX, newY, condition);
    //         coordslock.readLock().lock();

    //         final var coords = getCoords(); // getCoords() statt getX() und getY() nutzen, um verschachteltes Locking zu testen

    //         if (condition.apply(coords[0], coords[1])) {
    //             coordslock.writeLock().lock();
    //             setX(newX);
    //             setY(newY);
    //             coordslock.writeLock().unlock();
    //         }

    //         coordslock.readLock().unlock();
    //     }
    // }
    class PointWithReentrantReadWriteLock extends Point {
        private ReentrantReadWriteLock coordsLock = new ReentrantReadWriteLock();
        private ReentrantReadWriteLock logLock = new ReentrantReadWriteLock();


        @Override
        public void setCoords(double x, double y) {
            logAction("setCoords", x, y);

            coordsLock.writeLock().lock();
            setX(x);
            setY(y);
            coordsLock.writeLock().unlock();
        }

        @Override
        public double[] getCoords() {
            logAction("getCoords");

            // Synchronisation nötig, da zwischen Aufruf von getX() und von getY() Funktion setCoords() durchlaufen kann
            try {
                coordsLock.readLock().lock();
                return new double[]{getX(), getY()};
            }
            finally {
                coordsLock.readLock().unlock();
            }
        }

        protected void addToLog(String action, Object ...params) {
            logLock.writeLock().lock();
            addToActionLog(action);
            addToParamsLog(params);
            logLock.writeLock().unlock();
        }

        public void move(double deltaX, double deltaY) {
            logAction("move", deltaX, deltaY);

            coordsLock.writeLock().lock();
            setX(getX() + deltaX);
            setY(getY() + deltaY);
            coordsLock.writeLock().unlock();
        }

        public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
            logAction("moveIfCondition", newX, newY, condition);

            coordsLock.readLock().lock();

            final var coords = getCoords(); // getCoords() statt getX() und getY() nutzen, um verschachteltes Locking zu testen

            if (condition.apply(coords[0], coords[1])) {
                coordsLock.writeLock().lock();
                setX(newX);
                setY(newY);
                coordsLock.writeLock().unlock();
            }

            coordsLock.readLock().unlock();
        }
    }

    class PointWithStampedLock extends Point {
        private StampedLock coordsLock = new StampedLock();
        private StampedLock logLock = new StampedLock();

        @Override
        public void setCoords(double x, double y) {
            logAction("setCoords", x, y);

            final var stamp = coordsLock.writeLock();
            setX(x);
            setY(y);
            coordsLock.unlockWrite(stamp);
        }

        public double[] getCoords() {
            logAction("getCoords");

            long stamp = coordsLock.tryOptimisticRead();
            double currentX = getX(), currentY = getY();
            if (!coordsLock.validate(stamp)) {
                stamp = coordsLock.readLock();
                try {
                    currentX = getX();
                    currentY = getY();
                } finally {
                    coordsLock.unlockRead(stamp);
                }
            }
            return new double[]{currentX, currentY};
        }

        protected void addToLog(String action, Object ...params) {
            final var stamp = logLock.writeLock();
            addToActionLog(action);
            addToParamsLog(params);
            logLock.unlockWrite(stamp);
        }

        public void move(double deltaX, double deltaY) {
            logAction("move", deltaX, deltaY);

            long stamp = coordsLock.writeLock();
            try {
                setX(getX() + deltaX);
                setY(getY() + deltaY);
            } finally {
                coordsLock.unlockWrite(stamp);
            }
        }

        public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
            logAction("moveIfCondition", newX, newY, condition);

            // Could instead start with optimistic, not read mode
            long stamp = coordsLock.readLock();
            try {
                final var coords = getCoords(); // Nicht getCoords() statt getX() und getY() nutzen, da nur dann beide in konsistentem Zustand
                while (condition.apply(coords[0], coords[1])) {
                    long ws = coordsLock.tryConvertToWriteLock(stamp);
                    if (ws != 0L) {
                        stamp = ws;
                        setX(newX);
                        setY(newY);
                        break;
                    }
                    else {
                        coordsLock.unlockRead(stamp);
                        stamp = coordsLock.writeLock();
                    }
                }
            } finally {
                coordsLock.unlock(stamp);
            }
        }
    }
    
//     class PointWithMutex extends Point {
//         private final Semaphore mutex = new Semaphore(1); // Mutex mit Kapazität 1
    
//         @Override
//         public void setCoords(double x, double y) {
//             logAction("setCoords", x, y);
//             try {
//                 mutex.acquire(); // Sperrt den Mutex
//                 setX(x);
//                 setY(y);
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt(); // Unterbrechungsstatus setzen
//             } finally {
//                 mutex.release(); // Gibt den Mutex frei
//             }
//         }
    
//         @Override
//         public double[] getCoords() {
//             logAction("getCoords");
//             try {
//                 mutex.acquire(); // Sperrt den Mutex
//                 return new double[]{getX(), getY()};
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt(); // Unterbrechungsstatus setzen
//                 return new double[]{0, 0}; // Rückgabewert im Fehlerfall
//             } finally {
//                 mutex.release(); // Gibt den Mutex frei
//             }
//         }
    
//         @Override
//         protected void addToLog(String action, Object... params) {
//             try {
//                 mutex.acquire(); // Sperrt den Mutex
//                 addToActionLog(action);
//                 addToParamsLog(params);
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt();
//             } finally {
//                 mutex.release(); // Gibt den Mutex frei
//             }
//         }
    
//         @Override
//         public void move(double deltaX, double deltaY) {
//             logAction("move", deltaX, deltaY);
//             try {
//                 mutex.acquire(); // Sperrt den Mutex
//                 setX(getX() + deltaX);
//                 setY(getY() + deltaY);
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt();
//             } finally {
//                 mutex.release(); // Gibt den Mutex frei
//             }
//         }
    
//         @Override
//         public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
//             logAction("moveIfCondition", newX, newY, condition);
//             try {
//                 mutex.acquire(); // Sperrt den Mutex
//                 // Direkter Zugriff auf x und y, um Deadlock zu vermeiden
//                 double currentX = getX(); // Zugriff innerhalb des Mutex
//                 double currentY = getY();
//                 if (condition.apply(currentX, currentY)) {
//                     setX(newX);
//                     setY(newY);
//                 }
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt();
//             } finally {
//                 mutex.release(); // Gibt den Mutex frei
//             }
//         }
//     }
    
//     class PointWithSemaphore extends Point {
//         private final Semaphore readSemaphore = new Semaphore(10); // Bis zu 10 parallele Leser
//         private final Semaphore writeSemaphore = new Semaphore(1); // Nur 1 Schreiber
    
//         @Override
//         public void setCoords(double x, double y) {
//             logAction("setCoords", x, y);
//             try {
//                 writeSemaphore.acquire(10); // Schreibzugriff blockiert Leser
//                 setX(x);
//                 setY(y);
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt();
//             } finally {
//                 writeSemaphore.release(10);
//             }
//         }
    
//         @Override
//         public double[] getCoords() {
//             logAction("getCoords");
//             try {
//                 readSemaphore.acquire(); // Paralleles Lesen erlaubt
//                 return new double[]{getX(), getY()};
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt();
//                 return new double[]{0, 0};
//             } finally {
//                 readSemaphore.release();
//             }
//         }
    
//         @Override
//         protected void addToLog(String action, Object... params) {
//             try {
//                 writeSemaphore.acquire(); // Schutz für die Log-Operationen
//                 addToActionLog(action);
//                 addToParamsLog(params);
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt();
//             } finally {
//                 writeSemaphore.release();
//             }
//         }
    
//         @Override
//         public void move(double deltaX, double deltaY) {
//             logAction("move", deltaX, deltaY);
//             try {
//                 writeSemaphore.acquire();
//                 setX(getX() + deltaX);
//                 setY(getY() + deltaY);
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt();
//             } finally {
//                 writeSemaphore.release();
//             }
//         }
    
//         @Override
//         public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
//             logAction("moveIfCondition", newX, newY, condition);
//             try {
//                 writeSemaphore.acquire(); // Sperrt den Schreibzugriff
//                 // Direkter Zugriff auf die Koordinaten
//                 double currentX = getX();
//                 double currentY = getY();
//                 if (condition.apply(currentX, currentY)) {
//                     setX(newX);
//                     setY(newY);
//                 }
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt();
//             } finally {
//                 writeSemaphore.release();
//             }
//         }
//     } 
// class PointWithAtomicSynchronization extends Point {
//     private final AtomicReference<Double> atomicX = new AtomicReference<>(0.0);
//     private final AtomicReference<Double> atomicY = new AtomicReference<>(0.0);

//     public void setCoords(double x, double y) {
//         logAction("setCoords", x, y);
//         atomicX.set(x);
//         atomicY.set(y);
//     }

//     public double[] getCoords() {
//         logAction("getCoords");
//         return new double[]{atomicX.get(), atomicY.get()};
//     }

//     protected void addToLog(String action, Object... params) {
//         addToActionLog(action);
//         addToParamsLog(params);
//     }

//     public void move(double deltaX, double deltaY) {
//         logAction("move", deltaX, deltaY);
//         atomicX.updateAndGet(oldX -> oldX + deltaX);
//         atomicY.updateAndGet(oldY -> oldY + deltaY);
//     }

//     public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
//         logAction("moveIfCondition", newX, newY, condition);
//         final var coords = getCoords();
//         if (condition.apply(coords[0], coords[1])) {
//             setCoords(newX, newY);
//         }
//     }
// }
class PointWithMutex extends Point {
    private Semaphore coordsMutex = new Semaphore(1);
    private Semaphore logMutex = new Semaphore(1);

    @Override
    public void setCoords(double x, double y) {
        logAction("setCoords", x, y);

        try {
            coordsMutex.acquire();
            setX(x);
            setY(y);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            coordsMutex.release();
        }
    }

    @Override
    public double[] getCoords() {
        logAction("getCoords");

        try {
            coordsMutex.acquire();
            return new double[]{getX(), getY()};
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            coordsMutex.release();
        }
    }

    protected void addToLog(String action, Object ...params) {
        try {
            logMutex.acquire();
            addToActionLog(action);
            addToParamsLog(params);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            logMutex.release();
        }
    }

    public void move(double deltaX, double deltaY) {
        logAction("move", deltaX, deltaY);

        try {
            coordsMutex.acquire();
            setX(getX() + deltaX);
            setY(getY() + deltaY);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            coordsMutex.release();
        }
    }

    public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
        logAction("moveIfCondition", newX, newY, condition);

        try {
            coordsMutex.acquire();

            // getX() und getY() statt getCoords() müssen genutzt werden, weil sonst ein zweites acquire auf Mutex
            // in gleichem Thread in getCoords() erfolgen würde und Thread blockiert (Mutex ist nicht reenetrant)
            final var coords = new double[]{getX(), getY()};
            if (condition.apply(coords[0], coords[1])) {
                setX(newX);
                setY(newY);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            coordsMutex.release();
        }
    }
}

class PointWithSemaphore extends Point {
    private static final int SEMAPHORE_SIZE = 10;
    private Semaphore coordsSemaphore = new Semaphore(SEMAPHORE_SIZE);
    private Semaphore logSemaphore = new Semaphore(SEMAPHORE_SIZE);

    @Override
    public void setCoords(double x, double y) {
        logAction("setCoords", x, y);

        try {
            coordsSemaphore.acquire(SEMAPHORE_SIZE);
            setX(x);
            setY(y);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            coordsSemaphore.release(SEMAPHORE_SIZE);
        }
    }

    @Override
    public double[] getCoords() {
        logAction("getCoords");

        try {
            coordsSemaphore.acquire();
            return new double[]{getX(), getY()};
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            coordsSemaphore.release();
        }
    }

    protected void addToLog(String action, Object ...params) {
        try {
            logSemaphore.acquire();
            addToActionLog(action);
            addToParamsLog(params);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            logSemaphore.release();
        }
    }

    public void move(double deltaX, double deltaY) {
        logAction("move", deltaX, deltaY);

        try {
            coordsSemaphore.acquire(SEMAPHORE_SIZE);
            setX(getX() + deltaX);
            setY(getY() + deltaY);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            coordsSemaphore.release(SEMAPHORE_SIZE);
        }
    }

    public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
        logAction("moveIfCondition", newX, newY, condition);

        try {
            coordsSemaphore.acquire(SEMAPHORE_SIZE);

            // getX() und getY() statt getCoords() müssen genutzt werden, weil sonst ein zweites acquire auf Mutex
            // in gleichem Thread in getCoords() erfolgen würde und Thread blockiert (Mutex ist nicht reenetrant)
            final var coords = new double[]{getX(), getY()};
            if (condition.apply(coords[0], coords[1])) {
                setX(newX);
                setY(newY);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            coordsSemaphore.release(SEMAPHORE_SIZE);
        }
    }
}
    class PointWithAtomicSynchronization extends Point {
        private final AtomicReference<double[]> coords =new AtomicReference<>(new double[]{0.,0.});

        @Override
        public void setCoords(double x, double y) {
            logAction("setCoords", x, y);

            coords.set(new double[]{x,y});
            
        }

        @Override
        public double[] getCoords() {
            logAction("getCoords");

           return coords.get();
        }

        protected void addToLog(String action, Object ...params) {
            
                addToActionLog(action);
                addToParamsLog(params);
            
        }

        public void move(double deltaX, double deltaY) {
            logAction("move", deltaX, deltaY);

            coords.updateAndGet(current -> new double[]{current[0] + deltaX, current[1] + deltaY});
        }

        public void moveIfCondition(double newX, double newY, BiFunction<Double, Double, Boolean> condition) {
            logAction("moveIfCondition", newX, newY, condition);

            coords.updateAndGet(current -> {
                if (condition.apply(current[0], current[1])) {
                    return new double[]{newX, newY};
                }
                return current; 
            });
        }
    }
}