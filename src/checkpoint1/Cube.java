package checkpoint1;

import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;

public class Cube {
static Texture terrain = TextureUtil.terrain;

public static void drawCubeWithFaces(int topTile, int sideTile, int bottomTile) {
    terrain.bind();
    float size = 0.5f;
    float tileSize = 1f / 16f;

    float txTop = (topTile % 16) * tileSize;
    float tyTop = (topTile / 16) * tileSize;

    float txSide = (sideTile % 16) * tileSize;
    float tySide = (sideTile / 16) * tileSize;

    float txBot = (bottomTile % 16) * tileSize;
    float tyBot = (bottomTile / 16) * tileSize;

    glBegin(GL_QUADS);

    // Top
    glTexCoord2f(txTop, tyTop);
    glVertex3f(-size, size, -size);
    glTexCoord2f(txTop + tileSize, tyTop);
    glVertex3f(size, size, -size);
    glTexCoord2f(txTop + tileSize, tyTop + tileSize);
    glVertex3f(size, size, size);
    glTexCoord2f(txTop, tyTop + tileSize);
    glVertex3f(-size, size, size);

    // Bottom
    glTexCoord2f(txBot, tyBot);
    glVertex3f(-size, -size, -size);
    glTexCoord2f(txBot + tileSize, tyBot);
    glVertex3f(-size, -size, size);
    glTexCoord2f(txBot + tileSize, tyBot + tileSize);
    glVertex3f(size, -size, size);
    glTexCoord2f(txBot, tyBot + tileSize);
    glVertex3f(size, -size, -size);

    // Front (side, flipped Y)
    glTexCoord2f(txSide, tySide + tileSize);
    glVertex3f(-size, -size, -size);
    glTexCoord2f(txSide + tileSize, tySide + tileSize);
    glVertex3f(size, -size, -size);
    glTexCoord2f(txSide + tileSize, tySide);
    glVertex3f(size, size, -size);
    glTexCoord2f(txSide, tySide);
    glVertex3f(-size, size, -size);

    // Back (side, flipped Y)
    glTexCoord2f(txSide, tySide + tileSize);
    glVertex3f(-size, -size, size);
    glTexCoord2f(txSide + tileSize, tySide + tileSize);
    glVertex3f(size, -size, size);
    glTexCoord2f(txSide + tileSize, tySide);
    glVertex3f(size, size, size);
    glTexCoord2f(txSide, tySide);
    glVertex3f(-size, size, size);

    // Left (side, flipped Y)
    glTexCoord2f(txSide, tySide + tileSize);
    glVertex3f(-size, -size, size);
    glTexCoord2f(txSide + tileSize, tySide + tileSize);
    glVertex3f(-size, -size, -size);
    glTexCoord2f(txSide + tileSize, tySide);
    glVertex3f(-size, size, -size);
    glTexCoord2f(txSide, tySide);
    glVertex3f(-size, size, size);

    // Right (side, flipped Y)
    glTexCoord2f(txSide, tySide + tileSize);
    glVertex3f(size, -size, -size);
    glTexCoord2f(txSide + tileSize, tySide + tileSize);
    glVertex3f(size, -size, size);
    glTexCoord2f(txSide + tileSize, tySide);
    glVertex3f(size, size, size);
    glTexCoord2f(txSide, tySide);
    glVertex3f(size, size, -size);

    glEnd();
}


    public static void drawGrass(){
        drawCubeWithFaces(146, 3, 2);
    }
    public static void drawSand()    { drawCubeWithFaces(18,18,18); }   // 1 * 16 + 2
    public static void drawWater()   { drawCubeWithFaces(205,205,205); }  // 12 * 16 + 13
    public static void drawDirt()    { drawCubeWithFaces(2,2,2); }   // 0 * 16 + 2
    public static void drawStone()   { drawCubeWithFaces(1,1,1); }   // 0 * 16 + 1
    public static void drawBedrock() { drawCubeWithFaces(17,17,17); }  // 1 * 16 + 1

}
