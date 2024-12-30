package A25;

import java.util.function.Supplier;
import java.util.function.BiConsumer;
import java.util.Arrays;
import java.util.stream.Gatherer;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

public class A25 {
    public static void main(String[] args) {
        var d1 = new String[]{ "Alex", "Chris", "Mike", "Alex", "Dave","Chris", "Chris" };
        var duplicateStatistics2 = Arrays.stream(d1).gather(
        new DuplicateCounter2<String>()).collect(Collectors.toList());
        System.out.println(duplicateStatistics2);
        }
}
record DuplicateCounter2<T>()
    implements Gatherer<T, Set<T>, T>
{
    @Override
    public Supplier<Set<T>> initializer() {
        return HashSet::new;
    }

    @Override
    public Integrator<Set<T>, T, T> integrator() {       
        return (agg, el, downstream) -> { 
            if(agg.add(el)){
                downstream.push(el);
            } 
            return true; 
        };            
    }

    @Override
    public BiConsumer<Set<T>, Downstream<? super T>> finisher() {
        return (agg, downstream) -> {};
    }
}