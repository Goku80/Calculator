import java.util.Stack;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Infix
{
    public static String toPostfixString(String infixString)
    {
        // Definiere regulären Ausdruck zur Tokenisierung der Eingabe
        String regex = "[0−9]+\\.?[0−9]*|\\+|\\−|\\*|/|\\)|\\(";
        // Erstelle Pattern-Objekt für den regulären Ausdruck
        var pattern = Pattern.compile(regex);
        // Erstelle Matcher-Objekt für die Eingabe
        var matcher = pattern.matcher(infixString);
        // Definiere Menge der Operatoren
        var operators = new HashSet<String>(Arrays.asList("+", "-", "*", "/"));
        // Definiere Präzedenz für die Operatoren
        var precedences = new HashMap<String, Integer>();
        precedences.put("(", 0);
        precedences.put("+", 1);
        precedences.put("-", 1);
        precedences.put("*", 2);
        precedences.put("/", 2);

        // Initialisiere Operatorstapel und Ergebniszeichenkette
        var operatorStack = new Stack<String>();
        String result = "";

        // Iteriere über jedes Token in der Eingabe
        for (String token : infixString.split(" "))
        {
            if (operators.contains(token)) // Wenn Token ein Operator ist
            {
                // Pop-Operationen ausführen, um die Reihenfolge zu gewährleisten
                while (!operatorStack.empty() && precedences.get(operatorStack.peek()) >= precedences.get(token))
                {
                    result += operatorStack.pop() + " ";
                }
                operatorStack.push(token); // Füge aktuellen Operator zum Stapel hinzu
            }
            else if (token.equals("(")) // Wenn Token eine öffnende Klammer ist
            {
                operatorStack.push(token); // Füge sie einfach zum Stapel hinzu
            }
            else if (token.equals(")")) // Wenn Token eine schließende Klammer ist
            {
                if (!operatorStack.empty()) // Solange der Stapel nicht leer ist
                {
                    String operator;
                    // Pop-Operationen ausführen, bis die entsprechende öffnende Klammer gefunden ist
                    while (!(operator = operatorStack.pop()).equals("("))
                    {
                        result += operator + " ";
                    }
                }
            }
            else    // Wenn das Token weder Operator noch Klammer ist
            {
                result += token + " "; // Füge es direkt zum Ergebnis hinzu
            }
        }
        // Alle verbleibenden Operatoren vom Stapel nehmen und zum Ergebnis hinzufügen
        while (!operatorStack.empty())
        {
            result += operatorStack.pop() + " ";
        }
        return result; // Gib das postfixe Ergebnis zurück
    }

    public static void main(String[] args)
    {
        // Teste die toPostfixString-Methode mit verschiedenen Eingaben
        System.out.println(toPostfixString("( 1 + 2 ) * ( 3 + 4 )"));
        System.out.println(toPostfixString("4 - 2 + 1"));
        System.out.println(toPostfixString("1 * 2 - 3"));
        System.out.println(toPostfixString("( 5 - 6 * 7 + ( 9 - 8 ) )"));
        System.out.println(toPostfixString("( 1 + 2 ) * ( 3 + 4 ) / ( 5 - 6 * 7 + ( 9 - 8 ) )"));
    }
}
