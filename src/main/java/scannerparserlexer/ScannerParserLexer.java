package scannerparserlexer;

import ast.Program;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import scannerparserlexer.adapter.ProgramAdapter;
import scannerparserlexer.parser.ASTLexer;
import scannerparserlexer.parser.ASTParser;

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
            String input = "class emptyClass {}";
            Program program = parse(input);
            System.out.println("Successfully parsed input: " + input);
            if (program.classes != null) {
                System.out.println("Found " + program.classes.size() + " classes");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
