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
        Token[] output = new Token[tokens.length];

        //FIND THE RIGHT PATTERN!!!
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].getTtype() == Token.Toktype.NUMBER)
                output[i] = tokens[i];
            else operatorStack.add(tokens[i]);
        }

        return output;
    }

    public static int calcRPN(Token[] list) {
        // Calcula el valor resultant d'avaluar la llista de tokens
        return 0;
    }
}
