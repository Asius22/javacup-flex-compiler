package nodes.stmt;

import tree.Visitor;
import tree.table.SymbolTable;

public class ElseStmt {
    private Body body;

    private boolean isFirstVisit;
    private SymbolTable symbolTable;

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

    public ElseStmt(Body body) {
        this.body = body;
        isFirstVisit = true;
    }

    public ElseStmt() {
        isFirstVisit = true;
        this.body = null;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ElseStmt{" +
                "body=" + body +
                ", isFirstVisit=" + isFirstVisit +
                ", symbolTable=" + symbolTable +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
