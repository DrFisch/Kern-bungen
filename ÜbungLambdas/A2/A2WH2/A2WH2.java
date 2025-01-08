package A2.A2WH2;

interface Consumer2<T>{
    void consume(T t);
    default void consume(T t, int x){
        for(int i =0;i<x;i++){
            consume(t);
        }
    }
}

public class A2WH2 {
    public static void main(String[] args) {
        Consumer2 x = System.out::print;
        x.consume("Hallo");
        x.consume("o",5);
    }
    
}
