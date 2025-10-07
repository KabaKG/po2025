import  java.util.Random;
public class Lotto {
    public static void main(String[] args) {
        int[] tablica = new int[10];
        for (int i = 0; i < 6; i++) {
            tablica[i] = new Random().nextInt(48) + 1;
        System.out.println(tablica[i]);
        }
    }
}
