package nodes.stmt;

import nodes.ops.ExprNode;
import tree.Visitor;
import nodes.leafs.IdLeaf;

import java.util.ArrayList;

public class AssignStmt {

    private ArrayList<IdLeaf> idList;
    private ArrayList<ExprNode> values;

    public AssignStmt(ArrayList<IdLeaf> idList, ArrayList<ExprNode> values) {
        this.idList = idList;
        this.values = values;
    }

    public AssignStmt() {
    }

    public ArrayList<IdLeaf> getIdList() {
        return idList;
    }

    public void setIdList(ArrayList<IdLeaf> idList) {
        this.idList = idList;
    }

    public ArrayList<ExprNode> getValues() {
        return values;
    }

    public void setValues(ArrayList<ExprNode> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "AssignStmt{" +
                "idList=" + idList +
                ", values=" + values +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

