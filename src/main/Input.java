package main;

import canvas.Canvas;
import canvas.Point;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Window.canvas.strokePoints.add(new Point(x, y));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Rectangle mouse = new Rectangle(x, y, 1, 1);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ArrayList<Point> points = new ArrayList<Point>();

        for (Point point : Window.canvas.strokePoints) {
            points.add(new Point(point.x, point.y));
        }

        Window.canvas.layerPoints.add(points);
        Window.canvas.strokePoints.clear();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
