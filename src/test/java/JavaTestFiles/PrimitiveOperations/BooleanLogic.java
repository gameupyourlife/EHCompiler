package JavaTestFiles.PrimitiveOperations;

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

    boolean xor(boolean a, boolean b) {
        return a ^ b;
    }

    boolean nand(boolean a, boolean b) {
        return !(a && b);
    }

    boolean nor(boolean a, boolean b) {
        return !(a || b);
    }

    boolean xnor(boolean a, boolean b) {
        return !(a ^ b);
    }
}