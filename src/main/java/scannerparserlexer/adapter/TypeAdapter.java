package scannerparserlexer.adapter;

import ast.Type;
import scannerparserlexer.parser.ASTParser;

public class TypeAdapter {
    public static Type adapt(ASTParser.TypeContext ctx) {
        // Parse the type from the context
        String typeText = ctx.getText();

        // Map the type to our Type enum
        switch (typeText) {
            case "int":
                return Type.INT;
            case "boolean":
                return Type.BOOLEAN;
            case "char":
                return Type.CHAR;
            case "void":
                return Type.VOID;
            default:
                throw new IllegalArgumentException("Inkompatibler Typ");
        }
    }
}