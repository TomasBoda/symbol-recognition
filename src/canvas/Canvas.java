package canvas;

import logic.Handler;
import components.Matrix;
import main.Window;

import java.awt.*;
import java.util.ArrayList;

public class Canvas {

    public ArrayList<ArrayList<Point>> layerPoints;
    public ArrayList<Point> strokePoints;
    private Matrix showingMatrix;

    public Canvas() {
        layerPoints = new ArrayList<>();
        strokePoints = new ArrayList<>();
    }

    public void render(Graphics g) {
        if (Window.handler.mode == Handler.MODE.SHOW) {
            if (showingMatrix == null) return;

            int width = Window.panel.getWidth();
            int height = Window.panel.getHeight();

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    g.setColor(new Color(showingMatrix.data[i][j].red, showingMatrix.data[i][j].green, showingMatrix.data[i][j].blue));
                    g.fillRect(j, i, 1, 1);
                }
            }

            return;
        }

        // draws the handwritten lines onto a canvas

        g.setColor(Color.WHITE);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(25));

        for (int i = 0; i < layerPoints.size(); i++) {
            ArrayList<Point> layer = layerPoints.get(i);

            for (int j = 0; j < layer.size() - 1; j++) {
                Point p1 = layer.get(j);
                Point p2 = layer.get(j + 1);

                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        for (int j = 0; j < strokePoints.size() - 1; j++) {
            Point p1 = strokePoints.get(j);
            Point p2 = strokePoints.get(j + 1);

            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public void setShowingMatrix(Matrix showingMatrix) {
        this.showingMatrix = showingMatrix;
    }

    // clears the canvas
    public void clear() {
        this.layerPoints.clear();
        this.strokePoints.clear();
    }
}
