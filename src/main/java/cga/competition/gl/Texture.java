package cga.competition.gl;

import cga.competition.Activatable;
import cga.competition.gl.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture implements Disposable, Activatable
{
    public enum TextureType
    {
        ALBEDO(0), SPECULAR(1), NORMAL(2), OTHER(3);
        
        private int unit;
        
        TextureType(int unit)
        {
            this.unit = unit;
        }
    
        public int getUnit()
        {
            return unit;
        }
    }
    
    private static Logger logger = LogManager.getLogger(Texture.class);
    
    private int id;
    private int unit;
    
    public Texture(String path, TextureType type)
    {
        id = glGenTextures();
        unit = type.unit;
        glActiveTexture(GL_TEXTURE0+unit);
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        try
        {
            TextureLoader.Image img = TextureLoader.load(path);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, img.width, img.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, img.data);
        }
        catch(IOException e)
        {
            logger.error(e);
            throw new GLException("Failed to load texture "+path);
        }
        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    public void activate()
    {
        glActiveTexture(GL_TEXTURE0+unit);
        glBindTexture(GL_TEXTURE_2D, id);
    }
    
    public void deactivate()
    {
        glActiveTexture(GL_TEXTURE0+unit);
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    public void dispose()
    {
        deactivate();
        glDeleteTextures(id);
    }
}
