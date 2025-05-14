package org.example.context;

import org.example.syntaxtree.structure.program;

public class context {
    private program program;

    public context(program program) {
        this.program = program;
    }

    public program getProgram() {
        return program;
    }
}