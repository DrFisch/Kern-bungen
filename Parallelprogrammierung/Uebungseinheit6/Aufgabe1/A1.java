package Uebungseinheit6.Aufgabe1;

public class A1 {
    public static void main(String[] args) {
        Runnable outputTest = () -> {
            out("Hi"); 
            out("Working"); 
            out("Bye");
        };

        var t1 = Thread.ofVirtual().name("Thread A").start(outputTest);
        var t2 = Thread.ofVirtual().name("Thread B").start(outputTest);
        try { 
            t1.join(); 
            t2.join(); 
        } 
        catch (InterruptedException e) 
        {

        }
        
    }
    
    static void out(String text) {
        System.out.println(Thread.currentThread().threadId() + " - "
        + Thread.currentThread().getName() + ": " + text); sleep(1000);
    }
    static void sleep(long t) { 
        try { Thread.sleep(t); 
        } 
        catch (Exception e){} 
    }
}
