package symulator;

public class Sprzeglo extends Komponent {
    private boolean stan;

    public void wcisnij(){
        stan = true;
    }
    public void zwolnij(){
        stan = false;
    }
}
