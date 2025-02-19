package GUI;
import BusinessLogic.SimulationManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
public class View extends JFrame {
    private JPanel contentPanel;
    private JPanel inputDataPanel;
    private JPanel resultPanel;
    private JLabel nLabel;
    private JTextField nTextField;
    private JLabel qLabel;
    private JTextField qTextField;
    private JLabel tMaxSimulationLabel;
    private JTextField tMaxSimulationTextField;
    private JLabel tMinArrivalLabel;
    private JTextField tMinArrivalTextField;
    private JLabel tMaxArrivalLabel;
    private JTextField tMaxArrivalTextField;
    private JLabel tMinServiceLabel;
    private JTextField tMinServiceTextField;
    private JLabel tMaxServiceLabel;
    private JTextField tMaxServiceTextField;
    private JLabel strategyLabel;
    private JComboBox  strategyComboBox;
    private JButton computeButton;
    private JTextArea resultArea;
    private SimulationManager simulationManager = new SimulationManager(this);

    public View(String name) {
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.contentPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(this.contentPanel, BoxLayout.Y_AXIS);
        this.contentPanel.setLayout(boxlayout);
        this.contentPanel.setBorder(new EmptyBorder(new Insets(30, 50, 50, 50)));
        this.contentPanel.setBackground(Color.yellow);

        JLabel titleLabel = new JLabel("Queues management application");
        titleLabel.setFont(titleLabel.getFont().deriveFont(16.0f));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.contentPanel.add(titleLabel);

        this.prepareInputDataPanel();
        this.prepareResultPanel();

        this.setContentPane(this.contentPanel);
    }

    private void prepareResultPanel() {

        this.resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.resultPanel.setBackground(Color.yellow);

        this.resultArea = new JTextArea(10, 60);
        this.resultArea.setFont( this.resultArea.getFont().deriveFont(16.0f));
        this.resultArea.setBackground(Color.PINK);
        this.resultArea.setBorder(new LineBorder(Color.magenta, 2));

        this.resultPanel.add(this.resultArea);
        this.contentPanel.add(this.resultPanel);
    }

