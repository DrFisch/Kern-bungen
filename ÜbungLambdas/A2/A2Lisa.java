package A2;

import java.util.function.Consumer;

interface Konsumierer<T>  {
    void essen(T food);

    
    default void essen(T food,int cnt){
        for(int i =0;i<cnt;i++){
            essen(food);
        }
    }
    
}

public class A2Lisa {
    public static void main(String[] args) {
        
        Konsumierer<String> k=x ->System.out.print(x);
        k.essen("Hallo");
        k.essen("o", 5);
    }
}
