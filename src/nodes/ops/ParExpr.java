package nodes.ops;

import tree.Visitor;

public class ParExpr extends ExprNode {
    private ExprNode expr;

    public ParExpr(ExprNode expr) {
        super.classType = "parExpr";
        this.expr = expr;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }
    @Override
    public String getType() {
        return this.expr.getType();
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
