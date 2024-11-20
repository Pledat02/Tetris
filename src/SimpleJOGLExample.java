import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import javax.swing.JFrame;

public class SimpleJOGLExample implements GLEventListener {

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Simple JOGL Example");
        GLJPanel canvas =  new GLJPanelTemplate();
        canvas.addGLEventListener(new SimpleJOGLExample());
        frame.add(canvas);
        frame.setSize(400, 400);
        frame.setVisible(true);
        final FPSAnimator animator = new FPSAnimator(canvas, 300, true);
        animator.start();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
    	System.out.println(1);
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(-0.5f, -0.5f);
        gl.glVertex2f( 0.5f, -0.5f);
        gl.glVertex2f( 0.0f,  0.5f);
        gl.glEnd();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    @Override
    public void init(GLAutoDrawable drawable) {}

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
}
