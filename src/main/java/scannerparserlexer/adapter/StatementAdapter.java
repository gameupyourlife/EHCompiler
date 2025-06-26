package scannerparserlexer.adapter;

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
            return adaptIf((ASTParser.IfStmtContext) ctx);
        } else if (ctx instanceof ASTParser.WhileStmtContext) {
            return adaptWhile((ASTParser.WhileStmtContext) ctx);
        } else if (ctx instanceof ASTParser.ForStmtContext) {
            return adaptFor((ASTParser.ForStmtContext) ctx);
        } else if (ctx instanceof ASTParser.ReturnStmtContext) {
            return adaptReturn((ASTParser.ReturnStmtContext) ctx);
        } else if (ctx instanceof ASTParser.LocalVarDeclStmtContext) {
            return adaptLocalVarDecl((ASTParser.LocalVarDeclStmtContext) ctx);
        } else if (ctx instanceof ASTParser.ExprStmtContext) {
            return adaptExprStmt((ASTParser.ExprStmtContext) ctx);
        } else if (ctx instanceof ASTParser.EmptyStmtContext) {
            return new EmptyStatement();
        } else if (ctx instanceof ASTParser.BreakStmtContext) {
            return new Break();
        } else if (ctx instanceof ASTParser.ContinueStmtContext) {
            return new Continue();
        } else if (ctx instanceof ASTParser.DoWhileStmtContext) {
            return adaptDoWhile((ASTParser.DoWhileStmtContext) ctx);
        } else {
            return new EmptyStatement();
        }
    }

    private static Statement adaptIf(ASTParser.IfStmtContext ctx) {
        If ifStmt = new If();
        ifStmt.condition = ExpressionAdapter.adapt(ctx.expression());

        Statement thenRaw = adapt(ctx.statement(0));
        ifStmt.thenStatement = unwrapSingleStatementBlock(thenRaw);

        if (ctx.statement().size() > 1) {
            Statement elseRaw = adapt(ctx.statement(1));
            ifStmt.elseStatement = unwrapSingleStatementBlock(elseRaw);
        }

        return ifStmt;
    }

    private static Statement adaptWhile(ASTParser.WhileStmtContext ctx) {
        While whileStmt = new While();
        if (ctx.expression() != null) {
            whileStmt.condition = ExpressionAdapter.adapt(ctx.expression());
        }
        whileStmt.statement = adapt(ctx.statement());
        return whileStmt;
    }

    private static Statement adaptFor(ASTParser.ForStmtContext ctx) {
    For forStmt = new For();

    // --- 1) Init (ctx.forInit() statt ctx.localVariableDeclaration())
    ASTParser.ForInitContext initCtx = ctx.forInit();
    if (initCtx != null) {
        if (initCtx.localVarDecl() != null) {
            ASTParser.LocalVarDeclContext lv = initCtx.localVarDecl();
            if (lv.expression() != null) {
                forStmt.init = ExpressionAdapter.adapt(lv.expression());
            }
        } else if (initCtx.expressionList() != null) {
            forStmt.init = ExpressionAdapter.adapt(initCtx.expressionList().expression(0));
        }
    }

    if (ctx.expression().size() > 0) {
        forStmt.condition = ExpressionAdapter.adapt(ctx.expression(0));
    }

    if (ctx.expression().size() > 1) {
        forStmt.update = ExpressionAdapter.adapt(ctx.expression(1));
    }

    forStmt.statement = adapt(ctx.statement());
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
    if (ctx.expression() != null) {
        Expression expr = ExpressionAdapter.adapt(ctx.expression());
        return new ExpressionStatement(expr); // Immer wrap in ExpressionStatement
    }
    return new EmptyStatement();
}


    private static Statement adaptDoWhile(ASTParser.DoWhileStmtContext ctx) {
        DoWhile doWhileStmt = new DoWhile();
        if (ctx.expression() != null) {
            doWhileStmt.condition = ExpressionAdapter.adapt(ctx.expression());
        }
        doWhileStmt.statement = adapt(ctx.statement());
        return doWhileStmt;
    }

    private static Statement unwrapSingleStatementBlock(Statement stmt) {
        if (stmt instanceof Block) {
            Block b = (Block) stmt;
            if (b.statements.size() == 1) {
                return b.statements.get(0);
            }
        }
        return stmt;
    }
}
