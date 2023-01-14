package nodes.leafs;


import nodes.ops.ExprNode;
import tree.Visitor;

public class ConstLeaf extends ExprNode {
    public String type, value;

    public ConstLeaf(String type, String value) {
        super.classType = "constLeaf";
        this.type = type;
        this.value = value;
    }

    public ConstLeaf(String type) {
        this.type = type;
    }

    public String getConstType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return this.getConstType();
    }

    @Override
    public String toString() {
        return "ConstLeaf{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}

