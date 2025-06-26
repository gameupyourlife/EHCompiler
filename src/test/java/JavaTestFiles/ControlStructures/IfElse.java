class IfElse {
    boolean isZeroOrPositive(int number) {
        if (number > 0) {
            return true;
        }
        if (number == 0) {
            return true;
        }
        return false;
    }

    boolean isEven(int number) {
        if (number % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    boolean isInRange(int number, int min, int max) {
        if (number >= min && number <= max) {
            return true;
        } else {
            return false;
        }
    }
}
