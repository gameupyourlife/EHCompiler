package bytecode.visitors;

import ast.Statement;
import ast.exprStatements.Assign;
import ast.exprStatements.MethodCall;
import ast.exprStatements.New;
import ast.exprStatements.Unary;
import ast.expressions.Identifier;
import ast.statements.*;
import ast.types.Type;
import ast.types.TypeResolver;
import bytecode.VarContext;
import bytecode.interfaces.IStatementBytecodeGenerator;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Map;

public class StatementBytecodeGenerator implements IStatementBytecodeGenerator {

    private final ExpressionBytecodeGenerator generator;
    private final MethodVisitor mv;
    private final VarContext context;
    private final TypeResolver resolver;

    public StatementBytecodeGenerator(
            ExpressionBytecodeGenerator generator,
            MethodVisitor mv,
            VarContext context,
            TypeResolver resolver) {
        this.generator = generator;
        this.mv = mv;
        this.context = context;
        this.resolver = resolver;
    }


    @Override
    public void visitBlock(Block stmt) {
        for (Statement statement : stmt.statements) {
            statement.accept(this);
        }
    }

    @Override
    public void visitBreak(Break stmt) {
        // label nötig
    }

    @Override
    public void visitContinue(Continue stmt) {
        // label nötig
    }

    @Override
    public void visitDoWhile(DoWhile stmt) {
        Label start = new Label();
        Label condition = new Label();
        Label end = new Label();

        mv.visitLabel(start);
        stmt.block.accept(this);

        mv.visitLabel(condition);
        stmt.condition.accept(generator);
        mv.visitJumpInsn(Opcodes.IFEQ, end);

        mv.visitJumpInsn(Opcodes.GOTO, start);
        mv.visitLabel(end);
    }

    @Override
    public void visitEmptyStatement(EmptyStatement stmt) {
        mv.visitInsn(Opcodes.NOP);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement stmt) {
        // Kann das weg?
    }

    @Override
    public void visitFor(For stmt) {
        // For hat nur ein Statement und keinen Block
    }

    @Override
    public void visitIf(If stmt) {
        Label elseLabel = new Label();
        Label end = new Label();

        stmt.condition.accept(generator);
        mv.visitJumpInsn(Opcodes.IFEQ, elseLabel);

        stmt.thenStatement.accept(this);
        mv.visitJumpInsn(Opcodes.GOTO, end);

        mv.visitLabel(elseLabel);
        if (stmt.elseStatement != null) {
            stmt.elseStatement.accept(this);
        }

        mv.visitLabel(end);
    }

    @Override
    public void visitLocalVarDecl(LocalVarDecl stmt) {
        context.declareVariable(stmt.name);
        int index = context.getLocalIndex(stmt.name);

        if (stmt.init != null) {
            stmt.init.accept(generator);
            switch (stmt.type) {
                case INT, BOOLEAN, CHAR:
                    mv.visitVarInsn(Opcodes.ISTORE, index);
                    break;
                case CLASS:
                    mv.visitVarInsn(Opcodes.ASTORE, index);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported type: " + stmt.type);
            }
        }
    }

    @Override
    public void visitReturn(Return stmt) {
        if (stmt.expression != null) {
            stmt.expression.accept(generator);
            // Typ der expression muss mit resolver irgendwie ermittelt werden können

        }

    }

    // müssen wir das überhaupt können?
    @Override
    public void visitSwitchStatement(SwitchStatement stmt) {

    }

    @Override
    public void visitWhile(While stmt) {
        Label start = new Label();
        Label end = new Label();

        mv.visitLabel(start);
        stmt.condition.accept(generator);

        mv.visitJumpInsn(Opcodes.IFEQ, end);

        stmt.block.accept(this);
        mv.visitJumpInsn(Opcodes.GOTO, start);

        mv.visitLabel(end);
    }

    @Override
    public void visitAssign(Assign stmt) {
        if (!(stmt.target instanceof Identifier)) {
            throw new UnsupportedOperationException("Assignment target must be a variable");
        }

        stmt.value.accept(generator);

        // Ich kann den Typ von Exression allgemein nicht auflösen
        Type targetType = resolver.resolve(stmt.target);
        Type valueType = resolver.resolve(stmt.value);

        if (targetType != valueType) {
            throw new UnsupportedOperationException("Type mismatch in assignment");
        }

        // unmöglich den Namen der Variablen zu bekommen || Expression in Identifier ändern!
        int index = context.getLocalIndex(ident.name);
        int index2 = context.getLocalIndex(((Identifier) stmt.target).name);
        mv.visitVarInsn(Opcodes.ISTORE, index);



//        Integer index = localVarIndex.get(stmt.variableName);
//        if (index == null) {
//            throw new RuntimeException("Variable not defined: " + stmt.variableName);
//        }
//
//        // 3. Variablentyp ermitteln
//        Type type = resolver.resolve(stmt);
//
//        // 4. Je nach Typ Wert speichern
//        switch (type) {
//            case INT, BOOLEAN, CHAR -> mv.visitVarInsn(Opcodes.ISTORE, index);
//            case OBJECT -> mv.visitVarInsn(Opcodes.ASTORE, index);
//            default -> throw new UnsupportedOperationException("Unsupported type in assignment: " + type);
    }

    @Override
    public void visitMethodCall(MethodCall stmt) {

    }

    @Override
    public void visitNew(New stmt) {

    }

    @Override
    public void visitUnary(Unary stmt) {

    }
}
