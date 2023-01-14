package nodes.stmt;

import tree.Visitor;
import nodes.leafs.IdLeaf;

import java.util.ArrayList;

public class ReadStmt{

    private ArrayList<IdLeaf> idList;
    private String string;

    public ReadStmt(ArrayList<IdLeaf> idList, String string) {
        this.idList = idList;
        this.string = (string == null) ? "" : string;
    }
    public ReadStmt(ArrayList<IdLeaf> idList){
        this.idList = idList;
    }

    public ArrayList<IdLeaf> getIdList() {
        return idList;
    }

    public void setIdList(ArrayList<IdLeaf> idList) {
        this.idList = idList;
    }

    public String getString() {
        return string;
    }

    public void setStrings(String strings) {
        this.string = strings;
    }

    @Override
    public String toString() {
        return "ReadOP{" +
                "idList=" + idList +
                ", string=" + string +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

