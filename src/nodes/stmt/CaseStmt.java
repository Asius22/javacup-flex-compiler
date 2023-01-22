package nodes.stmt;

import nodes.ops.ExprNode;
import tree.Visitor;

public class CaseStmt {
    private ExprNode expr;
    private Body body;

    public CaseStmt(ExprNode expr, Body body) {
        this.expr = expr;
        this.body = body;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }

    public String getCondType(){
        return this.expr.getType();
    }
    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "CaseStmt{" +
                "expr=" + expr +
                ", body=" + body +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
