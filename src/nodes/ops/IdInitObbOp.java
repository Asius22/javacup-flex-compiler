package nodes.ops;

import tree.Visitor;
import nodes.leafs.ConstLeaf;
import nodes.leafs.IdLeaf;

import java.util.ArrayList;

public class IdInitObbOp {
    private IdLeaf id;
    private ConstLeaf con;

    public IdInitObbOp(IdLeaf id, ConstLeaf con) {
        this.id = id;
        this.con = con;
    }

    public IdLeaf getId() {
        return id;
    }

    public void setId(IdLeaf id) {
        this.id = id;
    }

    public ConstLeaf getCon() {
        return con;
    }

    public void setCon(ConstLeaf con) {
        this.con = con;
    }

    @Override
    public String toString() {
        return "IdInitObbOp{" +
                "id=" + id +
                ", con=" + con +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}

