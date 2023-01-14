package nodes.leafs;


import tree.Visitor;

public class TypeLeaf {
    private String type;

    public TypeLeaf(String type){
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Type{" +
                "type='" + type + '\'' +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
