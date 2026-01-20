package symulator;

public class Pozycja {
    private double x;
    private double y;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Pozycja(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void moveTo(float predkosc, float vdt, Pozycja cel) {
        double dx = cel.getX() - this.x;
        double dy = cel.getY() - this.y;
        double dxy = Math.sqrt(dx * dx + dy * dy);
        if (dxy == 0) {
            // Już jesteśmy w celu
            System.out.println("Jesteśmy już u celu: " + this.x + ", " + this.y);
            return;
        }

        // Obliczamy krok w kierunku celu
        double dxt = predkosc * vdt * (dx / dxy);
        double dyt = predkosc * vdt * (dy / dxy);

        // Aktualizujemy pozycję
        this.x += dxt;
        this.y += dyt;

        System.out.println("Nowa pozycja: " + this.x + ", " + this.y);
    }
}