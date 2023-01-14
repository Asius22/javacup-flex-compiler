package nodes.ops;

import tree.Visitor;
import nodes.leafs.IdLeaf;

//Operazione in cui viene dichiarata una variabile con un valore
public class IdInitOp {
    private IdLeaf id;
    private ExprNode expr;

    public IdInitOp(IdLeaf id, ExprNode expr){
        this.id = id;
        this.expr = expr;
    }

    public IdInitOp(IdLeaf id) {
        this.id = id;
    }

    public IdLeaf getId() {
        return id;
    }

    public void setId(IdLeaf id) {
        this.id = id;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "IdInit{" +
                "id=" + id +
                ", expr=" + expr +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

