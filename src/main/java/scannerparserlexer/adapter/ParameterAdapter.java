package scannerparserlexer.adapter;

import ast.Parameter;
import scannerparserlexer.parser.ASTParser;

public class ParameterAdapter {
    public static Parameter adapt(ASTParser.FormalParameterContext ctx) {
        Parameter parameter = new Parameter();
        
        // Set parameter name
        parameter.name = ctx.Identifier().getText();
        
        // Set parameter type
        parameter.type = TypeAdapter.adapt(ctx.type());
        
        return parameter;
    }
}