package View;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

public class Game extends JFrame {

    public Game() {
        initUI();
    }

    private void initUI() {
    	  Board board = new Board();

        add(board);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        FPSAnimator animator = new FPSAnimator(board, 60);  // 60 FPS
        animator.start();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
    	 SwingUtilities.invokeLater(() -> {
             new Game(); // Khởi tạo và hiển thị Frame
         });
    }
}
