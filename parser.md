EH-Compiler(parser)

- Klassendefinitionen mit optionaler Vererbung über 'extends'
 --> Klassenmitglieder:
  -Felder mit optionaler Initialisierung
  - Konstruktoren
  - Methoden (optional mit 'static'-Modifikator)

- --> Anweisungen (Statements), darunter:
  - Codeblöcke: { ... }
  - Bedingte Anweisungen: if / else
  - Schleifen: while, do-while, for
  - Kontrollfluss: return, break, continue
  - Lokale Variablendeklarationen
  - Ausdrucksanweisungen und leere Anweisungen
  - Ausgabebefehle: print(...) und println(...)

 -->  Ausdrücke (Expressions):
  - Literale (int, boolean, char, string, null)
  - Variablen- und Feldzugriffe
  - Methodenaufrufe und Objekterzeugung
  - Unäre, binäre und Postfix-Operatoren
  - Typumwandlungen (Casts)
  - Zuweisungen und zusammengesetzte Zuweisungen

Besondere Merkmale/ 

 1). Integrierte 'println(...)'-Anweisungen als eigene Sprachkonstrukttion.

2). Kein globaler Code – alles ist in Klassen eingeschlossen
3) access modifiers wurde weggelassen.

3).  Die Grammatik erlaubt objektorientierte Konstrukte wie  Vererbung, Methodenaufruf und Casting:
 wie z.B new Dog() 	(Dog) a 
4. Operatoren mit richtiger Assoziativität und Präzeden

- Unterstützung von Ausdruckslisten in print/println (z.B. print(y + 1);)

- Unterstützung für:
  - Verkettete Methodenaufrufe und Feldzugriffe (z.B. obj.method().field)
  - Typumwandlungen mit voller Operator-Präzedenz

Vorgehensweise: Parser-Erstellung mit ANTLR
1) Grammatik definieren
 ---> Ast.g4 datei für die Grammatik wie z.B Beispielregeln:
  - `classDeclaration`, `methodDeclaration`, `statement`, `expression` usw.
  - Literale, Identifier, Schlüsselwörter (z. B. 'print', 'while', 'return')

2)  ANTLR ausführen. mit antlr4

3) Dadurch entstehen:

ASTLexer.java : tokenizierung

ASTParser.java : Reihenfolge

ASTBaseVisitor.java oder ASTListener.java

4) Die Basevisitor oder Listener wird die generierte Parsetree in eine eigene AST Struktur überführt.


