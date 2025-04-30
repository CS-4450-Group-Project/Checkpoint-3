package checkpoint1;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.Random;

public class CheckPoint1 {

    Camera cam = new Camera();

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Checkpoint 1");
            Display.create();
            initGL();
            renderLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGL() {
        cam.initGLProjection();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glEnable(GL_COLOR_MATERIAL);
        glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
        glClearColor(0f, 0f, 0f, 1f);
        Mouse.setGrabbed(true);
    }

    Random rand = new Random();
    SimplexNoise noise = new SimplexNoise(64, 0.4, rand.nextInt());

    private void renderLoop() {
        float[] lightPosition = {15.0f, 20.0f, 15.0f, 1.0f};
        float[] lightBright = {1f, 1f, 1f, 1.0f};
        float[] lightDim = {0.3f, 0.3f, 0.3f, 1.0f};

        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            cam.updateCamera();
            glLoadIdentity();
            cam.applyCamera();

            if (cam.getCamX() > 15) {
                glLight(GL_LIGHT0, GL_DIFFUSE, asFloatBuffer(lightBright));
            } else {
                glLight(GL_LIGHT0, GL_DIFFUSE, asFloatBuffer(lightDim));
            }
            glLight(GL_LIGHT0, GL_POSITION, asFloatBuffer(lightPosition));

            int gridSize = 30;
            float spacing = 1.0f;
            int maxHeight = 10;

            for (int x = 0; x < gridSize; x++) {
                for (int z = 0; z < gridSize; z++) {
                    double noiseVal = noise.getNoise(x, z);
                    int height = (int) ((noiseVal + 1) / 2 * maxHeight);

                    for (int y = 0; y <= height; y++) {
                        glPushMatrix();
                        glTranslatef(x * spacing, y * spacing, z * spacing);

                        if (y == 0) {
                            Cube.drawBedrock();
                        } else if (y == height) {
                            double topType = noise.getNoise(x * 2, z * 2);
                            if (topType < -0.33) {
                                Cube.drawWater();
                            } else if (topType < 0.33) {
                                Cube.drawSand();
                            } else {
                                Cube.drawGrass();
                            }
                        } else {
                            double middleType = noise.getNoise(x, y, z);
                            if (middleType > 0) {
                                Cube.drawDirt();
                            } else {
                                Cube.drawStone();
                            }
                        }
                        glPopMatrix();
                    }
                }
            }

            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    private FloatBuffer asFloatBuffer(float[] values) {
        FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }

    public static void main(String[] args) {
        new CheckPoint1().start();
    }
}

class TextureUtil {
    public static Texture loadTexture(String fileName) {
        try {
            String path = "/checkpoint1/textures/" + fileName;
            InputStream in = TextureUtil.class.getResourceAsStream(path);

            if (in == null) {
                System.err.println("❓ Could not find texture: " + path);
                return null;
            }

            Texture texture = TextureLoader.getTexture("PNG", in);
            System.out.println("✅ Loaded texture: " + fileName);
            return texture;
        } catch (Exception e) {
            System.err.println("❌ Failed to load texture: " + fileName);
            e.printStackTrace();
            return null;
        }
    }
}
