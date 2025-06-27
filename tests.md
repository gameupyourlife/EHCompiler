Tests

Einstiegspunkt: src\test\java
Alle Tests befinden sich im Ordner src\test\java. Aufgeteilt in Parser, SemantikCheck und Bytecode. Die TestFiles, die der Compiler bestehen muss, befinden sich im Ordner src\test\java\JavaTestFiles. Diese wurde nochmals unterteilt in ControlStructures, DeclarationsAndClassStructure, Inheritance, PrimitiveOperations

Vorgehen

Die Tests sind so aufgebaut, dass jede Komponente dieselben Funktionen nacheinander durchläuft. So können die Features schrittweise erstellt und unabhängig voneinander entwickelt werden

Komponententests

Die drei Komponenten Parser, SemantikCheck und Bytecode besitzen jeweils einen eigenen Ordner mit jeweils vier Unit-Test-Klassen:

1.	PrimitiveOperationsTest.java
2.	DeclarationsAndClassStructureTest.java
3.	ControlStrucuresTest.java
4.	InheritanceTest.java

Die Tests des Parsers starten mit einem String und überprüfen mithilfe eines ASTs das Ergebnis. 
Die Tests des SemantikChecks starten mit einem vordefinierten ASTs. Dieser kann sowohl korrekt sein, als auch Fehler enthalten. Dies gilt es für den SemantikCheck zu erkennen und entsprechend zu reagieren.
Die Tests des BytecodeGenerators erhalten einen vordefinierten AST. Dieser wird in Bytecode umgewandelt und mithilfe von Java Reflection auf seine Korrektheit überprüft.

Die Tests können sowohl einzeln, als auch im Gesamten durchlaufen werden, um so die Entwicklung der Komponente zu erleichtern.

Hilfsfunktionen und Werkzeuge

Als Hilfsfunktionen wurden die org.junit.jupiter.api.Assertions verwendet um erwartete Ergebnisse zu Testen. 
Mit Hilfe von Reflection konnte aus einem generierten Bytecode wieder eine Java-Klasse erstellt werden. So war es möglich die Tests des BytecodeGenerators zu überprüfen.

