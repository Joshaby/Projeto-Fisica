package br.edu.ifpb;

import javax.swing.*;

public class App extends JFrame {
    public static void main( String[] args ) {
        while (true) {
            try {
                String num1 = JOptionPane.showInputDialog("Enter first number:");
                if (num1.equals(null)) break;
                int intNum1 = Integer.parseInt(num1);
                String num2 = JOptionPane.showInputDialog("Enter second number:");
                if (num2.equals(null)) break;
                int intNum2 = Integer.parseInt(num2);
                JOptionPane.showMessageDialog(
                        null,
                        String.format("The sum is %d", (intNum1 + intNum2)),
                        "Sum of two integers",
                        JOptionPane.PLAIN_MESSAGE);
                break;
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "You typed a string instead of an integer!",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            catch (NullPointerException e) {
                System.err.println("Exiting");
                break;
            }
        }
    }
}
