package cga.competition.gl;

import cga.competition.Activatable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL40.*;

public class ShaderProgram implements Activatable, Disposable
{
	private static Logger logger = LogManager.getLogger(ShaderProgram.class);
	public int id;
	
	public ShaderProgram(String vertexPath, String tessellationControlPath, String tessellationEvaluationPath, String geometryPath, String fragmentPath) throws URISyntaxException, IOException
	{
		id = glCreateProgram();
		if(vertexPath!=null)
			createAndAttachShader(vertexPath, GL_VERTEX_SHADER);
		if(tessellationControlPath!=null)
			createAndAttachShader(tessellationControlPath, GL_TESS_CONTROL_SHADER);
		if(tessellationEvaluationPath!=null)
			createAndAttachShader(tessellationEvaluationPath, GL_TESS_EVALUATION_SHADER);
		if(geometryPath!=null)
			createAndAttachShader(geometryPath, GL_GEOMETRY_SHADER);
		if(fragmentPath!=null)
			createAndAttachShader(fragmentPath, GL_FRAGMENT_SHADER);
		
		glLinkProgram(id);
		if(glGetProgrami(id, GL_LINK_STATUS) == 0)
		{
			logger.error("Shader program linking error");
			logger.error(glGetProgramInfoLog(id));
			throw new GLException("Shader program linking error:\n"+glGetProgramInfoLog(id));
		}
		glValidateProgram(id);
		if(glGetProgrami(id, GL_VALIDATE_STATUS) == 0)
		{
			logger.error("Shader program validating error");
			logger.error(glGetProgramInfoLog(id));
			throw new GLException("Shader program validating error:\n"+glGetProgramInfoLog(id));
		}
	}
	
	private void createAndAttachShader(String path, int shaderType) throws URISyntaxException, IOException
	{
		int sid = glCreateShader(shaderType);
		glShaderSource(sid, new String(Files.readAllBytes(Paths.get(ShaderProgram.class.getClassLoader().getResource(path).toURI()))));
		glCompileShader(sid);
		if(glGetShaderi(sid, GL_COMPILE_STATUS) == 0)
		{
			logger.error("Shader compilation error");
			logger.error("Path: "+path);
			logger.error(glGetShaderInfoLog(sid));
			throw new GLException("Shader compilation error:\n"+glGetShaderInfoLog(sid));
		}
		glAttachShader(id, sid);
		glDeleteShader(sid);
	}
	
	public void activate()
	{
		glUseProgram(id);
	}
	
	public void deactivate()
	{
		glUseProgram(0);
	}
	
	public void dispose()
	{
		deactivate();
		glDeleteProgram(id);
	}
}
