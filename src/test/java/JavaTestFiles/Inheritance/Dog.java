package JavaTestFiles.Inheritance;

public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
    
    @Override
    public String speak() {
        return "Dog " + name + " barks.";
    }
    
    public String fetch() {
        return "Dog " + name + " is fetching a ball.";
    }
}

