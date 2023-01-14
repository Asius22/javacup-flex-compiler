package nodes.stmt;

import tree.Visitor;
import nodes.leafs.IdLeaf;
import nodes.ops.ExprNode;

import java.util.ArrayList;

public class FunCall extends ExprNode{
    private IdLeaf funName;
    private String type;
    private ArrayList<ExprNode> paramValues;


    public String getReturnType() {
        return type;
    }

    public void setReturnType(String type) {
        this.type = type;
    }

    public FunCall(IdLeaf funName, ArrayList<ExprNode> paramValues) {
        super.classType = "funCall";
        this.funName = funName;
        this.paramValues = paramValues;
    }

    public FunCall(IdLeaf funName) {
        super.classType = "funCall";
        this.funName = funName;
        paramValues = new ArrayList<>();
    }

    public FunCall(ArrayList<ExprNode> paramValues) {
        this.paramValues = paramValues;
    }

    public IdLeaf getFunName() {
        return funName;
    }

    public void setFunName(IdLeaf funName) {
        this.funName = funName;
    }

    public ArrayList<ExprNode> getParamValues() {
        return paramValues;
    }

    public void setParamValues(ArrayList<ExprNode> paramValues) {
        this.paramValues = paramValues;
    }

    @Override
    public String getType() {
        return this.getReturnType();
    }
    @Override
    public String toString() {
        return "FunCall{" +
                "funName=" + funName +
                ", paramValues=" + paramValues +
                '}';
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
