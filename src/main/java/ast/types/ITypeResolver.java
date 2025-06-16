package ast.types;

import ast.exprStatements.New;
import ast.exprStatements.Unary;
import ast.expressions.*;

public interface ITypeResolver {
    Type resolve(BooleanConst expr);

    Type resolve(BooleanLiteral expr);

    Type resolve(CharConst expr);

    Type resolve(EmptyExpression expr);

    Type resolve(Identifier expr);

    Type resolve(IntConst expr);

    Type resolve(Null expr);

    Type resolve(Super expr);

    Type resolve(This expr);

    Type resolve(Unary expr);

    Type resolve(New expr);
}
