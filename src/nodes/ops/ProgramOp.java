package nodes.ops;

import tree.Visitor;
import tree.table.SymbolTable;

public class ProgramOp {
    private DeclList declList1, declList2;
    private FunDeclOp main;

    //variabili per analisi semantica
    private boolean isFirstVisit;
    private SymbolTable symbolTable;
    public ProgramOp(DeclList declList1, DeclList declList2, FunDeclOp main) {
        this.declList1 = declList1;
        this.declList2 = declList2;
        this.main = main;
        isFirstVisit = true;

    }

    public DeclList getDeclList1() {
        return declList1;
    }

    public void setDeclList1(DeclList declList1) {
        this.declList1 = declList1;
    }

    public DeclList getDeclList2() {
        return declList2;
    }

    public void setDeclList2(DeclList declList2) {
        this.declList2 = declList2;
    }

    public FunDeclOp getMain() {
        return main;
    }

    public void setMain(FunDeclOp main) {
        this.main = main;
    }

    public boolean isFirstVisit() {
        return isFirstVisit;
    }

    public void setFirstVisit(boolean firstVisit) {
        isFirstVisit = firstVisit;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public String toString() {
        return "ProgramOp{" +
                "declList1=" + declList1 +
                ", declList2=" + declList2 +
                ", main=" + main +
                ", isFirstVisit=" + isFirstVisit +
                ", symbolTable=" + symbolTable +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
