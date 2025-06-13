package bytecode;

import ast.Program;
import ast.Field;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

    public class ByteArrayClassLoader extends ClassLoader {
        public java.lang.Class<?> defineClass(String name, byte[] b) {
            return super.defineClass(name, b, 0, b.length);
        }
    }