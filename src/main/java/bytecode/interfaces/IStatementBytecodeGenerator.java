package bytecode.interfaces;

import ast.statements.*;

public interface IStatementBytecodeGenerator {
    void visitBlock(Block stmt);
    void visitBreak(Break stmt);
    void visitContinue(Continue stmt);
    void visitDoWhile(DoWhile stmt);
    void visitEmptyStatement(EmptyStatement stmt);
    void visitExpressionStatement(ExpressionStatement stmt);
    void visitFor(For stmt);
    void visitIf(If stmt);
    void visitLocalVarDecl(LocalVarDecl stmt);
    void visitReturn(Return stmt);
    void visitSwitchStatement(SwitchStatement stmt);
    void visitWhile(While stmt);


    void visitPrintStatementln(PrintlnStatement printlnStatement);

    void visitPrintStatement(PrintStatement printStatement);
}