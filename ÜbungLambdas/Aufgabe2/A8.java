
import java.util.Arrays;
import java.util.function.Function;

public class A8 {
    public static void main(String[] args) {
        var s1 = new String[]{"Hello", "World"};
        var s2 = new String[]{"Alex", "Chris"}; 
        swap(s1, s2);
        System.out.println(Arrays.toString(s1)); // [Alex, Chris]
        System.out.println(Arrays.toString(s2)); // [Hello, World]
        
        var i1=new Integer[]{1, 4}; 
        var d2=new Double[]{2.1, 6.8};
        swap(i1, d2,i -> Double.valueOf(i), d -> Integer.valueOf(d.intValue()));
        System.out.println(Arrays.toString(i1)); // [2, 6]
        System.out.println(Arrays.toString(d2)); // [1.0, 4.0]
    }
    public static <T1,T2> void swap(T1[] one , T2[] two, Function<T1,T2> onetotwo, Function<T2,T1> twotoone){
        
        for(int i=0; i<one.length;i++){
            T1 tempT1 = one[i];
            T2 tempT2 = two[i];

            one[i]= twotoone.apply(tempT2);
            two[i]=onetotwo.apply(tempT1);
        }
    }
    public static <T> void swap(T[] one , T[] two){
        
        for (int i = 0; i < one.length; i++) {
            T temp = one[i];
            one[i] = two[i];
            two[i] = temp;
        }
    }
}
