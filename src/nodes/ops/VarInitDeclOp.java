package nodes.ops;

import tree.Visitor;
import nodes.leafs.TypeLeaf;

import java.util.ArrayList;

public class VarInitDeclOp extends VarDeclOp{

    private TypeLeaf type;
    private ArrayList<IdInitOp> idList;

    public VarInitDeclOp(TypeLeaf type, ArrayList<IdInitOp> idList) {
        this.type = type;
        this.idList = idList;
    }

    public TypeLeaf getType() {
        return type;
    }

    public void setType(TypeLeaf type) {
        this.type = type;
    }

    public ArrayList<IdInitOp> getIdList() {
        return idList;
    }

    public void setIdList(ArrayList<IdInitOp> idList) {
        this.idList = idList;
    }

    @Override
    public String toString() {
        return "VadInitDeclOp{" +
                "type=" + type +
                ", idList=" + idList +
                '}';
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

