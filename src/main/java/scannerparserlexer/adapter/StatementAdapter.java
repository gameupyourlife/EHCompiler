package scannerparserlexer.adapter;

import ast.AbstractExpression;
import ast.Expression;
import ast.Statement;
import ast.statements.*;
import scannerparserlexer.parser.ASTParser;

import java.util.ArrayList;
import java.util.List;

public class StatementAdapter {
    public static Block adaptBlock(ASTParser.BlockContext ctx) {
        Block block = new Block();

        if (ctx.statement() != null && !ctx.statement().isEmpty()) {
            List<Statement> statements = new ArrayList<>();
            for (ASTParser.StatementContext stmtCtx : ctx.statement()) {
                statements.add(adapt(stmtCtx));
            }
            block.statements = statements;
        }

        return block;
    }

    public static Statement adapt(ASTParser.StatementContext ctx) {
        if (ctx instanceof ASTParser.BlockStmtContext) {
            ASTParser.BlockStmtContext blockCtx = (ASTParser.BlockStmtContext) ctx;
            return adaptBlock(blockCtx.block());
        } else if (ctx instanceof ASTParser.IfStmtContext) {
            ASTParser.IfStmtContext ifCtx = (ASTParser.IfStmtContext) ctx;
            return adaptIf(ifCtx);
        } else if (ctx instanceof ASTParser.WhileStmtContext) {
            ASTParser.WhileStmtContext whileCtx = (ASTParser.WhileStmtContext) ctx;
            return adaptWhile(whileCtx);
        } else if (ctx instanceof ASTParser.ForStmtContext) {
            ASTParser.ForStmtContext forCtx = (ASTParser.ForStmtContext) ctx;
            return adaptFor(forCtx);
        } else if (ctx instanceof ASTParser.ReturnStmtContext) {
            ASTParser.ReturnStmtContext returnCtx = (ASTParser.ReturnStmtContext) ctx;
            return adaptReturn(returnCtx);
        } else if (ctx instanceof ASTParser.LocalVarDeclStmtContext) {
            ASTParser.LocalVarDeclStmtContext localVarCtx = (ASTParser.LocalVarDeclStmtContext) ctx;
            return adaptLocalVarDecl(localVarCtx);
        } else if (ctx instanceof ASTParser.ExprStmtContext) {
            ASTParser.ExprStmtContext exprCtx = (ASTParser.ExprStmtContext) ctx;
            return adaptExprStmt(exprCtx);
        } else if (ctx instanceof ASTParser.EmptyStmtContext) {
            return new EmptyStatement();
        } else if (ctx instanceof ASTParser.BreakStmtContext) {
            return new Break();
        } else if (ctx instanceof ASTParser.ContinueStmtContext) {
            return new Continue();
        } else if (ctx instanceof ASTParser.DoWhileStmtContext) {
            ASTParser.DoWhileStmtContext doWhileCtx = (ASTParser.DoWhileStmtContext) ctx;
            return adaptDoWhile(doWhileCtx);
        } else {
            // Default case for other statement types
            return new EmptyStatement();
        }
    }

    private static Statement adaptIf(ASTParser.IfStmtContext ctx) {
        If ifStmt = new If();
        if (ctx.expression() != null) {
            ifStmt.condition = ExpressionAdapter.adapt(ctx.expression());
        }
        if (ctx.statement() != null && !ctx.statement().isEmpty()) {
            ifStmt.thenStatement = adapt(ctx.statement(0));
            if (ctx.statement().size() > 1) {
                ifStmt.elseStatement = adapt(ctx.statement(1));
            }
        }
        return ifStmt;
    }

    private static Statement adaptWhile(ASTParser.WhileStmtContext ctx) {
        While whileStmt = new While();
        if (ctx.expression() != null) {
            whileStmt.condition = ExpressionAdapter.adapt(ctx.expression());
        }
        if (ctx.statement() != null) {
            whileStmt.statement = adapt(ctx.statement());
        }
        return whileStmt;
    }

    private static Statement adaptFor(ASTParser.ForStmtContext ctx) {
        For forStmt = new For();
        if (ctx.expression() != null && ctx.expression().size() >= 3) {
            forStmt.init = ExpressionAdapter.adapt(ctx.expression(0));
            forStmt.condition = ExpressionAdapter.adapt(ctx.expression(1));
            forStmt.update = ExpressionAdapter.adapt(ctx.expression(2));
        }
        if (ctx.statement() != null) {
            forStmt.statement = adapt(ctx.statement());
        }
        return forStmt;
    }

    private static Statement adaptReturn(ASTParser.ReturnStmtContext ctx) {
        Return returnStmt = new Return();
        if (ctx.expression() != null) {
            returnStmt.expression = ExpressionAdapter.adapt(ctx.expression());
        }
        return returnStmt;
    }

    private static Statement adaptLocalVarDecl(ASTParser.LocalVarDeclStmtContext ctx) {
        LocalVarDecl localVarDecl = new LocalVarDecl();
        if (ctx.type() != null) {
            localVarDecl.type = TypeAdapter.adapt(ctx.type());
        }
        if (ctx.Identifier() != null) {
            localVarDecl.name = ctx.Identifier().getText();
        }
        if (ctx.expression() != null) {
            localVarDecl.init = ExpressionAdapter.adapt(ctx.expression());
        }
        return localVarDecl;
    }

    private static Statement adaptExprStmt(ASTParser.ExprStmtContext ctx) {
        // Create an ExpressionStatement that wraps the expression
        if (ctx.expression() != null) {
            Expression expr = ExpressionAdapter.adapt(ctx.expression());
            return new ExpressionStatement((AbstractExpression) expr);
        }
        return new EmptyStatement();
    }

    private static Statement adaptDoWhile(ASTParser.DoWhileStmtContext ctx) {
        DoWhile doWhileStmt = new DoWhile();
        if (ctx.expression() != null) {
            doWhileStmt.condition = ExpressionAdapter.adapt(ctx.expression());
        }
        if (ctx.statement() != null) {
            doWhileStmt.statement = adapt(ctx.statement());
        }
        return doWhileStmt;
    }

}