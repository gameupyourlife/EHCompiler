package JavaTestFiles.Inheritance;

public class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public String speak() {
        return "Animal " + name + " makes a sound.";
    }
}
