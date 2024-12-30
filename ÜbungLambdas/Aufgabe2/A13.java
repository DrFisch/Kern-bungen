import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.Arrays;

public class A13 {

    public static void main(String[] args) {
        Double[] vector1 = {1.0,2.0, 3.0, 4.0};
        Double[] vector2 = {5.0, 6.0, 7.0,8.0};

        
        BiFunction<Double[], Double[], Double[]> multiplyElements = (v1, v2) -> {
            Double[] result = new Double[v1.length];
            for (int i = 0; i < v1.length; i++) {
                result[i] = v1[i] * v2[i];
            }
            return result;
        };

        
        Function<Double[], Double> sumAll = array -> {Double result = 0.0;
        for(Double el:array){
            result+=el;
        }
    return result;};
        //        Arrays.stream(array).reduce(0.0, Double::sum);

        
        BiFunction<Double[], Double[], Double> scalarProduct = multiplyElements.andThen(sumAll);
        //BiFunction<Double[], Double[], Double> scalarProduct2 = sumAll.compose(multiplyElements);

        Double vectorSum2 = scalarProduct.apply(vector1, vector2);
        System.out.println(vectorSum2); // Ausgabe: 70.0
    }
}
