package A2;

import java.util.stream.*;
import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;

// Übung 2: Ändern Sie nun den vorherigen Code, sodass anstelle einer Klasse nun ein Record
// Vektor implementiert wird, der beliebig viele double-Werte entgegennimmt und die
// dargestellten public-Methoden bietet. Berücksichtigen Sie auch hier in der internen
// Implementierung, dass add und subtract auf eine gemeinsame Methode zurückgreifen, die die
// Berechnung in der gewünschten funktional zu übergebenden Rechenoperation ausführt. Welche
// Nachteile hat dieser Ansatz?

public class A2 {
    public static void main(String[] args) {
        final var v1 = new Vector(1.0, 2.4, 3.8, 4.5);
        final var v2 = new Vector(2.5, 3.1, 4.8, 5.2);
        final var v3 = v1.add(v2);
        final var v4 = v3.subtract(new Vector(1.0, 1.0, 1.0, 1.0));
        System.out.println(v3); 
        System.out.println(v4);
        System.out.println("Länge " + v4.getLength()+ ", Element 0: " + v4.getElement(0));
    }
}
record Vector(double... values){
    public Vector add(Vector v){
        return operation((x,y)->x+y, v);
    }
    public Vector subtract(Vector v){
        return operation((x,y)->x-y, v);
    }
    // public Vector operation(int faktor, Vector v){
    //     return new Vector(
    //         IntStream.range(0, values().length)
    //         .mapToDouble(i->values()[i]+faktor*v
    //         .values()[i]).toArray()
    //         );
    // }
    public Vector operation(DoubleBinaryOperator bin,Vector v ){
        if(v.getLength()!=this.getLength()){
            throw new IllegalArgumentException("Die Vektoren sind nicht gleich lang.");
        }
        double[] result=new double[values().length];
        for(int i=0; i<values.length;i++){
            result[i]=bin.applyAsDouble(values()[i], v.values()[i]);
        }
        return new Vector(result);
    }

    public int getLength(){
        return values.length;
    }
    public double getElement(int i){
        return values()[i];
    }
    public String toString(){
        return Arrays.toString(values());
    }
}

//NAchteile: man muss Instanzen bei jeder Rechenoperation neu erstellen, weil records final sind