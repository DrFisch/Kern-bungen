package A7.A7Wh;

import java.util.function.Function;

public class A7Wh {
    public static void main(String[] args) {
        var fn_inside = outside(10);
        var result1a = fn_inside.apply(2);
        
        var result1b = outside(10).apply(2);
        System.out.println(result1a);
        System.out.println(result1b);
    }
    public static Function<Integer,Integer> outside(int x){
        return y ->  y * x;
    }
}
