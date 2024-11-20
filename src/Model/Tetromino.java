package Model;
import java.util.Arrays;

public class Tetromino {
    // Các hình dạng của Tetromino (ví dụ: I, O, T, L, S, Z, J)
    private int[][] shape;
    private int x, y;
    private float[] color;
    private int[][][] rotations; // Tất cả các trạng thái xoay của Tetromino
    private int currentRotation; // Vị trí xoay hiện tại
    public Tetromino(Shape shape, int startX, int startY) {
    	this.shape = shape.getShape();
        this.x = startX;
        this.y = startY;
        this.color = GameUtils.randomColor();
        this.rotations = generateRotations(shape.getShape()); // Tạo tất cả các phiên bản xoay
        this.currentRotation = 0;  // Bắt đầu từ xoay đầu tiên
    }
    // Xoay khối theo chiều kim đồng hồ
    public void rotate() {
        currentRotation = (currentRotation + 1) % rotations.length;
        shape = rotations[currentRotation];
    }

    // Hoàn tác xoay (trong trường hợp xoay gây va chạm)
    public void undoRotate() {
        currentRotation = (currentRotation - 1 + rotations.length) % rotations.length;
        shape = rotations[currentRotation];
    }

    public int[][] getShape() {
        return shape;
    }

    public void moveDown() {
        y += 1; // Thay đổi hướng xuống
    }

    public void moveLeft() {
        x -= 1; 
    }

    public void moveRight() {
        x += 1; 
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public float[] getColor() {
        return color; // Trả về màu
    }
    // Tạo tất cả các phiên bản xoay của Tetromino
    private int[][][] generateRotations(int[][] originalShape) {
        int[][][] allRotations = new int[4][][]; // Có tối đa 4 trạng thái xoay
        allRotations[0] = originalShape;         // Trạng thái ban đầu
        for (int i = 1; i < 4; i++) {
            allRotations[i] = rotateMatrix(allRotations[i - 1]); // Xoay từ phiên bản trước
        }
        return allRotations;
    }

    // Phương thức xoay ma trận (xoay theo chiều kim đồng hồ)
    private int[][] rotateMatrix(int[][] matrix) {
        int rowCount = matrix.length;
        int colCount = matrix[0].length;
        int[][] rotated = new int[colCount][rowCount];

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                rotated[col][rowCount - 1 - row] = matrix[row][col];
            }
        }

        return rotated;
    }
	@Override
	public String toString() {
		return "Tetromino [shape=" + Arrays.toString(shape) + ", x=" + x + ", y=" + y + ", color="
				+ Arrays.toString(color) + "]";
	}
	public void setY(int y) {
		this.y = y;
	}
	
    
}
