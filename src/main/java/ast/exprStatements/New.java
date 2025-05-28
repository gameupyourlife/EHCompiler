package ast.exprStatements;

import ast.Expression;
import ast.Statement;

public class New implements Expression, Statement {
    public String objectName;

    public New(String objectName) {
        this.objectName = objectName;
    }
}
