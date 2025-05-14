package scannerparserlexer;

import ast.Program;

public class TestParser {
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
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}