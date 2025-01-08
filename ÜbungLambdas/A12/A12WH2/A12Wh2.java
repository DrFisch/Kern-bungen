package A12.A12WH2;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public class A12Wh2 {
    public static void main(String[] args) {

        BiFunction<Double[],Double[],Double[]> multiplyElements= (x,y)->{
            Double[] arr= new Double[x.length];
            for(int i=0;i<x.length;i++){
                arr[i]=x[i]*y[i];
            }
            return arr;
        };

        Function<Double[],Double> sumAll= x->{
            Double sum =Arrays.stream(x).reduce(0.,Double::sum);
            return sum;
        };

        Double[] v1 = new Double[]{1.0, 2.0, 3.0, 4.0};
        Double[] v2 = new Double[]{5.0, 6.0, 7.0, 8.0};
        Double[] vectorProduct = multiplyElements.apply(v1, v2);
        Double vectorSum = sumAll.apply(vectorProduct);
        System.out.println(vectorSum); // 70
    }
}
