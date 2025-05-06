import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import java.nio.FloatBuffer;
import java.util.Random;

public class Main {

    Camera cam = new Camera();

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Checkpoint 1");
            Display.create();
            TextureUtil.loadTerrain();
            SkyboxUtil.loadSkybox();  // Loads skybox
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
        glEnable(GL_LIGHT1);
        glEnable(GL_COLOR_MATERIAL);
        glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
        glClearColor(0.68f, 0.85f, 0.9f, 1.0f);
        Mouse.setGrabbed(true);
    }

    Random rand = new Random();
    SimplexNoise noise = new SimplexNoise(64, 0.4, rand.nextInt());

    private void renderLoop() {
        float[] lightPosition = {15.0f, 20.0f, 15.0f, 1.0f};
        float[] lightPosition2 = {-15.0f, 20.0f, -15.0f, 1.0f};
        float[] lightBright = {1f, 1f, 1f, 1.0f};
        float[] lightDim = {0.3f, 0.3f, 0.3f, 1.0f};

        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            SkyboxUtil.drawSkybox(100);  // 3D skybox

            cam.updateCamera();
            glLoadIdentity();
            cam.applyCamera();

            glLight(GL_LIGHT0, GL_DIFFUSE, asFloatBuffer(lightBright));
            glLight(GL_LIGHT0, GL_POSITION, asFloatBuffer(lightPosition));
            glLight(GL_LIGHT1, GL_DIFFUSE, asFloatBuffer(lightDim));
            glLight(GL_LIGHT1, GL_POSITION, asFloatBuffer(lightPosition2));

            int gridSize = 30;
            float spacing = 1.0f;
            int maxHeight = 9;

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
                            if (topType < -0.3) {
                                Cube.drawWater();
                            } else if (topType < 0) {
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
        new Main().start();
    }
}