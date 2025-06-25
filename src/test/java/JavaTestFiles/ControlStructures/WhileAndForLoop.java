class WhileAndForLoop {
    void whileLoop() {
        int i = 0;
        while (i < 10) {
            i++;
        }
    }

    void whileLoopWithBreak() {
        int i = 0;
        while (i < 10) {
            if (i == 5) {
                break;
            }
            i++;
        }
    }

    void whileLoopWithContinue() {
        int i = 0;
        while (i < 10) {
            i++;
            if (i % 2 == 0) {
                continue;
            }
        }
    }

    void nestedWhileLoop() {
        int i = 0;
        while (i < 5) {
            int j = 0;
            while (j < 3) {
                j++;
            }
            i++;
        }
    }

    void basicForLoop() {
        for (int i = 0; i < 10; i++) {
            // Do something
        }
    }

    int forLoopWithBreak() {
        int result = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                result = i;
                break;
            }
        }
        return result;
    }

    int forLoopWithContinue() {
        int sumOfEvens = 0;
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                sumOfEvens += i;
                continue;
            }
        }
        return sumOfEvens;
    }
}
