package nodes.stmt;

import tree.Visitor;
import nodes.leafs.IdLeaf;
import nodes.leafs.TypeLeaf;

import java.util.ArrayList;

public class ParamDecl {
    private boolean isOutParams;
    private TypeLeaf type;
    private ArrayList<IdLeaf> idList;

    public ParamDecl(TypeLeaf type, ArrayList<IdLeaf> idList, boolean isOutParams) {
        this.type = type;
        this.idList = idList;
        this.isOutParams = isOutParams;
    }

    public boolean isOutParams() {
        return isOutParams;
    }

    public void setOutParams(boolean outParams) {
        isOutParams = outParams;
    }

    public ParamDecl(){}

    public TypeLeaf getType() {
        return type;
    }

    public void setType(TypeLeaf type) {
        this.type = type;
    }

    public ArrayList<IdLeaf> getId() {
        return idList;
    }

    public void setId(ArrayList<IdLeaf> idList) {
        this.idList = idList;
    }

    @Override
    public String toString() {
        return "ParamDecl{" +
                "isOutParams=" + isOutParams +
                ", type=" + type +
                ", idList=" + idList +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}


