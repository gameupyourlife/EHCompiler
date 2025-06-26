package scannerparserlexer.adapter;

import ast.Expression;
import ast.Operator;
import ast.exprStatements.Assign;
import ast.exprStatements.MethodCall;
import ast.exprStatements.Unary;
import scannerparserlexer.parser.ASTParser;

public class ExpressionAdapter {
    public static Expression adapt(ASTParser.ExpressionContext ctx) {
        if (ctx instanceof ASTParser.PrimaryExprContext) {
            ASTParser.PrimaryExprContext primaryCtx = (ASTParser.PrimaryExprContext) ctx;
            return adaptPrimary(primaryCtx.primary());
        } else if (ctx instanceof ASTParser.MethodCallExprContext) {
            ASTParser.MethodCallExprContext methodCallCtx = (ASTParser.MethodCallExprContext) ctx;
            return adaptMethodCall(methodCallCtx);
        } else if (ctx instanceof ASTParser.AssignExprContext) {
            ASTParser.AssignExprContext assignCtx = (ASTParser.AssignExprContext) ctx;
            return adaptAssign(assignCtx);
        } else if (ctx instanceof ASTParser.NewExprContext) {
            ASTParser.NewExprContext newCtx = (ASTParser.NewExprContext) ctx;
            return adaptNewExpr(newCtx);
//        } else if (ctx instanceof ASTParser.UnaryExprContext) {
//            ASTParser.UnaryExprContext unaryCtx = (ASTParser.UnaryExprContext) ctx;
//            return adaptUnary(unaryCtx);
//        } else if (ctx instanceof ASTParser.MultiplicativeExprContext) {
//            ASTParser.MultiplicativeExprContext multCtx = (ASTParser.MultiplicativeExprContext) ctx;
//            return adaptBinary(multCtx.expression(0), multCtx.getChild(1).getText(), multCtx.expression(1));
//        } else if (ctx instanceof ASTParser.AdditiveExprContext) {
//            ASTParser.AdditiveExprContext addCtx = (ASTParser.AdditiveExprContext) ctx;
//            return adaptBinary(addCtx.expression(0), addCtx.getChild(1).getText(), addCtx.expression(1));
//        } else if (ctx instanceof ASTParser.RelationalExprContext) {
//            ASTParser.RelationalExprContext relCtx = (ASTParser.RelationalExprContext) ctx;
//            return adaptBinary(relCtx.expression(0), relCtx.getChild(1).getText(), relCtx.expression(1));
//        } else if (ctx instanceof ASTParser.EqualityExprContext) {
//            ASTParser.EqualityExprContext eqCtx = (ASTParser.EqualityExprContext) ctx;
//            return adaptBinary(eqCtx.expression(0), eqCtx.getChild(1).getText(), eqCtx.expression(1));
//        } else if (ctx instanceof ASTParser.LogicalAndExprContext) {
//            ASTParser.LogicalAndExprContext andCtx = (ASTParser.LogicalAndExprContext) ctx;
//            return adaptBinary(andCtx.expression(0), "&&", andCtx.expression(1));
//        } else if (ctx instanceof ASTParser.LogicalOrExprContext) {
//            ASTParser.LogicalOrExprContext orCtx = (ASTParser.LogicalOrExprContext) ctx;
//            return adaptBinary(orCtx.expression(0), "||", orCtx.expression(1));
        } else {
            // Default case for other expression types
            return new ast.expressions.EmptyExpression();
        }
    }
    
    private static Expression adaptPrimary(ASTParser.PrimaryContext ctx) {
        if (ctx.Identifier() != null) {
            return new ast.expressions.Identifier(ctx.Identifier().getText(), null);
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
            System.out.println("DEBUG: StringConst class missing, using EmptyExpression for string: " + stringValue);
            return new ast.expressions.EmptyExpression();
        } else {
            return new ast.expressions.Null();
        }
    }
    
    private static Expression adaptMethodCall(ASTParser.MethodCallExprContext ctx) {
        MethodCall methodCall = new MethodCall();
        if (ctx.expression() != null) {
            methodCall.target = adapt(ctx.expression());
        }
        if (ctx.Identifier() != null) {
            methodCall.methodName = ctx.Identifier().getText();
        }
        if (ctx.expressionList() != null) {
            methodCall.arguments = new java.util.ArrayList<>();
            for (ASTParser.ExpressionContext exprCtx : ctx.expressionList().expression()) {
                methodCall.arguments.add(adapt(exprCtx));
            }
        }
        return methodCall;
    }

    private static Expression adaptAssign(ASTParser.AssignExprContext ctx) {
        Assign assign = new Assign();
        if (ctx.expression() != null && ctx.expression().size() >= 2) {
            assign.target = adapt(ctx.expression(0));
            assign.value = adapt(ctx.expression(1));
        }
        return assign;
    }

    private static Expression adaptNewExpr(ASTParser.NewExprContext ctx) {
        MethodCall constructor = new MethodCall();
        if (ctx.Identifier() != null) {
            constructor.methodName = ctx.Identifier().getText();
        }
        if (ctx.expressionList() != null) {
            constructor.arguments = new java.util.ArrayList<>();
            for (ASTParser.ExpressionContext exprCtx : ctx.expressionList().expression()) {
                constructor.arguments.add(adapt(exprCtx));
            }
        }
        return constructor;
    }
}