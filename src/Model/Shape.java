package Model;
public enum Shape {
    I(new int[][]{
        {1, 1, 1, 1} // Hình dạng I
    }),
    O(new int[][]{
        {1, 1},
        {1, 1}  // Hình dạng O
    }),
    T(new int[][]{
        {0, 1, 0},
        {1, 1, 1} // Hình dạng T
    }),
    L(new int[][]{
        {1, 0, 0},
        {1, 1, 1} // Hình dạng L
    }),
    J(new int[][]{
        {0, 0, 1},
        {1, 1, 1} // Hình dạng J
    }),
    S(new int[][]{
        {0, 1, 1},
        {1, 1, 0} // Hình dạng S
    }),
    Z(new int[][]{
        {1, 1, 0},
        {0, 1, 1} // Hình dạng Z
    });

    private final int[][] shape;

    Shape(int[][] shape) {
        this.shape = shape;
    }

    public int[][] getShape() {
        return shape;
    }
    public int getMaxCols() {
        int maxCols = 0;
        for (int[] row : shape) {
            if (row.length > maxCols) {
                maxCols = row.length;
            }
        }
        return maxCols;
    }
   
}
