package Controller;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import Model.Tetromino;
import View.Board;

public class GLController implements GLEventListener {

	private Board board ;
	
	public GLController(Board board) {
		this.board = board;
	}


	@Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); // Điều này là cần thiết
        // Vẽ lưới
        board.drawGrid(gl);

        // Vẽ Tetromino hiện tại
        board.drawTetromino(gl);
        // vẽ điểm
        board.drawScore(gl);
        
        // Vẽ các Tetromino đã rơi
        for (Tetromino tetromino : board.getTetrominoList()) {
        	board.drawSingleTetromino(gl, tetromino);
        }

        gl.glFlush();
        drawable.swapBuffers();
    }

   
    // Các phương thức GLEventListener
    @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        
        // Sử dụng phép chiếu trực giao để phù hợp với không gian 2D của trò chơi
        GLU glu = new GLU();
        glu.gluOrtho2D(0.0, Board.BOARD_WIDTH * Board.CELL_SIZE, Board.BOARD_HEIGHT * Board.CELL_SIZE, 0.0); // Y lộn ngược cho dễ xử lý
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}
