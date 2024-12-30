package A12;

import java.util.function.BiFunction;
import java.util.function.Function;

public class A12 {
    public static void main(String[] args) {

        BiFunction<Double[],Double[],Double[]>multiplyElements=
        (arr1,arr2)->{
            Double[]res=new Double[arr1.length];
            for (int i=0;i<arr1.length;i++) {
                res[i]=arr1[i]*arr2[i];
            }
            return res;
        };
        
        Function<Double[],Double> sumAll=arr->{
            Double res=0.;
            for (Double d : arr) {
                res+=d;
            }
            return res;
        };

        Double[] v1 = new Double[]{1.0, 2.0, 3.0, 4.0};
        Double[] v2 = new Double[]{5.0, 6.0, 7.0, 8.0};
        Double[] vectorProduct = multiplyElements.apply(v1, v2);
        Double vectorSum = sumAll.apply(vectorProduct);
        System.out.println(vectorSum); // 70
    }
    
}
