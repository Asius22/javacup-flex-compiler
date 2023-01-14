package nodes.ops;

import tree.Visitor;

import java.util.ArrayList;

public class VarObbInitDeclOp extends VarDeclOp{

    private ArrayList<IdInitObbOp> idList;

    public VarObbInitDeclOp(ArrayList<IdInitObbOp> idList) {
        this.idList = idList;
    }

    public ArrayList<IdInitObbOp> getIdList() {
        return idList;
    }

    public void setIdList(ArrayList<IdInitObbOp> idList) {
        this.idList = idList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
