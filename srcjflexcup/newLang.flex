package main;


import esercitazione5.sym;import java_cup.runtime.*;
%%
%class Lexer
%unicode
%cup
%line
%column
%cupsym sym
%{
    StringBuffer string = new StringBuffer();

    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
LineTerminator = \r|\n|\r\n
InputCharacters = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]
Comment = {MultilineComment} | {LineComment}
MultilineComment = "|*" {CommentContent} "|"
LineComment = "||" {InputCharacters}* {LineTerminator}?
CommentContent = [^|]*
Identifier = [$_A-Za-z][$_A-Za-z0-9]*
CharConst = \'[^\']{1}\'
StringConst = \"[^\"]*\"
digit = [0-9]
IntegerConst = {digit}+
RealConst = {IntegerConst}("."{IntegerConst}+)?
%state STRING

%%

<YYINITIAL> {
    /*  comments    */
    {Comment} {/*   ignore  */}
    /*  keywords    */
    "start:" {return symbol(sym.MAIN);}
    "integer" {return symbol(sym.INT);}
    "float" {return symbol(sym.FLOAT);}
    "string" {return symbol(sym.STRING);}
    "boolean" {return symbol(sym.BOOL);}
    "char" {return symbol(sym.CHAR);}
    "void" {return symbol(sym.VOID);}
    ";" {return symbol(sym.SEMI);}
    "," {return symbol(sym.COMMA);}
    "|" {return symbol(sym.PIPE);}
    "var" {return symbol(sym.VAR);}
    "def" {return symbol(sym.DEF);}
    "out" {return symbol(sym.OUT);}
    "for" {return symbol(sym.FOR);}
    "if" {return symbol(sym.IF);}
    "then" {return symbol(sym.THEN);}
    "else" {return symbol(sym.ELSE);}
    "while" {return symbol(sym.WHILE);}
    "to" {return symbol(sym.TO);}
    "loop" {return symbol(sym.LOOP);}
    "switch" {return symbol(sym.SWITCH);}
    "case" {return symbol(sym.CASE);}
    /*  operators   */
    "<--" {return symbol(sym.READ);}
    "-->" {return symbol(sym.WRITE);}
    "-->!" {return symbol(sym.WRITELN);}
    "(" {return symbol(sym.LPAR);}
    ")" {return symbol(sym.RPAR);}
    "{" {return symbol(sym.LBRACK);}
    "}" {return symbol(sym.RBRACK);}
    ":" {return symbol(sym.COLON);}
    "<<" {return symbol(sym.ASSIGN);}
    "return" {return symbol(sym.RETURN);}
    "true" {return new Symbol(sym.TRUE);}
    "false" {return new Symbol(sym.FALSE);}
    "+" {return new Symbol(sym.PLUS);}
    "-" {return new Symbol(sym.MINUS);}
    "*" {return new Symbol(sym.TIMES);}
    "/" {return new Symbol(sym.DIV);}
    "^" {return new Symbol(sym.POW);}
    "&" {return new Symbol(sym.STR_CONCAT);}
    "=" {return new Symbol(sym.EQ);}
    "<>" {return new Symbol(sym.NE);}
    "!=" {return new Symbol(sym.NE);}
    "<" {return new Symbol(sym.LT);}
    "<=" {return new Symbol(sym.LE);}
    ">" {return new Symbol(sym.GT);}
    ">=" {return new Symbol(sym.GE);}
    "and" {return new Symbol(sym.AND);}
    "or" {return new Symbol(sym.OR);}
    "not" {return new Symbol(sym.NOT);}


    /*  literals    */
    {IntegerConst} {return symbol(sym.INTEGER_CONST, yytext());}
    {RealConst} {return symbol(sym.REAL_CONST, yytext());}
    {StringConst} {return symbol(sym.STRING_CONST, yytext());}
    {CharConst} {return symbol(sym.CHAR_CONST, yytext());}
    //\" {string.setLength(0);

      //    yybegin(STRING);
      }


    /*  identifiers */
    {Identifier} {return symbol(sym.ID, yytext());}
    /*  whitespace  */
    {WhiteSpace} {/*   ignore  */}
/*  error fallback  */
[^] {throw new Error("Illegal character <"+yytext()+">");}
<<EOF>> {return null;}

