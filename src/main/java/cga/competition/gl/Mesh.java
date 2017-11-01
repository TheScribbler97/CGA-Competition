package cga.competition.gl;

import cga.competition.Activatable;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh implements Activatable, Disposable
{
	private int vaoId;
	private int vboId;
	private int eboId;
	
	private boolean usesIndices;
	private int renderCount;
	
	public Mesh(float[] verticesData)
	{
		usesIndices = false;
		renderCount = verticesData.length/5;
		vaoId = glGenVertexArrays();
		vboId = glGenBuffers();
		
		glBindVertexArray(vaoId);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, verticesData, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * Float.BYTES, 0);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);
		glEnableVertexAttribArray(1);
		/*glVertexAttribPointer(2, 3, GL_FLOAT, false, 8 * Float.BYTES, 5 * Float.BYTES);
		glEnableVertexAttribArray(2);*/
		glBindVertexArray(0);
	}
	
	public Mesh(float[] verticesData, int[] indices)
	{
		usesIndices = true;
		renderCount = indices.length;
		vaoId = glGenVertexArrays();
		vboId = glGenBuffers();
		eboId = glGenBuffers();
		
		glBindVertexArray(vaoId);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, verticesData, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * Float.BYTES, 0);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);
		glEnableVertexAttribArray(1);
		/*glVertexAttribPointer(2, 3, GL_FLOAT, false, 8 * Float.BYTES, 5 * Float.BYTES);
		glEnableVertexAttribArray(2);*/
		glBindVertexArray(0);
	}
	
	public void activate()
	{
		glBindVertexArray(vaoId);
	}
	
	public void render()
	{
		if(usesIndices)
			glDrawElements(GL_POINTS, renderCount, GL_UNSIGNED_INT, 0);
		else
			glDrawArrays(GL_POINTS, 0, renderCount);
	}
	
	public void deactivate()
	{
		glBindVertexArray(0);
	}
	
	public void dispose()
	{
		deactivate();
		glDeleteBuffers(vaoId);
		glDeleteBuffers(vboId);
		glDeleteBuffers(eboId);
	}
}