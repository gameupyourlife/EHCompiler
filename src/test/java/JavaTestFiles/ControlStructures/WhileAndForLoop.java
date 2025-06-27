package JavaTestFiles.ControlStructures;

class WhileAndForLoop {

    void basicForLoop() {
        for (int i = 0; i < 10; i++) {
            // Do something
        }
    }

    int forLoopTest() {
        int sumOfEvens = 0;
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                sumOfEvens++;
                sumOfEvens++;
            }
        }
        return sumOfEvens;
    }

//    void whileLoop() {
//        int i = 0;
//        while (i < 10) {
//            i++;
//        }
//    }
//
//    void whileLoopWithBreak() {
//        int i = 0;
//        while (i < 10) {
//            if (i == 5) {
//                break;
//            }
//            i++;
//        }
//    }
//
//    void whileLoopWithContinue() {
//        int i = 0;
//        while (i < 10) {
//            i++;
//            if (i % 2 == 0) {
//                continue;
//            }
//        }
//    }
//
//    void nestedWhileLoop() {
//        int i = 0;
//        while (i < 5) {
//            int j = 0;
//            while (j < 3) {
//                j++;
//            }
//            i++;
//        }
//    }
}
