# EHCompiler
Mini Java Compiler für Compilerbau INF23 DHBW Horb

## Team
| Aufgabe              | Personen   | |
|--------------------|---| --- |
| parser | Oliver Ilczuk, Ayo Adeniyi | https://github.com/gameupyourlife/EHCompiler/blob/main/parser.md|   
| Semantik | Julian Lachenmaier, Rebecca Niklaus | https://github.com/gameupyourlife/EHCompiler/blob/main/semantic.md | 
| Bytecode | Din Alomerovic | https://github.com/gameupyourlife/EHCompiler/blob/main/bytecode.md |
| Tester | Max Bantle | https://github.com/gameupyourlife/EHCompiler/blob/main/tests.md |
| Projektleitung | Cedric Balzer |



## AST
![EHCopmiler - AST (1)](https://github.com/user-attachments/assets/08126776-bcd4-4af4-aff9-90a6b7185657)

## Aktueller Stand
Datum: 12.06.2025
![EHCopmiler - AST](https://github.com/user-attachments/assets/f0336004-0d68-4886-af63-a07cd893921e)

| Milestone              | Status   | Datum   |
|--------------------|---|---|
| Leere Klasse       | ✅  | 29.05.2025  |   
| Klasse mit Feldern | ✅  | 12.06.2025  |  
| Klasse mit allen Expressions | ✅  | 26.06.2025  |  
| Klasse mit Methoden | ✅  | 26.06.2025  |

Der Compiler ist ein Maven Projekt. Die Test sind in dem `src/test` Verzeichnis zu finden. Im `src/main` sind die drei Komponenten des Compilers, der `scannerparserlexer`, der `semantic`(check) und die `bytecode`generierung.

### scannerparserlexer
Der `scannerparserlexer` liest die Input Datei ein und wandelt diese in den AST um.

### semantic
Der `semantic` nimmt den generierten AST und überprüft diesen auf semantische Korrektheit und liefert den typisierten AST.

### bytecode
Mit dem typisierten AST erstellt der `bytecode` dann schließlich den Bytecode, welcher dann von der JVM ausgeführt werden kann.

### Main
Die Orchistrierung des Compilers wird in der Main Klasse vorgenommen. In diser werden die Konsolenargumente eingelsen und entsprechend an den scannerparserlexer weitergegeben. Der daraus resultierende AST an den semantic und der daraus resultierende typisierter AST an den bytecode.

## Unterstützte Features
Folgende Features werden von dem EHCompiler untestützt

### Datentypen
- boolean
- int
- char

### Operationen
- Arithmetische (+, -, *, /)
- Boolsche (&&, ||, !, ==, >=, <=, <, >)
- Unäre (++, --)
- Zuweisung

### Statments
- Branching (if, else)
- Loops (For, while, do while)
- return

### Objektorientierung
- Klassen mit Standartkonstruktoren
- Vererbung (this, super, extends)
- Felder
- Methoden mit Parametern


## Verwendung
Im Root Verzeichnis des Compilers:
```
mvn exec:java
```
Dann nach dem welcome promot eingeben
```
javac <dateipfad>
```

