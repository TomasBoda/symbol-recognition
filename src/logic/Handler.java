package logic;

import components.RGB;
import main.Window;
import matrix.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Handler {

    public enum MODE { TRAIN, ESTIMATE, SHOW };
    public MODE mode = MODE.TRAIN;

    public Hashtable<String, ArrayList<Matrix>> trainedData;
    public Hashtable<String, Matrix> averagedData;

    public Handler() {
        trainedData = new Hashtable<>();
        averagedData = new Hashtable<>();
    }

    // changes app mode - train / estimate
    public void changeMode() {
        if (mode == MODE.TRAIN) {
            Window.frame.setTitle("Handwritten Symbol Recognition - MODE: ESTIMATE");
            Window.actionButton.setText("Estimate");

            Window.inputField.setText("");
            Window.inputField.setFocusable(false);

            calculateMatrixAverages();
            mode = MODE.ESTIMATE;
        } else if (mode == MODE.ESTIMATE) {
            Window.frame.setTitle("Handwritten Symbol Recognition - MODE: SHOW");
            Window.actionButton.setText("Show");

            Window.inputField.setText("");
            Window.inputField.setFocusable(true);

            mode = MODE.SHOW;
        } else {
            Window.frame.setTitle("Handwritten Symbol Recognition - MODE: TRAIN");
            Window.actionButton.setText("Train");

            Window.inputField.setText("");
            Window.inputField.setFocusable(true);

            mode = MODE.TRAIN;
        }
    }

    // generates matrix and pairs it with a given symbol
    public void train() {
        String key = Window.inputField.getText().trim();
        Matrix value = Window.handler.getDigitMatrix();

        if (key.equals("")) {
            JOptionPane.showMessageDialog(Window.frame, "Input field cannot be empty. Please fill in a value.", "Warning", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        ArrayList<Matrix> matrices = trainedData.get(key);

        if (matrices == null) {
            matrices = new ArrayList<>();
            trainedData.put(key, matrices);
        }

        matrices.add(value);

        Window.canvas.layerPoints.clear();
        Window.canvas.strokePoints.clear();
        Window.inputField.setText("");
    }

    // estimates given symbol matrix
    public void estimate() {
        Matrix drawnMatrix = getDigitMatrix();

        Hashtable<String, Double> distances = new Hashtable<>();

        int width = Window.panel.getWidth();
        int height = Window.panel.getHeight();

        Enumeration<String> enumeration = averagedData.keys();

        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            Matrix matrix = averagedData.get(key);

            if (matrix == null) continue;

            double distance = 0;

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    double colorDistance = Math.sqrt((drawnMatrix.data[i][j].red - matrix.data[i][j].red)^2 + (drawnMatrix.data[i][j].green - matrix.data[i][j].green)^2 + (drawnMatrix.data[i][j].blue - matrix.data[i][j].blue)^2);
                    double percentageDistance = colorDistance / Math.sqrt((255^2) + (255^2) + (255^2));

                    if (Double.isNaN(colorDistance)) continue;

                    distance += colorDistance;
                }
            }

            distance = Math.sqrt(distance);
            distances.put(key, distance);
        }

        double closestValue = 0;
        String closestKey = "";

        Enumeration<String> eDist = distances.keys();

        while (eDist.hasMoreElements()) {
            String key = eDist.nextElement();
            double value = distances.get(key);

            if (closestValue == 0) {
                closestValue = value;
                closestKey = key;
            } else {
                if (value < closestValue) {
                    closestValue = value;
                    closestKey = key;
                }
            }
        }

        Window.inputField.setText(closestKey);
        Window.canvas.clear();
    }

    public void show() {
        String key = Window.inputField.getText().trim();
        Matrix matrix = averagedData.get(key);

        if (matrix == null) {
            JOptionPane.showMessageDialog(Window.frame, "Given symbol does not exist.", "Warning", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        Window.canvas.setShowingMatrix(matrix);
    }

    // generates symbol averages
    private void calculateMatrixAverages() {
        averagedData.clear();

        Enumeration<String> enumeration = trainedData.keys();

        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            ArrayList<Matrix> matrices = trainedData.get(key);

            int width = Window.panel.getWidth();
            int height = Window.panel.getHeight();

            Matrix sumMatrix = new Matrix(width, height);

            for (Matrix matrix : matrices) {
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        sumMatrix.data[i][j] = RGB.sum(sumMatrix.data[i][j], matrix.data[i][j]);
                    }
                }
            }

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    sumMatrix.data[i][j] = RGB.average(sumMatrix.data[i][j], matrices.size());
                }
            }

            averagedData.put(key, sumMatrix);
        }
    }

    public Matrix getDigitMatrix() {
        JPanel panel = Window.panel;

        int width = panel.getWidth();
        int height = panel.getHeight();

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        panel.paint(g);

        Matrix newDigit = new Matrix(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = img.getRGB(i, j);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = (rgb & 0xFF);

                newDigit.data[j][i].setRGB(red, green, blue);
            }
        }

        return newDigit;
    }
}