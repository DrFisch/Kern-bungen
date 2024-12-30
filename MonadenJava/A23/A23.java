package A23;

import java.util.function.Supplier;
import java.util.function.BiConsumer;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Gatherer;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

public class A23 {
    public static void main(String[] args) {
        var d1 = new String[]{ "Alex", "Chris", "Mike", "Alex", "Dave","Chris", "Chris" };
    var duplicateStatistics = Arrays.stream(d1).gather(
    new DuplicateCounter<String>()).collect(Collectors.toList());
    System.out.println(duplicateStatistics);
    // [{Alex=2, Mike=1, Chris=3, Dave=1}]
    }
}
record DuplicateCounter<T>() implements Gatherer<T, Map<T, Integer>, Map<T, Integer>>
{
    @Override
    public Supplier<Map<T, Integer>> initializer() {
        return () -> new HashMap<T, Integer>();
    }

    @Override
    public Integrator<Map<T, Integer>, T, Map<T, Integer>> integrator() {       
        // Bei Greedy ist Rückgabewert egal, da immer alle Inputs verarbeitet werden
        // return Gatherer.Integrator.ofGreedy((agg, el, downstream) -> agg.put(el, agg.getOrDefault(el, 0) + 1) == null);            

        // true ist wichtig, da sonst Verarbeitung weiterer Elemente abgebrochen wird
        // return Gatherer.Integrator.of((agg, el, downstream) -> { agg.put(el, agg.getOrDefault(el, 0) + 1); return true; });            

        // true ist wichtig da sonst Verarbeitung weiterer Elemente abgebrochen wird
        return (agg, el, downstream) -> { agg.put(el, agg.getOrDefault(el, 0) + 1); return true; };            
    }

    @Override
    public BiConsumer<Map<T, Integer>, Downstream<? super Map<T, Integer>>> finisher() {
        return (agg, downstream) -> downstream.push(agg);
    }
}

record DuplicateCounter2<T>()
    implements Gatherer<T, Map<T, Integer>, Map<T, Integer>>
{
    @Override
    public Supplier<Map<T, Integer>> initializer() {
        return HashMap::new;
    }

    @Override
    public Integrator<Map<T, Integer>, T, Map<T, Integer>> integrator() {             
        return (agg, el, _) -> agg.merge(el, 1, Integer::sum) != null;            
    }

    @Override
    public BiConsumer<Map<T, Integer>, Downstream<? super Map<T, Integer>>> finisher() {
        return (agg, downstream) -> downstream.push(agg);
    }
}

record DuplicateCounter3<T>()
    implements Gatherer<T, Map<T, Integer>, String>
{
    @Override
    public Supplier<Map<T, Integer>> initializer() {
        return HashMap::new;
    }

    @Override
    public Integrator<Map<T, Integer>, T, String> integrator() {       
        return (agg, el, _) -> { agg.compute(el, (_, value) -> value == null ? 1 : value + 1); return true; };            
    }

    @Override
    public BiConsumer<Map<T, Integer>, Downstream<? super String>> finisher() {
        return (agg, downstream) -> downstream.push(agg.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .map((Map.Entry<T, Integer> el) -> el.getValue() + ": " + el.getKey())
            .collect(Collectors.joining(", "))
        );
    }
}

record DuplicateCounter4<T>()
    implements Gatherer<T, Map<T, Integer>, String>
{
    @Override
    public Supplier<Map<T, Integer>> initializer() {
        return HashMap::new;
    }

    @Override
    public Integrator<Map<T, Integer>, T, String> integrator() {       
        return (agg, el, _) -> agg.compute(el, (_, value) -> value == null ? 1 : value + 1) != null;            
    }

    @Override
    public BiConsumer<Map<T, Integer>, Downstream<? super String>> finisher() {
        return (agg, downstream) -> downstream.push(agg.entrySet().stream()
            .sorted(Map.Entry.<T, Integer>comparingByValue().reversed())
            .map((Map.Entry<T, Integer> el) -> el.getValue() + ": " + el.getKey())
            .collect(Collectors.joining(", "))
        );
    }
}

// Man kann alternativ in integrator() gleich (ggf. gemapptes) Element bzw. Teilergebnis via downstream weitergeben,
// was für Echtzeitverarbeitung hilfreich ist (d.h. downstream in integrator() am besten für Einzelelementverarbeitung nutzen)
record DuplicateFilter<T>()
    implements Gatherer<T, Set<T>, Set<T>>
{
    @Override
    public Supplier<Set<T>> initializer() {
        return HashSet::new;
    }

    @Override
    public Integrator<Set<T>, T, Set<T>> integrator() {             
        return (agg, el, downstream) -> agg.add(el) ? downstream.push(Set.of(el)) : true;            
    }
}

record DuplicateFilter2<T>()
    implements Gatherer<T, Set<T>, T>
{
    @Override
    public Supplier<Set<T>> initializer() {
        return HashSet::new;
    }

    @Override
    public Integrator<Set<T>, T, T> integrator() {             
        return (agg, el, downstream) -> agg.add(el) ? downstream.push(el) : true;            
    }
}