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
    int laenge;
    Function<Integer, T> berechne;


    public Sequence(int laenge, Function<Integer, T> berechne){
        this.laenge=laenge;
        this.berechne=berechne;
    }

    public Iterator<T> iterator() {
		return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < laenge;
            }

            @Override
            public T next() {
                return berechne.apply(currentIndex++);
            }
        };
	}

}
