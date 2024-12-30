package A11;

import java.util.Iterator;
import java.util.function.Function;

public class A11 {
    public static void main(String[] args) {
        var sequence = new Sequence<>(10, i -> (i+1)*(i+1));
        for (var s : sequence)
            System.out.println(s);
    }
}
class Sequence<T> implements Iterable<T>{

    Function<Integer,T> berechne;
    public int cnt;
    

    public Sequence(int cnt, Function<Integer,T> berechne){
        this.cnt=cnt;
        this.berechne=berechne;
    }

    public Iterator<T> iterator() {
		return new Iterator<T>(){
            private int pos =0;
            
            @Override
            public boolean hasNext(){
                return pos<cnt;
            }
            @Override
			public T next() {
				return berechne.apply(pos++);
			}
        };
	}

}
