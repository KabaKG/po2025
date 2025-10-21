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
            ArrayList<Integer> typy = new ArrayList<Integer>();
            Random rand = new Random();

            for (int i = 0; i < 6; i++) {
                int x = Integer.parseInt((args[i]));
                typy.add(x);
            }
            while (wyniki.size() < 6) {
                int liczba = rand.nextInt(49) + 1;
                boolean istnieje = false;

                for (int i = 0; i < wyniki.size(); i++) {
                    if (wyniki.get(i) == liczba) {
                        istnieje = true;
                        break;
                    }
                }
                if(!istnieje){
                    wyniki.add(liczba);
                }
            }

            for (int i = 0; i < typy.size(); i++) {
                int typ = typy.get(i);
                for (int j = 0; j < wyniki.size(); j++) {
                    if (typ == wyniki.get(j)) {
                        trafienia.add(typ);
                    }
                }
            }

            System.out.print("Twoje typy: [");
            for (int i = 0; i < typy.size(); i++) {
                System.out.print(typy.get(i));
                if (i < typy.size() - 1) System.out.print(", ");
            }
            System.out.println("]");

            System.out.print("Wylosowane liczby: [");
            for (int i = 0; i < wyniki.size(); i++) {
                System.out.print(wyniki.get(i));
                if (i < wyniki.size() - 1) System.out.print(", ");
            }
            System.out.println("]");

            System.out.println("Liczba trafień: " + trafienia.size());
            if (trafienia.size() > 0) {
                System.out.print("Trafione liczby: [");
                for (int i = 0; i < trafienia.size(); i++) {
                    System.out.print(trafienia.get(i));
                    if (i < trafienia.size() - 1) System.out.print(", ");
                }
                System.out.println("]");
            }
        }
    }




