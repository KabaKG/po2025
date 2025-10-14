import  java.util.Random;
import java.util.ArrayList;






    public class lotto {
        public static void main(String[] args) {
            if (args.length != 6) {
                System.out.println("Użycie: java Lotto <liczba1> <liczba2> <liczba3> <liczba4> <liczba5> <liczba6>");
                return;
            }
            ArrayList<Integer> wyniki = new ArrayList<>();
            ArrayList<Integer> trafienia = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                wyniki.add(new Random().nextInt(48));
                int x = Integer.parseInt(args[i]);
                if (wyniki.get(i).equals(x)) {
                    trafienia.add(wyniki.get(i));
                    system.out.println()
                }
            }

        }
    }