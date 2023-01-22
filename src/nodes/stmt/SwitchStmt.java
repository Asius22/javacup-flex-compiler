package nodes.stmt;

import nodes.leafs.IdLeaf;
import tree.Visitor;

import java.util.ArrayList;

public class SwitchStmt {
    private IdLeaf id;
    private ArrayList<CaseStmt> caseList;

    public SwitchStmt(IdLeaf id, ArrayList<CaseStmt> caseList) {
        this.id = id;
        this.caseList = caseList;
    }

    public void setId(IdLeaf id) {
        this.id = id;
    }

    public void setCaseList(ArrayList<CaseStmt> caseList) {
        this.caseList = caseList;
    }

    public IdLeaf getId() {
        return id;
    }

    public ArrayList<CaseStmt> getCaseList() {
        return caseList;
    }

    @Override
    public String toString() {
        return "SwitchStmt{" +
                "id=" + id +
                ", caseList=" + caseList +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
