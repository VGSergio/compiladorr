package com.vgs.compilador;

import com.vgs.compilador.manager.*;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.ComplexSymbolFactory.Location;

%%
%public
%class Scanner
%type java_cup.runtime.Symbol
%line
%column
%cup

%{
    private ComplexSymbol token(int type) {
        TokenManager.add(getTerminalName(type));
        return symbol(type);
    }

    private ComplexSymbol token(int type, Object value) {
        TokenManager.add(getTerminalName(type));
        return symbol(type, value);
    }

    private ComplexSymbol symbol(int type) {
        return new ComplexSymbol(getTerminalName(type), type, getLeftLocation(), getRightLocation());
    }

    private ComplexSymbol symbol(int type, Object value) {
        return new ComplexSymbol(getTerminalName(type), type, getLeftLocation(), getRightLocation(), value);
    }

    private String getTerminalName(int type){
      return ParserSym.terminalNames[type];
    }

    /**
    * Método auxiliar para calcular la posición inicial de un lexema.
    * @return Posición inicial del lexema
    */
    private Location getLeftLocation(){
        return new Location(yyline+1, yycolumn+1);
    }

    /**
    * Método auxiliar para calcular la posición final de un lexema.
    * @return Posición final del lexema
    */
    private Location getRightLocation(){
        return new Location(yyline+1, yycolumn+yylength());
    }
%}

// Separado para que no imprima dos veces el token EOF aunque lo pueda
// encontrar múltiples veces tal y como se indica en https://jflex.de/manual.html#options-and-declarations
%eofval{
    return symbol(ParserSym.EOF);
%eofval}
%eof{
    TokenManager.add(getTerminalName(ParserSym.EOF));
%eof}

// Patrones obtenidos del ejemplo de JFlex https://jflex.de/manual.html#Example
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

// Patrones propios
Identifier      = [a-zA-Z_][a-zA-Z0-9_]*
IntegerLiteral  = 0 | [1-9][0-9]*
DoubleLiteral   = {IntegerLiteral}?\.[0-9]+
BooleanLiteral  = "true" | "false"
CharLiteral     = \'[^\']?\'
StringLiteral   = \"[^\"]*\"

%%

// Main
"main"      { return token(ParserSym.MAIN); }

// Funciones y metodos
"return"    { return token(ParserSym.RETURN); }

// Tipos
"int"       { return token(ParserSym.INT); }
"double"    { return token(ParserSym.DOUBLE); }
"boolean"   { return token(ParserSym.BOOLEAN); }
"char"      { return token(ParserSym.CHAR); }
"String"    { return token(ParserSym.STRING); }
"void"      { return token(ParserSym.VOID); }

// Variables
"final"     { return token(ParserSym.FINAL); }
"new"       { return token(ParserSym.NEW); }

// Operaciones
"="         { return token(ParserSym.ASSIGN); }
"if"        { return token(ParserSym.IF); }
"else"      { return token(ParserSym.ELSE); }
"switch"    { return token(ParserSym.SWITCH); }
"case"      { return token(ParserSym.CASE); }
"break"     { return token(ParserSym.BREAK); }
"default"   { return token(ParserSym.DEFAULT); }
"for"       { return token(ParserSym.FOR); }
"while"     { return token(ParserSym.WHILE); }

// I/O
"input"     { return token(ParserSym.INPUT); }
"print"     { return token(ParserSym.PRINT); }

// Operadores(6)
//// Aritmeticos(2)
"+"         { return token(ParserSym.ADD); }
"-"         { return token(ParserSym.SUB); }
"*"         { return token(ParserSym.MUL); }
"/"         { return token(ParserSym.DIV); }
"%"         { return token(ParserSym.MOD); }

//// Relacionales(2)
"=="        { return token(ParserSym.EQ); }
"!="        { return token(ParserSym.NE); }
">"         { return token(ParserSym.GT); }
"<"         { return token(ParserSym.LT); }
">="        { return token(ParserSym.GE); }
"<="        { return token(ParserSym.LE); }

//// Lógicos(2)
"&&"        { return token(ParserSym.AND); }
"||"        { return token(ParserSym.OR); }
"!"         { return token(ParserSym.NOT); }
"^"         { return token(ParserSym.XOR); }

//// Especiales
"+="        { return token(ParserSym.ADD_ASSIGN); }
"-="        { return token(ParserSym.SUB_ASSIGN); }
"*="        { return token(ParserSym.MUL_ASSIGN); }
"/="        { return token(ParserSym.DIV_ASSIGN); }

// Auxiliares
"("         { return token(ParserSym.LPAREN); }
")"         { return token(ParserSym.RPAREN); }
"["         { return token(ParserSym.LSQUARE); }
"]"         { return token(ParserSym.RSQUARE); }
"{"         { return token(ParserSym.LBRACKET); }
"}"         { return token(ParserSym.RBRACKET); }
";"         { return token(ParserSym.SEMICOLON); }
":"         { return token(ParserSym.COLON); }
","         { return token(ParserSym.COMMA); }


{IntegerLiteral}    { return token(ParserSym.INTEGER_LITERAL, Integer.parseInt(yytext())); }
{DoubleLiteral}     { return token(ParserSym.DOUBLE_LITERAL, Double.parseDouble(yytext())); }
{BooleanLiteral}    { return token(ParserSym.BOOLEAN_LITERAL, Boolean.parseBoolean(yytext())); }
{CharLiteral}       {
                        String text = yytext();
                        // 'a' -> lenght = 3 | char a leer en pos 1
                        char value = text.length() == 3 ? text.charAt(1) : '\0';
                        return token(ParserSym.CHAR_LITERAL, value);
                    }
{StringLiteral}     {
                        String text = yytext();
                        // "string" no nos interesan los " -> (1, n-1)
                        String value = text.substring(1, text.length() - 1);
                        return token(ParserSym.STRING_LITERAL, value);
                    }

{Identifier}        { return token(ParserSym.IDENTIFIER, yytext()); }

{Comment} |
{WhiteSpace}        { /* No hacemos nada */ }

[^] {
          ErrorManager.lexical(getLeftLocation().getLine(), getLeftLocation().getColumn(), yytext());
          return token(ParserSym.error);
}