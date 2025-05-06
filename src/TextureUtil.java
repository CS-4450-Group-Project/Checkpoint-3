


import java.io.InputStream;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureUtil {
    public static Texture terrain;

    public static void loadTerrain() {
        try {
            InputStream in = TextureUtil.class.getResourceAsStream("/terrain.png");
            terrain = TextureLoader.getTexture("PNG", in);
            System.out.println("Loaded terrain.png");
        } catch (Exception e) {
            System.err.println("Failed to load terrain.png");
            e.printStackTrace();
        }
    }
}