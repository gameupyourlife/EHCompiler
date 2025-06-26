package parser;

import scannerparserlexer.ScannerParserLexer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ast.Program;
import ast.Class;
import ast.statements.Block;
import ast.statements.Continue;
import ast.statements.If;
import ast.statements.Return;
import ast.types.Type;
import ast.statements.*;
import ast.exprStatements.*;
import ast.expressions.*;
import ast.Operator;
import ast.Method;

class ControlStructuresTest {

    @Test
    void testParseIsZeroOrPositive() throws Exception {
        String src = "class IfElse { boolean isZeroOrPositive(int number) { " +
                "if (number > 0) { return true; } " +
                "if (number == 0) { return true; } " +
                "return false; } }";
        Program program = ScannerParserLexer.parse(src);
        assertNotNull(program);
        assertEquals(1, program.classes.size());

        Class cls = program.classes.get(0);
        assertEquals("IfElse", cls.name);
        assertEquals(1, cls.methods.size());

        var method = cls.methods.get(0);
        assertEquals("isZeroOrPositive", method.name);
        assertEquals(Type.BOOLEAN, method.type);
        assertEquals(1, method.parameters.size());
        assertEquals("number", method.parameters.get(0).name);
        assertEquals(Type.INT, method.parameters.get(0).type);

        assertEquals(3, method.statement.size());

        assertTrue(method.statement.get(0) instanceof If);
        If if1 = (If) method.statement.get(0);
        assertNull(if1.elseStatement);
        assertTrue(if1.thenStatement instanceof Return);

        assertTrue(method.statement.get(1) instanceof If);
        If if2 = (If) method.statement.get(1);
        assertNull(if2.elseStatement);
        assertTrue(if2.thenStatement instanceof Return);

        assertTrue(method.statement.get(2) instanceof Return);
    }

    @Test
    void testParseIsEven() throws Exception {
        String src = "class IfElse { boolean isEven(int number) { " +
                "if (number % 2 == 0) { return true; } else { return false; } } }";
        Program program = ScannerParserLexer.parse(src);
        Class cls = program.classes.get(0);
        var method = cls.methods.get(0);

        // Ein If mit Else
        assertEquals(1, method.statement.size());
        assertTrue(method.statement.get(0) instanceof If);
        If ifStmt = (If) method.statement.get(0);
        assertNotNull(ifStmt.elseStatement);
        assertTrue(ifStmt.thenStatement instanceof Return);
        assertTrue(ifStmt.elseStatement instanceof Return);
    }

    @Test
    void testParseIsInRange() throws Exception {
        String src = "class IfElse { boolean isInRange(int number,int min,int max) { " +
                "if (number >= min && number <= max) { return true; } else { return false; } } }";
        Program program = ScannerParserLexer.parse(src);
        Class cls = program.classes.get(0);
        var method = cls.methods.get(0);

        // Ein If mit Else
        assertEquals(1, method.statement.size());
        assertTrue(method.statement.get(0) instanceof If);
        If ifStmt = (If) method.statement.get(0);
        assertNotNull(ifStmt.elseStatement);
        assertTrue(ifStmt.thenStatement instanceof Return);
        assertTrue(ifStmt.elseStatement instanceof Return);
    }

    // ==============================
    // Nested Blocks
    // ==============================

