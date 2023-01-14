package nodes.ops;

import tree.Visitor;

public class BinaryOp extends ExprNode{

    private String op, exprType;
    private ExprNode left, right;


    public BinaryOp(String op, ExprNode left, ExprNode right, String exprType) {
        super.classType = "BinaryOp";
        this.op = op;
        this.left = left;
        this.right = right;
        this.exprType = exprType;
    }

    public BinaryOp(String op, ExprNode left, ExprNode right) {
        super.classType = "BinaryOp";
        this.op = op;
        this.left = left;
        this.right = right;
        this.exprType = "";
    }
    public BinaryOp(BinaryOp b){
        this.op = b.getOp();
        this.left = b.getLeft();
        this.right = b.getRight();
        this.exprType = b.getExprType();
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getExprType() {
        return exprType;
    }

    public void setExprType(String exprType) {
        this.exprType = exprType;
    }

    public ExprNode getLeft() {
        return left;
    }

    public void setLeft(ExprNode left) {
        this.left = left;
    }

    public ExprNode getRight() {
        return right;
    }

    public void setRight(ExprNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "BinaryOp{" +
                "op='" + op + '\'' +
                ", exprType='" + exprType + '\'' +
                ", left=" + left +
                ", right=" + right +
                ", type='" + classType + '\'' +
                '}';
    }

    @Override
    public String getType() {
        return this.getExprType();
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

