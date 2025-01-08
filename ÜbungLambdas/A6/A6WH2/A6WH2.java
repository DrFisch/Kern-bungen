package A6.A6WH2;

import java.util.function.Function;

public class A6WH2 {
    public static void main(String[] args) {
        var pow2 = pow(2.0).apply(3.0);
        System.out.println(pow2);
    }
    static Function<Double,Double> pow(double basis){
        return exp -> Math.pow(basis, exp);
    }
}
