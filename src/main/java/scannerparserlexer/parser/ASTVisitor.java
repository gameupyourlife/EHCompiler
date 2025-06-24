// Generated from C:/Users/meist/OneDrive/STUDIUM/DHBW/4.Semester/Compilerbau/EHCompiler/src/main/java/scannerparserlexer/AST.g4 by ANTLR 4.13.2
package parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ASTParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ASTVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ASTParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(ASTParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(ASTParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(ASTParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(ASTParser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(ASTParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(ASTParser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#formalParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameters(ASTParser.FormalParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#formalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameter(ASTParser.FormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ASTParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ASTParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStmt(ASTParser.BlockStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(ASTParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(ASTParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doWhileStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileStmt(ASTParser.DoWhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(ASTParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(ASTParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(ASTParser.BreakStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStmt(ASTParser.ContinueStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code localVarDeclStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVarDeclStmt(ASTParser.LocalVarDeclStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(ASTParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStmt(ASTParser.EmptyStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStmt(ASTParser.PrintStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printlnStmt}
	 * labeled alternative in {@link ASTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintlnStmt(ASTParser.PrintlnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlockStatementGroup(ASTParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#switchLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabel(ASTParser.SwitchLabelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpr(ASTParser.NewExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compoundAssignExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundAssignExpr(ASTParser.CompoundAssignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalAndExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpr(ASTParser.LogicalAndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code castExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpr(ASTParser.CastExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpr(ASTParser.AdditiveExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpr(ASTParser.RelationalExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identVarExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentVarExpr(ASTParser.IdentVarExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalOrExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpr(ASTParser.LogicalOrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodCallExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallExpr(ASTParser.MethodCallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(ASTParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpr(ASTParser.PrimaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code postfixExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpr(ASTParser.PostfixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiplicativeExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpr(ASTParser.MultiplicativeExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(ASTParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link ASTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(ASTParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(ASTParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(ASTParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ASTParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(ASTParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by {@link ASTParser#println}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintln(ASTParser.PrintlnContext ctx);
}