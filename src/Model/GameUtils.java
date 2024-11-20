package Model;
import java.awt.Color;
import java.util.Random;

public class GameUtils {
    private static final Random RANDOM = new Random();

    public static float[] randomColor() {
        float red = RANDOM.nextFloat();   
        float green = RANDOM.nextFloat(); 
        float blue = RANDOM.nextFloat();  
        return new float[]{red, green, blue};
    }

    public static Shape randomShape() {
        Shape[] shapes = Shape.values(); 
        return shapes[RANDOM.nextInt(shapes.length)]; 
    }
    public static int getMaxColsInAllShapes() {
        int maxCols = 0;
        for (Shape shape : Shape.values()) {
            int cols = shape.getMaxCols();
            if (cols > maxCols) {
                maxCols = cols;
            }
        }
        return maxCols;
    }
}