    @Test
    void testParseNestedBlocks() throws Exception {
        String src =
            "class NestedBlocks {" +
            "  int nestedBlock(int x) {" +
            "    {" +
            "      int y = x * 2;" +
            "      {" +
            "        x = y + 1;" +
            "      }" +
            "    }" +
            "    return x;" +
            "  }" +
            "}";
        Program program = ScannerParserLexer.parse(src);
        assertNotNull(program, "Programm darf nicht null sein");
        assertEquals(1, program.classes.size(), "Es muss genau eine Klasse geparst werden");
        Class cls = program.classes.get(0);
        assertEquals("NestedBlocks", cls.name, "Klassenname muss 'NestedBlocks' sein");
        assertEquals(1, cls.methods.size(), "Klasse muss genau eine Methode haben");

        Method m = cls.methods.get(0);
        assertEquals("nestedBlock", m.name, "Methodenname muss 'nestedBlock' sein");
        assertEquals(Type.INT, m.type, "Rückgabetyp muss INT sein");
        assertEquals(1, m.parameters.size(), "Methode muss einen Parameter haben");
        assertEquals("x", m.parameters.get(0).name, "Parametername muss 'x' sein");
        assertEquals(Type.INT, m.parameters.get(0).type, "Parametertyp muss INT sein");

        assertEquals(2, m.statement.size(), "Es müssen 2 Statements sein");
        assertTrue(m.statement.get(0) instanceof Block, "Erste Anweisung muss ein Block sein");
        assertTrue(m.statement.get(1) instanceof Return, "Zweite Anweisung muss Return sein");

        Block outer = (Block) m.statement.get(0);
        assertEquals(2, outer.statements.size(), "Äußerer Block muss 2 Statements enthalten");

        assertTrue(outer.statements.get(0) instanceof LocalVarDecl,
                   "Erstes Statement muss LocalVarDecl sein");
        LocalVarDecl declY = (LocalVarDecl) outer.statements.get(0);
        assertEquals("y", declY.name, "Variablenname muss 'y' sein");
        assertEquals(Type.INT, declY.type, "Variablentyp muss INT sein");
        assertTrue(declY.init instanceof Binary, "Initializer muss eine Binäroperation sein");
        Binary bin = (Binary) declY.init;
        assertEquals(Operator.MULTIPLY, bin.operator, "Operator muss '*' sein");
        assertTrue(bin.left instanceof Identifier, "Linke Seite muss Identifier sein");
        assertEquals("x", ((Identifier)bin.left).name, "Identifier muss 'x' sein");
        assertTrue(bin.right instanceof IntConst, "Rechte Seite muss IntConst sein");
        assertEquals(2, ((IntConst)bin.right).value, "Rechter Wert muss 2 sein");

        assertTrue(outer.statements.get(1) instanceof Block,
                   "Zweites Statement muss ein Block sein");
        Block inner = (Block) outer.statements.get(1);
        assertEquals(1, inner.statements.size(), "Innerer Block muss 1 Statement enthalten");

        assertTrue(inner.statements.get(0) instanceof ExpressionStatement,
                   "Innerer Block muss eine ExpressionStatement enthalten");
    }

    // ==============================
    // For- und While-Schleifen
    // ==============================

 @Test
    void testParseWhileLoop() throws Exception {
        String src =
            "class WhileLoop {" +
            "  void whileLoop() {" +
            "    int i = 0;" +
            "    while (i < 10) {" +
            "      i++;" +
            "    }" +
            "  }" +
            "}";
        Program program = ScannerParserLexer.parse(src);
        assertNotNull(program, "Programm darf nicht null sein");
        assertEquals(1, program.classes.size(), "Es muss genau eine Klasse geparst werden");
        Class cls = program.classes.get(0);
        assertEquals("WhileLoop", cls.name, "Klassenname muss 'WhileLoop' sein");
        assertEquals(1, cls.methods.size(), "Klasse muss genau eine Methode haben");

        Method m = cls.methods.get(0);
        assertEquals("whileLoop", m.name, "Methodenname muss 'whileLoop' sein");
        assertEquals(Type.VOID, m.type, "Rückgabetyp muss void sein");
        assertTrue(m.parameters.isEmpty(), "Methode darf keine Parameter haben");
        assertEquals(2, m.statement.size(), "Methode muss zwei Anweisungen haben");

        assertTrue(m.statement.get(0) instanceof LocalVarDecl, "Erste Anweisung muss LocalVarDecl sein");
        LocalVarDecl decl = (LocalVarDecl) m.statement.get(0);
        assertEquals("i", decl.name, "Variable muss 'i' heißen");
        assertEquals(Type.INT, decl.type, "Variablentyp muss INT sein");
        assertTrue(decl.init instanceof IntConst, "Initialisierer muss IntConst sein");
        assertEquals(0, ((IntConst) decl.init).value, "Initialwert muss 0 sein");

        assertTrue(m.statement.get(1) instanceof While, "Zweite Anweisung muss While sein");
        While w = (While) m.statement.get(1);

        assertTrue(w.condition instanceof Binary, "Condition muss Binary sein");
        Binary cond = (Binary) w.condition;
        assertEquals(Operator.LESS_THAN, cond.operator, "Operator muss '<' sein");
        assertTrue(cond.left instanceof Identifier, "Linke Seite muss Identifier sein");
        assertEquals("i", ((Identifier) cond.left).name, "Identifier muss 'i' sein");
        assertTrue(cond.right instanceof IntConst, "Rechte Seite muss IntConst sein");
        assertEquals(10, ((IntConst) cond.right).value, "Wert muss 10 sein");

        assertTrue(w.statement instanceof Block, "Body muss ein Block sein");
        Block body = (Block) w.statement;
        assertEquals(1, body.statements.size(), "Body muss genau eine Anweisung enthalten");
        assertTrue(body.statements.get(0) instanceof Unary, "Body-Anweisung muss Unary sein");
        Unary inc = (Unary) body.statements.get(0);
        assertEquals(Operator.INCREMENT, inc.operator, "Operator muss '++' sein");
        assertTrue(inc.expression instanceof Identifier, "Expression muss Identifier sein");
        assertEquals("i", ((Identifier) inc.expression).name, "Identifier muss 'i' sein");
    }

