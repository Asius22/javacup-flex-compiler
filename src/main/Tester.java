package main;

import esercitazione5.*;
import java_cup.runtime.Symbol;
import nodes.ops.ProgramOp;
import tree.*;

import java.io.FileReader;

public class Tester {
    public static void main(String[] args) {
        try {
            //Lexer l = new Lexer(new FileReader("srcjflexcup/parserTest"));
            Lexer l = new Lexer(new FileReader(args[0] != null ? args[0] : "srcjflexcup/exprTest"));
            Symbol s;
            parser p = new parser(l);

            XMLTreeGenerator xmlTreeGenerator = new XMLTreeGenerator();
            SemanticVisitor semanticVisitor = new SemanticVisitor();
            /*while ((s = p.scan()).sym != sym.EOF)
                System.out.println(sym.terminalNames[s.sym]);*/

            ProgramOp program = (ProgramOp) p.parse().value;
            program.accept(xmlTreeGenerator);
            //xmlTreeGenerator.saveTo("srcjflexcup/xmlTreeGenerated.xml");
            program.accept(semanticVisitor);
            CodeGenerator cd = new CodeGenerator("srcjflexcup/exprTest.c");
            program.accept(cd);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
