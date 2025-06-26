package bytecode.visitors;

import ast.*;
import ast.Class;
import ast.exprStatements.Assign;
import ast.exprStatements.MethodCall;
import ast.exprStatements.New;
import ast.exprStatements.Unary;
import ast.expressions.Identifier;
import ast.statements.*;
import ast.types.ClassResolver;
import ast.types.Type;
import ast.types.TypeResolver;
import bytecode.VarContext;
import bytecode.interfaces.IStatementBytecodeGenerator;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

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
        context.enterScope();
        try {
            for (Statement statement : stmt.statements) {
                statement.accept(this);
            }
        } finally {
            context.leaveScope();
        }
    }

    @Override
    public void visitDoWhile(DoWhile stmt) {
        Label start = new Label();
        Label condition = new Label();
        Label end = new Label();

        mv.visitLabel(start);
        stmt.statement.accept(this);

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
    public void visitFor(For stmt) {
        Label start = new Label();
        Label check = new Label();
        Label end = new Label();

        if (stmt.init != null) {
            stmt.init.accept(this);
        }

        mv.visitJumpInsn(Opcodes.GOTO, check);

        mv.visitLabel(start);
        stmt.statement.accept(this);

        if (stmt.update != null) {
            stmt.update.accept(generator);
            mv.visitInsn(Opcodes.POP);
        }

        mv.visitLabel(check);
        if (stmt.condition != null) {
            stmt.condition.accept(generator);
            mv.visitJumpInsn(Opcodes.IFEQ, end);
        }

        mv.visitJumpInsn(Opcodes.GOTO, start);

        mv.visitLabel(end);
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
        if (index < 0) {
            System.out.println("Alarm!");
        }

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
            Type type = stmt.expression.resolveType(resolver);

            switch (type) {
                case INT, BOOLEAN, CHAR -> mv.visitInsn(Opcodes.IRETURN);
                case CLASS -> mv.visitInsn(Opcodes.ARETURN);
                default -> throw new UnsupportedOperationException("Unsupported return type: " + type);
            }
        } else {
            mv.visitInsn(Opcodes.RETURN);
        }
    }

    @Override
    public void visitWhile(While stmt) {
        Label start = new Label();
        Label end = new Label();

        mv.visitLabel(start);
        stmt.condition.accept(generator);

        mv.visitJumpInsn(Opcodes.IFEQ, end);

        stmt.statement.accept(this);

        mv.visitJumpInsn(Opcodes.GOTO, start);

        mv.visitLabel(end);
    }

    @Override
    public void visitAssign(Assign stmt) {
        if (!(stmt.target instanceof Identifier)) {
            throw new UnsupportedOperationException("Assignment target must be a variable");
        }

        stmt.value.accept(generator);

        Type targetType = stmt.target.resolveType(resolver);
        Type valueType = stmt.value.resolveType(resolver);

        if (targetType != valueType) {
            throw new UnsupportedOperationException("Type mismatch in assignment");
        }

        int index = context.getLocalIndex(((Identifier) stmt.target).name);

        if (index < 0) {
            throw new IllegalStateException("Variable not declared: " + ((Identifier) stmt.target).name);
        }

        switch (targetType) {
            case INT, BOOLEAN, CHAR -> mv.visitVarInsn(Opcodes.ISTORE, index);
            case CLASS -> mv.visitVarInsn(Opcodes.ASTORE, index);
            default -> throw new UnsupportedOperationException("Unsupported type in assignment: " + targetType);
        }
    }

    @Override
    public void visitMethodCall(MethodCall stmt, List<Class> classes) {
        stmt.target.accept(generator);

        List<Type> argTypes = new ArrayList<>();
        for (Expression arg : stmt.arguments) {
            arg.accept(generator);
            argTypes.add(arg.resolveType(resolver));
        }

        ClassResolver classResolver = new ClassResolver(resolver);


        Type returnType = null;
        boolean typeFound = false;

        for (Class cls : classes) {
            for (Method m : cls.methods) {
                var parameters = m.parameters;
                var arguments = stmt.arguments;
                if (stmt.methodName.equals(m.name) && matchParameterTypes(parameters, arguments)) {
                    returnType = m.type;
                    typeFound = true;
                    break;
                }
            }
            if (typeFound) {
                break;
            }
        }

        String owner = classResolver.resolveClassName(stmt.target);
        String descriptor = classResolver.makeMethodDescriptor(returnType, argTypes, owner);

        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                owner,
                stmt.methodName,
                descriptor,
                false
        );

        if (returnType != Type.VOID) {
            mv.visitInsn(Opcodes.POP);
        }
    }

    private boolean matchParameterTypes(List<Parameter> parameters, List<Expression> expressions) {
        if (parameters.size() != expressions.size()) {
            return false;
        }

        for (int i = 0; i < parameters.size(); i++) {
            Type paramType = parameters.get(i).type;
            Type argType = expressions.get(i).resolveType(resolver);

            if (!paramType.equals(argType)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void visitNew(New stmt) {
        generator.visitNew(stmt);
        mv.visitInsn(Opcodes.POP);
    }

    @Override
    public void visitUnary(Unary stmt) {
        Operator op = stmt.operator;
        generator.visitUnary(stmt);

        if (op == Operator.UMINUS || op == Operator.NEGATE) {
            mv.visitInsn(Opcodes.POP);
        }
    }

    @Override
    public void visitPrintLnStatement(PrintlnStatement printlnStatement) {

        Type type = printlnStatement.expression.resolveType(resolver);
        ClassResolver classResolver = new ClassResolver(resolver);

        String descriptor = classResolver.getTypeDescriptor(type, "");

        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        printlnStatement.expression.accept(generator);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "("+ descriptor + ")V", false);
    }

    @Override
    public void visitPrintStatement(PrintStatement printStatement) {

    }
}
