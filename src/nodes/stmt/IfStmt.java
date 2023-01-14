package nodes.stmt;

import tree.Visitor;
import nodes.ops.ExprNode;
import tree.table.SymbolTable;

public class IfStmt{
    private ExprNode expr;
    private Body body;
    /**
     * elseStmt può essere null
     */
    private ElseStmt elseStmt;
    private boolean isFirstVisit;
    private SymbolTable symbolTable;

    public IfStmt(ExprNode expr, Body body, ElseStmt elseStmt) {
        this.expr = expr;
        this.body = body;
        this.elseStmt = elseStmt;
        isFirstVisit = true;
    }

    public IfStmt(){}

    public ExprNode getExpr() {
        return expr;
    }

    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public ElseStmt getElseStmt() {
        return elseStmt;
    }

    public void setElseStmt(ElseStmt elseStmt) {
        this.elseStmt = elseStmt;
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
        return "IfStmt{" +
                "expr=" + expr +
                ", body=" + body +
                ", elseStmt=" + elseStmt +
                ", isFirstVisit=" + isFirstVisit +
                ", symbolTable=" + symbolTable +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
