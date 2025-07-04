package ast;

public enum Operator {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULUS("%"),

    EQUALS("=="),
    NOT_EQUALS("!="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL("<="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL(">="),
    
    AND("&&"),
    OR("||"),
    NOT("!"),
    XOR("^"),
    NAND("!&&"),
    NOR("!||"),
    XNOR("!^"),

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
