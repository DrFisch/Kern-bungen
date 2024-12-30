package A12.A12Si;

import java.util.Arrays;

// Übung 12: Implementieren Sie über einen Lambdaausdruck eine Funktion multiplyElements, die
// die Elemente von 2 angegebenen Vektoren multipliziert und eine Funktion sumAll, die alle
// Elemente eines Vektors aufsummiert. Tipp: Benutzen Sie als Datentyp für multiplyElements den
// Typ BiFunction<Double[], Double[], Double[]> und für sumAll den Datentyp Function<Double[],
// Double>

import java.util.function.*;

public class A12Si {
    public static void main(String[] args) {
        Double[] v1 = new Double[]{1.0, 2.0, 3.0, 4.0};
        Double[] v2 = new Double[]{5.0, 6.0, 7.0, 8.0};

        BiFunction<Double[],Double[],Double[]> multiplyElements = (arr1,arr2)->{
            Double[] result=new Double[arr1.length];
            for(int i =0;i<v1.length;i++){
                result[i]=arr1[i]*arr2[i];
            }
            return result;
        };

        Function<Double[],Double> sumAll= (x) -> {
            return Arrays.stream(x).mapToDouble(Double::doubleValue).sum();
            // Double res =0.;
            // for (Double d : x) {
            //     res+=d;
            // }
            // return res;
        };

        Double[] vectorProduct = multiplyElements.apply(v1, v2);
        Double vectorSum = sumAll.apply(vectorProduct);
        System.out.println(vectorSum); // 70
    }
}
