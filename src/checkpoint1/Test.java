package checkpoint1;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Keyboard;

public class Test {
    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Texture Loading Example");
            Display.create();
            initGL();
            renderLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-5, 5, -5, 5, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glClearColor(0f, 0f, 0f, 1f);
    }

    private void renderLoop() {
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();
            Cube.drawGrass();
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
