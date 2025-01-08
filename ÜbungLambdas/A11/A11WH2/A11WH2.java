package A11.A11WH2;

import java.util.Iterator;
import java.util.function.Function;

public class A11WH2 {
    public static void main(String[] args) {
        var sequence = new Sequence<>(10, i -> (i+1)*(i+1));
        for (var s: sequence)
            System.out.println(s);
    }
}
class Sequence<T> implements Iterable{
    int wdh;
    Function<Integer,T> func;

    public Sequence(int wdh, Function<Integer,T> func){
        this.wdh=wdh;
        this.func=func;
    }

    public Iterator<T> iterator(){
        return new Iterator<T>(){
            int cnt=0;
            @Override
            public boolean hasNext(){
                return cnt<wdh;
            }
            public T next(){
                var x=func.apply(cnt);
                cnt++;
                return x;
            }
        }
        ;
    }
}