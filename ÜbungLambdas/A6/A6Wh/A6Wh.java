package A6.A6Wh;

import java.util.function.Function;

public class A6Wh {
    public static void main(String[] args) {
        var pow2=pow(5.0);
        System.out.println(pow2.apply(3.)); // 8
        var pow10 = pow(10.0);
        System.out.println(pow10.apply(3.0)); // 1000
    }
    public static Function<Double,Double> pow(double d){
        return x -> Math.pow(d, x);
    }
}
