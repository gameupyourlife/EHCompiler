package scannerparserlexer.adapter;

import ast.Expression;
import ast.Statement;
import ast.statements.*;
import ast.statements.SwitchStatement.SwitchCase;
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
        if (ctx.block() != null) {
            return adaptBlock(ctx.block());
        } else if (ctx.ifStmt() != null) {
            return adaptIf(ctx.ifStmt());
        } else if (ctx.whileStmt() != null) {
            return adaptWhile(ctx.whileStmt());
        } else if (ctx.forStmt() != null) {
            return adaptFor(ctx.forStmt());
        } else if (ctx.returnStmt() != null) {
            return adaptReturn(ctx.returnStmt());
        } else if (ctx.localVarDeclStmt() != null) {
            return adaptLocalVarDecl(ctx.localVarDeclStmt());
        } else if (ctx.exprStmt() != null) {
            return adaptExprStmt(ctx.exprStmt());
        } else if (ctx.emptyStmt() != null) {
            return new EmptyStatement();
        } else if (ctx.breakStmt() != null) {
            return new Break();
        } else if (ctx.continueStmt() != null) {
            return new Continue();
        } else if (ctx.doWhileStmt() != null) {
            return adaptDoWhile(ctx.doWhileStmt());
        } else if (ctx.switchStmt() != null) {
            return adaptSwitch(ctx.switchStmt());
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
            return new ExpressionStatement(expr);
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

    private static Statement adaptSwitch(ASTParser.SwitchStmtContext ctx) {
        SwitchStatement switchStmt = new SwitchStatement();

        if (ctx.expression() != null) {
            switchStmt.expression = ExpressionAdapter.adapt(ctx.expression());
        }

        if (ctx.switchBlockStatementGroup() != null) {
            List<SwitchCase> cases = new ArrayList<>();

            for (ASTParser.SwitchBlockStatementGroupContext groupCtx : ctx.switchBlockStatementGroup()) {
                SwitchCase switchCase = new SwitchCase();

                // Get the case expressions
                if (groupCtx.switchLabel() != null && !groupCtx.switchLabel().isEmpty()) {
                    // We'll use the first case expression as the case value
                    // (In a full implementation, we'd handle multiple case labels differently)
                    ASTParser.SwitchLabelContext firstLabel = groupCtx.switchLabel(0);

                    if (firstLabel.expression() != null) {
                        switchCase.caseValue = ExpressionAdapter.adapt(firstLabel.expression());
                    }
                }

                // Get the case statements
                if (groupCtx.statement() != null) {
                    List<Statement> statements = new ArrayList<>();
                    for (ASTParser.StatementContext stmtCtx : groupCtx.statement()) {
                        statements.add(adapt(stmtCtx));
                    }
                    switchCase.statements = statements;
                }

                cases.add(switchCase);
            }

            switchStmt.cases = cases;
        }

        return switchStmt;
    }
}