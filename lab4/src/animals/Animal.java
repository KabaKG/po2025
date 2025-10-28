package animals;


public abstract class Animal {

    public String name = "";
    public int legs = 0;

    public static Animal getRandomAnimal() {

        int randomNum = (int) (Math.random() * 3);
        switch (randomNum) {

            case 0:
                return new Dog();

            case 1:

                return new parrot();

            case 2:

                return new Snake();
            default:
                return null;

        }

    }
    public String getDescription(){
        return "This is an animal named " + name + " with " + legs + " legs.";

    }
}

