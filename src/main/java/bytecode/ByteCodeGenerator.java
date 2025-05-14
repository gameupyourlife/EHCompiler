package bytecode;

import ast.Class;
import ast.Field;
import ast.Method;
import ast.Program;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ByteCodeGenerator {

    public HashMap<String, byte[]> generateByteCode(Program program, File outputDir) {

        List<Class> classes = (List<Class>) program.classes;
        HashMap<String, byte[]> byteList = new HashMap<>();



        for(Class currentClass : classes){

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            int visibility = Opcodes.ACC_PUBLIC;
            cw.visit(Opcodes.V1_8,
                    visibility,
                    currentClass.name,
                    null,
                    "java/lang/Object",
                    null);

            String type = currentClass.name;

            //generate fields
            cw = generateByteCodeFields(cw, currentClass.fields);

            //generate constructors
            cw = generateByteCodeStandardConstructor(cw, currentClass);

            //generate methods
            cw = generateByteCodeMethods(cw, currentClass.methods);
            cw.visitEnd();

            byteList.put(currentClass.name, cw.toByteArray());
        }
        return byteList;
    }

    public ClassWriter generateByteCodeFields(ClassWriter cw, List<Field> fields) {
        if (fields.isEmpty()) {
            return cw;
        }

        // Bytecode muss hier generiert werden
        return cw;
    }

    public ClassWriter generateByteCodeStandardConstructor(ClassWriter cw, Class cl) {

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

        // return cw.toByteArray();
        return cw;
    }

    public ClassWriter generateByteCodeMethods(ClassWriter cw, List<Method> methods) {
        if (methods.isEmpty()) {
            return cw;
        }

        // Bytecode muss hier generiert werden

        return cw;
    }
}
