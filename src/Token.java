import java.util.ArrayList;
import java.util.List;

public class Token {
    enum Toktype {
        NUMBER, OP, PAREN
    }

    // Pensa a implementar els "getters" d'aquests atributs
    private Toktype ttype;
    private int value;
    private char tk;

    public Toktype getTtype() {
        return ttype;
    }

    public int getValue() {
        return value;
    }

    public char getTk() {
        return tk;
    }

    // Constructor privat. Evita que es puguin construir objectes Token externament
    private Token() {
    }

    // Torna un token de tipus "NUMBER"
    static Token tokNumber(int value) {
        Token t = new Token();
        t.ttype = Toktype.NUMBER;
        t.value = value;
        return t;
    }

    // Torna un token de tipus "OP"
    static Token tokOp(char c) {
        Token t = new Token();
        t.ttype = Toktype.OP;
        t.tk = c;
        return t;
    }

    // Torna un token de tipus "PAREN"
    static Token tokParen(char c) {
        Token t = new Token();
        t.ttype = Toktype.PAREN;
        t.tk = c;
        return t;
    }

    // Mostra un token (conversió a String)
    public String toString() {
        String valueS;
        if (this.ttype == Toktype.NUMBER) {
            valueS = String.valueOf(this.value);
        }
        else valueS = String.valueOf(this.tk);
        return valueS;
    }

    // Mètode equals. Comprova si dos objectes Token són iguals
    public boolean equals(Object o) {
        Token t = (Token) o;
        if (t.ttype != this.ttype) return false;
        if (t.ttype == Toktype.NUMBER){
            return t.value == this.value;
        } else
            return t.tk == this.tk;
    }

    // A partir d'un String, torna una llista de tokens
    public static Token[] getTokens(String expr) {
        List<Token> tokenList = new ArrayList<>();
        String stack = "";

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c >= '0' && c <= '9')
                stack += c;
            else if (!stack.equals("")){
                tokenList.add(tokNumber(Integer.parseInt(stack)));
                stack = "";
            }
            if (c == '(' || c == ')')
                tokenList.add(tokParen(c));
            if (c == '/' || c == '*' || c == '-' || c == '+')
                tokenList.add((tokOp(c)));
        }
        //Al acabar el número, acaba el bucle i hem d'usar el que tenim a stack
        if (!stack.equals(""))
            tokenList.add(tokNumber(Integer.parseInt(stack)));
        return tokenList.toArray(new Token[0]);
    }
}
