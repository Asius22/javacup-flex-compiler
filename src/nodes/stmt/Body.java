package nodes.stmt;

import tree.Visitor;
import nodes.ops.VarDeclOp;
import tree.table.SymbolTable;

import java.util.ArrayList;

public class Body {

    private ArrayList<VarDeclOp> variables;
    private ArrayList<StmtNode> statements;

    private boolean isFirstVisit;
    private SymbolTable symbolTable;

    public Body(ArrayList<VarDeclOp> variables, ArrayList<StmtNode> statements) {
        this.variables = variables;
        this.statements = statements;
        isFirstVisit = true;
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

    public Body(Body b){
        this.variables = b.getVariables();
        this.statements = b.getStatements();
    }

    public ArrayList<VarDeclOp> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<VarDeclOp> variables) {
        this.variables = variables;
    }

    public ArrayList<StmtNode> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<StmtNode> statements) {
        this.statements = statements;
    }

    public boolean isEmpty(){
        return this.statements.size() == 0 && this.variables.size() == 0;
    }

    @Override
    public String toString() {
        return "Body{" +
                "variables=" + variables +
                ", statements=" + statements +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}


