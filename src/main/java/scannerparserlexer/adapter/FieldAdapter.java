package scannerparserlexer.adapter;

import ast.Field;
import scannerparserlexer.parser.ASTParser;

public class FieldAdapter {
    public static Field adapt(ASTParser.FieldDeclarationContext ctx) {
        Field field = new Field();
        
        // Set field name
        field.name = ctx.Identifier().getText();
        
        // Set field type
        field.type = TypeAdapter.adapt(ctx.type());
        
        // Set default value if exists
        if (ctx.expression() != null) {
            // For simplicity, we'll just store the text of the expression
            // In a real implementation, we would use an ExpressionAdapter here
            field.defaultValue = ctx.expression().getText();
        }
        
        return field;
    }
}