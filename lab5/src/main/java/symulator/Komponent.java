package symulator;

abstract public class Komponent {

    private String producent;
    private String model;
    private double cena; // Dodajemy pole cena
    private int waga;    // Dodajemy pole waga

    // --- SETTERY (Te metody są potrzebne dla NewCarController) ---

    public void setNazwa(String model) { // Mapujemy "Nazwa" z GUI na pole "model"
        this.model = model;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setWaga(int waga) {
        this.waga = waga;
    }

    // --- GETTERY ---

    public String getModel() {
        return model;
    }

    public String getProducent() {
        return producent;
    }

    public double getCena() {
        return cena;
    }
    public int getWaga() {
        return waga;
    }
}