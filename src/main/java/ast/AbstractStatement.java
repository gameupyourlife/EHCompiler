package ast;

import bytecode.interfaces.IStatementBytecodeGenerator;

/**
 * Basis-Klasse für alle Statements.
 * Aktuell bringt sie nur die accept-Methode mit,
 * kann aber später um weitere Meta-Daten erweitert werden.
 */
public abstract class AbstractStatement implements Statement {
    /** Muss von den konkreten Knoten implementiert werden */
    @Override
    public abstract void accept(IStatementBytecodeGenerator visitor);
}