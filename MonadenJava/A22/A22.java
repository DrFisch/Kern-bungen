package A22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class A22 {
    public static void main(String[] args) {
        double[] d1 = {1.0, 2.0, 3.0, 4.0};

        // Erzeuge einen DoubleStream aus dem Array
        DoubleStream doubleStream = DoubleStream.of(d1);
        DoubleStream doubleStream2 = DoubleStream.of(d1);

        // Verwende collect, um die Werte als Points in eine ArrayList<Point> zu sammeln
        List<Point> result = doubleStream.collect(
            ArrayList::new,                  // Supplier: Erstellt eine neue ArrayList<Point>
            (list, value) -> list.add(new Point(value, value)), // Accumulator: Fügt Point(x, y) zur Liste hinzu
            ArrayList::addAll                // Combiner: Kombiniert Listen (für parallele Verarbeitung)
        );

        //methode 2
        List<Point> result2 =doubleStream2.mapToObj(value -> new Point(value,value)).collect(Collectors.toList());

        // Ausgabe des Ergebnisses
        System.out.println(result); // Ausgabe: [Point[x=1.0, y=1.0], Point[x=2.0, y=2.0], ...]
        System.out.println(result2);

        Point p = new Point(1.5, 3.7);
        System.out.println(p);

        //Test mit Methodenreferenz
        List<Point2> result3 =Arrays.stream(d1).mapToObj(Point2::new).collect(Collectors.toList());
        System.out.println(result3);

        List<Point> result4 =Arrays.stream(d1).mapToObj(A22::createPoint).collect(Collectors.toList());
        System.out.println(result4);
    }
    public static Point createPoint(double x){
        return new Point(x,x);
    }
}
record Point(double x, double y){}
record Point2(double x, double y){
    public Point2(double x) {
        //Ruft den Primärkonstruktor Point2(x,x) auf
        this(x,x);
    }
}