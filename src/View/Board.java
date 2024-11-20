package View;
import com.jogamp.graph.geom.plane.Path2F.Iterator;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import Controller.GLController;
import Controller.KeyController;
import Model.GameUtils;
import Model.LineClear;
import Model.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Board extends GLJPanel {
    public static final int CELL_SIZE = 30;
    public static final int BOARD_WIDTH = 16;  // Chiều rộng của bàn cờ
    public static final int BOARD_HEIGHT = 20; // Chiều cao của bàn cờ
    private static int Score =0;
    private int[][] grid = new int[BOARD_HEIGHT][BOARD_WIDTH]; // Lưới bàn cờ
    private ArrayList<Tetromino> tetrominoList = new ArrayList<>(); // Danh sách các Tetromino đã rơi xuống
    private static final Random RANDOM = new Random();
    private Tetromino currentTetromino;
    private Timer timer;
    private GLController glController ;
    private KeyController keyController;
    public Board() {
        setPreferredSize(new Dimension(BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE));
        int randomX =  RANDOM.nextInt(BOARD_WIDTH -  GameUtils.getMaxColsInAllShapes());
        currentTetromino = new Tetromino(GameUtils.randomShape(),randomX, 0); 
        tetrominoList.add(currentTetromino);
        initializeGrid();
        // them even gl
        glController = new GLController(this);
        addGLEventListener(glController);
        // them event keylistener
        keyController = new KeyController(this);
        addKeyListener(keyController);
        // Khởi tạo Timer để di chuyển Tetromino
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveTetrominoDown();
                repaint();
            }
        });
        timer.start();
    }

    // Khởi tạo lưới trống
    private void initializeGrid() {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                grid[row][col] = 0;  // 0 đại diện cho ô trống
            }
        }
    }

    // Di chuyển Tetromino xuống
    private void moveTetrominoDown() {
        if (!checkCollision(currentTetromino, 0, 1)) {
            currentTetromino.moveDown();
        } else {
            // Nếu va chạm, thêm Tetromino vào lưới và danh sách
            addTetrominoToGrid(currentTetromino);
            tetrominoList.add(currentTetromino);
            if (currentTetromino.getY() == 0) {
                // Game over logic here
                System.out.println("Game Over!");
                timer.stop(); // Dừng timer
                return;
            }
            // check clear line
          
        	   takePoint();
           // check finish Game
            int randomX =  RANDOM.nextInt(BOARD_WIDTH -  GameUtils.getMaxColsInAllShapes());
            currentTetromino = new Tetromino(GameUtils.randomShape(),randomX, 0); 
        }
    }

    // Kiểm tra va chạm với lưới hoặc đáy
    private boolean checkCollision(Tetromino tetromino, int offsetX, int offsetY) {
        for (int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                if (tetromino.getShape()[row][col] != 0) {
                    int newX = tetromino.getX() + col + offsetX;
                    int newY = tetromino.getY() + row + offsetY;
                    // Kiểm tra nếu Tetromino chạm đáy hoặc chạm Tetromino khác
                    if (newY >= BOARD_HEIGHT || newX < 0 || newX >= BOARD_WIDTH || grid[newY][newX] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Thêm Tetromino vào lưới
    private void addTetrominoToGrid(Tetromino tetromino) {
        for (int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                if (tetromino.getShape()[row][col] != 0) {
                    grid[tetromino.getY() + row][tetromino.getX() + col] = 1;  // Đánh dấu ô bị chiếm
                }
            }
        }
    }

    // Vẽ lưới
    public void drawGrid(GL2 gl) {
        gl.glColor3f(0.5f, 0.5f, 0.5f); // Màu xám cho lưới
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (grid[row][col] != 0) {
                    float x = col * CELL_SIZE;
                    float y = row * CELL_SIZE;
                    gl.glBegin(GL2.GL_QUADS);
                    gl.glVertex2f(x, y);
                    gl.glVertex2f(x + CELL_SIZE, y);
                    gl.glVertex2f(x + CELL_SIZE, y + CELL_SIZE);
                    gl.glVertex2f(x, y + CELL_SIZE);
                    gl.glEnd();
                }
            }
        }
    }

    // Vẽ Tetromino hiện tại
    public void drawTetromino(GL2 gl) {
        drawSingleTetromino(gl, currentTetromino);
    }
    public void drawScore(GL2 gl) {
    	TextRenderer textRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 18));
    	textRenderer.beginRendering(BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE);

        textRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        textRenderer.draw("Score: " + Score, 10, BOARD_HEIGHT * CELL_SIZE - 20);

        textRenderer.endRendering();
    }


    // Vẽ một Tetromino
    public void drawSingleTetromino(GL2 gl, Tetromino tetromino) {
    	 float[] color = tetromino.getColor();
    	  gl.glColor3f(color[0], color[1], color[2]);
        for (int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                if (tetromino.getShape()[row][col] != 0) {
                    float x = (tetromino.getX() + col) * CELL_SIZE;
                    float y = (tetromino.getY() + row) * CELL_SIZE;
                    gl.glBegin(GL2.GL_QUADS);
                    gl.glVertex2f(x, y);
                    gl.glVertex2f(x + CELL_SIZE, y);
                    gl.glVertex2f(x + CELL_SIZE, y + CELL_SIZE);
                    gl.glVertex2f(x, y + CELL_SIZE);
                    gl.glEnd();
                }
            }
        }
    }
    public void moveLeft() {
        if (!checkCollision(currentTetromino, -1, 0)) {
            currentTetromino.moveLeft();
            repaint();
        }
    }

    public void moveRight() {
        if (!checkCollision(currentTetromino, 1, 0)) {
            currentTetromino.moveRight();
            repaint();
        }
    }

    public void moveDown() {
        if (!checkCollision(currentTetromino, 0, 1)) {
            currentTetromino.moveDown();
            repaint();
        }
    }

    public void rotate() {
        currentTetromino.rotate();
        if (checkCollision(currentTetromino, 0, 0)) {
            currentTetromino.undoRotate();
        }
        repaint();
    }
    public void takePoint() {
    	 int linesCleared = 0;

         for (int row = 0; row < grid.length; row++) {
             boolean isFull = true;

             for (int col = 0; col < grid[row].length; col++) {
                 if (grid[row][col] == 0) {
                     isFull = false;
                     break;
                 }
             }

             if (isFull) {
                 linesCleared++;
                 removeLine(row);
                
             }
         }
        
         if (linesCleared > 0) {
        	 printGrid();
        	 System.out.println(tetrominoList);
             Score += getPointsForLines(linesCleared);
             repaint(); 
         }
    	
    }
    public void printGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println(); // Xuống dòng sau mỗi hàng
        }
        System.out.println(); // Thêm khoảng trống giữa các lần in ra
    }
   
    private void removeLine(int row) {
        // Di chuyển tất cả các dòng phía trên xuống 1 hàng
        for (int r = row; r > 0; r--) {
            System.arraycopy(grid[r - 1], 0, grid[r], 0, grid[r].length);
        }
        // Đặt hàng đầu tiên (index 0) thành 0
        for (int col = 0; col < grid[0].length; col++) {
            grid[0][col] = 0; // Đặt dòng đầu tiên thành 0
           
           
        }
     // Xóa các Tetromino tại dòng đã bị xóa
        java.util.Iterator<Tetromino> iterator = tetrominoList.iterator();
        while (iterator.hasNext()) {
            Tetromino tetromino = iterator.next();
            if (tetromino.getY() == row) {
                iterator.remove(); // An iterator is used for safe removal
            }
        }

        // Cập nhật vị trí Y của các Tetromino còn lại nếu cần
        for (Tetromino tetromino : tetrominoList) {
            if (tetromino.getY() < row) {
                tetromino.setY(tetromino.getY() + 1); // Move down if above the removed line
            }
        }
    }


    // Hàm tính điểm dựa trên số dòng đã xóa
    private int getPointsForLines(int linesCleared) {
        for (LineClear lineClear : LineClear.values()) {
            if (lineClear.getLinesCleared() == linesCleared) {
                return lineClear.getPoints();
            }
        }
        return 0; 
    }
    public ArrayList<Tetromino> getTetrominoList() {
		return tetrominoList;
	}

}
