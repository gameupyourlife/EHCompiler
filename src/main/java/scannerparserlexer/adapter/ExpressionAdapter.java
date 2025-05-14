package scannerparserlexer.adapter;

import ast.Expression;
import scannerparserlexer.parser.ASTParser;

public class ExpressionAdapter {
    public static Expression adapt(ASTParser.ExpressionContext ctx) {
        if (ctx.primary() != null) {
            return adaptPrimary(ctx.primary());
        } else if (ctx.methodCallExpr() != null) {
            return adaptMethodCall(ctx);
        } else if (ctx.assignExpr() != null) {
            return adaptAssign(ctx);
        } else {
            // Default case for other expression types
            return new ast.expressions.EmptyExpression();
        }
    }
    
    private static Expression adaptPrimary(ASTParser.PrimaryContext ctx) {
        if (ctx.Identifier() != null) {
            return new ast.expressions.Identifier(ctx.Identifier().getText());
        } else if (ctx.literal() != null) {
            return adaptLiteral(ctx.literal());
        } else {
            return new ast.expressions.EmptyExpression();
        }
    }
    
    private static Expression adaptLiteral(ASTParser.LiteralContext ctx) {
        if (ctx.IntegerLiteral() != null) {
            return new ast.expressions.IntConst(Integer.parseInt(ctx.IntegerLiteral().getText()));
        } else if (ctx.BooleanLiteral() != null) {
            return new ast.expressions.BooleanLiteral(Boolean.parseBoolean(ctx.BooleanLiteral().getText()));
        } else if (ctx.CharacterLiteral() != null) {
            String charText = ctx.CharacterLiteral().getText();
            // Remove the quotes
            char charValue = charText.substring(1, charText.length() - 1).charAt(0);
            return new ast.expressions.CharConst(charValue);
        } else if (ctx.StringLiteral() != null) {
            String stringText = ctx.StringLiteral().getText();
            // Remove the quotes
            String stringValue = stringText.substring(1, stringText.length() - 1);
            return new ast.expressions.StringConst(stringValue);
        } else {
            return new ast.expressions.Null();
        }
    }
    
    private static Expression adaptMethodCall(ASTParser.ExpressionContext ctx) {
        return new ast.expressions.MethodCall();
    }
    
    private static Expression adaptAssign(ASTParser.ExpressionContext ctx) {
        return new ast.expressions.Assign();
    }
}