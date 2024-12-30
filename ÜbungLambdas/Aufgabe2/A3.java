

interface Consumer3<T>{
    void consume(T t);
    default void consume(T[] arr){
        for (T x : arr) {
            consume(x);
        }
    }
}


public class A3 {
    public static void main(String[] args) {
        Consumer3<String> x = System.out::println;
        x.consume(new String[]{"Alex", "Chris", "Mike"});
    }
}
