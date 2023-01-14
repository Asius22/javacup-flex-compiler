package nodes.stmt;

import nodes.leafs.IdLeaf;
import tree.Visitor;
import nodes.leafs.ConstLeaf;
import nodes.ops.VarDeclOp;
import tree.table.SymbolTable;

/**
 * ForStat ::= FOR ID ASSIGN INTEGER_CONST TO INTEGER_CONST LOOP Body:body
 */
public class ForStmt{
    private IdLeaf id;
    private ConstLeaf startValue, endValue;
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

    public ForStmt(IdLeaf id, ConstLeaf startValue, ConstLeaf endValue, Body body) {
        this.startValue = startValue;
        this.id = id;
        this.endValue = endValue;
        this.body = body;
        isFirstVisit = true;
    }

    public IdLeaf getId() {
        return id;
    }

    public void setId(IdLeaf id) {
        this.id = id;
    }

    public ConstLeaf getStartValue() {
        return startValue;
    }

    public void setStartValue(ConstLeaf startValue) {
        this.startValue = startValue;
    }

    public ConstLeaf getEndValue() {
        return endValue;
    }

    public void setEndValue(ConstLeaf endValue) {
        this.endValue = endValue;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ForStmt{" +
                "id=" + id +
                ", startValue=" + startValue +
                ", endValue=" + endValue +
                ", body=" + body +
                ", isFirstVisit=" + isFirstVisit +
                ", symbolTable=" + symbolTable +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
