package tree;

import nodes.leafs.*;
import nodes.ops.*;
import nodes.stmt.*;

public interface Visitor {

    Object visit(TypeLeaf typLeaf);

    Object visit(FunCall call);

    Object visit(IdInitOp idInit);

    Object visit(ConstLeaf leaf);

    Object visit(BinaryOp bop);

    Object visit(UnaryOp uop);

    Object visit(ParamDecl param);

    Object visit(Body body);

    Object visit(FunDeclOp fun);

    Object visit(DeclList s);

    Object visit(ProgramOp s);

    Object visit(IdLeaf id);

    Object visit(IdInitObbOp id);

    Object visit(StmtNode stmtNode);

    Object visit(NonEmptyParamDecl nonEmptyParamDecl);

    Object visit(ReturnExprStmt returnExprStmt);

    Object visit(AssignStmt returnExprStmt);

    Object visit(ElseStmt returnExprStmt);

    Object visit(ForStmt returnExprStmt);

    Object visit(IfStmt returnExprStmt);

    Object visit(ReadStmt returnExprStmt);

    Object visit(WhileStmt returnExprStmt);

    Object visit(WriteStmt returnExprStmt);

    Object visit(VarInitDeclOp v);

    Object visit(VarObbInitDeclOp v);

    Object visit(ParExpr expr);


    Object visit(SwitchStmt s);

    Object visit(CaseStmt c);
}

