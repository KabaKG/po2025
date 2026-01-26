package symulator;

public class Pozycja {
    private double x;
    private double y;
    private double maxWidth = 2000;
    private double maxHeight = 1200;
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

        double krok = predkosc * vdt;

        if (dxy < krok) {
            this.x = cel.getX();
            this.y = cel.getY();
            System.out.println("Jesteśmy już u celu: " + this.x + ", " + this.y);
            return;
        }




        if (krok > dxy) {
            this.x = cel.getX();
            this.y = cel.getY();
            return;
        }



        double dxt = krok * (dx / dxy);
        double dyt = krok * (dy / dxy);

        double nextX = this.x + dxt;
        double nextY = this.y + dyt;

        if (nextX < 0) {
            this.x = 0;
        } else if (nextX > maxWidth) {
            this.x = maxWidth;
        } else {
            this.x = nextX;
        }


        if (nextY < 0) {
            this.y = 0;
        } else if (nextY > maxHeight) {
            this.y = maxHeight;
        } else {
            this.y = nextY;
        }

    }
}

