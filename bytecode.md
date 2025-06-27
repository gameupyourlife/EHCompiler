Einstiegspunkt: bytecode.ByteCodeGenerator

Der zentrale Einstiegspunkt zur Bytecode-Erzeugung ist die Methode generateByteCode in der Klasse ByteCodeGenerator. Diese Methode erhält ein Program-Objekt, das den vollständigen abstrakten Syntaxbaum (AST) eines zuvor geparsten und semantisch geprüften Programms repräsentiert.

Ablauf der Bytecode-Generierung

Die Bytecode-Generierung erfolgt in mehreren Schritten:
Zunächst iteriert der Generator über alle Klassen im Program-Objekt. Für jede Klasse wird Bytecode für:

- Felder über generateBytecodeFields,

- den Standardkonstruktor über generateBytecodeStandardConstructor, und

- die Methoden über generateBytecodeMethods erzeugt.

Für die Inhalte der Methoden – also Expressions und Statements – wird ein Visitor-Pattern eingesetzt. Dabei implementieren alle relevanten AST-Klassen entweder das Interface Expression, Statement oder in Sonderfällen (z. B. Assign, MethodCall, New, Unary) beide. Jedes dieser Interfaces definiert eine eigene accept-Methode, die bei der Traversierung den jeweils zuständigen Generator aufruft.

Die Klassen ExpressionBytecodeGenerator und StatementBytecodeGenerator sind jeweils für die Bytecode-Erzeugung von Ausdrücken und Anweisungen zuständig. Sie nutzen intern:

- einen VarContext, der lokale Variablen verwaltet, einschließlich ihrer Zuordnung zu Indexpositionen im lokalen Stack-Frame, und

- einen TypeResolver, der die Typinformationen zu Ausdrücken zur Bytecode-Generierung bereitstellt.
