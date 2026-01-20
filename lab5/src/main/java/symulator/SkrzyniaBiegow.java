package symulator;

public class SkrzyniaBiegow extends Komponent {
    private int aktualnyBieg = 0;
    private int iloscBiegow = 6;
    private int aktualnePrzelozenie;



    public void zwiekszBieg(){
        if (aktualnyBieg<iloscBiegow){
            aktualnyBieg++;
        }
        else {
            System.out.println("Najwyzszy mozliwy bieg");
        }

    }
    public void zmniejszBieg(){
        if (aktualnyBieg>0){
            aktualnyBieg--;

        }
        else {
                System.out.println("Najnizszy mozliwy bieg");
        }
    }
    public int getAktualnyBieg(){
        return aktualnyBieg;
    }
    public  int  getPrzelozenie(){
        return  aktualnePrzelozenie;
    }
}