    private void prepareInputDataPanel() {

        this.inputDataPanel = new JPanel(new GridLayout(9, 1, 2, 10));
        this.inputDataPanel.setBackground(Color.yellow);

        this.nLabel = new JLabel("Numar clienti: ", JLabel.CENTER);
        this.nLabel.setFont( this.nLabel.getFont().deriveFont(16.0f));
        this.nTextField = new JTextField();
        this.nTextField.setBackground(Color.PINK);
        this.nTextField.setBorder(new LineBorder(Color.magenta, 2));

        this.qLabel = new JLabel("Numar servere: ", JLabel.CENTER);
        this.qLabel.setFont( this.qLabel.getFont().deriveFont(16.0f));
        this.qTextField = new JTextField();
        this.qTextField.setBackground(Color.PINK);
        this.qTextField.setBorder(new LineBorder(Color.magenta, 2));

        this.tMaxSimulationLabel = new JLabel("Intervalul de simulare: ", JLabel.CENTER);
        this.tMaxSimulationLabel.setFont( this.tMaxSimulationLabel.getFont().deriveFont(16.0f));
        this.tMaxSimulationTextField = new JTextField();
        this.tMaxSimulationTextField.setBackground(Color.PINK);
        this.tMaxSimulationTextField.setBorder(new LineBorder(Color.magenta, 2));

        this.tMinArrivalLabel = new JLabel("Timpul minim de preluare: ", JLabel.CENTER);
        this.tMinArrivalLabel.setFont( this.tMinArrivalLabel.getFont().deriveFont(16.0f));
        this.tMinArrivalTextField = new JTextField();
        this.tMinArrivalTextField.setBackground(Color.PINK);
        this.tMinArrivalTextField.setBorder(new LineBorder(Color.magenta, 2));

        this.tMaxArrivalLabel = new JLabel("Timpul maxim de preluare: ", JLabel.CENTER);
        this.tMaxArrivalLabel.setFont( this.tMaxArrivalLabel.getFont().deriveFont(16.0f));
        this.tMaxArrivalTextField = new JTextField();
        this.tMaxArrivalTextField.setBackground(Color.PINK);
        this.tMaxArrivalTextField.setBorder(new LineBorder(Color.magenta, 2));

        this.tMinServiceLabel = new JLabel("Timpul minim de procesare: ", JLabel.CENTER);
        this.tMinServiceLabel.setFont( this.tMinServiceLabel.getFont().deriveFont(16.0f));
        this.tMinServiceTextField = new JTextField();
        this.tMinServiceTextField.setBackground(Color.PINK);
        this.tMinServiceTextField.setBorder(new LineBorder(Color.magenta, 2));

        this.tMaxServiceLabel = new JLabel("Timpul maxim de procesare: ", JLabel.CENTER);
        this.tMaxServiceLabel.setFont( this.tMaxServiceLabel.getFont().deriveFont(16.0f));
        this.tMaxServiceTextField = new JTextField();
        this.tMaxServiceTextField.setBackground(Color.PINK);
        this.tMaxServiceTextField.setBorder(new LineBorder(Color.magenta, 2));

        this.strategyLabel = new JLabel("Alege strategia: ", JLabel.CENTER);
        this.strategyLabel.setFont( this.strategyLabel.getFont().deriveFont(16.0f));
        String[] strategy = new String[]{"Shortest queue", "Shortest time"};
        this.strategyComboBox = new JComboBox(strategy);
        this.strategyComboBox.setBackground(Color.PINK);
        this.strategyComboBox.setBorder(new LineBorder(Color.magenta, 2));
        this.strategyComboBox.setFont(this.strategyComboBox.getFont().deriveFont(15.0f));

        this.computeButton = new JButton("Start simulare");
        this.computeButton.setPreferredSize(new Dimension(150, 30));
        this.computeButton.setBorder(new LineBorder(Color.magenta, 2));
        this.computeButton.setFont(this.computeButton.getFont().deriveFont(13.0f));
        this.computeButton.setBackground(Color.PINK);
        this.computeButton.setActionCommand("COMPUTE");
        this.computeButton.addActionListener(simulationManager);


        this.inputDataPanel.add(this.nLabel);
        this.inputDataPanel.add(this.nTextField);
        this.inputDataPanel.add(this.qLabel);
        this.inputDataPanel.add(this.qTextField);
        this.inputDataPanel.add(this.tMaxSimulationLabel);
        this.inputDataPanel.add(this.tMaxSimulationTextField);
        this.inputDataPanel.add(this.tMinArrivalLabel);
        this.inputDataPanel.add(this.tMinArrivalTextField);
        this.inputDataPanel.add(this.tMaxArrivalLabel);
        this.inputDataPanel.add(this.tMaxArrivalTextField);
        this.inputDataPanel.add(this.tMinServiceLabel);
        this.inputDataPanel.add(this.tMinServiceTextField);
        this.inputDataPanel.add(this.tMaxServiceLabel);
        this.inputDataPanel.add(this.tMaxServiceTextField);
        this.inputDataPanel.add(this.strategyLabel);
        this.inputDataPanel.add(this.strategyComboBox);
        this.inputDataPanel.add(this.computeButton);

        this.contentPanel.add(this.inputDataPanel);
    }

    public JTextField nTextField() {
        return nTextField;
    }

    public JTextField qTextField() {
        return qTextField;
    }

    public JTextField getMaxSimulationTextField() {
        return tMaxSimulationTextField;
    }

    public JTextField getMinArrivalTextField() {
        return tMinArrivalTextField;
    }

    public JTextField getMaxArrivalTextField() {
        return tMaxArrivalTextField;
    }

    public JTextField getMinServiceTextField() {
        return tMinServiceTextField;
    }

    public JTextField getMaxServiceTextField() {
        return tMaxServiceTextField;
    }

    public JTextArea getResultArea() {
        return resultArea;
    }

    public JComboBox  getStrategyComboBox () {
        return strategyComboBox;
    }
}

