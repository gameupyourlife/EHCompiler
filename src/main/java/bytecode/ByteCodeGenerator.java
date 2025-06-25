package bytecode;

import ast.*;
import ast.Class;
import ast.types.Type;

import ast.types.TypeResolver;
import bytecode.visitors.ExpressionBytecodeGenerator;
import bytecode.visitors.StatementBytecodeGenerator;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ByteCodeGenerator {

    public HashMap<String, byte[]> generateByteCode(Program program) {

        List<Class> classes = program.classes;
        HashMap<String, byte[]> byteList = new HashMap<>();

        for(Class currentClass : classes){

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            String className = currentClass.name;
            int visibility = Opcodes.ACC_PUBLIC;

            cw.visit(Opcodes.V1_8,
                    visibility,
                    className,
                    null,
                    "java/lang/Object",
                    null);

            //generate fields
            cw = generateBytecodeFields(cw, currentClass.fields);

            //generate constructors
            cw = generateBytecodeStandardConstructor(cw, currentClass);

            //generate methods
            cw = generateBytecodeMethods(cw, currentClass.methods);
            cw.visitEnd();

            byte[] classBytes = cw.toByteArray();

            byteList.put(className, classBytes);
        }

        return byteList;
    }

    public ClassWriter generateBytecodeFields(ClassWriter cw, List<Field> fields) {
        if (fields.isEmpty()) {
            return cw;
        }

        for (Field field : fields) {
            String descriptor = getDescriptor(field.type);
            int access = Opcodes.ACC_PUBLIC;
            FieldVisitor fv = cw.visitField(access, field.name, descriptor, null, null);
            fv.visitEnd();
        }
        return cw;
    }

    private String getDescriptor(Type type) {
        return switch (type) {
            case INT -> "I";
            case BOOLEAN -> "Z";
            case CHAR -> "C";
            case VOID -> "V";
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    }

    public ClassWriter generateBytecodeStandardConstructor(ClassWriter cw, Class cl) {

        cw.visit(Opcodes.V1_8,
                Opcodes.ACC_PUBLIC,
                cl.name,
                null,
                "java/lang/Object",
                null);

        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC,
                "<init>",
                "()V",
                null,
                null);

        constructor.visitCode();
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL,
                "java/lang/Object",
                "<init>",
                "()V",
                false);
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(0, 0);
        constructor.visitEnd();

        return cw;
    }

    public ClassWriter generateBytecodeMethods(ClassWriter cw, List<Method> methods) {
        if (methods.isEmpty()) {
            return cw;
        }

        for (Method method : methods) {
            String descriptor = getMethodDescriptor(method);
            int access = Opcodes.ACC_PUBLIC;
            MethodVisitor mv = cw.visitMethod(access, method.name, descriptor, null, null);
            mv.visitCode();

            VarContext context = new VarContext();
            context.declareVariable("this");

            for (Parameter p : method.parameters) {
                context.declareVariable(p.name);
            }

            TypeResolver resolver = new TypeResolver();
            ExpressionBytecodeGenerator ex = new ExpressionBytecodeGenerator(mv, context, resolver);
            StatementBytecodeGenerator gen = new StatementBytecodeGenerator(ex, mv, context, resolver);

            boolean hasStatements = false;
            for (Statement statement : method.statement) {
                hasStatements = true;
                statement.accept(gen);
            }

            if (!hasStatements) {
                switch (method.type) {
                    case INT, BOOLEAN, CHAR -> {
                        mv.visitInsn(Opcodes.ICONST_0); // default-Wert
                        mv.visitInsn(Opcodes.IRETURN);
                    }
                    case CLASS -> {
                        mv.visitInsn(Opcodes.ACONST_NULL); // default-Objektwert
                        mv.visitInsn(Opcodes.ARETURN);
                    }
                    case VOID -> {
                        mv.visitInsn(Opcodes.RETURN);
                    }
                    default -> throw new UnsupportedOperationException("Unknown return type: " + method.type);
                }
            }

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        return cw;
    }

    private String getMethodDescriptor(Method method) {
        StringBuilder descriptor = new StringBuilder();
        descriptor.append('(');

        for (Parameter param : method.parameters) {
            descriptor.append(getDescriptor(param.type));
        }
        descriptor.append(')');
        descriptor.append(getDescriptor(method.type));
        return descriptor.toString();
    }
}
