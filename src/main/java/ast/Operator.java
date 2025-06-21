package ast;

public enum Operator {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULO("%"),

    NEGATE("!"),      
    UMINUS("-"),     
    INCREMENT("++"),  
    DECREMENT("--"),

    EQUALS("=="),
    NOT_EQUALS("!="),
    LESS_THAN("<"),
    LESS_OR_EQUALS("<="),
    GREATER_THAN(">"),
    GREATER_OR_EQUALS(">=");



    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
