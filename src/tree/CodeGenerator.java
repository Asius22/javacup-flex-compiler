package tree;

import nodes.leafs.ConstLeaf;
import nodes.leafs.IdLeaf;
import nodes.leafs.TypeLeaf;
import nodes.ops.*;
import nodes.stmt.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CodeGenerator implements Visitor {
    private static File FILE;
    private static FileWriter WRITER;
    private int numTab;

    public CodeGenerator(String path) throws IOException {
        FILE = new File(path);
        if (!FILE.exists())
            FILE.createNewFile();
        WRITER = new FileWriter(FILE);

        //inserimneto import necessari per c
        write("#include <stdio.h>\n");
        write("#include <stdlib.h>\n");
        write("#include <math.h>\n");
        write("#include <stdbool.h>\n");
        write("#include <string.h>\n\n");

        write("//procedure di supporto\n");
        write("char * itos(int n) {\n");
        write("\tchar * dest = malloc(sizeof(char)*16);\n");
        write("\tsprintf(dest, \"%d\", n);\n");
        write("\treturn dest;\n");
        write("}\n\n");

        write("char * dtos(double n) {\n");
        write("\tchar * dest = malloc(sizeof(char)*16);\n");
        write("\tsprintf(dest, \"%f\", n);\n");
        write("\treturn dest;\n");
        write("}\n\n");
        write("char* ctos(char c){");
        write("\tchar* dest = malloc(sizeof(char)*2);\n");
        write("\tsprintf(dest, \"%c\", c);\n");
        write("\treturn dest;\n}\n\n");

        write("char * btos(bool b) {\n");
        write("\tchar * dest = malloc(sizeof(char)*2);\n");
        write("\tif(b == true)\n");
        write("\t\tdest = \"true\";\n");
        write("\telse\n");
        write("\t\tdest = \"false\";\n");
        write("\treturn dest;\n");
        write("}\n\n");

        write("char * strconcat(char * str1, char * str2) {\n");
        write("\tchar * dest = malloc(sizeof(char)*256);\n");
        write("\tstrcat(dest, str1);\n");
        write("\tstrcat(dest, str2);\n");
        write("\treturn dest;\n");
        write("}\n\n");

        this.numTab = 0;
    }

    @Override
    public Object visit(TypeLeaf typLeaf) {
        try {
            String type = switch (typLeaf.getType()) {
                case "integer" -> "int";
                case "bool" -> "bool";
                case "float" -> "float";
                case "string" -> "char *";
                case "char" -> "char";
                default -> "void";
            };

            write(type + " ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(FunCall call) {
        try {
            call.getFunName().accept(this);
            write("(");
            for (int i = 0; i < call.getParamValues().size(); i++) {
                call.getParamValues().get(i).accept(this);
                if (i != call.getParamValues().size() - 1)
                    write(",");
            }
            write(")");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Object visit(IdInitOp idInit) {
        try {
            //Se l'espressione è nulla scrivimi ID, altrimenti scrivi ID = expr
            //NO PUNTEGGGIATURA!!!

            write(idInit.getId().getName());
            if (idInit.getExpr() != null) {
                write(" = ");
                idInit.getExpr().accept(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(ConstLeaf leaf) {
        try {
            write(leaf.getValue());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(BinaryOp bop) {
        try {
            ExprNode left = bop.getLeft(),
                    right = bop.getRight();
            String op;
            if (bop.getOp().equals("strconcat")) {
                write("strconcat(");
                ExprToString(left);
                write(", ");
                ExprToString(right);
                write(")");
            } else if (bop.getOp().equals("pow")) {
                write("pow(");
                left.accept(this);
                write(", ");
                right.accept(this);
                write(")");
            } else {
                op = switch (bop.getOp()) {
                    case "plus" -> op = "+";
                    case "minus" -> op = "-";
                    case "times" -> op = "*";
                    case "div" -> op = "/";
                    case "gt" -> op = ">";
                    case "ge" -> op = ">=";
                    case "eq" -> op = "==";
                    case "lt" -> op = "<";
                    case "le" -> op = "<=";
                    case "ne" -> op = "!=";
                    case "and" -> op = "&&";
                    case "or" -> op = "||";
                    default -> throw new Error("errore nell'operazione binaria '" + bop.getOp() + "'");
                };

                left.accept(this);
                write(op);
                right.accept(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(UnaryOp uop) {
        try {
            write(switch (uop.getOp()) {
                case "minus" -> "-";
                case "not" -> "!";
                default -> throw new Error("UnaryOp '" + uop.getOp() + "' non valida!");
            });
            uop.getExpr().accept(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public Object visit(ParamDecl param) {
        try {
            ArrayList<IdLeaf> params = param.getId();
            for (int i = 0; i < params.size(); i++) {
                param.getType().accept(this);
                params.get(i).accept(this);
                if (i < params.size() - 1)
                    write(",");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Object visit(Body body) {

        for (VarDeclOp v : body.getVariables())
            v.accept(this);
        for (StmtNode s : body.getStatements())
            s.accept(this);
        return null;
    }

    @Override
    public Object visit(FunDeclOp fun) {

        fun.getReturnType().accept(this);
        try {
            fun.getId().accept(this);
            write("(");
            for (ParamDecl p : fun.getParams()) {
                p.accept(this);
                if (fun.getParams().indexOf(p) < fun.getParams().size() - 1)
                    try {
                        write(",");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
            }
            write(") {\n");
            numTab++;
            fun.getBody().accept(this);
            numTab--;
            printTab();
            write("}\n");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Object visit(DeclList s) {

        for (VarDeclOp v : s.getVariables())
            v.accept(this);
        for (FunDeclOp f : s.getFunctions())
            f.accept(this);
        return null;
    }

    @Override
    public Object visit(ProgramOp s) {
        try {
            write("//Dichiarazione variabili globali\n");
            if (s.getDeclList1().getVariables().size() != 0)
                for (VarDeclOp v : s.getDeclList1().getVariables())
                    v.accept(this);
            if (s.getDeclList2().getVariables().size() != 0)
                for (VarDeclOp v : s.getDeclList2().getVariables())
                    v.accept(this);

            write("//Dichiarazione funzioni \n");
            if (s.getDeclList1().getFunctions().size() != 0)
                for (FunDeclOp f : s.getDeclList1().getFunctions())
                    f.accept(this);
            if (s.getDeclList2().getFunctions().size() != 0)
                for (FunDeclOp f : s.getDeclList2().getFunctions())
                    f.accept(this);

            write("//main\n");
            write("int main(void){\n");
            numTab++;
            s.getMain().getBody().accept(this);
            numTab--;
            printTab();
            write("\n}\n\n");
            WRITER.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(IdLeaf id) {
        try {
            if (id.isOutPatam() && !(id.getClassType().equals("string")))
                write("*");
            write(id.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(IdInitObbOp id) {
        try {
            printTab();
            write(id.getCon().getConstType() + " ");
            write(id.getId().getName() + " = ");
            write(id.getCon().getValue());
            write(";\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(StmtNode stmtNode) {
        switch (stmtNode.getType()) {
            case "if" -> ((IfStmt) stmtNode.getNode()).accept(this);
            case "for" -> ((ForStmt) stmtNode.getNode()).accept(this);
            case "read" -> ((ReadStmt) stmtNode.getNode()).accept(this);
            case "write" -> ((WriteStmt) stmtNode.getNode()).accept(this);
            case "assign" -> ((AssignStmt) stmtNode.getNode()).accept(this);
            case "while" -> ((WhileStmt) stmtNode.getNode()).accept(this);
            case "funCall" -> {
                printTab();
                ((FunCall) stmtNode.getNode()).accept(this);
                try {
                    write(";\n");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            case "return" -> ((ReturnExprStmt) stmtNode.getNode()).accept(this);
            default -> throw new Error("Statement type Error");
        }
        return null;
    }

    @Override
    public Object visit(NonEmptyParamDecl nonEmptyParamDecl) {
        for (int i = 0; i < nonEmptyParamDecl.getParams().size(); i++) {
            ParamDecl p = nonEmptyParamDecl.getParams().get(i);
            p.accept(this);
            if (i < nonEmptyParamDecl.getParams().size() - 1)
                try {
                    write(",");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
        }
        return null;
    }

    @Override
    public Object visit(ReturnExprStmt returnExprStmt) {
        try {

            if (returnExprStmt.getNode() != null) {
                printTab();
                write("return ");
                returnExprStmt.getNode().accept(this);
                write(";\n");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public Object visit(AssignStmt a) {
        try {
            for (int i = 0; i < a.getIdList().size(); i++) {
                printTab();

                a.getIdList().get(i).accept(this);
                write(" = ");
                a.getValues().get(i).accept(this);
                write(";\n");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(ElseStmt es) {
        if (es.getBody() != null && !es.getBody().isEmpty()) {
            try {
                printTab();
                write("else {\n");
                numTab++;

                es.getBody().accept(this);

                numTab--;
                printTab();
                write("}\n");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Object visit(ForStmt fs) {
        int start = Integer.parseInt(fs.getStartValue().getValue()),
                end = Integer.parseInt(fs.getEndValue().getValue());
        try {
            printTab();
            write("for (int " + fs.getId().getName() + " = " + fs.getStartValue().getValue());
            write(";" + fs.getId().getName() + " != " + fs.getEndValue().getValue() + " ;");
            write(fs.getId().getName());
            write(
                    (start < end) ? "++" : "--"
            );
            write("){\n");
            numTab++;
            fs.getBody().accept(this);
            numTab--;
            printTab();
            write("}\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public Object visit(IfStmt i) {
        try {
            printTab();
            write("if (");
            i.getExpr().accept(this);
            write("){\n");
            numTab++;

            i.getBody().accept(this);

            numTab--;
            printTab();
            write("}\n");
            i.getElseStmt().accept(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Object visit(ReadStmt rs) {
        try {
            if (rs.getString() != null) {
                printTab();
                write("printf(" + rs.getString() + ");\n");
            }
            for (IdLeaf id : rs.getIdList()) {
                printTab();
                write("scanf( \"");
                write(
                        switch (id.getIdType()) {
                            case "integer", "bool" -> "%d\", &";
                            case "float" -> "%f\", &";
                            case "string" -> "%s\", ";
                            case "char" -> "%c\", &";
                            default -> throw new Error("tipo " + id.getIdType() + "per l'id " + id + "non valido");
                        }
                );
                id.accept(this);
                write(");\n");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public Object visit(WhileStmt returnExprStmt) {
        try {
            printTab();
            write("while(");
            returnExprStmt.getExpr().accept(this);
            write("){\n");
            numTab++;
            returnExprStmt.getBody().accept(this);

            numTab--;
            printTab();
            write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(WriteStmt w) {
        try {
            String s = "", f;
            int size = w.getExpressions().size();
            for (int i = 0; i < size; i++) {
                ExprNode e = w.getExpressions().get(i);
                printTab();

                write("printf(");

                if (e.getClassType().equals("constLeaf")) {
                    ConstLeaf c = (ConstLeaf) e;
                    //e.accept(this);
                    if (c.getConstType().equals("string")) {
                        c.setValue(c.getValue().replace("\"", ""));
                        s = "\"" + c.getValue() + "\"";
                        if (i == size - 1 && w.getOp().equals("WRITELN"))
                            s = s.substring(0, s.length() - 1) + "\\n\"";
                        c.setValue(s);
                        e.accept(this);
                    } else {
                        if (w.getOp().equals("WRITELN") && i == size - 1)
                            write("\"%s\\n\", ");
                        else
                            write("\"%s\", ");
                        ExprToString(e);
                    }
                } else {
                    if (w.getOp().equals("WRITELN") && i == size - 1)
                        write("\"%s\\n\", ");
                    else
                        write("\"%s\", ");
                    ExprToString(e);
                }
                write(");\n");

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Object visit(VarInitDeclOp v) {
        try {
            printTab();
            v.getType().accept(this);
            for (int i = 0; i < v.getIdList().size(); i++) {
                v.getIdList().get(i).accept(this);
                if (i != v.getIdList().size() - 1)
                    write(", ");
            }
            write(";\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(VarObbInitDeclOp v) {
        for (IdInitObbOp i : v.getIdList())
            i.accept(this);

        return null;
    }

    @Override
    public Object visit(ParExpr expr) {
        try {
            write("(");
            expr.getExpr().accept(this);
            write(")");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void write(String s) throws IOException {
        WRITER.write(s);
    }

    private void printTab() {
        try {
            write("\t".repeat(numTab));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void ExprToString(ExprNode en) throws IOException {
        if (!en.getClassType().equals("parExpr")) {
            String type = en.getType();
            write(
                    switch (en.getType()) {
                        case "integer" -> "itos(";
                        case "float" -> "dtos(";
                        case "char" -> "ctos(";
                        case "bool" -> "btos(";
                        default -> "";
                    }
            );
            en.accept(this);
            if (!type.equals("string"))
                write(")");
        } else {
            write("(");
            ExprToString(((ParExpr) en).getExpr());
            write(")");
        }
    }
}
















