import java.util.function.Predicate;


public class Test {
    public static void main(String[] args) {
        Predicate<Integer> gorte = (i)->i==1;
        Integer in=5;
        System.out.println(gorte.test(in));
    }
}
