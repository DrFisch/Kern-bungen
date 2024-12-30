package A25;

import java.util.Arrays;
import java.util.stream.Collectors;

public class A25AlternativMitToSet {
    public static void main(String[] args) {
        var d1 = new String[]{ "Alex", "Chris", "Mike", "Alex", "Dave","Chris", "Chris" };
        var set = Arrays.stream(d1).collect(Collectors.toSet());
        System.out.println(set);
    }
}
