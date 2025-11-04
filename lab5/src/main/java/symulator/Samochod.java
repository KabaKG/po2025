package symulator;

public class Samochod {
    private Silnik silnik;
    private SkrzyniaBiegow skrzynia;
    private Pozycja pozycja1;
    private int waga;
    private boolean wlaczony;

    public Samochod(Silnik silnik, SkrzyniaBiegow skrzynia, Pozycja pozycja1, int waga) {
        this.silnik = silnik;
        this.skrzynia = skrzynia;
        this.pozycja1 = pozycja1;
        this.waga = waga;
        this.wlaczony = false;
    }

    public void wlacz(){
        wlaczony = true;
        System.out.println("Samochód został włączony.");
    }
    public void wylacz(){
        wlaczony = false;
        System.out.println("Samochód został wyłączony.");
    }
    public void jedzDo(Pozycja cel){
        if (!wlaczony) {
            System.out.println("Samochód jest wyłączony!");
            return;
        }
        this.pozycja1 = cel;
        System.out.println("Samochód dojechał do: " + cel);
    }

    public int getWaga() {
        return waga;
    }
    public Pozycja getPozycja() {
        return pozycja1;
    }

    }

