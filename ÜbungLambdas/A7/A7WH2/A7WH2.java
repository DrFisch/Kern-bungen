package A7.A7WH2;

import java.util.function.Function;

public class A7WH2 {
    public static void main(String[] args) {
        var result= outside(10).apply(4);
        System.out.println(result);
    }
    static Function<Integer,Integer> outside(int x){
        return y -> x*y;
    }
}
