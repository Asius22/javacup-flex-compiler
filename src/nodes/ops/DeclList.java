package nodes.ops;

import tree.Visitor;

import java.util.ArrayList;

public class DeclList {
    private ArrayList<VarDeclOp> variables;
    private ArrayList<FunDeclOp> functions;

    public DeclList(ArrayList<VarDeclOp> variables, ArrayList<FunDeclOp> functions) {
        this.variables = variables;
        this.functions = functions;
    }

    public DeclList() {
        this.variables = new ArrayList<>();
        this.functions = new ArrayList<>();
    }

    public void addVarDecl(VarDeclOp var) {
        this.variables.add(var);
    }

    public void addFunDecl(FunDeclOp var) {
        this.functions.add(var);
    }

    public void addDecl(DeclList declList) {
        this.variables.addAll(declList.getVariables());
        this.functions.addAll(declList.getFunctions());
    }

    public ArrayList<VarDeclOp> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<VarDeclOp> variables) {
        this.variables = variables;
    }

    public ArrayList<FunDeclOp> getFunctions() {
        return functions;
    }

    public void setFunctions(ArrayList<FunDeclOp> functions) {
        this.functions = functions;
    }

    @Override
    public String toString() {
        return "DeclList{" +
                "variables=" + variables +
                ", functions=" + functions +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
