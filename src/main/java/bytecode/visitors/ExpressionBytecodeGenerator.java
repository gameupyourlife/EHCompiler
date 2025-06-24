package bytecode.visitors;

import ast.Expression;
import ast.Operator;
import ast.exprStatements.Assign;
import ast.exprStatements.MethodCall;
import ast.exprStatements.New;
import ast.exprStatements.Unary;
import ast.expressions.*;
import ast.types.ClassResolver;
import ast.types.Type;
import ast.types.TypeResolver;
import bytecode.VarContext;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;


public class ExpressionBytecodeGenerator implements IExpressionBytecodeGenerator {

    private final MethodVisitor mv;
    private final VarContext context;
    private final TypeResolver resolver;

    public ExpressionBytecodeGenerator(MethodVisitor mv, VarContext context, TypeResolver resolver) {
        this.mv = mv;
        this.context = context;
        this.resolver = resolver;
    }

    @Override
    public void visitBooleanConst(BooleanConst expr) {
        if (expr.value) {
            mv.visitInsn(Opcodes.ICONST_1);
        } else {
            mv.visitInsn(Opcodes.ICONST_0);
        }
    }

    @Override
    public void visitBooleanLiteral(BooleanLiteral expr) {
        if (expr.value) {
            mv.visitInsn(Opcodes.ICONST_1);
        } else {
            mv.visitInsn(Opcodes.ICONST_0);
        }
    }

    @Override
    public void visitCharConst(CharConst expr) {
        char value = expr.value;
        if (value <= 127) {
            mv.visitIntInsn(Opcodes.BIPUSH, (int) value);
        } else if (value <= 32767) {
            mv.visitIntInsn(Opcodes.SIPUSH, (int) value);
        } else {
            mv.visitLdcInsn(value);
        }
    }

    @Override
    public void visitEmptyExpression(EmptyExpression expr) {
        mv.visitInsn(Opcodes.NOP);
    }

    @Override
    public void visitIdentifier(Identifier expr) {
        Type exprType = resolver.resolve(expr);
        int index = context.getLocalIndex(expr.name);

        if (exprType == Type.INT || exprType == Type.BOOLEAN || exprType == Type.CHAR) {
            mv.visitVarInsn(Opcodes.ILOAD, index);
        } else {
            mv.visitVarInsn(Opcodes.ALOAD, index);
        }
    }

