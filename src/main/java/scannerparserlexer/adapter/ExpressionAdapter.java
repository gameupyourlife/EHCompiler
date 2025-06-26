package scannerparserlexer.adapter;

import ast.Expression;
import ast.Operator;
import ast.exprStatements.Assign;
import ast.exprStatements.MethodCall;
import ast.exprStatements.Unary;
import parser.ASTParser;

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
        } else if (ctx instanceof ASTParser.UnaryExprContext) {
            ASTParser.UnaryExprContext unaryCtx = (ASTParser.UnaryExprContext) ctx;
            return adaptUnary(unaryCtx);
        } else if (ctx instanceof ASTParser.MultiplicativeExprContext) {
            ASTParser.MultiplicativeExprContext multCtx = (ASTParser.MultiplicativeExprContext) ctx;
            return adaptBinary(multCtx.expression(0), multCtx.getChild(1).getText(), multCtx.expression(1));
        } else if (ctx instanceof ASTParser.AdditiveExprContext) {
            ASTParser.AdditiveExprContext addCtx = (ASTParser.AdditiveExprContext) ctx;
            return adaptBinary(addCtx.expression(0), addCtx.getChild(1).getText(), addCtx.expression(1));
        } else if (ctx instanceof ASTParser.RelationalExprContext) {
            ASTParser.RelationalExprContext relCtx = (ASTParser.RelationalExprContext) ctx;
            return adaptBinary(relCtx.expression(0), relCtx.getChild(1).getText(), relCtx.expression(1));
        } else if (ctx instanceof ASTParser.EqualityExprContext) {
            ASTParser.EqualityExprContext eqCtx = (ASTParser.EqualityExprContext) ctx;
            return adaptBinary(eqCtx.expression(0), eqCtx.getChild(1).getText(), eqCtx.expression(1));
        } else if (ctx instanceof ASTParser.LogicalAndExprContext) {
            ASTParser.LogicalAndExprContext andCtx = (ASTParser.LogicalAndExprContext) ctx;
            return adaptBinary(andCtx.expression(0), "&&", andCtx.expression(1));
        } else if (ctx instanceof ASTParser.LogicalOrExprContext) {
            ASTParser.LogicalOrExprContext orCtx = (ASTParser.LogicalOrExprContext) ctx;
            return adaptBinary(orCtx.expression(0), "||", orCtx.expression(1));
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
            System.out.println("DEBUG: StringConst class missing, using EmptyExpression for string: " + stringValue);
            return new ast.expressions.EmptyExpression();
        } else {
            return new ast.expressions.Null();
        }
    }
    
    private static Expression adaptMethodCall(ASTParser.MethodCallExprContext ctx) {
        try {
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
            // Check if MethodCall implements Expression properly
            return (Expression) methodCall;
        } catch (ClassCastException e) {
            System.out.println("DEBUG: MethodCall doesn't implement Expression interface properly, using EmptyExpression");
            return new ast.expressions.EmptyExpression();
        }
        return (Expression) methodCall;
    }

    private static Expression adaptAssign(ASTParser.AssignExprContext ctx) {
        try {
            Assign assign = new Assign();
            if (ctx.expression() != null && ctx.expression().size() >= 2) {
                assign.target = adapt(ctx.expression(0));
                assign.value = adapt(ctx.expression(1));
            }
            return (Expression) assign;
        } catch (ClassCastException e) {
            System.out.println("DEBUG: Assign doesn't implement Expression interface properly, using EmptyExpression");
            return new ast.expressions.EmptyExpression();
        }
        return (Expression) assign;
    }

    private static Expression adaptNewExpr(ASTParser.NewExprContext ctx) {
        try {
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
            return (Expression) constructor;
        } catch (ClassCastException e) {
            System.out.println("DEBUG: MethodCall (constructor) doesn't implement Expression interface properly, using EmptyExpression");
            return new ast.expressions.EmptyExpression();
        }
    }
    
    private static Expression adaptUnary(ASTParser.UnaryExprContext ctx) {
        String operatorText = ctx.getChild(0).getText();
        Operator operator = getUnaryOperator(operatorText);
        Expression expression = adapt(ctx.expression());
        return new Unary(operator, expression);
    }
    
    private static Expression adaptBinary(ASTParser.ExpressionContext leftCtx, String operatorText, ASTParser.ExpressionContext rightCtx) {
        try {
            // Try to use Binary class when it gets created
            Expression left = adapt(leftCtx);
            Expression right = adapt(rightCtx);
            Operator operator = getBinaryOperator(operatorText);
            
            // This will work when ast.exprStatements.Binary class is created
            Class<?> binaryClass = Class.forName("ast.exprStatements.Binary");
            java.lang.reflect.Constructor<?> constructor = binaryClass.getConstructor(Expression.class, Operator.class, Expression.class);
            return (Expression) constructor.newInstance(left, operator, right);
        } catch (Exception e) {
            // Binary class doesn't exist yet - return placeholder
            System.out.println("DEBUG: Binary class missing for operator '" + operatorText + "', using EmptyExpression");
            return new ast.expressions.EmptyExpression();
        }
    }
    
    private static Operator getUnaryOperator(String operatorText) {
        switch (operatorText) {
            case "+": return Operator.PLUS;
            case "-": return Operator.UMINUS;
            case "!": return Operator.NEGATE;
            case "++": return Operator.INCREMENT;
            case "--": return Operator.DECREMENT;
            default: 
                throw new IllegalArgumentException("Unknown unary operator: " + operatorText);
        }
    }
    
    private static Operator getBinaryOperator(String operatorText) {
        switch (operatorText) {
            // Arithmetic
            case "+": return Operator.PLUS;
            case "-": return Operator.MINUS;
            case "*": return Operator.MULTIPLY;
            case "/": return Operator.DIVIDE;
            case "%": return getOperatorByName("MODULO");
            
            // Relational
            case "<": return getOperatorByName("LESS_THAN");
            case ">": return getOperatorByName("GREATER_THAN");
            case "<=": return getOperatorByName("LESS_EQUAL");
            case ">=": return getOperatorByName("GREATER_EQUAL");
            
            // Equality
            case "==": return getOperatorByName("EQUALS");
            case "!=": return getOperatorByName("NOT_EQUALS");
            
            // Logical
            case "&&": return getOperatorByName("LOGICAL_AND");
            case "||": return getOperatorByName("LOGICAL_OR");
            
            default:
                throw new IllegalArgumentException("Unknown binary operator: " + operatorText);
        }
    }
    
    private static Operator getOperatorByName(String name) {
        try {
            return Operator.valueOf(name);
        } catch (IllegalArgumentException e) {
            // Fallback to existing operators if new ones don't exist
            switch (name) {
                case "MODULO": return Operator.DIVIDE; // placeholder
                case "LESS_THAN": case "GREATER_THAN": case "LESS_EQUAL": case "GREATER_EQUAL":
                case "EQUALS": case "NOT_EQUALS": case "LOGICAL_AND": case "LOGICAL_OR":
                    return Operator.PLUS; // placeholder
                default:
                    throw new IllegalArgumentException("Unknown operator: " + name);
            }
        }
        return (Expression) constructor;
    }
}
