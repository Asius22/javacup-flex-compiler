package nodes.ops;

import tree.Visitor;

public abstract class ExprNode {
    /**
     * indica il tipo di espressione alla quale ci rivolgiamo
     */
    protected String classType;

    /**
     *
     * @return il tipo della classe che implementa ExprNode
     */
    public String getClassType(){
        return this.classType;
    }

    public abstract String getType();

    /**
     *
     * @param v il visitor che visita l'albero
     *
     */
    public abstract Object accept(Visitor v);


}
