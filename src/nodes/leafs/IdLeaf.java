package nodes.leafs;

import nodes.ops.ExprNode;
import tree.Visitor;

public class IdLeaf extends ExprNode {
    private String name, idType;
    private boolean isOutPatam;

    public IdLeaf(String name, boolean isOut) {
        super.classType = "idLeaf";
        this.name = name;
        this.isOutPatam = isOut;
    }
    public IdLeaf(String name) {
        super.classType = "idLeaf";
        this.name = name;
        this.isOutPatam = false;
    }

    public void setType(String type){
        super.classType = type;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public boolean isOutPatam() {
        return isOutPatam;
    }

    public void setOutPatam(boolean outPatam) {
        isOutPatam = outPatam;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    @Override
    public String getType() {
        return this.getIdType();
    }
    @Override
    public String toString() {
        return "IdLeaf{" +
                "name='" + name + '\'' +
                ", idType='" + idType + '\'' +
                ", isOutPatam=" + isOutPatam +
                '}';
    }
}
