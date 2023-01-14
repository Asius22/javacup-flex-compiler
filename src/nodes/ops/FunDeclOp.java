package nodes.ops;

import nodes.leafs.IdLeaf;
import tree.Visitor;
import nodes.leafs.TypeLeaf;
import nodes.stmt.Body;
import nodes.stmt.ParamDecl;
import tree.table.SymbolTable;

import java.util.ArrayList;

public class FunDeclOp {
    private IdLeaf id;
    private ArrayList<ParamDecl> params;
    private TypeLeaf returnType;
    private Body body;

    //variabili per analisi semantica
    private boolean isFirstVisit;
    private SymbolTable symbolTable;
    public FunDeclOp(String id, ArrayList<ParamDecl> params, TypeLeaf returnType, Body body) {
        this.id = new IdLeaf(id);
        this.params = params;
        this.returnType = returnType;
        this.body = body;
        isFirstVisit = true;

    }

    public FunDeclOp(String id, TypeLeaf returnType, Body body) {
        this.params = new ArrayList<>();
        this.id = new IdLeaf(id);
        this.returnType = returnType;
        this.body = body;
    }

    public IdLeaf getId() {
        return id;
    }

    public void setId(IdLeaf id) {
        this.id = id;
    }

    public ArrayList<ParamDecl> getParams() {
        return params;
    }

    public void setParams(ArrayList<ParamDecl> params) {
        this.params = params;
    }

    public TypeLeaf getReturnType() {
        return returnType;
    }

    public void setReturnType(TypeLeaf returnType) {
        this.returnType = returnType;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public boolean isFirstVisit() {
        return isFirstVisit;
    }

    public void setFirstVisit(boolean firstVisit) {
        isFirstVisit = firstVisit;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public String toString() {
        return "FunDeclOp{" +
                "id=" + id +
                ", params=" + params +
                ", returnType=" + returnType +
                ", body=" + body +
                ", isFirstVisit=" + isFirstVisit +
                ", symbolTable=" + symbolTable +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}




