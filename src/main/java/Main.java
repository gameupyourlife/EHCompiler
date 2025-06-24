import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.example.semantic.semanticCheck;

import ast.Program;
import bytecode.ByteCodeGenerator;
import scannerparserlexer.ScannerParserLexer;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("MiniCompiler gestartet. Gib 'quit' ein zum Beenden.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Beende Programm.");
                break;
            }

            String[] tokens = input.split("\\s+", 2);

            if (tokens.length < 2 || !tokens[0].equals("javac")) {
                System.out.println("Ungültiger Befehl. Benutze: javac <absoluter Pfad zur Datei>");
                continue;
            }

            String filePath = tokens[1];

            try {
                String content = Files.readString(Path.of(filePath));
                System.out.println("Inhalt der Datei '" + filePath + "':\n");
                System.out.println(content);
                Program scannerProgram = ScannerParserLexer.parse(content);
                Program semanticProgram = semanticCheck.generateTast(scannerProgram);
                ByteCodeGenerator gen = new ByteCodeGenerator();

                var bytecodeMap = gen.generateByteCode(semanticProgram);

                for (var entry : bytecodeMap.entrySet()) {
                    String className = entry.getKey();
                    byte[] bytecode = entry.getValue();

                    Path outputPath = Path.of("out", className + ".class");

                    try {
                        Files.createDirectories(outputPath.getParent());
                        Files.write(outputPath, bytecode);
                        System.out.println("Klasse geschrieben: " + outputPath.toAbsolutePath());
                    } catch (IOException e) {
                        System.out.println("Fehler beim Schreiben der Klasse " + className + ": " + e.getMessage());
                    }
                }

            } catch (Exception e) {
                System.out.println("Fehler beim Lesen der Datei: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