    @Test
    void testParseNestedWhileLoop() throws Exception {
        String src =
            "class WhileLoop {" +
            "  void nestedWhileLoop() {" +
            "    int i = 0;" +
            "    while (i < 5) {" +
            "      int j = 0;" +
            "      while (j < 3) {" +
            "        j++;" +
            "      }" +
            "      i++;" +
            "    }" +
            "  }" +
            "}";
        Program program = ScannerParserLexer.parse(src);
        assertNotNull(program);
        Class cls = program.classes.get(0);
        Method m = cls.methods.get(0);

        While outer = (While) m.statement.get(1);
        assertTrue(outer.statement instanceof Block);
        Block outerBody = (Block) outer.statement;
        assertEquals(3, outerBody.statements.size(), "Outer-Body muss drei Anweisungen enthalten");

        assertTrue(outerBody.statements.get(0) instanceof LocalVarDecl);
        LocalVarDecl declJ = (LocalVarDecl) outerBody.statements.get(0);
        assertEquals("j", declJ.name, "Variable muss 'j' heißen");
        assertEquals(Type.INT, declJ.type, "Typ muss INT sein");

        assertTrue(outerBody.statements.get(1) instanceof While, "Zweite Anweisung muss While sein");
        While inner = (While) outerBody.statements.get(1);
        Binary cond = (Binary) inner.condition;
        assertEquals(3, ((IntConst) cond.right).value, "Rechter Wert muss 3 sein");
        Block innerBody = (Block) inner.statement;
        assertTrue(innerBody.statements.get(0) instanceof Unary, "Innerer Body muss Unary sein");
        assertEquals(Operator.INCREMENT, ((Unary) innerBody.statements.get(0)).operator,
                     "Operator muss '++' sein");

        assertTrue(outerBody.statements.get(2) instanceof Unary, "Dritte Anweisung muss Unary sein");
        assertEquals(Operator.INCREMENT,
                     ((Unary) outerBody.statements.get(2)).operator,
                     "Operator muss '++' sein");
    }

    @Test
    void testParseBasicForLoop() throws Exception {
        String src = "class ForLoop {" +
                "  void basicForLoop() {" +
                "    for (int i = 0; i < 10; i++) { }" +
                "  }" +
                "}";
        Program program = ScannerParserLexer.parse(src);
        assertNotNull(program, "Programm darf nicht null sein");
        assertEquals(1, program.classes.size(), "Es muss genau eine Klasse geben");
        Class cls = program.classes.get(0);
        assertEquals("ForLoop", cls.name, "Klassenname muss 'ForLoop' sein");
        assertEquals(1, cls.methods.size(), "ForLoop muss genau eine Methode haben");

        Method m = cls.methods.get(0);
        assertEquals("basicForLoop", m.name, "Methodenname muss 'basicForLoop' sein");
        assertEquals(Type.VOID, m.type, "Rückgabetyp muss void sein");
        assertTrue(m.parameters.isEmpty(), "Methode basicForLoop darf keine Parameter haben");
        assertEquals(1, m.statement.size(), "Methode basicForLoop muss genau eine Anweisung haben");

        assertTrue(m.statement.get(0) instanceof For, "Anweisung muss eine For-Schleife sein");
        For f = (For) m.statement.get(0);

        assertTrue(f.init instanceof IntConst, "Init muss ein IntConst sein");
        assertEquals(0, ((IntConst) f.init).value, "Init-Wert muss 0 sein");

        assertTrue(f.condition instanceof Binary, "Condition muss ein Binary sein");
        Binary cond = (Binary) f.condition;
        assertEquals(Operator.LESS_THAN, cond.operator, "Operator muss '<' sein");
        assertTrue(cond.left instanceof Identifier, "Linke Seite muss Identifier sein");
        assertEquals("i", ((Identifier) cond.left).name, "Identifier muss 'i' sein");
        assertTrue(cond.right instanceof IntConst, "Rechte Seite muss IntConst sein");
        assertEquals(10, ((IntConst) cond.right).value, "Rechter Wert muss 10 sein");

        assertTrue(f.update instanceof Unary, "Update muss ein Unary sein");
        Unary upd = (Unary) f.update;
        assertEquals(Operator.INCREMENT, upd.operator, "Operator muss '++' sein");
        assertTrue(upd.expression instanceof Identifier, "Expression muss Identifier sein");
        assertEquals("i", ((Identifier) upd.expression).name, "Identifier muss 'i' sein");

        assertTrue(f.statement instanceof Block, "Body muss ein Block sein");
        assertTrue(((Block) f.statement).statements.isEmpty(), "Block darf keine Statements enthalten");
    }

