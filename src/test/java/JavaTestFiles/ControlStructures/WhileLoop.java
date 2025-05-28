package JavaTestFiles.ControlStructures;

class WhileLoop {
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
}
