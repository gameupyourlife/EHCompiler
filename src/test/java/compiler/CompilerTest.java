package compiler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.example.semantic.semanticCheck;
import org.junit.jupiter.api.Test;

import ast.Program;
import bytecode.ByteCodeGenerator;
import scannerparserlexer.ScannerParserLexer;

public class CompilerTest {
    public static class ByteArrayClassLoader extends ClassLoader {
        public java.lang.Class<?> defineClass(String name, byte[] b) {
            return super.defineClass(name, b, 0, b.length);
        }
    }

    @Test
    void testCompileEmptyClass() throws Exception {
        String emptyClassInput = "class EmptyClass {}";
        Program scannerProgram = ScannerParserLexer.parse(emptyClassInput);
        Program semanticProgram = semanticCheck.generateTast(scannerProgram);
        ByteCodeGenerator gen = new ByteCodeGenerator();

        var bytecodeMap = gen.generateByteCode(semanticProgram);
        byte[] bytes = bytecodeMap.get("EmptyClass");
        
        assertNotNull(bytes, "Bytecode should not be null");
        java.lang.Class<?> dyn = new ByteArrayClassLoader()
                .defineClass("EmptyClass", bytes);

        assertEquals("EmptyClass", dyn.getSimpleName());
        assertEquals(Object.class, dyn.getSuperclass());
    }
}
