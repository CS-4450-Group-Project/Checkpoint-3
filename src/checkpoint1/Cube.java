package checkpoint1;

import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;

public class Cube {
    static Texture grass = TextureUtil.loadTexture("grass.png");
    static Texture sand = TextureUtil.loadTexture("sand.png");
    static Texture water = TextureUtil.loadTexture("water.png");
    static Texture dirt = TextureUtil.loadTexture("dirt.png");
    static Texture stone = TextureUtil.loadTexture("stone.png");
    static Texture bedrock = TextureUtil.loadTexture("bedrock.png");

    public static void drawCube(Texture texture) {
        if (texture != null) texture.bind();
        float size = 1f;

        glBegin(GL_QUADS);

        // Front
        glTexCoord2f(0, 0); glVertex3f(-size, -size, -size);
        glTexCoord2f(1, 0); glVertex3f(size, -size, -size);
        glTexCoord2f(1, 1); glVertex3f(size, size, -size);
        glTexCoord2f(0, 1); glVertex3f(-size, size, -size);

        // Back
        glTexCoord2f(0, 0); glVertex3f(-size, -size, size);
        glTexCoord2f(1, 0); glVertex3f(size, -size, size);
        glTexCoord2f(1, 1); glVertex3f(size, size, size);
        glTexCoord2f(0, 1); glVertex3f(-size, size, size);

        // Left
        glTexCoord2f(0, 0); glVertex3f(-size, -size, size);
        glTexCoord2f(1, 0); glVertex3f(-size, -size, -size);
        glTexCoord2f(1, 1); glVertex3f(-size, size, -size);
        glTexCoord2f(0, 1); glVertex3f(-size, size, size);

        // Right
        glTexCoord2f(0, 0); glVertex3f(size, -size, -size);
        glTexCoord2f(1, 0); glVertex3f(size, -size, size);
        glTexCoord2f(1, 1); glVertex3f(size, size, size);
        glTexCoord2f(0, 1); glVertex3f(size, size, -size);

        // Top
        glTexCoord2f(0, 0); glVertex3f(-size, size, -size);
        glTexCoord2f(1, 0); glVertex3f(size, size, -size);
        glTexCoord2f(1, 1); glVertex3f(size, size, size);
        glTexCoord2f(0, 1); glVertex3f(-size, size, size);

        // Bottom
        glTexCoord2f(0, 0); glVertex3f(-size, -size, -size);
        glTexCoord2f(1, 0); glVertex3f(-size, -size, size);
        glTexCoord2f(1, 1); glVertex3f(size, -size, size);
        glTexCoord2f(0, 1); glVertex3f(size, -size, -size);

        glEnd();
    }

    public static void drawGrass()   { drawCube(grass); }
    public static void drawSand()    { drawCube(sand); }
    public static void drawWater()   { drawCube(water); }
    public static void drawDirt()    { drawCube(dirt); }
    public static void drawStone()   { drawCube(stone); }
    public static void drawBedrock() { drawCube(bedrock); }
}
