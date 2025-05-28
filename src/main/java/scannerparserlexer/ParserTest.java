package scannerparserlexer;

import ast.Program;

public class ParserTest {
    public static void main(String[] args) {
        try {
            // Test with a simple class
            String input = "class EmptyClass {}";
            System.out.println("Parsing input: " + input);
            
            Program program = ScannerParserLexer.parse(input);
            
            System.out.println("Successfully parsed input!");
            if (program.classes != null) {
                System.out.println("Found " + program.classes.size() + " classes");
                
                for (ast.Class clazz : program.classes) {
                    System.out.println("Class name: " + clazz.name);
                    
                    if (clazz.fields != null) {
                        System.out.println("  Fields: " + clazz.fields.size());
                    }
                    
                    if (clazz.methods != null) {
                        System.out.println("  Methods: " + clazz.methods.size());
                    }
                }
            }
            
            // Test with a more complex class
            input = "class ComplexClass {\n" +
                    "  int field1;\n" +
                    "  boolean field2 = true;\n" +
                    "  \n" +
                    "  void method1() {\n" +
                    "    int localVar = 5;\n" +
                    "  }\n" +
                    "  \n" +
                    "  int method2(int param1, boolean param2) {\n" +
                    "    if (param2) {\n" +
                    "      return param1 + 10;\n" +
                    "    } else {\n" +
                    "      return param1 - 5;\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
            
            System.out.println("\nParsing more complex input...");
            program = ScannerParserLexer.parse(input);
            
            System.out.println("Successfully parsed complex input!");
            if (program.classes != null) {
                System.out.println("Found " + program.classes.size() + " classes");
                
                for (ast.Class clazz : program.classes) {
                    System.out.println("Class name: " + clazz.name);
                    
                    if (clazz.fields != null) {
                        System.out.println("  Fields: " + clazz.fields.size());
                        for (ast.Field field : clazz.fields) {
                            System.out.println("    - " + field.name + " (Type: " + field.type + ")");
                        }
                    }
                    
                    if (clazz.methods != null) {
                        System.out.println("  Methods: " + clazz.methods.size());
                        for (ast.Method method : clazz.methods) {
                            System.out.println("    - " + method.name + " (Return Type: " + method.type + ")");
                            if (method.parameters != null) {
                                System.out.println("      Parameters: " + method.parameters.size());
                                for (ast.Parameter param : method.parameters) {
                                    System.out.println("        - " + param.name + " (Type: " + param.type + ")");
                                }
                            }
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}