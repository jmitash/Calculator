import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jacob on 11/14/15.
 */
public class ButtonsPanel extends JPanel implements ActionListener
{
    private final static int ROWS = 4;
    private final static int COLUMNS = 6;

    private JTextField expressionField;
    private DefaultListModel<Calculation> calculationHistory;
    private JList<Calculation> calculationJList;

    public ButtonsPanel(JList<Calculation> calculationJList, DefaultListModel<Calculation> calculationHistory, JTextField expressionField)
    {
        super(new GridLayout(ROWS, COLUMNS));

        this.calculationJList = calculationJList;
        this.calculationHistory = calculationHistory;
        this.expressionField = expressionField;

        String[] buttons = new String[]
                {"7", "8", "9", "—", "-", "(",
                "4", "5", "6", "+", "%", ")",
                "1", "2", "3", "*", "^", null,
                "0", ".", "=", "/", null, null};

        for (String buttonText : buttons) {
            JButton button;
            if(buttonText == null)
            {
                button = null;
            }
            else
            {
                button = new JButton(buttonText);
            }

            if(button != null)
            {
                this.add(button);
                button.addActionListener(this);
            }
            else
            {
                this.add(Box.createRigidArea(new Dimension(10, 10)));
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        final JButton button = (JButton) actionEvent.getSource();
        final String expressionText = expressionField.getText();

       if(button.getText().equals("="))
       {
           if(expressionText.isEmpty())
           {
               return;
           }
           Calculation calculation = new Calculation(expressionText);
           expressionField.setText(""/*String.valueOf(calculation.calculate())*/);
           calculationHistory.addElement(calculation);
           calculationJList.ensureIndexIsVisible(calculationHistory.getSize() - 1);
           calculationJList.setSelectedIndex(calculationHistory.getSize() - 1);
       }
       else
       {
           String appendText = button.getText();
           if(Calculation.isOperator(button.getText()))
           {
               //check if user is putting two operators side by side
               if(expressionText.length() > 2
                       && Calculation.isOperator(String.valueOf(expressionText.charAt(expressionText.length() - 2))))
               {
                   appendText = "";
                   StringBuilder stringBuilder = new StringBuilder(expressionText);
                   stringBuilder.setCharAt(expressionText.length() - 2, button.getText().charAt(0));
                   expressionField.setText(stringBuilder.toString());
               }
               else if(expressionText.isEmpty())
               {
                   appendText = "";
               }
               else
               {
                   appendText = " " + appendText + " ";
               }
           }
           if(button.getText().charAt(0) == '-' && !expressionText.isEmpty())
           {
               if(expressionText.charAt(expressionText.length() - 1) == '-')
               {
                   return;
               }
           }
           this.expressionField.setText(expressionField.getText() + appendText);
       }
    }


}
