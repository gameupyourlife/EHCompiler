package org.example.context;

import ast.Program;

public class context {
    private Program program;

    public context(Program program) {
        this.program = program;
    }

    public Program getProgram() {
        return program;
    }
}