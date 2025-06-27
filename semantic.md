1. Einstiegspunkt: typeCheck(Program program)
	•	Eingabe
	•	Ein fertig geparstes AST-Objekt vom Typ Program, das u. a. eine Liste aller Klassen enthält.
	•	Was passiert
	1.	Erzeugung des Context mit allen Klassen-Deklarationen (für spätere Namens- und Typauflösungen).
	2.	Initialisierung einer Fehlerliste (errors) und eines Flags valid = true.
	3.	Iteration über alle Klassen:
	•	Verdopplungen des Klassennamens erkennen.
	•	Rekursive Prüfung jeder Klasse durch Aufruf von typeCheck(Class cls).
	•	Kombination des Rückgabewerts (classRes.isValid()) mit dem globalen valid.
	•	Ausgabe
	•	Ein typeCheckResult(valid, program).
	•	valid: true genau dann, wenn in allen Klassen keine semanticErrors aufgetreten sind.
⸻

2. Klassenprüfung: typeCheck(Class cls)
	•	Eingabe
	•	Eine einzelne Klassendeklaration aus dem AST.
	•	Was passiert
	1.	Registrierung der Klasse im Context.
	2.	Prüfung der Oberklasse (Existenz, keine Zyklen).
	3.	Felddeklarationen: je Feld Aufruf von typeCheck(Field field).
	4.	Methodendeklarationen: je Methode Aufruf von typeCheck(Method methodDecl).
	5.	Überprüfung von Methodenüberschreibungen (Signaturen müssen übereinstimmen, Rückgabetyp ).
	•	Ausgabe
	•	typeCheckResult(valid, cls)
	•	valid: Blendet eigene Fehler mit Rückgabewerten aus Feldern und Methoden ein.
	•	

⸻

3. Feldprüfung: typeCheck(Field field)
	•	Eingabe
	•	Eine Felddeklaration (name + Type).
	•	Was passiert
	1.	Überprüfung, ob der angegebene Typ im Kontext existiert (context.typeExists()).
	2.	Falls nicht, Hinzufügen eines semanticError("Unknown type '…' for field '…'").
	•	Ausgabe
	•	typeCheckResult(valid, field)
	•	valid = false, wenn der Typ unbekannt war.

⸻

4. Methodenprüfung: typeCheck(Method method)
	•	Eingabe
	•	Eine Methodendeklaration mit Name, Rückgabetyp, Parameterliste und optionalem Rumpf (Statement-Block).
	•	Was passiert
	1.	Neuer lokaler Scope im Context:
	•	Eintragen aller Parameter.
	•	Prüfung auf doppelte Parameternamen.
	2.	Parameterliste validieren (Typen existieren).
	3.	Lokale Variablen (LocalVarDecl) im Methodenkörper:
	•	Aufruf von typeCheck(Statement stmt, expectedReturnType), dabei wird bei jeder LocalVarDecl geprüft,
ob Initialisierungstyp und deklarierter Typ übereinstimmen.
	4.	Statements im Rumpf: je Statement Aufruf von typeCheck(stmt, returnTypeDerMethode).
	•	Ausgabe
	•	typeCheckResult(valid, method)
	•	valid fasst alle Fehler im Kopf (Parameter) und Rumpf (Statements) zusammen.

⸻

5. Statement- und Ausdrucksprüfung

a) Statements: typeCheck(Statement stmt, Type expectedReturnType)
	•	Eingabe
	•	Ein Statement-Objekt und der erwartete Rückgabetyp (z. B. bei return-Anweisungen).
	•	Was passiert
	•	ExpressionStatement: Typ des Ausdrucks ermitteln, das Ergebnis wird verworfen.
	•	LocalVarDecl: Init-Ausdruck typprüfen, Typkompatibilität prüfen, Variable im Scope anlegen.
	•	Return: Ausdruck typprüfen, mit expectedReturnType vergleichen (Subtyp erlaubt).
	•	If/While/For/DoWhile:
	•	Bedingungstyp muss BOOLEAN sein.
	•	Körper mit erhöhtem loopDepth rekursiv prüfen.
	•	Break/Continue: nur zulässig, wenn loopDepth > 0, sonst Fehler.
	•	Ausgabe
	•	typeCheckResult(valid, stmt) (gibt das Statement zurück, valid fasst alle Fehler im Unterbaum zusammen).

b) Ausdrücke: evaluateExpressionType(Expression expr)
	•	Eingabe
	•	Ein beliebiger Ausdrucksknoten (Binary, Unary, Assign, MethodCall, New, Literale, Identifier …).
	•	Was passiert
	•	Literals: geben konstante Type-Enums (INT, BOOLEAN, STRING …).
	•	Identifier: Auflösen in lokale Variable oder Feld via context.lookupVariable/Field().
	•	Zuweisungen: Typ des Targets vs. Typ der rechten Seite vergleichen.
	•	Binäroperationen: je Operator (+, ==, < …) linke und rechte Seite typprüfen,
erlaubte Typkombinationen sicherstellen, Ergebnis-Typ zurückgeben (INT vs. BOOLEAN).
	•	MethodCalls: Target-Typ ermitteln, Methode im Kontext suchen (context.findMethod()),
Parameterliste vergleichen, Rückgabetyp der Methodensignatur zurückgeben.
	•	New- und Cast-Ausdrücke: Existenz der Typen prüfen, Cast-Kompatibilität sicherstellen.
	•	Fehler: Bei jeder Unstimmigkeit wird ein semanticError("…") in die Liste geworfen und null zurückgegeben.
	•	Ausgabe
	•	Einen Type (oder null bei Fehlern), der dem Ausdrucksbaumknoten zugeordnet wird.

⸻

6. Ergebnis und Fehlerausgabe
	•	typeCheckResult
	•	Fehlerliste
	•	Intern in semanticCheck in einer List<Exception> errors gesammelt.
	•	Nach typeCheck(program) kann man über eine Getter-Methode (z. B. getErrors()) oder direkt auf das Feld zugreifen,
um alle semanticError-Meldungen auszugeben.

7.	Typauflösung & Weitergabe (TypeResolver)

  •	Ursprüngliches, unmodifiziertes AST-Objekt
	•	Context mit allen Typinformationen

Was passiert:
 	•	Jeder AST-Knoten erhält intern eine Referenz auf das aufgelöste Type-Objekt
	•	Keine Kopie oder neues Baum-Objekt wird erzeugt  Output:
	•	Kein neues, komplett getyptes AST
	•	Stattdessen:
	•	Originales AST mit eingebetteten Type-Verweisen
	•	typeCheckResult-Objekt (gesamt und pro Knoten)
	•	Vollständige Liste aller semanticError-Instanzen
