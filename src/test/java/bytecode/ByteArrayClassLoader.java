package bytecode;

public class ByteArrayClassLoader extends ClassLoader {
    public java.lang.Class<?> defineClass(String name, byte[] b) {
        return super.defineClass(name, b, 0, b.length);
    }
}