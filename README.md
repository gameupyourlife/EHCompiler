# EHCompiler

## Team
| Aufgabe              | Personen   |
|--------------------|---|
| parser | Oliver Ilczuk, Ayo Adeniyi |
| Semantik | Julian Lachenmaier, Rebecca Niklaus |
| Bytecode | Din Alomerovic |
| Tester | Max Bantle |
| Projektleitung | Cedric Balzer |

## AST
![EHCopmiler - AST](https://github.com/user-attachments/assets/f0336004-0d68-4886-af63-a07cd893921e)

### Einschränkungen
Kein Else If
Kein Switch
Alles in einer Datei

## Aktueller Stand
Datum: 12.06.2025

| Milestone              | Status   | Datum   |
|--------------------|---|---|
| Leere Klasse       | ✅  | 29.05.2025  |   
| Klasse mit Feldern | ✅  | 12.06.2025  |   
| Klasse mit allen Expressions | ⚙️  |   |   
| Klasse mit Methoden | ⚙️  |   |  


### 12.06.2025
#### Max
- Testmethoden für die einzelnen Bereiche
--	Parser
--	Semantic
--  Bytecode
- Test für gesamten Compiler angepasst
- Möglichkeit wenn Compiler läuft wirklich Dateien zu compiliern

#### Olli
- Austausch mit Ayo 

#### Julian
- Klasse mit Feldern Semantik Check erfolgreich

#### Ayo
- Parse Gramattik stimmt soweit
- Klasse mit Feldern

#### Din
Bytecode Generierung für Expressions steht
Bytecode Generierung für Methoden in Arbeit
Struktur im Ast (accept Methode) steht

#### Rebecca
Felder Check Valide Typen
Return Statements Valide check


### 23.06.2025
#### MAX	
Zusammenarbeit mit DIN
Bytecode Auflösung von Expressions
Fast Alle bis auf ein Test fertig
-> Kontrollstruktur Test

#### DIN
Bytecode für Expressions fertig
Vererbung fehlt
Statements fehlt nur noch break und continue
-> Da fehlt lable => Parser muss dieses noch mitliefern
Neuer Classresolver eingeführt für Abbildung der Klassennahmen

#### JULIAN
If Else, While & Vererbung fehlt noch
Test laufen soweit durch

#### AYO
War krank

#### Generell
Print muss noch eingefügt werden
Neues hardcode AST Objekt welches vom Parser geparst wird
Keine Ahnung ob casting (insb. explizites Casting funktioniert)
	Das wird gemacht, wenn wir noch Zeit haben
Funktionieren Referenzen auf andere Klassen? (Z.B. hund.bellen() in der Main)
	Aus anderen Klassen vermutlich nicht (?)
Standartkonstruktor automatisch im Bytecode

