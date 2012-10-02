import java.util.ArrayList;

public class World {
    private static PixelColor[][] pixels;
    private int width;
    private int height;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new PixelColor[width][height];
        createBackground();

    }

    private void createBackground() {
        for(int i= 0; i < width ; i++){
            for(int j= 0 ; j < height ;j++){
               pixels[i][j] = PixelColor.BLACK;
            }
        }
    }

    public static void checkForCollisions(int x, int y){

    }

    public static PixelColor getPixel(int x, int y){
        return pixels[x][y];
    }

    public static void setPixel(int x, int y, PixelColor color){
        pixels[x][y] = color;
    }


}
