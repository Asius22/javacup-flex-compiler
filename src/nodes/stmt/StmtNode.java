package nodes.stmt;

import tree.Visitor;

public class StmtNode {
    private Object node;
    private String type;

    public StmtNode(IfStmt node) {
        this.type = "if";
        this.node = node;
    }

    public StmtNode(ForStmt node) {
        this.type = "for";
        this.node = node;
    }

    public StmtNode(ReadStmt node) {
        this.type = "read";
        this.node = node;
    }

    public StmtNode(SwitchStmt node){
        this.node = node;
        this.type = "switch";
    }
    public StmtNode(WriteStmt node) {
        this.type = "write";
        this.node = node;
    }

    public StmtNode(AssignStmt node) {
        this.type = "assign";
        this.node = node;
    }

    public StmtNode(WhileStmt node) {
        this.type = "while";
        this.node = node;
    }

    public StmtNode(FunCall node) {
        this.type = "funCall";
        this.node = node;
    }

    public StmtNode(ReturnExprStmt node) {
        this.type = "return";
        this.node = node;
    }

    public void setNode(Object node) {
        this.node = node;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getNode() {
        return node;
    }

    @Override
    public String toString() {
        return "StmtNode{" +
                "node=" + node +
                ", type='" + type + '\'' +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
