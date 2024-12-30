package A1;

import java.util.Arrays;
import java.util.stream.IntStream;


// Übung 1: In den folgenden Übungen sollen verschiedene Alternativen zur Definition von
// unveränderlichen Datentypen betrachtet werden. Implementieren Sie zunächst auf Basis einer
// regulären Java-Klasse (d.h. ohne Records) eine Klasse Vektor, die beliebig viele double-Werte
// entgegennimmt und die dargestellten public-Methoden bietet. Berücksichtigen Sie in der
// internen Implementierung, dass add und subtract auf eine gemeinsame Methode zurückgreifen,
// die die Berechnung in der gewünschten funktional zu übergebenden Rechenoperation ausführt.

public class A1 {
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
class Vector{
    private double[] werte;

    public Vector(double... werte){
        this.werte=werte;
    }

    public int getLength(){
        return werte.length;
    }

    public Vector operation(int faktor,Vector v){
        double[] result=new double[werte.length];
        for(int i=0;i<werte.length;i++){
            result[i]=werte[i]+faktor*v.werte[i];
        }
        return new Vector(result);
    }

    public Vector add(Vector v){
        return operation(1,v);
    }

    public Vector subtract(Vector v){
        return operation(-1,v);
    }

    @Override
    public String toString() {
        return Arrays.toString(werte);
    }

    public double getElement(int i){
        return werte[i];
    }
    // public Vector add(Vector werte2){
    //     // double[] result = IntStream.range(0, this.werte.length)
    //     //                        .mapToDouble(i -> this.werte[i] + other.werte[i])
    //     //                        .toArray();
    //     double[] result = new double[this.werte.length];
    //     for (int i = 0; i < this.werte.length; i++) {
    //         result[i] = this.werte[i] + werte2.werte[i];
    //     }
    //     return new Vector(result);
    // }
    // public Vector subtract(Vector werte2){
    //     double[]result =new double[this.werte.length];
    //     for (int i = 0; i < this.werte.length; i++) {
    //         result[i] = this.werte[i] - werte2.werte[i];
    //     }
    //     return new Vector(result);
    // }

}