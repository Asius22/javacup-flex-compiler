package tree;

import nodes.leafs.ConstLeaf;
import nodes.leafs.IdLeaf;
import nodes.leafs.TypeLeaf;
import nodes.ops.*;
import nodes.stmt.*;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class XMLTreeGenerator implements Visitor {

    private Document document;

    public XMLTreeGenerator() throws ParserConfigurationException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        this.document = (Document) documentBuilder.newDocument();
    }

    public void saveTo(String filepath) throws TransformerException {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.transform(new DOMSource((Node) document), new StreamResult(new File(filepath)));
    }

    @Override
    public Object visit(ConstLeaf leaf) {
        Element constLeafElement = document.createElement("Const"),
                typeNode = document.createElement("type"),
                valueNode = document.createElement("value");
        typeNode.appendChild(document.createTextNode(leaf.getConstType()));
        valueNode.appendChild(document.createTextNode(leaf.getValue()));

        constLeafElement.appendChild(typeNode);
        constLeafElement.appendChild(valueNode);
        return constLeafElement;
    }

    @Override
    public Object visit(IdLeaf id) {
        Element IDLeafElement = document.createElement("ID");
        Element e1 = document.createElement("name");
        e1.appendChild(document.createTextNode(id.getName()));
        IDLeafElement.appendChild(e1);
        return IDLeafElement;
    }

    @Override
    public Object visit(TypeLeaf typLeaf) {
        Element typeElement = document.createElement("Type");

        typeElement.appendChild(document.createTextNode(typLeaf.getType()));
        return typeElement;
    }

    @Override
    public Object visit(FunCall call) {
        Element element = document.createElement("FunctionCall");

        ArrayList<ExprNode> params = call.getParamValues();
        Element e1 = (Element) call.getFunName().accept(this);
        Element e2 = document.createElement("ExprList");

        if (params != null) {
            for (ExprNode n1 : params) {
                Element e = null;

                if (n1 instanceof BinaryOp) e = (Element) ((BinaryOp) n1).accept(this);
                else if (n1 instanceof UnaryOp) e = (Element) ((UnaryOp) n1).accept(this);
                else if (n1 instanceof ConstLeaf) e = (Element) ((ConstLeaf) n1).accept(this);
                else if (n1 instanceof IdLeaf) e = (Element) ((IdLeaf) n1).accept(this);
                else if (n1 instanceof FunCall) e = (Element) ((FunCall) n1).accept(this);

                e2.appendChild(e);
            }
        }
        element.appendChild(e1);
        if (params.size() > 0)
            element.appendChild(e2);

        return element;
    }

    @Override
    public Object visit(BinaryOp bop) {
        Element element = document.createElement(bop.getOp());
        ;
        Element e1 = null, e2 = null;
        ExprNode n1, n2;

        n1 = bop.getLeft();
        n2 = bop.getRight();

        //Controlli n1
        if (n1 instanceof BinaryOp) e1 = (Element) ((BinaryOp) n1).accept(this);
        else if (n1 instanceof UnaryOp) e1 = (Element) ((UnaryOp) n1).accept(this);
        else if (n1 instanceof ConstLeaf) e1 = (Element) ((ConstLeaf) n1).accept(this);
        else if (n1 instanceof IdLeaf) e1 = (Element) ((IdLeaf) n1).accept(this);
        else if (n1 instanceof FunCall) e1 = (Element) ((FunCall) n1).accept(this);

        if (n2 instanceof BinaryOp) e2 = (Element) ((BinaryOp) n2).accept(this);
        else if (n2 instanceof UnaryOp) e2 = (Element) ((UnaryOp) n2).accept(this);
        else if (n2 instanceof ConstLeaf) e2 = (Element) ((ConstLeaf) n2).accept(this);
        else if (n2 instanceof IdLeaf) e2 = (Element) ((IdLeaf) n2).accept(this);
        else if (n2 instanceof FunCall) e2 = (Element) ((FunCall) n2).accept(this);
        if (e1 != null)
            element.appendChild(e1);
        if (e2 != null)
            element.appendChild(e2);
        return element;
    }

    @Override
    public Object visit(UnaryOp uop) {
        Element element = document.createElement(uop.getOp());
        Element e1 = null;
        ExprNode n1 = uop.getExpr();

        if (n1 instanceof BinaryOp) e1 = (Element) ((BinaryOp) n1).accept(this);
        else if (n1 instanceof UnaryOp) e1 = (Element) ((UnaryOp) n1).accept(this);
        else if (n1 instanceof ConstLeaf) e1 = (Element) ((ConstLeaf) n1).accept(this);
        else if (n1 instanceof IdLeaf) e1 = (Element) ((IdLeaf) n1).accept(this);
        else if (n1 instanceof FunCall) e1 = (Element) ((FunCall) n1).accept(this);
        if (e1 != null)
            element.appendChild(e1);
        return element;
    }

    @Override
    public Object visit(IdInitOp idInitOp) {
        Element element = document.createElement("IdInitOp");

        element.appendChild((Element) idInitOp.getId().accept(this));
        if (idInitOp.getExpr() != null) element.appendChild((Element) idInitOp.getExpr().accept(this));

        return element;
    }

    @Override
    public Object visit(ParamDecl param) {
        Element element = document.createElement("ParamDecl");
        Element e1 = null,
                e2 = (Element) param.getType().accept(this),
                e3 = document.createElement("IdList");
        ArrayList<IdLeaf> ids = param.getId();

        if (param.isOutParams())
            e1 = document.createElement("out");


        for (IdLeaf i : ids)
            e3.appendChild((Element) i.accept(this));
        if (e1 != null)
            element.appendChild(e1);
        element.appendChild(e2);
        if (ids != null && ids.size() > 0)
            element.appendChild(e3);

        return element;
    }

    @Override
    public Object visit(Body body) {
        Element element = document.createElement("BodyOp");
        ArrayList<VarDeclOp> vars = body.getVariables();
        ArrayList<StmtNode> nodes = body.getStatements();
        Element e1 = document.createElement("VarDeclList"),
                e2 = document.createElement("StatList");
        if (vars.size() == 0)
            e1 = null;
        else
            for (VarDeclOp v : vars)
                e1.appendChild((Element) v.accept(this));

        if (nodes.size() == 0)
            e2 = null;
        else
            for (StmtNode n : nodes)
                e2.appendChild((Element) n.accept(this));
        if (e1 != null)
            element.appendChild(e1);
        if (e2 != null)
            element.appendChild(e2);

        return element;
    }

    @Override
    public Object visit(FunDeclOp fun) {
        Element element = document.createElement("FunDecOP");
        Element e1 = (Element) fun.getId().accept(this),
                e2 = document.createElement("ParamDeclList"),
                e3 = document.createElement("ReturnType"),
                e4 = (Element) fun.getBody().accept(this);
        ArrayList<ParamDecl> params = fun.getParams();


        for (ParamDecl p : params)
            e2.appendChild((Element) p.accept(this));

        if (fun.getReturnType() != null)
            e3.appendChild((Element) fun.getReturnType().accept(this));
        else
            e3.appendChild((document.createElement("null")));

        element.appendChild(e1);
        if (params.size() > 0)
            element.appendChild(e2);
        element.appendChild(e3);
        element.appendChild(e4);

        return element;
    }

    @Override
    public Object visit(DeclList s) {
        Element element = document.createElement("DeclList");
        Element e1 = null,
                e2 = null;
        ArrayList<VarDeclOp> variables = s.getVariables();
        ArrayList<FunDeclOp> functions = s.getFunctions();

        if (variables.size() > 0) {
            e1 = document.createElement("VarDeclList");
            for (VarDeclOp v : variables)
                e1.appendChild((Element) v.accept(this));
        }
        if (functions.size() > 0) {
            e2 = document.createElement("FunDeclList");
            for (FunDeclOp f : functions)
                e2.appendChild((Element) f.accept(this));
        }
        if (e1 != null)
            element.appendChild(e1);
        if (e2 != null)
            element.appendChild(e2);

        return (e2 == null && e1 == null) ? null : element;
    }

    @Override
    public Object visit(ProgramOp s) {
        Element element = document.createElement("ProgramOp");
        Element e1 = (Element) s.getDeclList1().accept(this),
                e2 = document.createElement("main"),
                e3 = (Element) s.getDeclList2().accept(this);
        e2.appendChild((Element) s.getMain().accept(this));
        if (e1 != null)
            element.appendChild(e1);
        element.appendChild(e2);
        if (e3 != null)
            element.appendChild(e3);
        document.appendChild(element);
        return document;
    }

    @Override
    public Object visit(IdInitObbOp id) {
        Element element = document.createElement("IdInitObbOp");

        element.appendChild((Element) id.getId().accept(this));
        element.appendChild((Element) id.getCon().accept(this));

        return element;
    }

    @Override
    public Object visit(StmtNode stmtNode) {
        Element element = document.createElement("StmtNode");
        Element e1 = null;

        switch (stmtNode.getType()) {
            case "for":
                e1 = (Element) ((ForStmt) stmtNode.getNode()).accept(this);
                break;
            case "if":
                e1 = (Element) ((IfStmt) stmtNode.getNode()).accept(this);
                break;
            case "read":
                e1 = (Element) ((ReadStmt) stmtNode.getNode()).accept(this);
                break;
            case "write":
                e1 = (Element) ((WriteStmt) stmtNode.getNode()).accept(this);
                break;
            case "assign":
                e1 = (Element) ((AssignStmt) stmtNode.getNode()).accept(this);
                break;
            case "while":
                e1 = (Element) ((WhileStmt) stmtNode.getNode()).accept(this);
                break;
            case "funCall":
                e1 = (Element) ((FunCall) stmtNode.getNode()).accept(this);
                break;
            case "return":
                e1 = (Element) ((ReturnExprStmt) stmtNode.getNode()).accept(this);
                break;
        }

        if (e1 != null)
            element.appendChild(e1);

        return element;
    }

    @Override
    public Object visit(NonEmptyParamDecl nonEmptyParamDecl) {
        Element element = document.createElement("NonEmptyparamDecl"),
                e1 = document.createElement("ParamDeclList");
        ArrayList<ParamDecl> params = nonEmptyParamDecl.getParams();

        for (ParamDecl p : params)
            e1.appendChild((Element) p.accept(this));

        element.appendChild(e1);

        return (params.size() > 0) ? element : null;
    }

    @Override
    public Object visit(ReturnExprStmt returnExprStmt) {
        Element element = document.createElement("ReturnExprStmt"),
                e1 = document.createElement("ExprNode");

        if (returnExprStmt.getNode() == null)
            e1.appendChild(document.createElement("null"));
        else
            e1 = (Element) returnExprStmt.getNode().accept(this);

        element.appendChild(e1);

        return element;
    }

    @Override
    public Object visit(AssignStmt returnExprStmt) {
        Element element = document.createElement("AssignStmt");
        Element result1 = document.createElement("IdList");
        Element e2 = document.createElement("ExprList");

        ArrayList<ExprNode> n1 = returnExprStmt.getValues();
        ArrayList<IdLeaf> e1 = returnExprStmt.getIdList();
        for (IdLeaf id : e1)
            result1.appendChild((Element) id.accept(this));

        for (ExprNode exp : n1)
            e2.appendChild((Element) exp.accept(this));

        element.appendChild(result1);
        element.appendChild(e2);
        return element;
    }

    @Override
    public Object visit(ElseStmt elseStmt) {
        Element element = document.createElement("ElseStmt");
        Body b = elseStmt.getBody();
        if (b != null && !b.isEmpty()) {
            Element e = (Element) b.accept(this);
            element.appendChild(e);
            return element;
        }
        return null;
    }

    @Override
    public Object visit(ForStmt returnExprStmt) {
        Element element = document.createElement("ForStmt");
        Element e1 = (Element) returnExprStmt.getId().accept(this),
                e2 = (Element) returnExprStmt.getStartValue().accept(this),
                e3 = (Element) returnExprStmt.getEndValue().accept(this),
                e4 = (Element) returnExprStmt.getBody().accept(this);

        element.appendChild(e1);
        element.appendChild(e2);
        element.appendChild(e3);
        element.appendChild(e4);

        return element;
    }

    @Override
    public Object visit(IfStmt stmt) {
        Element element = document.createElement("IfStmt");
        Element exp = (Element) stmt.getExpr().accept(this);

        Element e1 = (Element) stmt.getBody().accept(this);
        Element e2 = (Element) stmt.getElseStmt().accept(this);


        element.appendChild(exp);
        element.appendChild(e1);
        if (e2 != null)
            element.appendChild(e2);
        return element;
    }

    @Override
    public Object visit(ReadStmt returnExprStmt) {
        Element element = document.createElement("ReadStmt");
        ArrayList<IdLeaf> list = returnExprStmt.getIdList();
        Element e1 = document.createElement("IdList");
        for (IdLeaf id : list)
            e1.appendChild((Element) id.accept(this));

        element.appendChild(e1);
        element.appendChild(document.createTextNode(returnExprStmt.getString()));

        return element;
    }

    @Override
    public Object visit(WhileStmt returnExprStmt) {
        Element element = document.createElement("WhileStmt");
        Element e1 = (Element) returnExprStmt.getExpr().accept(this),
                e2 = (Element) returnExprStmt.getBody().accept(this);

        element.appendChild(e1);
        element.appendChild(e2);
        return element;
    }

    @Override
    public Object visit(WriteStmt stmt) {
        Element element = document.createElement("WriteStmt");
        Element e1 = document.createElement(stmt.getOp()),
                e2 = document.createElement("ExprNodeList");
        ArrayList<ExprNode> list = stmt.getExpressions();
        for (ExprNode e : list)
            e2.appendChild((Element) e.accept(this));

        element.appendChild(e1);
        if (list.size() > 0)
            element.appendChild(e2);

        return element;
    }

    @Override
    public Object visit(VarInitDeclOp v) {
        Element element = document.createElement("VarInitDecOp");
        Element e1 = (Element) v.getType().accept(this),
                e2 = document.createElement("idInitList");
        ArrayList<IdInitOp> ids = v.getIdList();

        for (IdInitOp i : ids)
            e2.appendChild((Element) i.accept(this));

        element.appendChild(e1);
        if (ids.size() > 0)
            element.appendChild(e2);

        return element;
    }

    @Override
    public Object visit(VarObbInitDeclOp v) {
        Element element = document.createElement("VarObbInitDeclOp");
        Element e1 = document.createElement("IdList");
        ArrayList<IdInitObbOp> ids = v.getIdList();

        for (IdInitObbOp i : ids)
            e1.appendChild((Element) i.accept(this));

        element.appendChild(e1);

        return (ids.size() > 0) ? element : null;
    }

    @Override
    public Object visit(ParExpr expr) {
        Element element = document.createElement("ParExpr");
        element.appendChild((Element) expr.getExpr().accept(this));

        return element;
    }

    @Override
    public Object visit(SwitchStmt s) {
        return null;
    }

    @Override
    public Object visit(CaseStmt c) {
        return null;
    }
}
