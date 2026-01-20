package symulator;

public class Silnik extends Komponent{
    private boolean czyWlaczony = false;
    private int maxObroty = 0;
    private int obroty;

    public void uruchom(){
        czyWlaczony = true;
        System.out.println("Silnik jest wlaczony!");
    }
    public void zatrzymaj(){
        czyWlaczony = false;
        System.out.println("Silnik jest wylaczony!");
    }
    public boolean  getCzyWlaczony(){
        return czyWlaczony;
    }
    public int getObroty() {
        return obroty;
    }

    public void setObroty(int obroty) {
        this.obroty = obroty;
    }
}
