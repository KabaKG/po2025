
package symulator;

public class Samochod extends Thread {

    private String model;
    private String nrRejestracyjny;

    private Silnik silnik;
    private SkrzyniaBiegow skrzynia;
    private Sprzeglo sprzeglo;

    private Pozycja pozycja1;
    private int waga;
    private boolean wlaczony;
    private int predkosc = 0;

    private Pozycja cel;
    private double mapWidth = 800;
    private double mapHeight = 600;

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


        double mnoznikBiegu = 0.02 * bieg;
        this.predkosc = (int) ((silnik.getObroty() * mnoznikBiegu))/2;

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
    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                if (wlaczony && pozycja1 != null && cel != null) {

                    aktualizujPredkosc();

                    if (this.predkosc > 0) {
                        // Teraz tylko jedna linijka!
                        // moveTo samo zaktualizuje x i y wewnątrz obiektu pozycja1
                        // oraz samo dopilnuje, żeby nie wyjechać poza 800x600
                        pozycja1.moveTo((float)this.predkosc, 0.02f, cel);
                    }
                }
                Thread.sleep(20); // 50 klatek na sekundę
            }
        } catch (InterruptedException e) {
            System.out.println("Wątek samochodu " + model + " został zatrzymany.");
        }
    }
    public void setCel(double x, double y) {
        this.cel = new Pozycja(x, y);
    }

}

