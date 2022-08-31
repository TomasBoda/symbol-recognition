package components;

public class RGB {

    public int red;
    public int green;
    public int blue;

    public RGB(int red, int green, int blue) {
        setRGB(red, green, blue);
    }

    public void setRGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    // sums two RGB values
    // returns a new RGB instance
    public static RGB sum(RGB rgb1, RGB rgb2) {
        return new RGB(rgb1.red + rgb2.red, rgb1.green + rgb2.green, rgb1.blue + rgb2.blue);
    }

    // divides an RGB value by a whole number (used for calculating averaged data)
    // returns a new RGB instance
    public static RGB average(RGB rgb, int n) {
        return new RGB(rgb.red / n, rgb.green / n, rgb.red / n);
    }
}
