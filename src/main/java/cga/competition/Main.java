package cga.competition;

import cga.competition.gl.Mesh;
import cga.competition.gl.ShaderProgram;
import cga.competition.gl.Texture;
import cga.competition.glfw.Window;
import cga.competition.math.Mat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Main
{
	private static final Logger logger = LogManager.getLogger(Launcher.class);
	private static ShaderProgram program;
	
	public static void start()
	{
		logger.debug("Creating window");
		Window window = Window.getInstance(1600, 900, "CGA", true);
		window.setOnResize((w,h)->glViewport(0,0, w, h));
		
		logger.debug("Setting up OpenGL");
		glClearColor(0,0,0,1);
		glEnable(GL_DEPTH_TEST);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glPointSize(3);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glFrontFace(GL_CCW);
		
		logger.debug("Creating shader program");
		try
		{
			program = new ShaderProgram("vertex.glsl",null, null, "geometry.glsl","fragment.glsl");
		}
		catch(URISyntaxException|IOException e)
		{
			logger.error("Couldn't create shader program");
			logger.error(e);
		}
		
		logger.debug("Creating mesh");
		Mesh mesh = new Mesh(new float[]
		{
			0,0,0, 0,0.25f, 0.25f,0, 0.5f,0.25f, 0.5f,0, 0.25f,0.25f, 0,0,
			1,0,0, 0,0.25f, 0.25f,0, 0.5f,0.25f, 0.5f,0, 0.25f,0.25f, 0,0,
			2,0,0, 0,0.25f, 0.25f,0, 0.5f,0.25f, 0.5f,0, 0.25f,0.25f, 0,0,
			3,0,0, 0,0.25f, 0.25f,0, 0.5f,0.25f, 0.5f,0, 0.25f,0.25f, 0,0
		});
		Texture texture = new Texture("texture.png", Texture.TextureType.ALBEDO);
		
		window.setOnResize((w,h)->
		{
			glUniformMatrix4fv(glGetUniformLocation(program.id,"projection"), true, Mat.createProjectionMatrix(80f,(float)w/(float)h,0.001f, 1000f).toArray());
			glViewport(0,0, w, h);
		});
		
		float camX = 0;
		float camY = 0;
		float camZ = -5;
		
		program.activate();
		glUniformMatrix4fv(glGetUniformLocation(program.id,"view"), true, Mat.multiply(Mat.createTranslationMatrix(camX,camY,camZ),Mat.createRotationMatrix(0,180,0)).toArray());
		glUniformMatrix4fv(glGetUniformLocation(program.id,"projection"), true, Mat.createProjectionMatrix(80f,16f/9f,0.001f, 1000f).toArray());
		glUniform1f(glGetUniformLocation(program.id,"blockSize"), 0.2f);
		glUniform3fv(glGetUniformLocation(program.id,"camPos"), new float[]{-camX, -camY, -camZ});
		glUniform1f(glGetUniformLocation(program.id,"albedoSampler"), Texture.TextureType.ALBEDO.getUnit());
		glUniform1f(glGetUniformLocation(program.id,"textureBlockSize"), 0.25f);
		program.deactivate();
		
		logger.debug("Looping...");
		while(window.isOpen())
		{
			window.update();
			program.activate();
			texture.activate();
			mesh.activate();
			mesh.render();
			mesh.deactivate();
			texture.deactivate();
			program.deactivate();
			
			if(window.isPressed(GLFW.GLFW_KEY_W))
				camZ+=0.1f;
			if(window.isPressed(GLFW.GLFW_KEY_S))
				camZ-=0.1f;
			if(window.isPressed(GLFW.GLFW_KEY_LEFT_SHIFT))
				camY+=0.1f;
			if(window.isPressed(GLFW.GLFW_KEY_SPACE))
				camY-=0.1f;
			if(window.isPressed(GLFW.GLFW_KEY_A))
				camX+=0.1f;
			if(window.isPressed(GLFW.GLFW_KEY_D))
				camX-=0.1f;
			
			program.activate();
			glUniformMatrix4fv(glGetUniformLocation(program.id,"model"), true, new Mat(4,4).toArray());
			glUniformMatrix4fv(glGetUniformLocation(program.id,"view"), true, Mat.multiply(Mat.createTranslationMatrix(camX, camY, camZ),Mat.createRotationMatrix(0,180,0)).toArray());
			glUniform3fv(glGetUniformLocation(program.id,"camPos"), new float[]{-camX, -camY, -camZ});
			program.deactivate();
		}
		
		program.dispose();
		mesh.dispose();
	}
}
