import java.util.function.Consumer;

public class UebungLambdas {
    // a)
    interface Syso<T>{
        void schreibe (T printable);
       }
       
    public static void main(String[] args) {

        //a)
       Syso<String> drucker = System.out::println;
       drucker.schreibe("servus");

       // b)
       Consumer<String> konsumierer=System.out::println;
        konsumierer.accept("servus die zweite");

        // c)
        var konsumierer2 = (Consumer<String>) System.out::println;
        konsumierer2.accept("Servus die dritte");

        // d)
        Syso<String> drucker2= x -> System.out.println(x);
        drucker2.schreibe("servus zum letzten");
    }
}
