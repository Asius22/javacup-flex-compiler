package nodes.stmt;

import nodes.ops.ExprNode;
import tree.Visitor;

public class ReturnExprStmt{

    private ExprNode node;

    public ReturnExprStmt(ExprNode node) {
        this.node = node;
    }

    public ExprNode getNode() {
        return node;
    }

    public void setNode(ExprNode node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "ReturnExprStmt{" +
                "node=" + node +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

