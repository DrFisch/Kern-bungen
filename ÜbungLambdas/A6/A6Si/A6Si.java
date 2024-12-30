package A6.A6Si;

import java.util.function.*;

public class A6Si {
    public static void main(String[] args) {
        var pow2=pow(2.0);
        System.out.println(pow2.apply(3.0)); // 8
        var pow10 = pow(10.0);
        System.out.println(pow10.apply(3.0)); // 1000
    }
    public static Function<Double,Double>pow(double d){
        return x->Math.pow(d, x);
    }
}
