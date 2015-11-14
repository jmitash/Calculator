import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacob on 11/14/15.
 */
public class GraphulatorFrame extends JFrame
{
    private int xMin = -10;
    private int xMax = 10;
    private int yMin = 0;
    private int yMax = 100;

    private GraphPanel graphPanel = new GraphPanel();
    private JTextField equationField = new JTextField("x ^ 2", 15);
    private JTextField xMinField = new JTextField(String.valueOf(xMin), 5);
    private JTextField xMaxField = new JTextField(String.valueOf(xMax), 5);
    private JTextField yMinField = new JTextField(String.valueOf(yMin), 5);
    private JTextField yMaxField = new JTextField(String.valueOf(yMax), 5);



    public GraphulatorFrame()
    {
        super("Graphulator");

        JPanel optionsPanel = new JPanel();
        optionsPanel.add(new JLabel("X-Min: "));
        optionsPanel.add(xMinField);
        optionsPanel.add(new JLabel("X-Max: "));
        optionsPanel.add(xMaxField);
        optionsPanel.add(new JLabel("Y-Min: "));
        optionsPanel.add(yMinField);
        optionsPanel.add(new JLabel("Y-Max: "));
        optionsPanel.add(yMaxField);

        final JButton graphButton = new JButton("Graph");
        graphButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                graphPanel.setEquation(equationField.getText());
                try
                {
                    xMin = Integer.parseInt(xMinField.getText());
                    xMax = Integer.parseInt(xMaxField.getText());
                    yMin = Integer.parseInt(yMinField.getText());
                    yMax = Integer.parseInt(yMaxField.getText());
                    repaint();
                } catch (NumberFormatException nfe)
                {
                    System.err.println("Invalid bounds");
                }
            }
        });
        optionsPanel.add(graphButton);

        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("f(x) = "));
        northPanel.add(equationField);

        this.getContentPane().add(graphPanel, BorderLayout.CENTER);
        this.getContentPane().add(northPanel, BorderLayout.NORTH);
        this.getContentPane().add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);
        this.getContentPane().add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
        this.getContentPane().add(optionsPanel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private class GraphPanel extends JPanel
    {
        private String equation = "x ^ 2";

        public void setEquation(String equation)
        {
            this.equation = equation;
            this.repaint();
        }

        @Override
        public void paint(Graphics g)
        {
            double step = 0.1D;

            List<Point2D.Double> points = new ArrayList<>((int) ((xMax - xMin) / step));

            for(double x = xMin; x < xMax; x += step)
            {
                Calculation calculation = new Calculation(equation.replaceAll("x", String.valueOf(x)));
                points.add(new Point2D.Double(x, calculation.calculate()));
            }

            double minY = Double.MAX_VALUE;
            double maxY = Double.MIN_VALUE;
            for(Point2D.Double point : points)
            {
                minY = Math.min(minY, point.getY());
                maxY = Math.max(maxY, point.getY());
            }

            double xScale = (double) this.getWidth() / (xMax - xMin);
            double yScale = (double) this.getHeight() / (yMax - yMin);

            g.setColor(Color.BLACK);
            for(int i = 0; i < points.size() - 1; i++)
            {
                g.drawLine((int) ( xScale * (points.get(i).getX() - xMin)),
                        (int) (this.getHeight() - yScale * (points.get(i).getY() - minY)),
                        (int) (xScale * (points.get(i + 1).getX() - xMin)),
                        (int) (this.getHeight() - yScale * (points.get(i + 1).getY()) - minY));
            }


        }

        /*@Override
        public void paint(Graphics g)
        {
            final int width = xMax - xMin;
            final int height = yMax - yMin;
            *//*Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

            g2d.setColor(Color.BLACK);
            g2d.drawLine(bufferedImage.getWidth() / 2, 0, bufferedImage.getWidth() / 2, bufferedImage.getHeight());
            g2d.drawLine(0, bufferedImage.getHeight() / 2, bufferedImage.getWidth(), bufferedImage.getHeight() / 2);

            BufferedImage scaledImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.scale((double) this.getWidth() / bufferedImage.getWidth(), (double) this.getHeight() / bufferedImage.getHeight());
            AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
            scaledImage = affineTransformOp.filter(bufferedImage, scaledImage);

            g.drawImage(scaledImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), this);*//*

            double pixelWidthScale = this.getWidth() / (double) width;
            double pixelHeightScale = this.getHeight() / (double) height;

            System.out.println("W: " + pixelWidthScale);
            System.out.println("H: " + pixelHeightScale);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setBackground(Color.WHITE);
            super.paint(g);

            g2d.setColor(Color.BLACK);

            for(int x = xMin; x < xMax; x++)
            {
                Calculation calculation = new Calculation(equation.replaceAll("x", String.valueOf(x)));
                Calculation calculation1 = new Calculation(equation.replaceAll("x", String.valueOf(x + 1)));
                g2d.drawLine(
                        (int) (pixelWidthScale * (x - xMin)),
                        (int) (this.getHeight() - calculation.calculate() * pixelHeightScale),
                        (int) (pixelWidthScale * (x + 1 - xMin)),
                        (int) (this.getHeight() - calculation1.calculate() * pixelHeightScale));
            }

        }*/
    }
}
