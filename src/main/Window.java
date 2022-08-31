package main;

import canvas.Canvas;
import logic.Handler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JPanel implements ActionListener {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    public static JFrame frame;
    public static JPanel panel;
    public static JPanel toolbar;
    public static JTextField inputField;
    public static JButton actionButton;
    public static JButton modeButton;

    private Timer timer;
    private final int FPS = 120;

    public static Canvas canvas;
    public static Handler handler;
    private Input input;
    public Window() {
        frame = new JFrame("Handwritten Symbol Recognition - MODE: TRAIN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        canvas = new Canvas();
        handler = new Handler();

        input = new Input();

        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(WIDTH, HEIGHT + 38));

        panel = new JPanel(true) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                canvas.render(g);
            }
        };

        panel.addKeyListener(input);
        panel.addMouseListener(input);
        panel.addMouseMotionListener(input);
        panel.setFocusable(true);
        panel.setBackground(Color.BLACK);

        toolbar = new JPanel();
        toolbar.setLayout(new BorderLayout(0, 0));

        inputField = new JTextField();
        inputField.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));

        actionButton = new JButton("Train");
        actionButton.setFocusPainted(false);
        actionButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (handler.mode == Handler.MODE.TRAIN) {
                    handler.train();
                } else if (handler.mode == Handler.MODE.ESTIMATE) {
                    handler.estimate();
                } else {
                    handler.show();
                }
            }
        });

        modeButton = new JButton("Change mode");
        modeButton.setFocusPainted(false);
        modeButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        modeButton.addActionListener(e -> handler.changeMode());

        toolbar.add(modeButton, BorderLayout.LINE_START);
        toolbar.add(inputField, BorderLayout.CENTER);
        toolbar.add(actionButton, BorderLayout.LINE_END);

        timer = new Timer(1000 / FPS, this);
        timer.start();

        this.add(panel, BorderLayout.CENTER);
        this.add(toolbar, BorderLayout.PAGE_END);

        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        panel.repaint();
    }

    public static void main(String[] args) {
        new Window();
    }
}
