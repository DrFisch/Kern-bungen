package A8.A8Si;

import java.util.Arrays;
import java.util.function.*;

public class A8Si {
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
    public static <T,R> void swap(T[]i,R[]d,Function<T,R> ItoD,Function<R,T> DtoI){
        if(i.length!=d.length){
            throw new ArrayIndexOutOfBoundsException("Arrays sind nicht gleich lang");
        }
        T temp;
        for(int j=0;j<i.length;j++){
            temp=i[j];
            i[j]=DtoI.apply(d[j]);
            d[j]=ItoD.apply(temp);
        }
    }


    public static <T> void swap(T[] arr1,T[] arr2){
        T temp;
        if(arr1.length!=arr2.length){
            throw new ArrayIndexOutOfBoundsException("Arrays sind nicht gleich lang");
        }
        for(int i=0;i<arr1.length;i++){
            temp=arr1[i];
            arr1[i]=arr2[i];
            arr2[i]=temp;
        }
    }

}