      @Test
    void testParseDoWhile() throws Exception {
        String src =
            "class DoWhile {" +
            "  void doWhile() {" +
            "    int i = 0;" +
            "    do {" +
            "      i++;" +
            "    } while (i < 5);" +
            "  }" +
            "}";
        Program program = ScannerParserLexer.parse(src);
        assertNotNull(program, "Programm darf nicht null sein");
        assertEquals(1, program.classes.size(), "Es muss genau eine Klasse geparst werden");
        Class cls = program.classes.get(0);
        assertEquals("DoWhile", cls.name, "Klassenname muss 'DoWhile' sein");
        assertEquals(1, cls.methods.size(), "Klasse muss genau eine Methode haben");

        Method m = cls.methods.get(0);
        assertEquals("doWhile", m.name, "Methodenname muss 'doWhile' sein");
        assertEquals(Type.VOID, m.type, "Rückgabetyp muss void sein");
        assertTrue(m.parameters.isEmpty(), "Methode darf keine Parameter haben");
        assertEquals(2, m.statement.size(), "Methode muss genau zwei Statements enthalten");

        assertTrue(m.statement.get(0) instanceof LocalVarDecl, "Erstes Statement muss LocalVarDecl sein");
        LocalVarDecl decl = (LocalVarDecl) m.statement.get(0);
        assertEquals("i", decl.name, "Variable muss 'i' heißen");
        assertEquals(Type.INT, decl.type, "Variablentyp muss INT sein");
        assertTrue(decl.init instanceof IntConst, "Initializer muss IntConst sein");
        assertEquals(0, ((IntConst) decl.init).value, "Initialwert muss 0 sein");

        assertTrue(m.statement.get(1) instanceof DoWhile, "Zweites Statement muss DoWhile sein");
        DoWhile dw = (DoWhile) m.statement.get(1);

        assertTrue(dw.statement instanceof Block, "Body muss ein Block sein");
        Block body = (Block) dw.statement;
        assertEquals(1, body.statements.size(), "Body muss genau eine Anweisung enthalten");
        assertTrue(body.statements.get(0) instanceof ExpressionStatement,
                   "Body-Anweisung muss ExpressionStatement sein");
        ExpressionStatement es = (ExpressionStatement) body.statements.get(0);
        assertTrue(es.expression instanceof Unary, "Expression muss Unary sein");
        Unary u = (Unary) es.expression;
        assertEquals(Operator.INCREMENT, u.operator, "Operator muss '++' sein");
        assertTrue(u.expression instanceof Identifier, "Ausdruck muss Identifier sein");
        assertEquals("i", ((Identifier) u.expression).name, "Identifier muss 'i' sein");

        assertTrue(dw.condition instanceof Binary, "Condition muss Binary sein");
        Binary cond = (Binary) dw.condition;
        assertEquals(Operator.LESS_THAN, cond.operator, "Operator muss '<' sein");
        assertTrue(cond.left instanceof Identifier, "Linke Seite muss Identifier sein");
        assertEquals("i", ((Identifier) cond.left).name, "Identifier muss 'i' sein");
        assertTrue(cond.right instanceof IntConst, "Rechte Seite muss IntConst sein");
        assertEquals(5, ((IntConst) cond.right).value, "Wert muss 5 sein");
    }
}
