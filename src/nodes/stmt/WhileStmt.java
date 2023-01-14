package nodes.stmt;

import tree.Visitor;
import nodes.ops.ExprNode;
import tree.table.SymbolTable;

/**
 * WhileStat -> WHILE expr LOOP body
 */
public class WhileStmt {
    private ExprNode expr;
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

    public WhileStmt(ExprNode expr, Body body) {
        this.expr = expr;
        this.body = body;
        isFirstVisit = true;
    }
    public WhileStmt(){}

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

    @Override
    public String toString() {
        return "WhileStmt{" +
                "expr=" + expr +
                ", body=" + body +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
