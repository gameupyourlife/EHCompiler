grammar AST;

// parser Rules
program
    : classDeclaration* EOF
    ;

classDeclaration
    : 'class' Identifier ('extends' Identifier)? '{' classBody '}'
    ;

classBody
    : (fieldDeclaration | methodDeclaration | constructorDeclaration)*
    ;

fieldDeclaration
    : type Identifier ('=' expression)? ';'
    ;

methodDeclaration
    : 'static'? type Identifier '(' formalParameters? ')' block
    ;

constructorDeclaration
    : Identifier '(' formalParameters? ')' block
    ;


formalParameters
    : formalParameter (',' formalParameter)*
    ;

formalParameter
    : type Identifier
    ;

type
    : 'int'
    | 'boolean'
    | 'char'
    | 'void'
    | Identifier
    ;

// Statements
block
    : '{' statement* '}'
    ;

forInit
    : localVarDecl
    | expressionList
    ;

localVarDecl
    : type Identifier ('=' expression)?
    ;


statement
    : block                                                       # blockStmt
    | 'if' '(' expression ')' statement ('else' statement)?       # ifStmt
    | 'while' '(' expression ')' statement                        # whileStmt
    | 'do' statement 'while' '(' expression ')' ';'               # doWhileStmt
    | 'for' '(' forInit? ';' expression? ';' expression? ')' statement      # forStmt
    | 'return' expression? ';'                                    # returnStmt
    | 'break' ';'                                                 # breakStmt
    | 'continue' ';'                                              # continueStmt
    | type Identifier ('=' expression)? ';'                       # localVarDeclStmt
    | expression ';'                                              # exprStmt
    | ';'                                                         # emptyStmt
    | 'print' '(' expression? ')' ';'                         # printStmt
    | 'println' '(' expression? ')' ';'                       # printlnStmt
    ;

switchBlockStatementGroup
    : switchLabel+ statement*
    ;

switchLabel
    : 'case' expression ':'
    | 'default' ':'
    ;

// Expressions
expression
    : primary                                             # primaryExpr
    | expression '.' Identifier                           # identVarExpr
    | expression '.' Identifier '(' expressionList? ')'   # methodCallExpr
    | 'new' Identifier '(' expressionList? ')'            # newExpr
    | '(' type ')' expression                             # castExpr
    | expression ('++' | '--')                            # postfixExpr
    | ('+'|'-'|'++'|'--'|'!') expression                  # unaryExpr
    | expression ('*'|'/'|'%') expression                 # multiplicativeExpr
    | expression ('+'|'-') expression                     # additiveExpr
    | expression ('<=' | '>=' | '>' | '<') expression     # relationalExpr
    | expression ('==' | '!=') expression                 # equalityExpr
    | expression '&&' expression                          # logicalAndExpr
    | expression '||' expression                          # logicalOrExpr
    | <assoc=right> expression '=' expression             # assignExpr
    | <assoc=right> expression ('+=' | '-=' | '*=' | '/=' | '%=') expression # compoundAssignExpr
    ;

primary
    : '(' expression ')'
    | 'this'
    | 'super'
    | literal
    | Identifier
    ;

expressionList
    : expression (',' expression)*
    ;

literal
    : IntegerLiteral
    | StringLiteral
    | BooleanLiteral
    | CharacterLiteral
    | 'null'
    ;

// Lexer Rules
IntegerLiteral
    : [0-9]+
    ;

StringLiteral
    : '"' StringCharacters? '"'
    ;

fragment
StringCharacters
    : StringCharacter+
    ;

fragment
StringCharacter
    : ~["\\\r\n]
    | EscapeSequence
    ;

BooleanLiteral
    : 'true'
    | 'false'
    ;

CharacterLiteral
    : '\'' SingleCharacter '\''
    ;

fragment
SingleCharacter
    : ~['\\\r\n]
    ;

fragment
EscapeSequence
    : '\\' [btnfr"'\\]
    ;

Identifier
    : JavaLetter JavaLetterOrDigit*
    ;

fragment
JavaLetter
    : [a-zA-Z$_]
    ;

fragment
JavaLetterOrDigit
    : [a-zA-Z0-9$_]
    ;

// Skip whitespace and comments
WS
    : [ \t\r\n\u000C]+ -> skip
    ;

COMMENT
    : '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    : '//' ~[\r\n]* -> skip
    ;

    // Define print keywords IMMEDIATELY after skip rules
 print : 'print';
 println : 'println';