    @Override
    public void visitIntConst(IntConst expr) {
        int value = expr.value;
        if (value >= -1 && value <= 5) {
            mv.visitInsn(Opcodes.ICONST_0 + value);
        } else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            mv.visitIntInsn(Opcodes.BIPUSH, value);
        } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            mv.visitIntInsn(Opcodes.SIPUSH, value);
        } else {
            mv.visitLdcInsn(value);
        }
    }

    @Override
    public void visitNull(Null expr) {
        mv.visitInsn(Opcodes.ACONST_NULL);
    }

    @Override
    public void visitSuper(Super expr) {
        // muss man noch anpassen
        mv.visitVarInsn(Opcodes.ALOAD, 0);
    }

    @Override
    public void visitThis(This expr) {
        // nochmal anpassen
        mv.visitVarInsn(Opcodes.ALOAD, 0);
    }

    @Override
    public void visitBinary(Binary expr) {
        expr.left.accept(this);
        expr.right.accept(this);

        Type type = resolver.resolve(expr);

        generateBinaryOp(expr.operator, type);
    }

    private void generateBinaryOp(Operator op, Type type) {
        Label trueLabel = new Label();
        Label falseLabel = new Label();
        Label endLabel = new Label();

        switch (op) {
            case PLUS: mv.visitInsn(Opcodes.IADD); break;
            case MINUS: mv.visitInsn(Opcodes.ISUB); break;
            case MULTIPLY: mv.visitInsn(Opcodes.IMUL); break;
            case DIVIDE: mv.visitInsn(Opcodes.IDIV); break;
            case MODULUS: mv.visitInsn(Opcodes.IREM); break;

            case EQUALS: mv.visitJumpInsn(Opcodes.IF_ICMPEQ, trueLabel); break;
            case NOT_EQUALS:  mv.visitJumpInsn(Opcodes.IF_ICMPNE, trueLabel); break;
            case LESS_THAN: mv.visitJumpInsn(Opcodes.IF_ICMPLT, trueLabel); break;
            case LESS_OR_EQUALS: mv.visitJumpInsn(Opcodes.IF_ICMPLE, trueLabel); break;
            case GREATER_THAN: mv.visitJumpInsn(Opcodes.IF_ICMPGT, trueLabel); break;
            case GREATER_OR_EQUALS: mv.visitJumpInsn(Opcodes.IF_ICMPGE, trueLabel); break;
            default: throw new UnsupportedOperationException("operator " + op + " not supported");
        }

        mv.visitLabel(falseLabel);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, endLabel);

        mv.visitLabel(trueLabel);
        mv.visitInsn(Opcodes.ICONST_1);

        mv.visitLabel(endLabel);
    }

    @Override
    public void visitUnary(Unary expr) {
        Operator op = expr.operator;
        Type type = resolver.resolve(expr);

        switch (op) {
            case UMINUS:
                if (type != Type.INT) {
                    throw new UnsupportedOperationException("- only supports int");
                }
                expr.expression.accept(this);
                mv.visitInsn(Opcodes.INEG);
                break;
            case NEGATE:
                if (type != Type.BOOLEAN) {
                    throw new UnsupportedOperationException("! only supports boolean");
                }
                expr.expression.accept(this);
                mv.visitInsn(Opcodes.ICONST_1);
                mv.visitInsn(Opcodes.IXOR);
                break;
            case INCREMENT,  DECREMENT:
                if (type != Type.INT) {
                    throw new UnsupportedOperationException("++/-- only supports int");
                }
                if (!(expr.expression instanceof Identifier)) {
                    throw new UnsupportedOperationException("! only supports identifier");
                }
                int index = context.getLocalIndex(((Identifier) expr.expression).name);
                mv.visitVarInsn(Opcodes.ILOAD, index);
                if (op == Operator.INCREMENT) {
                    mv.visitInsn(Opcodes.ICONST_1);
                    mv.visitInsn(Opcodes.IADD);
                } else {
                    mv.visitInsn(Opcodes.ICONST_1);
                    mv.visitInsn(Opcodes.ISUB);
                }
                mv.visitVarInsn(Opcodes.ISTORE, index);
                mv.visitVarInsn(Opcodes.ILOAD, index);
                break;
            default:
                throw new UnsupportedOperationException("Unknown unary operator: " + op);
        }
    }

    @Override
    public void visitAssign(Assign expr) {
        expr.accept(this);
        mv.visitInsn(Opcodes.DUP);
        int index = context.getLocalIndex(((Identifier) expr.target).name);
        mv.visitVarInsn(Opcodes.ISTORE, index);
    }

    @Override
    public void visitMethodCall(MethodCall expr) {
        expr.target.accept(this);

        ClassResolver classResolver = new ClassResolver(resolver);

        List<Type> argTypes = new ArrayList<>();
        for (Expression arg : expr.arguments) {
            arg.accept(this);
            argTypes.add(arg.resolveType(resolver));
        }

        Type returnType = resolver.resolve(expr);
        String owner = classResolver.resolveClassName(expr.target);
        String descriptor = classResolver.makeMethodDescriptor(returnType, argTypes, owner);

        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                owner,
                expr.methodName,
                descriptor,
                false
        );
    }

    @Override
    public void visitNew(New expr) {
        ClassResolver classResolver = new ClassResolver(resolver);
        String internalName = classResolver.resolveClassName(expr.objectName);

        mv.visitTypeInsn(Opcodes.NEW, internalName);
        mv.visitInsn(Opcodes.DUP);

        mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                internalName,
                "<init>",
                "()V",
                false
        );
    }
}
