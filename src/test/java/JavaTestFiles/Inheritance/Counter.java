class Counter {
    int inc(int x) {
        return x + 1;
    }
}

class FancyCounter extends Counter {
    int inc(int x) {
        return x + 2;
    }

    int reset() {
        return 0;
    }
}