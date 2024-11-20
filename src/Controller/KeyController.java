package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import View.Board;

public class KeyController implements KeyListener {

    private Board board; // Tham chiếu đến đối tượng Board để xử lý hành động

    public KeyController(Board board) {
        this.board = board;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Không cần xử lý trong trường hợp này
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_LEFT:
                board.moveLeft();  // Di chuyển Tetromino sang trái
                break;

            case KeyEvent.VK_RIGHT:
                board.moveRight(); // Di chuyển Tetromino sang phải
                break;

            case KeyEvent.VK_DOWN:
                board.moveDown();  // Di chuyển Tetromino xuống nhanh
                break;

            case KeyEvent.VK_UP:
                board.rotate();    // Xoay Tetromino
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Không cần xử lý trong trường hợp này
    }
}
