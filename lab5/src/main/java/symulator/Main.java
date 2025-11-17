package symulator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program działa!");
        Silnik silnik = new Silnik();
        SkrzyniaBiegow skrzynia = new SkrzyniaBiegow();
        Pozycja pozycja0 = new Pozycja(0,0);
        Pozycja pozycja1 = new Pozycja(100, 100);
        Samochod samochod = new Samochod(silnik,skrzynia,pozycja1,100);
        samochod.wlacz();
        pozycja0.moveTo(10,2,pozycja1);
        samochod.jedzDo(pozycja1);

    }
}