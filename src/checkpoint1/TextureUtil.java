package checkpoint1;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.InputStream;

public class TextureUtil {

    /**
     * Loads a texture from /checkpoint1/textures/
     * Place texture PNGs inside: src/checkpoint1/textures/
     */
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
