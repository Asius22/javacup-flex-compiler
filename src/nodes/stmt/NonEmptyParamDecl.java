package nodes.stmt;

import tree.Visitor;

import java.util.ArrayList;

public class NonEmptyParamDecl {
    private ArrayList<ParamDecl> params;

    public NonEmptyParamDecl(ParamDecl par){
        this.params = new ArrayList<ParamDecl>();
        this.params.add(par);
    }

    public NonEmptyParamDecl(ParamDecl param, NonEmptyParamDecl params){
        this.params = params.getParams();
        this.params.add(param);
    }

    public ArrayList<ParamDecl> getParams() {
        return params;
    }

    public void setParams(ArrayList<ParamDecl> params) {
        this.params = params;
    }


    @Override
    public String toString() {
        return "NonEmptyParamDecl{" +
                "params=" + params +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}

