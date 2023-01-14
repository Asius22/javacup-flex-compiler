package nodes.stmt;

import tree.Visitor;
import nodes.ops.ExprNode;

import java.util.ArrayList;

public class WriteStmt {
    public enum WriteTypes{
        WRITE, WRITELN
    }
    private ArrayList<ExprNode> expressions;
    private WriteTypes op;

    public WriteStmt(ArrayList<ExprNode> expressions, WriteTypes op) {
        this.expressions = expressions;
        this.op = op;
    }
    public WriteStmt(){}

    public ArrayList<ExprNode> getExpressions() {
        return expressions;
    }

    public void setExpressions(ArrayList<ExprNode> expressions) {
        this.expressions = expressions;
    }

    public String getOp() {
        return (""+op);
    }

    public void setOp(WriteTypes op) {
        this.op = op;
    }

    @Override
    public String toString() {
        return "WriteStmt{" +
                "expressions=" + expressions +
                ", op=" + op +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
