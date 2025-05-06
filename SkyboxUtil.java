import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import java.io.InputStream;

public class SkyboxUtil {

    private static Texture front, back, left, right, top, bottom;

    public static void loadSkybox() {
        try {
            front = loadTexture("/skybox_front.png");
            back = loadTexture("/skybox_back.png");
            left = loadTexture("/skybox_left.png");
            right = loadTexture("/skybox_right.png");
            top = loadTexture("/skybox_top.png");
            bottom = loadTexture("/skybox_bottom.png");

            System.out.println("✅ Loaded all 6 skybox faces successfully");
        } catch (Exception e) {
            System.err.println("❌ Failed to load skybox textures");
            e.printStackTrace();
        }
    }

    private static Texture loadTexture(String path) throws Exception {
        InputStream in = SkyboxUtil.class.getResourceAsStream(path);
        if (in == null) {
            throw new Exception("Texture not found: " + path);
        }
        Texture tex = TextureLoader.getTexture("PNG", in);

        // Clamp & nearest filter to avoid seams
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, 0x812F);  // GL_CLAMP_TO_EDGE
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, 0x812F);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        return tex;
    }

    public static void drawSkybox(float size) {
        float offset = -0.1f;     // slight overlap of cube faces
        float texExpand = 999.0f;   

        glPushAttrib(GL_ENABLE_BIT);
        glPushMatrix();

        glDisable(GL_LIGHTING);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_CULL_FACE);

        // FRONT
        front.bind();
        glBegin(GL_QUADS);
        glTexCoord2f(-texExpand, -texExpand); glVertex3f(-size - offset, -size - offset, -size - offset);
        glTexCoord2f(1 + texExpand, -texExpand); glVertex3f(size + offset, -size - offset, -size - offset);
        glTexCoord2f(1 + texExpand, 1 + texExpand); glVertex3f(size + offset, size + offset, -size - offset);
        glTexCoord2f(-texExpand, 1 + texExpand); glVertex3f(-size - offset, size + offset, -size - offset);
        glEnd();

        // BACK
        back.bind();
        glBegin(GL_QUADS);
        glTexCoord2f(-texExpand, -texExpand); glVertex3f(size + offset, -size - offset, size + offset);
        glTexCoord2f(1 + texExpand, -texExpand); glVertex3f(-size - offset, -size - offset, size + offset);
        glTexCoord2f(1 + texExpand, 1 + texExpand); glVertex3f(-size - offset, size + offset, size + offset);
        glTexCoord2f(-texExpand, 1 + texExpand); glVertex3f(size + offset, size + offset, size + offset);
        glEnd();

        // LEFT
        left.bind();
        glBegin(GL_QUADS);
        glTexCoord2f(-texExpand, -texExpand); glVertex3f(-size - offset, -size - offset, size + offset);
        glTexCoord2f(1 + texExpand, -texExpand); glVertex3f(-size - offset, -size - offset, -size - offset);
        glTexCoord2f(1 + texExpand, 1 + texExpand); glVertex3f(-size - offset, size + offset, -size - offset);
        glTexCoord2f(-texExpand, 1 + texExpand); glVertex3f(-size - offset, size + offset, size + offset);
        glEnd();

        // RIGHT
        right.bind();
        glBegin(GL_QUADS);
        glTexCoord2f(-texExpand, -texExpand); glVertex3f(size + offset, -size - offset, -size - offset);
        glTexCoord2f(1 + texExpand, -texExpand); glVertex3f(size + offset, -size - offset, size + offset);
        glTexCoord2f(1 + texExpand, 1 + texExpand); glVertex3f(size + offset, size + offset, size + offset);
        glTexCoord2f(-texExpand, 1 + texExpand); glVertex3f(size + offset, size + offset, -size - offset);
        glEnd();

        // TOP
        top.bind();
        glBegin(GL_QUADS);
        glTexCoord2f(-texExpand, -texExpand); glVertex3f(-size - offset, size + offset, -size - offset);
        glTexCoord2f(1 + texExpand, -texExpand); glVertex3f(size + offset, size + offset, -size - offset);
        glTexCoord2f(1 + texExpand, 1 + texExpand); glVertex3f(size + offset, size + offset, size + offset);
        glTexCoord2f(-texExpand, 1 + texExpand); glVertex3f(-size - offset, size + offset, size + offset);
        glEnd();

        // BOTTOM
        bottom.bind();
        glBegin(GL_QUADS);
        glTexCoord2f(-texExpand, -texExpand); glVertex3f(-size - offset, -size - offset, size + offset);
        glTexCoord2f(1 + texExpand, -texExpand); glVertex3f(size + offset, -size - offset, size + offset);
        glTexCoord2f(1 + texExpand, 1 + texExpand); glVertex3f(size + offset, -size - offset, -size - offset);
        glTexCoord2f(-texExpand, 1 + texExpand); glVertex3f(-size - offset, -size - offset, -size - offset);
        glEnd();

        glPopMatrix();
        glPopAttrib();
    }
}
