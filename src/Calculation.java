/**
 * Created by jacob on 11/14/15.
 */
public class Calculation
{
    private final static String[] OPERATORS = {"—", "+", "*", "/", "%", "^"};

    private String expression;
    private String solution;

    public Calculation(String expression)
    {
        this.expression = expression;
    }

    public double calculate()
    {
        if(solution == null)
        {
            String solverExpression = expression;


            int i;

            while ((i = solverExpression.indexOf("(")) != -1)
            {
                Calculation calculation = new Calculation(solverExpression.substring(i + 1, solverExpression.lastIndexOf(")")));
                StringBuilder stringBuilder = new StringBuilder(solverExpression);
                stringBuilder.replace(i, solverExpression.lastIndexOf(")") + 1, String.valueOf(calculation.calculate()));
                solverExpression = stringBuilder.toString();
            }

            while ((i = findOperatorsIndex(solverExpression, '^')) != -1)
            {
                solverExpression = calculatePiece(i, solverExpression);
            }
            while ((i = findOperatorsIndex(solverExpression, '*', '/', '%')) != -1)
            {
                solverExpression = calculatePiece(i, solverExpression);
            }
            while ((i = findOperatorsIndex(solverExpression, '+', '—')) != -1)
            {
                solverExpression = calculatePiece(i, solverExpression);
            }

            solution = solverExpression;
        }

        double number;
        try
        {
            number = Double.parseDouble(solution);
        }
        catch (NumberFormatException nfe)
        {
            number = 0.0D;
        }

        return number;
    }

    private static String calculatePiece(int operatorIndex, String expression)
    {
        String operand1 = findWordBefore(expression, operatorIndex);
        String operand2 = findWordAfter(expression, operatorIndex);

        double result = evaluate(Double.parseDouble(operand1), Double.parseDouble(operand2), expression.charAt(operatorIndex));

        StringBuilder stringBuilder = new StringBuilder(expression);
        stringBuilder.replace(findWordIndexBefore(expression, operatorIndex), findWordLastIndexAfter(expression, operatorIndex), String.valueOf(result));
        return stringBuilder.toString();
    }

    private static int findOperatorsIndex(String expression, char... operators)
    {
        for(int i = 0; i < expression.length(); i++)
        {
            for(char operator : operators)
            {
                if(expression.charAt(i) == operator)
                {
                    return i;
                }
            }
        }
        return -1;
    }

    private static double evaluate(double first, double second, char operator)
    {
        if(operator == '*')
        {
            return first * second;
        }
        else if(operator == '/')
        {
            return first / second;
        }
        else if(operator == '+')
        {
            return first + second;
        }
        else if(operator == '—')
        {
            return first - second;
        }
        else if(operator == '%')
        {
            return first % second;
        }
        else if(operator == '^')
        {
            return Math.pow(first, second);
        }
        else
        {
            System.err.println("Unknown operator: " + operator);
            return 0.0D;
        }
    }

    private static String findWordBefore(String expression, int index)
    {
        int i;
        for(i = index - 2; i >= 0; i--)
        {
            if(expression.charAt(i) == ' ')
            {
                break;
            }
        }
        if(i < 0)
        {
            i = 0;
        }

        return expression.substring(i, index - 1);
    }

    private static String findWordAfter(String expression, int index)
    {
        int i;
        for(i = index + 2; i < expression.length(); i++)
        {
            if(expression.charAt(i) == ' ')
            {
                break;
            }
        }

        return expression.substring(index + 2, i);
    }

    private static int findWordIndexBefore(String expression, int index)
    {
        int i;
        for(i = index - 2; i >= 0; i--)
        {
            if(expression.charAt(i) == ' ')
            {
                i++;
                break;
            }
        }
        if(i < 0)
        {
            i = 0;
        }

        return i;
    }

    private static int findWordLastIndexAfter(String expression, int index)
    {
        int i;
        for(i = index + 2; i < expression.length(); i++)
        {
            if(expression.charAt(i) == ' ')
            {
                break;
            }
        }

        return i;
    }

    public static boolean isOperator(String text)
    {
        for (String operand : OPERATORS)
        {
            if (text.equals(operand))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString()
    {
        return expression + " = " + calculate();
    }
}
