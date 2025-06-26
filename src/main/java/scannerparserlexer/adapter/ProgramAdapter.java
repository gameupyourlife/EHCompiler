package scannerparserlexer.adapter;

import ast.Program;
import parser.ASTParser;

public class ProgramAdapter {
    public static Program adapt(ASTParser.ProgramContext ctx) {
        Program program = new Program();

        for (ASTParser.ClassDeclarationContext classCtx : ctx.classDeclaration()) {
            program.addClass(ClassAdapter.adapt(classCtx));
        }

        return program;
    }
}