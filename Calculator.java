import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener
{
    private JTextField textField; // Textfeld zur Anzeige der Eingaben und Ergebnisse
    private boolean clearField = false; // Flag zum Löschen des Textfelds bei der nächsten Eingabe

    // Konstruktor für die Klasse Calculator
    public Calculator()
    {
        // Erstellen eines JPanel mit GridBagLayout zur Anordnung der Komponenten
        var panel = new JPanel(new GridBagLayout());
        add(panel); // Hinzufügen des Panels zum JFrame

        // Erstellen und Konfigurieren des Textfelds
        textField = new JTextField("0", 20); // Textfeld mit anfänglichem Wert "0" und Breite 20
        textField.setHorizontalAlignment(JTextField.RIGHT); // Text rechtsbündig ausrichten
        var textFieldConstraints = new GridBagConstraints();
        textFieldConstraints.fill = GridBagConstraints.HORIZONTAL; // Horizontal ausfüllen
        textFieldConstraints.gridwidth = 6; // Breite des Textfelds auf 6 Spalten setzen
        textFieldConstraints.gridx = 0; // Start in der ersten Spalte
        textFieldConstraints.gridy = 0; // Start in der ersten Zeile
        panel.add(textField, textFieldConstraints); // Hinzufügen des Textfelds zum Panel

        // Standard-GridBagConstraints für die Schaltflächen
        var c = new GridBagConstraints();
        c.weightx = 1.0; // Horizontaler Wachstumsfaktor
        c.weighty = 1.0; // Vertikaler Wachstumsfaktor
        c.fill = GridBagConstraints.BOTH; // Beide Richtungen ausfüllen

        // Hinzufügen der Schaltflächen mit entsprechenden GridBagConstraints
        // AC-Schaltfläche (Alles Löschen)
        c.gridx = 0; c.gridy = 1; panel.add(createButton("AC", Color.LIGHT_GRAY), c);
        // +/- Schaltfläche (Vorzeichen wechseln)
        c.gridx = 1; panel.add(createButton("+/-", Color.LIGHT_GRAY), c);
        // %-Schaltfläche (Prozent)
        c.gridx = 2; panel.add(createButton("%", Color.LIGHT_GRAY), c);
        // /-Schaltfläche (Division)
        c.gridx = 3; panel.add(createButton("/", Color.ORANGE.darker()), c);

        // Ziffern- und Operationsschaltflächen
        c.gridx = 0; c.gridy = 2; panel.add(createButton("7", Color.DARK_GRAY), c);
        c.gridx = 1; panel.add(createButton("8", Color.DARK_GRAY), c);
        c.gridx = 2; panel.add(createButton("9", Color.DARK_GRAY), c);
        c.gridx = 3; panel.add(createButton("*", Color.ORANGE.darker()), c);

        c.gridx = 0; c.gridy = 3; panel.add(createButton("4", Color.DARK_GRAY), c);
        c.gridx = 1; panel.add(createButton("5", Color.DARK_GRAY), c);
        c.gridx = 2; panel.add(createButton("6", Color.DARK_GRAY), c);
        c.gridx = 3; panel.add(createButton("-", Color.ORANGE.darker()), c);

        c.gridx = 0; c.gridy = 4; panel.add(createButton("1", Color.DARK_GRAY), c);
        c.gridx = 1; panel.add(createButton("2", Color.DARK_GRAY), c);
        c.gridx = 2; panel.add(createButton("3", Color.DARK_GRAY), c);
        c.gridx = 3; panel.add(createButton("+", Color.ORANGE.darker()), c);

        // 0-Schaltfläche über zwei Spalten
        c.gridx = 0; c.gridy = 5; c.gridwidth = 2; panel.add(createButton("0", Color.DARK_GRAY), c);
        // Komma-Schaltfläche
        c.gridx = 2; c.gridwidth = 1; panel.add(createButton(",", Color.DARK_GRAY), c);
        // =-Schaltfläche (Gleichheit)
        c.gridx = 3; panel.add(createButton("=", Color.ORANGE.darker()), c);

        // JFrame-Eigenschaften setzen
        setSize(300, 300); // Fenstergröße setzen
        setVisible(true); // Fenster sichtbar machen
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE); // Standard-Schließoperation
    }

    // Methode zum Erstellen einer Schaltfläche mit dem angegebenen Text und der Farbe
    private JButton createButton(String text, Color color)
    {
        JButton button = new JButton(text); // Schaltfläche erstellen
        button.setBackground(color); // Hintergrundfarbe setzen
        button.setOpaque(true); // Undurchsichtig setzen
        button.addActionListener(this); // ActionListener hinzufügen, um Klicks zu verarbeiten
        button.setFocusPainted(false); // Fokusanzeige deaktivieren
        button.setBorderPainted(false); // Randanzeige deaktivieren
        button.setForeground(Color.WHITE); // Textfarbe auf Weiß setzen
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Schriftart setzen
        return button; // Schaltfläche zurückgeben
    }

    // Methode zur Verarbeitung von Schaltflächenklicks
    public void actionPerformed(ActionEvent e)
    {
        JButton button = (JButton) e.getSource(); // Quelle des Ereignisses (die geklickte Schaltfläche)
        String buttonText = button.getText(); // Text der Schaltfläche

        // Verzweigung basierend auf dem Text der geklickten Schaltfläche
        switch (buttonText)
        {
            case "AC":
                textField.setText("0"); // Textfeld auf "0" setzen
                break;
            case "+/-":
                handlePlusMinus(); // Vorzeichen wechseln
                break;
            case "%":
                handlePercentage(); // Prozent berechnen
                break;
            case ",":
                handleComma(); // Komma hinzufügen
                break;
            case "=":
                calculateResult(); // Ergebnis berechnen
                break;
            default:
                handleDigit(buttonText); // Ziffer verarbeiten
                break;
        }
    }

    // Methode zur Verarbeitung von Zifferneingaben
    private void handleDigit(String digit)
    {
        if (clearField) // Wenn das Textfeld gelöscht werden soll
        {
            textField.setText(digit); // Setze den Text auf die eingegebene Ziffer
            clearField = false; // Setze das Flag zurück
        }
        else
        {
            if (textField.getText().equals("0"))
            {
                textField.setText(digit); // Ersetze "0" durch die eingegebene Ziffer
            }
            else
            {
                textField.setText(textField.getText() + digit); // Füge die Ziffer zum vorhandenen Text hinzu
            }
        }
    }

    // Methode zur Verarbeitung der +/- Schaltfläche
    private void handlePlusMinus()
    {
        String text = textField.getText(); // Aktueller Text im Textfeld
        if (!text.equals("0"))  // Wenn der Text nicht "0" ist
        {
            if (text.startsWith("-"))
            {
                textField.setText(text.substring(1)); // Entferne das "-" Zeichen
            }
            else
            {
                textField.setText("-" + text); // Füge ein "-" Zeichen hinzu
            }
        }
    }

    // Methode zur Verarbeitung der % Schaltfläche
    private void handlePercentage()
    {
        double result = Double.parseDouble(textField.getText()) / 100; // Berechne den Prozentwert
        textField.setText(formatResult(result)); // Setze das Ergebnis im Textfeld
        clearField = true; // Setze das Flag zum Löschen des Textfelds bei der nächsten Eingabe
    }

    // Methode zur Verarbeitung der Komma Schaltfläche
    private void handleComma()
    {
        if (!textField.getText().contains(".")) // Wenn kein Komma im Text ist
        {
            textField.setText(textField.getText() + "."); // Füge ein Komma hinzu
        }
    }

    // Methode zur Berechnung des Ergebnisses
    private void calculateResult()
    {
        try
        {
            String expression = textField.getText(); // Ausdruck aus dem Textfeld
            String formattedExpression = formatExpression(expression); // Ausdruck formatieren
            String postfixExpression = Infix.toPostfixString(formattedExpression); // In Postfix umwandeln
            double result = Postfix.evaluatePostfixString(postfixExpression); // Postfix-Ausdruck auswerten
            textField.setText(formatResult(result)); // Ergebnis im Textfeld setzen
            clearField = true; // Setze das Flag zum Löschen des Textfelds bei der nächsten Eingabe
        }
        catch (Exception ex)
        {
            textField.setText("Error"); // Bei Fehler "Error" im Textfeld anzeigen
            clearField = true; // Setze das Flag zum Löschen des Textfelds bei der nächsten Eingabe
        }
    }

    // Methode zur Formatierung des Ausdrucks
    private String formatExpression(String expression)
    {
        expression = expression.replaceAll("([\\d.]+|\\))\\s*\\(", "$1 * ("); // Füge Multiplikationszeichen hinzu
        return expression.replaceAll("([\\d.]+|[^\\w\\s])", " $1 ").replaceAll("\\s+", " ").trim(); // Abstand und Formatierung
    }

    // Methode zur Formatierung des Ergebnisses
    private String formatResult(double result)
    {
        if (result == (int) result)
        {
            return String.valueOf((int) result); // Ganzzahliges Ergebnis
        }
        else
        {
            return String.valueOf(result); // Dezimalergebnis
        }
    }

    // Hauptmethode zum Starten des Rechners
    public static void main(String[] args)
    {
        new Calculator().setTitle("Calculator"); // Erstelle einen neuen Calculator mit Titel
    }
}
