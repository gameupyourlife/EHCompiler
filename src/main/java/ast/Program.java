package ast;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public List<Class> classes;

    public Program() {
        this.classes = new ArrayList<>();
    }

    public Program(List<Class> classes) {
        this.classes = classes;
    }

    public void addClass(Class clazz) {
        if (classes == null) {
            classes = new ArrayList<>();
        }
        classes.add(clazz);
    }
}
