package A14;

import java.util.function.Function;

public class A14 {
    public static void main(String[] args) {
        
    }
}
class M<T>{
    T x;
    private M(){this.x=null;}
    private M(T x){this.x=x;}

    static <T> M<T> unit(T x){
        return new M<>(x);
    }

    <S> M<S> bind(Function<T,M<S>>b){
        return b.apply(x);
    }
    @Override
    public String toString(){
        return x==null?"<Leer>":x.toString();
    }
}

record M2<T>(T x){ // das oijn () ist die signatur des Konstruktors
    // Konstruktor private M(){this.x=null;} wird von records geliefert
    static <T> M2<T> unit(T x){
        return new M2<>(x);
    }
    <S> M<S> bind(Function<T,M<S>>b){
        return b.apply(x);
    }
}

interface M3<T>{
    T value();

    static <T> M3<T> unit(T value){
        return ()-> value; // legt value() fest
    }

    default <S> M3<S> bind (Function<T, M3<S>>b){
        return b.apply(value());
    }
}