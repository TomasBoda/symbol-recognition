package matrix;

import components.RGB;

public class Matrix {

    public RGB[][] data;

    public Matrix(int width, int height) {
        this.data = new RGB[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.data[y][x] = new RGB(0, 0, 0);
            }
        }
    }
}
