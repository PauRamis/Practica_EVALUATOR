import java.util.Stack;

public class Evaluator {

    public static int calculate(String expr) {
        // Convertim l'string d'entrada en una llista de tokens
        Token[] tokens = Token.getTokens(expr);
        // Efectua el procediment per convertir la llista de tokens en notació RPN
        Token[] rpn = convertRPN(tokens);
        // Finalment, crida a calcRPN amb la nova llista de tokens i torna el resultat
        return calcRPN(rpn);
    }

    private static Token[] convertRPN(Token[] tokens) {
        Stack<Token> operatorStack = new Stack<>();
        Stack<Token> numberStack = new Stack<>();

        //FIND THE RIGHT PATTERN!!!
        //Posem els tokens a l'output, amb l'ajuda d'un stack d'operadors
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].getTtype() == Token.Toktype.NUMBER) {
                numberStack.add(tokens[i]);
                if (!operatorStack.isEmpty())
                    numberStack.add(operatorStack.pop());
            } else
                operatorStack.add(tokens[i]);
        }

        return numberStack.toArray(new Token[0]);
    }

    public static int calcRPN(Token[] list) {
        // Calcula el valor resultant d'avaluar la llista de tokens
        Stack<Token> numberStack = new Stack<>();
        for (Token token : list) {
            if (token.getTtype() == Token.Toktype.NUMBER)
                numberStack.add(token);
            else {
                char op = token.getTk();
                int b = numberStack.pop().getValue();
                int a = numberStack.pop().getValue();
                int r = operate(a, b, op);
                numberStack.add(Token.tokNumber(r));
            }
        }
        return numberStack.pop().getValue();
    }

    private static int operate(int a, int b, char op) {
        if (op == '+')
            return a + b;
        if (op == '-')
            return a - b;
        if (op == '*')
            return a * b;
        if (op == '/')
            return a / b;
        throw new RuntimeException("Not known operator");
    }
}
