import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by jacob on 11/14/15.
 */
public class GraphulatorFrame extends JFrame
{
    private JTextField xMinField = new JTextField("0", 5);
    private JTextField xMaxField = new JTextField("100", 5);
    private JTextField yMinField = new JTextField("0", 5);
    private JTextField yMaxField = new JTextField("100", 5);


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
        optionsPanel.add(new JButton("Graph"));

        this.getContentPane().add(new GraphPanel(), BorderLayout.CENTER);
        this.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
        this.getContentPane().add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);
        this.getContentPane().add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
        this.getContentPane().add(optionsPanel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    private class GraphPanel extends JPanel
    {
        private String equation;

        public GraphPanel()
        {
            equation = "x ^ 2";
        }

        @Override
        public void paint(Graphics g)
        {
            final int xMax = Integer.parseInt(xMaxField.getText());
            final int xMin = Integer.parseInt(xMinField.getText());
            final int yMax = Integer.parseInt(yMaxField.getText());
            final int yMin = Integer.parseInt(yMinField.getText());
            final int width = Integer.parseInt(xMaxField.getText()) - Integer.parseInt(xMinField.getText());
            final int height = Integer.parseInt(yMaxField.getText()) - Integer.parseInt(yMinField.getText());
            /*Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
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

            g.drawImage(scaledImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), this);*/

            double pixelWidthScale = this.getWidth() / width;
            double pixelHeightScale = this.getHeight() / height;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setBackground(Color.WHITE);
            super.paint(g);

            g2d.setColor(Color.BLACK);

            for(int x = xMin; x < xMax; x++)
            {
                Calculation calculation = new Calculation(equation.replaceAll("x", String.valueOf(x)));
                Calculation calculation1 = new Calculation(equation.replaceAll("x", String.valueOf(x + 1)));
                g2d.drawLine(x, (int) (calculation.calculate()), x + 1, (int) calculation1.calculate());
            }

        }
    }
}
