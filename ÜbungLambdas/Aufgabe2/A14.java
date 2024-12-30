import java.util.function.Function;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class A14 {

    public static void main(String[] args) {
        Double[] vector1 = {1.0, 2.0, 3.0, 4.0};

        
        Function<Double[], Double[]> filterEven = (array) -> {
            List<Double> res=new ArrayList<>();
            for(var el:array){
                if(el%2==0)
                    res.add(el);
            }
            return res.toArray(Double[]::new);
            // return Arrays.stream(array)
            //              .filter(x -> x % 2 == 0)
            //              .toArray(Double[]::new);
        };

        
        Function<Double[], Double> sumAll = array -> {Double result = 0.0;
            for(Double el:array){
                result+=el;
            }
        return result;};

        
        Function<Double[], Double> sumOfEven = sumAll.compose(filterEven);

        Double vectorSum3 = sumOfEven.apply(vector1);
        System.out.println(vectorSum3); // Ausgabe: 6.0
    }
}
