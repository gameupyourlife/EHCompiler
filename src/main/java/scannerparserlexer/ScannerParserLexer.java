package scannerparserlexer;

import ast.Program;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.ASTLexer;
import parser.ASTParser;
import scannerparserlexer.adapter.ProgramAdapter;

public class ScannerParserLexer {
    public static Program parse(String input) throws Exception {
        ASTLexer lexer = new ASTLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new ThrowingErrorListener());

        ASTParser parser = new ASTParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new ThrowingErrorListener());

        ASTParser.ProgramContext programContext = parser.program();

        Program program = ProgramAdapter.adapt(programContext);

        return program;
    }

    public static void main(String[] args) {
        try {
            String input = "class TestClass { static void main() {} }";
            Program program = parse(input);
            System.out.println("Successfully parsed input: " + input);
            if (program.classes != null) {
                System.out.println("Found " + program.classes.size() + " classes");
                if (!program.classes.isEmpty()) {
                    ast.Class firstClass = program.classes.get(0);
                    System.out.println("Class name: " + firstClass.name);
                    if (firstClass.methods != null && !firstClass.methods.isEmpty()) {
                        System.out.println("Found " + firstClass.methods.size() + " methods");
                        for (ast.Method method : firstClass.methods) {
                            System.out.println("Method: " + method.name + " (static: " + method.staticFlag + ")");
                            if (method.parameters != null) {
                                System.out.println("  Parameters: " + method.parameters.size());
                                for (ast.Parameter param : method.parameters) {
                                    System.out.println("    - " + param.name + " (" + param.type + ")");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
