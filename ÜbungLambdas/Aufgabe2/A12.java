import java.util.function.BiFunction;
import java.util.function.Function;

public class A12 {
    public static void main(String[] args) {
        





        BiFunction<Double[],Double[],Double[]> multiplyElements = (vector1,vector2) -> {
            Double[] ausgabe = new Double[vector1.length];
            for (int i =0; i<vector1.length;i++) {
                ausgabe[i]=vector1[i]*vector2[i];
            }
            return ausgabe;
        };

        Function<Double[], Double> sumAll= vector -> {
            Double lol =0.;
            for (Double d : vector) {
                lol +=d;
            }
            return lol;
        };


        Double[] v1 = new Double[]{1.0, 2.0, 3.0, 4.0};
        Double[] v2 = new Double[]{5.0, 6.0, 7.0, 8.0};
        Double[] vectorProduct = multiplyElements.apply(v1, v2);
        Double vectorSum = sumAll.apply(vectorProduct);
        System.out.println(vectorSum); // 70

    }
    
}
