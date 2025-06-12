package bytecode;

import ast.*;
import ast.Class;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.util.HashMap;
import java.util.List;

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
        switch (type) {
            case INT:
                return "I";
            case BOOLEAN:
                return "Z";
            case CHAR:
                return "C";
            case VOID:
                return "V";
            default:
                throw new IllegalArgumentException("Unsupported type: " + type);
        }
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
            emitDefaultReturn(mv, method.type);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        return cw;
    }

    // To Do: Einzelne Methoden für jeweilige Statement / Expressions mit visitor pattern

    private void emitDefaultReturn(MethodVisitor mv, Type returnType) {
        switch (returnType) {
            case VOID:
                mv.visitInsn(Opcodes.RETURN);
                break;
            case INT:
            case BOOLEAN:
            case CHAR:
                mv.visitInsn(Opcodes.ICONST_0);
                mv.visitInsn(Opcodes.IRETURN);
                break;
            default:
                throw new IllegalArgumentException("Unknown return type: " + returnType);
        }
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
