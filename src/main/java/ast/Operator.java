package ast;

public enum Operator {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULUS("%"),

    NEGATE("!"),      
    UMINUS("-"),     
    INCREMENT("++"),  
    DECREMENT("--");  

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
