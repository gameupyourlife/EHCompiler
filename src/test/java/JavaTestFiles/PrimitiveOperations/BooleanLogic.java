class BooleanLogic {

    boolean and(boolean a, boolean b) {
        return a && b;
    }

    boolean or(boolean a, boolean b) {
        return a || b;
    }

    boolean not(boolean a) {
        return !a;
    }

    boolean nand(boolean a, boolean b) {
        return !(a && b);
    }

    boolean nor(boolean a, boolean b) {
        return !(a || b);
    }

    boolean complexOperation(int a, boolean b, boolean c) {
        return ((a != 0) && b) || ((a == 0) && c);
    }
}