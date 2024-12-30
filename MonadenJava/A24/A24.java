package A24;

import java.util.function.Supplier;
import java.util.function.BiConsumer;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Gatherer;
import java.util.stream.Collectors;


public class A24 {
    public static void main(String[] args) {
        var d1 = new String[]{ "Alex", "Chris", "Mike", "Alex", "Dave","Chris", "Chris" };
        var duplicateStatistics2 = Arrays.stream(d1).gather(
        new DuplicateCounter2<String>()).collect(Collectors.toList());
        System.out.println(duplicateStatistics2);
    }
}
record DuplicateCounter2<T>()
    implements Gatherer<T, Map<T, Integer>, String>
{
    @Override
    public Supplier<Map<T, Integer>> initializer() {
        return () -> new HashMap<T, Integer>();
    }

    @Override
    public Integrator<Map<T, Integer>, T, String> integrator() {       
        // Bei Greedy ist Rückgabewert egal, da immer alle Inputs verarbeitet werden
        // return Gatherer.Integrator.ofGreedy((agg, el, downstream) -> agg.put(el, agg.getOrDefault(el, 0) + 1) == null);            

        // true ist wichtig, da sonst Verarbeitung weiterer Elemente abgebrochen wird
        // return Gatherer.Integrator.of((agg, el, downstream) -> { agg.put(el, agg.getOrDefault(el, 0) + 1); return true; });            

        // true ist wichtig da sonst Verarbeitung weiterer Elemente abgebrochen wird
        return (agg, el, downstream) -> { agg.put(el, agg.getOrDefault(el, 0) + 1); return true; };            
    }

    @Override
    public BiConsumer<Map<T, Integer>, Downstream<? super String>> finisher() {
        return (agg, downstream) -> {
            String res = agg.entrySet().stream()
            .sorted((el1,el2)->el2.getValue().compareTo(el1.getValue())).map(x -> x.getValue()+": "+x.getKey()).collect(Collectors.joining(", "));
            downstream.push(res);
        };
    }
}

// record DuplicateCounter2<T>()
//     implements Gatherer<T, Map<T, Integer>, String>
// {
//     @Override
//     public Supplier<Map<T, Integer>> initializer() {
//         return HashMap::new;
//     }

//     @Override
//     public Integrator<Map<T, Integer>, T, Map<T, Integer>> integrator() {
//         return (agg, el, downstream) -> {
//             agg.merge(el, 1, Integer::sum);
//             return true; // Rückgabe von true, um die Verarbeitung fortzusetzen
//         };
//     }

//     @Override
//     public BiConsumer<Map<T, Integer>, Downstream<? super String>> finisher() {
//         return (agg, downstream) ->{ 
//             String result = agg.entrySet().stream()
//             .sorted((el1,el2)->el2.getValue().compareTo(el2.getValue())).map(x ->x.getValue()+": "+x.getKey()).collect(Collectors.joining(","));
//             downstream.push(result);
//         };
//     }
// }
