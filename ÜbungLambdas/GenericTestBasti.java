import java.util.function.Function;


//Problem:
// public class GenericTestBasti {
//     public static void main(String[] args) {
//         var add = add(10);
//         var x=add.apply(14);
//         System.out.println(x);
//     }
//     static Function<T,T> add(T t){
//         return r->t+r;
//     }
// }

//Lösung:
// public class GenericTestBasti {
//     public static void main(String[] args) {
//         var add = add(10.5); 
//         System.out.println(add.apply(5));    
//         System.out.println(add.apply(5.5)); 
//     }

//     static Function<Number, Double> add(Number t) {
//         return r -> t.doubleValue() + r.doubleValue();
//     }
// }
public class GenericTestBasti {
    public static void main(String[] args) {
        var add = add(10.5); // Funktion erstellen, die 10.5 addiert
        System.out.println(add.apply(5));    // 15.5
        System.out.println(add.apply(5.5));  // 16.0
    }

    static <T extends Number> Function<Number, Double> add(T t) {
        return r -> t.doubleValue() + r.doubleValue();
    }
}