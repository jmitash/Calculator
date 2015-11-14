import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by jacob on 11/14/15.
 */
public class DankulatorFrame extends JFrame
{
    private final static int HISTORY_ROW_COUNT = 6;

    private JList<Calculation> historyList;
    private DefaultListModel<Calculation> historyListModel = new DefaultListModel<>();
    private JTextField expressionInputField = new JTextField();

    public DankulatorFrame()
    {
        super("Dankulator");


        //JMenuBar
        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Stuff");
        JMenuItem primeItem = new JMenuItem("Prime check");
        fileMenu.add(primeItem);
        primeItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                String input = JOptionPane.showInputDialog("Please enter a number to check:");
                int number = Integer.parseInt(input);

                boolean isPrime = true;

                for (int i = 2; i < Math.sqrt(number); i++)
                {
                    if (number % i == 0)
                    {
                        isPrime = false;
                    }
                }

                JOptionPane.showMessageDialog(DankulatorFrame.this, "" + number + (isPrime ? " is prime." : " is not prime."));
            }
        });
        JMenuItem graphulatorItem = new JMenuItem("Graphulator");
        graphulatorItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                new GraphulatorFrame().setVisible(true);
            }
        });
        fileMenu.add(graphulatorItem);
        jMenuBar.add(fileMenu);
        this.setJMenuBar(jMenuBar);

        //set up center panel
        JPanel centerPane = new JPanel();
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));

        JPanel historyListPanel = new JPanel(new GridLayout(1, 1));
        historyList = new JList<>(historyListModel);
        historyList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        historyList.setLayoutOrientation(JList.VERTICAL);
        historyList.setVisibleRowCount(HISTORY_ROW_COUNT);

        historyList.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
                {
                    int index = historyList.locationToIndex(e.getPoint());
                    expressionInputField.setText(expressionInputField.getText() + historyListModel.get(index).calculate());
                }
            }
        });

        historyListPanel.add(new JScrollPane(historyList));


        JPanel expressionInputPanel = new JPanel(new GridLayout(1, 1));
        expressionInputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        expressionInputField.setEditable(false);
        expressionInputPanel.add(expressionInputField);

        centerPane.add(historyListPanel);
        centerPane.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPane.add(expressionInputField);
        centerPane.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPane.add(new ButtonsPanel(historyList, historyListModel, expressionInputField));


        this.getContentPane().add(centerPane, BorderLayout.CENTER);
        this.getContentPane().add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);
        this.getContentPane().add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
        this.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
        this.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);

        this.setMinimumSize(new Dimension(200, 300));

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}
