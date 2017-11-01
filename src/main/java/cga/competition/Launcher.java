package cga.competition;

import cga.competition.glfw.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher
{
	private static final Logger logger = LogManager.getLogger(Launcher.class);
	
	public static void main(String[] args)
	{
		logger.debug("Starting...");
		Main.start();
		Window.dispose();
		logger.debug("Stopping...");
	}
}