package Zoo;

import animals.Animal;

public class Zoo {
    private Animal[] animals;

    public Zoo(){
            animals = new Animal[100];
            for (int i = 0; i < animals.length; i++) {
                animals[i] = Animal.getRandomAnimal();
                System.out.println(animals[i].getDescription());
            }
    }
    public int Leg_counter(){
        int sum = 0;
        for (Animal a : animals) {
            sum += a.legs; // bierze nogi z podklasy
        }
        return sum;

    }
}
