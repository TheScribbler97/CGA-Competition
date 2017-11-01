package cga.competition.glfw;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window
{
	private static Logger logger = LogManager.getLogger(Window.class);
	static
	{
		if(!glfwInit())
		{
			logger.error("Couldn't init GLFW");
			throw new GLFWException("Couldn't init GLFW");
		}
	}
	private static Window instance = new Window(1600, 900, "Window");
	
	private long id;
	private ResizeListener onResize;
	
	private Window(int width, int height, String title)
	{
		glfwWindowHint(GLFW_SAMPLES, 8);
		id = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		if(id == MemoryUtil.NULL)
		{
			logger.error("Couldn't create window");
			throw new GLFWException("Couldn't create window");
		}
		glfwSetWindowSizeCallback(id, (l,w,h)->onResize.onResize(w,h));
		glfwSwapInterval(1);
		glfwMakeContextCurrent(id);
		GL.createCapabilities();
	}
	
	public static Window getInstance(int width, int height, String title, boolean vsync)
	{
		glfwSwapInterval(vsync?1:0);
		glfwSetWindowSize(instance.id, width, height);
		glfwSetWindowTitle(instance.id, title);
		return instance;
	}
	
	public static void dispose()
	{
		glfwDestroyWindow(instance.id);
		Callbacks.glfwFreeCallbacks(instance.id);
		glfwTerminate();
	}
	
	public boolean isOpen()
	{
		return !glfwWindowShouldClose(id);
	}
	
	public void update()
	{
		glfwSwapBuffers(id);
		glfwPollEvents();
		glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
	}
	
	public void setOnResize(ResizeListener onResize)
	{
		this.onResize = onResize;
	}
	
	public boolean isPressed(int key)
	{
		return glfwGetKey(id,key) == GLFW_PRESS || glfwGetKey(id, key) == GLFW_REPEAT;
	}
}