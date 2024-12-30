package A3;

import java.util.Arrays;
import java.util.function.Consumer;

interface Konsumierer<T>  {
    void essen(T food);

    
    default void essen(T food,int cnt){
        for(int i =0;i<cnt;i++){
            essen(food);
        }
    }
    default void essen(T[] foods){
        System.out.println(Arrays.toString(foods));
    }
}

public class A3Lisa {
    public static void main(String[] args) {
        
        Konsumierer<String> k=System.out::println;
        k.essen(new String[]{"Gorte","Gorti","Gorteflausch"});
    }
}
