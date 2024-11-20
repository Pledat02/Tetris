package Model;

public enum LineClear {
    SINGLE(1, 100),
    DOUBLE(2, 300),
    TRIPLE(3, 500),
    TETRIS(4, 800);

    private final int linesCleared;
    private final int points;

    LineClear(int linesCleared, int points) {
        this.linesCleared = linesCleared;
        this.points = points;
    }

    public int getLinesCleared() {
        return linesCleared;
    }

    public int getPoints() {
        return points;
    }
}
