package nodes.ops;

import tree.Visitor;

public class UnaryOp extends ExprNode{
    /**
     * op equivale all'operazione da eseguire
     */
    private String op, exprType;
    private ExprNode expr;

    public UnaryOp(String op, String exprType, ExprNode expr) {
        super.classType = "UnaryOp";
        this.op = op;
        this.exprType = exprType;
        this.expr = expr;
    }

    public UnaryOp(String op, ExprNode expr) {
        super.classType = "UnaryOp";
        this.op = op;
        this.exprType = "";
        this.expr = expr;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getExprType() {
        return exprType;
    }

    public void setExprType(String exprType) {
        this.exprType = exprType;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }

    @Override
    public String getType() {
        return this.getExprType();
    }

    @Override
    public String toString() {
        return "UnaryOp{" +
                "op='" + op + '\'' +
                ", exprType='" + exprType + '\'' +
                ", expr=" + expr +
                ", type='" + classType + '\'' +
                '}';
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

