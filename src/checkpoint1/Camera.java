package checkpoint1;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Camera {
    private float camX = 15f, camY = 15f, camZ = 40f;
    private float pitch = 20f, yaw = 0f;

    public void updateCamera() {
        handleMouseLook(); // process mouse first
        handleInput();     // then move based on new yaw and pitch
    }

    public void applyCamera() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        glTranslatef(-camX, -camY, -camZ);
    }

    public float getCamX() {
        return camX;
    }

    public void setCamPosition(float x, float y, float z) {
        this.camX = x;
        this.camY = y;
        this.camZ = z;
    }

    public void setRotation(float pitch, float yaw) {
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public void initGLProjection() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(70f, 640f / 480f, 0.1f, 1000f);
        glMatrixMode(GL_MODELVIEW);
    }

    public void handleInput() {
        float speed = 0.5f;

        float radYaw = (float) Math.toRadians(yaw);
        float radPitch = (float) Math.toRadians(pitch);

        // Forward vector (including pitch)
        float forwardX = (float) Math.cos(radPitch) * (float) Math.sin(radYaw);
        float forwardY = (float) -Math.sin(radPitch);
        float forwardZ = (float) -Math.cos(radPitch) * (float) Math.cos(radYaw);

        // Right vector (perpendicular to forward, ignores pitch)
        float rightX = (float) Math.sin(radYaw - Math.PI / 2);
        float rightY = 0;
        float rightZ = (float) -Math.cos(radYaw - Math.PI / 2);

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            camX += forwardX * speed;
            camY += forwardY * speed;
            camZ += forwardZ * speed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            camX -= forwardX * speed;
            camY -= forwardY * speed;
            camZ -= forwardZ * speed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            camX += rightX * speed;
            camZ += rightZ * speed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            camX -= rightX * speed;
            camZ -= rightZ * speed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) camY += speed;
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) camY -= speed;
    }

    public void handleMouseLook() {
        float mouseSensitivity = 0.1f;
        float dx = Mouse.getDX();
        float dy = Mouse.getDY();

        yaw += dx * mouseSensitivity;
        pitch -= dy * mouseSensitivity;

        if (pitch > 90) pitch = 90;
        if (pitch < -90) pitch = -90;
    }
}
