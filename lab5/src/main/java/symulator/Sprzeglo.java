package symulator;

public class Sprzeglo extends Komponent {
    private boolean stan = false;

    public void wcisnij(){
        stan = true;
    }
    public void zwolnij(){
        stan = false;
    }
    public boolean getStan(){return stan;}
    public void setStan(boolean b){stan=b;}
}
