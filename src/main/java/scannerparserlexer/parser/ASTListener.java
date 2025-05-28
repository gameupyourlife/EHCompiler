// Generated from /Users/olli/Studium/semester_4/Compilerbau/compiler/git/EHCompiler/src/main/java/scannerparserlexer/AST.g4 by ANTLR 4.13.2
package scannerparserlexer.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ASTParser}.
 */
public interface ASTListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ASTParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(ASTParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(ASTParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(ASTParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(ASTParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(ASTParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(ASTParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(ASTParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(ASTParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(ASTParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(ASTParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclaration(ASTParser.ConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclaration(ASTParser.ConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameters(ASTParser.FormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameters(ASTParser.FormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameter(ASTParser.FormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameter(ASTParser.FormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(ASTParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(ASTParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ASTParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ASTParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(ASTParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(ASTParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(ASTParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(ASTParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(ASTParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(ASTParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doWhileStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileStmt(ASTParser.DoWhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doWhileStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileStmt(ASTParser.DoWhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(ASTParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(ASTParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(ASTParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(ASTParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(ASTParser.BreakStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(ASTParser.BreakStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStmt(ASTParser.ContinueStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStmt(ASTParser.ContinueStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code localVarDeclStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterLocalVarDeclStmt(ASTParser.LocalVarDeclStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code localVarDeclStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitLocalVarDeclStmt(ASTParser.LocalVarDeclStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(ASTParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(ASTParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code emptyStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStmt(ASTParser.EmptyStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStmt(ASTParser.EmptyStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlockStatementGroup(ASTParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlockStatementGroup(ASTParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterSwitchLabel(ASTParser.SwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitSwitchLabel(ASTParser.SwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpr(ASTParser.NewExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpr(ASTParser.NewExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compoundAssignExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCompoundAssignExpr(ASTParser.CompoundAssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compoundAssignExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCompoundAssignExpr(ASTParser.CompoundAssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalAndExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpr(ASTParser.LogicalAndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalAndExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpr(ASTParser.LogicalAndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code castExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCastExpr(ASTParser.CastExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code castExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCastExpr(ASTParser.CastExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(ASTParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(ASTParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(ASTParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(ASTParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identVarExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentVarExpr(ASTParser.IdentVarExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identVarExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentVarExpr(ASTParser.IdentVarExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalOrExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpr(ASTParser.LogicalOrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalOrExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpr(ASTParser.LogicalOrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code methodCallExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMethodCallExpr(ASTParser.MethodCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code methodCallExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMethodCallExpr(ASTParser.MethodCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(ASTParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(ASTParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpr(ASTParser.PrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpr(ASTParser.PrimaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code postfixExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpr(ASTParser.PostfixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code postfixExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpr(ASTParser.PostfixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplicativeExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(ASTParser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplicativeExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(ASTParser.MultiplicativeExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(ASTParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(ASTParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(ASTParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(ASTParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(ASTParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(ASTParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(ASTParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(ASTParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ASTParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(ASTParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ASTParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(ASTParser.LiteralContext ctx);
}