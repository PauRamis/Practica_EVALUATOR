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
        Stack<Token> outputStack = new Stack<>();
        Token lastOp = null;
        Token temp = null;
        boolean unariOp = false;

        //Posem els tokens a l'output, amb l'ajuda d'un stack temporal d'operadors
        //Si és un nombre
        for (Token token : tokens)
            if (token.getTtype() == Token.Toktype.NUMBER) {
                if (!unariOp)
                    outputStack.add(token);
                else{
                    //Si antes hi havia un operador unari, ara el valor del token es negatiu
                    Token negativeToken = Token.tokNumber(-token.getValue());
                    outputStack.add(negativeToken);
                    unariOp = false;
                }
                //Si tenim un operador en cua
                if (!operatorStack.isEmpty()) {
                    outputStack.add(operatorStack.pop());
                    //Si hi havia un altre operador de menor prioritat esperant
                    if (temp != null) {
                        outputStack.add(temp);
                        temp = null;
                    }
                }
            }
            //Si és un operador
            else {
                if (token.getTk() == '(' || token.getTk() == ')')
                    continue;
                //Si és el primer o ja hi havia un operador, és unari
                if (token.getTk() == '-' && (outputStack.isEmpty() || !operatorStack.isEmpty())) {
                    unariOp = true;
                    continue;
                }
                //Comprovem si hi ha hagut un altre operador
                else if (lastOp != null)
                    if (priorityPass(outputStack.peek(), token)) {
                        temp = outputStack.pop();
                    }
                operatorStack.add(token);
                lastOp = token;
            }

        return outputStack.toArray(new Token[0]);
    }

    //mirem si currentOp té més prioritat que l'anterior
    private static boolean priorityPass(Token lastOp, Token currentOp) {
        int lastP = assignPriority(lastOp);
        int currP = assignPriority(currentOp);
        return lastP < currP;
    }

    private static int assignPriority(Token op) {
        if (op == null)
            return 0;
        if (op.getTk() == '+' || op.getTk() == '-')
            return 1;
        if (op.getTk() == '*' || op.getTk() == '/')
            return 2;
        if (op.getTk() == '^')
            return 3;
        throw new RuntimeException("Not valid op");
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
