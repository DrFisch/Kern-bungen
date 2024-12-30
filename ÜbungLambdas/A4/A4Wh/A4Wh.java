package A4.A4Wh;

import java.util.function.Supplier;


// Übung 4: Die Erzeugung von Objekten kann manchmal teuer sein, weshalb es ratsam sein kann,
// diese erst beim Zugriff zu erzeugen, da ggf. je nach User-Interaktion das Objekt überhaupt nicht
// benötigt wird (z.B. nur wenn User bestimmten Dialog öffnet). Damit das gleiche Problem nicht
// immer wieder neu kodiert werden muss, soll in dieser Aufgabe ein Konstrukt Lazy<T> geschaffen
// werden, welches wie folgt benutzt werden kann.
// class SomeExpensiveObject { /* */ }
// var lazyObject = new Lazy(SomeExpensiveObject::new);
// lazyObject.get(); // Erst hier wird neue Instanz v. Typ erzeugt

public class A4Wh {
    public static void main(String[] args) {
        
        var lazyObject = new Lazy(SomeExpensiveObject::new);
        lazyObject.get(); // Erst hier wird neue Instanz v. Typ erzeugt

        Lazy<Pensi> lazyobject2= new Lazy(Pensi::new);
        Pensi pensi = lazyobject2.get();
        
    }
}
class SomeExpensiveObject { 
    /* */ 
}

class Lazy<T>{
    private Supplier<T> supp;

    public Lazy(Supplier<T> supp){
        this.supp=supp;
    }

    public T get(){
        return supp.get();
    }

}
class Pensi{
            
}