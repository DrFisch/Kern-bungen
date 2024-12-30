package A7.A7Si;

import java.util.function.*;

public class A7Si {
    public static void main(String[] args) {
        var fn_inside = outside(10);
        var result1a = fn_inside.apply(2);
        var result1b = outside(10).apply(2);
        System.out.println(result1a);
        System.out.println(result1b);
    }
    public static Function<Integer,Integer> outside(int x){
        //return y -> x+y;
        return new Function<Integer,Integer>() {
            @Override
            public Integer apply(Integer t){
                return x+t;
            }
        };
    }
}
