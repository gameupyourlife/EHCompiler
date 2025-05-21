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
            String descriptor = getDescriptor(field);
            int access = Opcodes.ACC_PUBLIC;
            FieldVisitor fv = cw.visitField(access, field.name, descriptor, null, null);
            fv.visitEnd();
        }
        return cw;
    }

    private String getDescriptor(Field field) {
        switch (field.type) {
            case INT:
                return "I";
            case BOOLEAN:
                return "Z";
            case CHAR:
                return "C";
            case VOID:
                return "V";
            default:
                if (field.customTypeName == null)
                    throw new IllegalStateException("CUSTOM type requires customTypeName");
                return "L" + field.customTypeName.replace('.', '/') + ";";
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

        // Bytecode muss hier generiert werden

        return cw;
    }
}
