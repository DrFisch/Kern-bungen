package A13.A13Si;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

// Übung 13: Java unterstützt die Komposition von Funktionen u.a. über die Methode andThen.
// Lesen Sie sich in die Verwendung dieser Methode ein und implementieren Sie unter Zuhilfenahme
// dieser Methode eine zusammengesetzte Funktion scalarProduct durch Kombination der
// Funktionen multiplyElements und sumAll.
// BiFunction<Double[], Double[], Double> scalarProduct = …
// Double vectorSum2 = scalarProduct.apply(vector1, vector2);
// System.out.println(vectorSum2); // 70

public class A13Si {
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
            
        };
        BiFunction<Double[], Double[], Double> scalarProduct =multiplyElements.andThen(sumAll);
        Double vectorSum2 = scalarProduct.apply(v1, v2);
        System.out.println(vectorSum2);
    }
}
