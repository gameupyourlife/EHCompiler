package bytecode;

import ast.*;
import ast.Class;
import ast.exprStatements.MethodCall;
import ast.types.Type;
import ast.types.TypeResolver;
import bytecode.visitors.ExpressionBytecodeGenerator;
import bytecode.visitors.StatementBytecodeGenerator;
import org.example.context.Context;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.List;

public class ByteCodeGenerator {

    public HashMap<String, byte[]> generateByteCode(Program program) {
        List<Class> classes = program.classes;
        HashMap<String, byte[]> byteList = new HashMap<>();

        for (Class currentClass : classes) {
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            String className = currentClass.name;
            int visibility = Opcodes.ACC_PUBLIC;

            String rawParent = currentClass.parentClass;
            String parentInternal;
            if (rawParent == null || rawParent.equals("Object")) {
                parentInternal = "java/lang/Object";
            } else {
                parentInternal = rawParent.replace('.', '/');
            }

            cw.visit(Opcodes.V1_8,
                     visibility,
                     className,
                     null,
                     parentInternal,
                     null);

            cw = generateBytecodeFields(cw, currentClass.fields);

            cw = generateBytecodeStandardConstructor(cw, currentClass, parentInternal);

            cw = generateBytecodeMethods(cw, currentClass.methods, classes, program);

            cw.visitEnd();
            byteList.put(className, cw.toByteArray());
        }

        return byteList;
    }

    public ClassWriter generateBytecodeFields(ClassWriter cw, List<Field> fields) {
        for (Field field : fields) {
            String descriptor = getDescriptor(field.type);
            FieldVisitor fv = cw.visitField(Opcodes.ACC_PUBLIC, field.name, descriptor, null, null);
            fv.visitEnd();
        }
        return cw;
    }

    private String getDescriptor(Type type) {
        return switch (type) {
            case INT     -> "I";
            case BOOLEAN -> "Z";
            case CHAR    -> "C";
            case VOID    -> "V";
            default      -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    }

    public ClassWriter generateBytecodeStandardConstructor(ClassWriter cw, Class cl, String parentInternal) {
        MethodVisitor constructor = cw.visitMethod(
            Opcodes.ACC_PUBLIC,
            "<init>",
            "()V",
            null,
            null
        );

        constructor.visitCode();
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(
            Opcodes.INVOKESPECIAL,
            parentInternal,
            "<init>",
            "()V",
            false
        );
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(0, 0);
        constructor.visitEnd();
        return cw;
    }

    public ClassWriter generateBytecodeMethods(ClassWriter cw, List<Method> methods, List<Class> classes, Program program) {
        Context ctx = new Context(program);

        for (Method method : methods) {
            String descriptor = getMethodDescriptor(method);
            MethodVisitor mv;
            if (method.name.equals("main") && method.staticFlag) {
                mv = cw.visitMethod(
                    Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                    "main",
                    "([Ljava/lang/String;)V",
                    null,
                    null
                );
            } else {
                mv = cw.visitMethod(
                    Opcodes.ACC_PUBLIC,
                    method.name,
                    descriptor,
                    null,
                    null
                );
            }

            mv.visitCode();
            VarContext varCtx = new VarContext();
            varCtx.declareVariable("this");
            for (Parameter p : method.parameters) {
                varCtx.declareVariable(p.name);
            }

            TypeResolver resolver = new TypeResolver(ctx);
            ExpressionBytecodeGenerator exGen = new ExpressionBytecodeGenerator(mv, varCtx, resolver);
            StatementBytecodeGenerator stGen = new StatementBytecodeGenerator(exGen, mv, varCtx, resolver);

            boolean hasStatements = false;
            for (Statement stmt : method.statement) {
                hasStatements = true;
                if (stmt instanceof MethodCallStatement) {
                    ((MethodCallStatement) stmt).accept(stGen, classes);
                } else {
                    stmt.accept(stGen);
                }
            }

            if (hasStatements && method.type == Type.VOID) {
                mv.visitInsn(Opcodes.RETURN);
            }

            if (!hasStatements) {
                switch (method.type) {
                    case INT, BOOLEAN, CHAR -> {
                        mv.visitInsn(Opcodes.ICONST_0);
                        mv.visitInsn(Opcodes.IRETURN);
                    }
                    case CLASS -> {
                        mv.visitInsn(Opcodes.ACONST_NULL);
                        mv.visitInsn(Opcodes.ARETURN);
                    }
                    case VOID -> mv.visitInsn(Opcodes.RETURN);
                    default -> throw new UnsupportedOperationException("Unknown return type: " + method.type);
                }
            }

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        return cw;
    }

    private String getMethodDescriptor(Method method) {
        StringBuilder desc = new StringBuilder("(");
        for (Parameter p : method.parameters) {
            desc.append(getDescriptor(p.type));
        }
        desc.append(")").append(getDescriptor(method.type));
        return desc.toString();
    }
}
