package A14;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;;

public class A14 {
    public static void main(String[] args) {
        Double[] v1 = new Double[]{1.0, 2.0, 3.0, 4.0};
        Function<Double[], Double[]> filterEven = x -> 
            {
                List<Double> result= new ArrayList<Double>();
                for (Double item : x) {
                    if(item%2==0){
                        result.add(item);
                    }
                }
                return result.toArray(Double[]::new);
            };
        Function<Double[], Double> sumAll =arr ->{
           return Arrays.stream(arr).reduce(0.,(x,y)->Double.sum(x, y));
            
        };
        Function<Double[],Double> sumofEven=sumAll.compose(filterEven);
        Double vectorSum=sumofEven.apply(v1);
        System.out.println(vectorSum);
    }
}
