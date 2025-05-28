package ast.statements;

import ast.Expression;
import ast.Statement;
import ast.Type;
import bytecode.interfaces.IStatementBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SwitchStatement implements Statement {
    public Expression expression;
    public List<SwitchCase> cases;
    
    public SwitchStatement() {
        this.cases = new ArrayList<>();
    }
    
    public SwitchStatement(Expression expression, List<SwitchCase> cases) {
        this.expression = expression;
        this.cases = cases;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitSwitchStatement(this);
    }

    public static class SwitchCase {
        public Expression caseValue; // null for default
        public List<Statement> statements;
        
        public SwitchCase() {
            this.statements = new ArrayList<>();
        }
        
        public SwitchCase(Expression caseValue, List<Statement> statements) {
            this.caseValue = caseValue;
            this.statements = statements;
        }
    }
}