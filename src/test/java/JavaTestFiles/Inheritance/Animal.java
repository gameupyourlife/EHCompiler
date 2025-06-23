package JavaTestFiles.Inheritance;

class Animal {
    String name;

    public Animal(String name) {
        this.name = name;
    }

    public String speak() {
        return "Animal " + name + " makes a sound.";
    }
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
    
    public String speak() {
        return "Dog " + name + " barks.";
    }
    
    public String fetch() {
        return "Dog " + name + " is fetching a ball.";
    }
}

