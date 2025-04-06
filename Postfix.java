import java.util.Stack;

public class Postfix
{
   public static double evaluatePostfixString(String postfixString)
   {
        // Erstelle einen Stack, um Operanden und Zwischenergebnisse zu speichern
        var stack = new Stack<Double>();

        // Iteriere über jedes Token im postfixen Ausdruck
        for (String token : postfixString.split(" "))
        {
            switch (token)
            {
                case "+": // Addition
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-": // Subtraktion
                    double subtrahend = stack.pop();
                    stack.push(stack.pop() - subtrahend);
                    break;
                case "*": // Multiplikation
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/": // Division
                    double divisor = stack.pop();
                    stack.push(stack.pop() / divisor);
                    break;
                case "^": // Potenzierung
                    double exponent = stack.pop();
                    stack.push(Math.pow(stack.pop(), exponent));
                    break;
                default: // Operand (Zahl)
                    stack.push(Double.parseDouble(token));
                    break;
            }
        }
        // Das letzte Element im Stapel ist das Endergebnis
        return stack.pop();
    }

    public static void main(String[] args)
    {
        // Teste die evaluatePostfixString-Methode mit verschiedenen postfixen Ausdrücken
        System.out.println(evaluatePostfixString("1 2 + 3 4 + *"));
        System.out.println(evaluatePostfixString("4 2 - 1 +"));
        System.out.println(evaluatePostfixString("1 2 * 3 -"));
        System.out.println(evaluatePostfixString("5 6 7 * - 9 8 - +"));
        System.out.println(evaluatePostfixString("1 2 + 3 4 + * 5 6 7 * - 9 8 - + /"));
    }
}
