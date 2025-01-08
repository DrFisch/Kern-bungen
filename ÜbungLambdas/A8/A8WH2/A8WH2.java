package A8.A8WH2;

import java.util.Arrays;
import java.util.function.Function;

public class A8WH2 {
    public static void main(String[] args) {
        // teil1

        var s1 = new String[]{"Hello","World"};
        var s2 = new String[]{"Alex", "Chris"};
        swap (s1,s2);
        System.out.println(Arrays.toString(s1));
        System.out.println(Arrays.toString(s2));

        // teil2 

        var ints = new Integer[]{1,4};
        var doubles = new Double[]{2.1,6.7};
        swap(ints,doubles,i -> Double.valueOf(i),d -> Integer.valueOf(d.intValue()));
        System.out.println(Arrays.toString(ints));
        System.out.println(Arrays.toString(doubles));

    }
    static <T> void swap(T[] arr1,T[] arr2){
        for(int i=0;i<arr1.length;i++){
            T temp = arr1[i];
            arr1[i]=arr2[i];
            arr2[i]=temp;
        }
    }
    // T=Integer
    // R = Double
    static <T,R> void swap(T[] intarr, R[] doublearr, Function<T,R> intToDouble, Function<R,T> doubleToInt){
        for(int i=0;i<intarr.length;i++){
            T temp = intarr[i];
            intarr[i]= doubleToInt.apply(doublearr[i]);
            doublearr[i]=intToDouble.apply(temp);
        }
    }
}
