package bsu.rfe.course2.group10.okolot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class MainFrame extends JFrame {

    enum RadioButtonsType {
        MEMORY,
        FORMULA
    }

    private static final int WIDTH = 650;
    private static final int HEIGHT = 320;

    private JTextField textFieldX;
    private JTextField textFieldY;
    private JTextField textFieldZ;
    private Double[] memCell = new Double[3];
    private JTextField textFieldResult;
    private JLabel labelForMemory = new JLabel("0.0", JLabel.CENTER);

    private ButtonGroup radioButtons = new ButtonGroup();
    private ButtonGroup radioButtons2 = new ButtonGroup();

    private int formulaId = 1;
    private int memoryId = 0;

    public MainFrame() {
        super("Вычисление формулы");
        setSize(WIDTH, HEIGHT);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(255, 255, 240));

        for (int i = 0; i < 3; i++) {
            memCell[i] = 0.0;
        }

        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        // Радио кнопки для формул
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(addRadioButton("Formula 1", 1, RadioButtonsType.FORMULA), gbc);
        gbc.gridx = 2;
        add(addRadioButton("Formula 2", 2, RadioButtonsType.FORMULA), gbc);

        // Создание панели для X, Y, Z
        JLabel labelForX = new JLabel("X:");
        textFieldX = new JTextField("0", 10);
        textFieldX.setMaximumSize(textFieldX.getPreferredSize());

        JLabel labelForY = new JLabel("Y:");
        textFieldY = new JTextField("0", 10);
        textFieldY.setMaximumSize(textFieldY.getPreferredSize());

        JLabel labelForZ = new JLabel("Z:");
        textFieldZ = new JTextField("0", 10);
        textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());

        Box hboxVariables = Box.createHorizontalBox();
        hboxVariables.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        hboxVariables.add(Box.createHorizontalGlue());
        hboxVariables.add(labelForX);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldX);
        hboxVariables.add(Box.createHorizontalStrut(50));
        hboxVariables.add(labelForY);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldY);
        hboxVariables.add(Box.createHorizontalStrut(50));
        hboxVariables.add(labelForZ);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldZ);
        hboxVariables.add(Box.createHorizontalGlue());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 6; // Занять всю ширину
        add(hboxVariables, gbc);

        // Кнопки и память
        Box hBoxMem = Box.createHorizontalBox();
        JButton buttonMemClear = new JButton("MC");
        buttonMemClear.setBorder(new LineBorder(new Color(252, 252, 252), 2));
        buttonMemClear.setBackground(new Color(215, 116, 116));
        buttonMemClear.setForeground(Color.WHITE);
        buttonMemClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                memCell[memoryId] = 0.0;
                labelForMemory.setText("0.0");
            }
        });

        JButton buttonMemAdd = new JButton("M+");
        buttonMemAdd.setBorder(new LineBorder(new Color(252, 252, 252), 2));
        buttonMemAdd.setBackground(new Color(215, 116, 116));
        buttonMemAdd.setForeground(Color.WHITE);
        buttonMemAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                double valueToAdd = Double.parseDouble(textFieldResult.getText());
                memCell[memoryId] += valueToAdd;
                labelForMemory.setText(Double.toString(memCell[memoryId]));
            }
        });

        hBoxMem.add(buttonMemClear);
        hBoxMem.add(buttonMemAdd);
        hBoxMem.add(labelForMemory);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 6; // Занять всю ширину
        add(hBoxMem, gbc);

        // Поле для результата
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Result:"), gbc);
        gbc.gridx = 1;
        textFieldResult = new JTextField("0", 12);
        add(textFieldResult, gbc);

        // Кнопки для вычисления и сброса
        gbc.gridy = 4;
        gbc.gridwidth = 1;

        JButton buttonReset = new JButton("Clear all");
        buttonReset.setPreferredSize(new Dimension(100, 30));
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                textFieldX.setText("0");
                textFieldY.setText("0");
                textFieldZ.setText("0");
                textFieldResult.setText("0");
            }
        });

        JButton buttonCalc = new JButton("Count");
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    Double x = Double.parseDouble(textFieldX.getText());
                    Double y = Double.parseDouble(textFieldY.getText());
                    Double z = Double.parseDouble(textFieldZ.getText());
                    Double result;
                    if (formulaId == 1)
                        result = calculate1(x, y, z);
                    else
                        result = calculate2(x, y, z);
                    textFieldResult.setText(result.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Error in the format of floating point number entry", "Erroneous number format", JOptionPane.WARNING_MESSAGE);
                } catch (ArithmeticException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Breached area of determination", "Area of determination error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        add(buttonReset, gbc);

        gbc.gridx = 5;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        add(buttonCalc, gbc);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JRadioButton addRadioButton(String buttonName, final int Id, RadioButtonsType Type) {
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (Type == RadioButtonsType.FORMULA) {
                    MainFrame.this.formulaId = Id;
                } else if (Type == RadioButtonsType.MEMORY) {
                    MainFrame.this.memoryId = Id;
                    labelForMemory.setText(Double.toString(memCell[Id]));
                }
            }
        });

        if (Type == RadioButtonsType.FORMULA)
            radioButtons.add(button);
        else if (Type == RadioButtonsType.MEMORY)
            radioButtons2.add(button);

        return button;
    }

    public Double calculate1(Double x, Double y, Double z) {
        // Формула 1
        double z2 = Math.pow(z, 2);
        double y2 = Math.pow(y, 2);
        double x2 = Math.pow(x, 2);
        double siny = Math.sin(y);
        double cosy = Math.cos(y);
        double pi = Math.PI;
        double sinpiy2 = Math.sin(pi * y2);
        double lnx2 = Math.log(x2);
        double second = Math.pow((sinpiy2 + lnx2), 1.0 / 4.0);
        double exp = Math.exp(cosy);
        return (Math.sin(siny + exp + z2)) * second;
    }

    public Double calculate2(Double x, Double y, Double z) {
        // Формула 2
        double y2 = Math.pow(y, 2);
        double first = Math.atan(Math.pow(z, 1.0 / x));
        double second = y2 + z * Math.sin(Math.log(x));
        return first / second;
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}