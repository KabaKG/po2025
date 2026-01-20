
package symulator;

public class Samochod {

    private String model;
    private String nrRejestracyjny;

    private Silnik silnik;
    private SkrzyniaBiegow skrzynia;
    private Sprzeglo sprzeglo;

    private Pozycja pozycja1;
    private int waga;
    private boolean wlaczony;
    private int predkosc = 0;

    public Samochod(String model,String nrRej,Sprzeglo sprzeglo,Silnik silnik, SkrzyniaBiegow skrzynia, Pozycja pozycja1, int waga) {
        this.silnik = silnik;
        this.skrzynia = skrzynia;
        this.pozycja1 = pozycja1;
        this.waga = waga;
        this.sprzeglo = sprzeglo;
        this.model = model;
        this.nrRejestracyjny = nrRej;
        this.wlaczony = false;
    }
    public  Samochod(){
        this.silnik = new Silnik();
        this.skrzynia = new SkrzyniaBiegow();
        this.pozycja1 = null;
        this.waga = 0;
        this.wlaczony = false;
        System.out.println("Stworzono pojazd domyśly!!");
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
        else{
         
            }
        }
    public void aktualizujPredkosc() {
        if (!wlaczony || sprzeglo.getStan()) {
            // Jeśli auto wyłączone lub sprzęgło wciśnięte, silnik nie napędza kół
            this.predkosc = 0;
            return;
        }

        int bieg = skrzynia.getAktualnyBieg();
        if (bieg == 0) { // Luz
            this.predkosc = 0;
            return;
        }

        // Uproszczony wzór: Prędkość = (Obroty * Bieg) / Stała_Przełożenia
        // Przykład: 1. bieg jest wolny, 5. bieg jest szybki
        double mnoznikBiegu = 0.02 * bieg;
        this.predkosc = (int) (silnik.getObroty() * mnoznikBiegu);

        System.out.println("Aktualna prędkość: " + this.predkosc + " km/h (Bieg: " + bieg + ")");
    }



    public int getWaga() {
        return waga;
    }
    public Pozycja getPozycja() {
        return pozycja1;
    }
    public Silnik getSilnik() { return silnik; }
    public SkrzyniaBiegow getSkrzynia() { return skrzynia; }
    public Sprzeglo getSprzeglo() { return sprzeglo; }
    public String getModel() { return model; }
    public String getNrRejestracyjny() {return nrRejestracyjny; }
    public int getPredkosc() { return predkosc; }
    public boolean isWlaczony() {
        return wlaczony;
    }
}

