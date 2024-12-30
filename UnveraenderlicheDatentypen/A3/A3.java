package A3;

import java.util.function.DoubleBinaryOperator;
import java.util.Arrays;
import java.util.stream.IntStream;

public class A3 {
    public static void main(String[] args) {
        final var v1 = Vector.create(1.0, 2.4, 3.8, 4.5);
        final var v2 = Vector.create(2.5, 3.1, 4.8, 5.2);
        final var v3 = v1.add(v2);
        final var v4 = v3.subtract(Vector.create(1.0, 1.0, 1.0, 1.0));
        System.out.println(v3.asString());
        System.out.println(v4.asString());
        System.out.println("Länge " + v4.getLength()
        + ", Element 0: " + v4.getElement(0));
    }
}
interface Vector{
    static Vector create(double... values){
        return new Vector() {
            private final double[] elements =values;

            @Override
            public Vector add(Vector v) {
                return operation(v, (a,b)->a+b);
            }

            @Override
            public Vector subtract(Vector v) {
                return operation(v, (a, b) -> a - b);
            }

            @Override
            public int getLength() {
                return elements.length;
            }

            @Override
            public double getElement(int index) {
                return elements[index];
            }

            @Override
            public String asString() {
                return Arrays.toString(elements);
            }

            @Override
            public Vector operation(Vector v, DoubleBinaryOperator operator) {
                if (this.getLength() != v.getLength()) {
                    throw new IllegalArgumentException("Die Vektoren müssen die gleiche Länge haben");
                }
                
                double[] result = IntStream.range(0, elements.length)
                                           .mapToDouble(i -> operator.applyAsDouble(elements[i], v.getElement(i)))
                                           .toArray();
                return Vector.create(result); 
            }
        };
    
    }

    Vector add(Vector v);

    Vector subtract(Vector v);

    int getLength();

    double getElement(int index);

    String asString();

    Vector operation(Vector v, DoubleBinaryOperator operator);
}
