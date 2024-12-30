package A1.A1Si;


import java.util.function.Consumer;
// Übung 1: Erstellen Sie eine Methodenreferenz auf System.out.println durch folgende Ansätze:
// a) Erstellung eines passenden generischen Functional Interfaces
// b) Nutzung eines generischen Standard-Functional-Interfaces mit direkter Typangabe
// c) Nutzung eines generischen Standard-Functional-Interfaces mit var-Schlüsselwort und Type Hint
// d) Angabe eines Lambda-Ausdrucks anstelle einer Methodenreferenz
// Testen Sie die Referenzen auf die Methode System.out.println durch Aufruf und Übergabe von "Hi"

interface Konsumierer<T> {
    void essen(T t);
}

public class A1Si {
    public static void main(String[] args) {
        Konsumierer esser = System.out::println;
        esser.essen("Fleisch mit Reis");

        // b)
        Consumer cons =System.out::println;
        cons.accept("HAllo Welt");

        // c)
        var cons2=(Consumer)System.out::println;
        cons2.accept("cons");
        
        // d)
        var cons3=(Consumer)x->System.out.println(x);;
        cons3.accept("Hi");
    }
}
