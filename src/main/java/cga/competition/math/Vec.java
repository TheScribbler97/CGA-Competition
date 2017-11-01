package cga.competition.math;

import java.util.Arrays;

public class Vec
{
	private float[] data;
	
	public Vec(float... data)
	{
		this.data = data;
	}
	
	public static Mat toMat(Vec vec)
	{
		float[][] data = new float[vec.data.length][1];
		for(int r = 0; r < vec.data.length; r++)
			data[r] = new float[]{vec.data[r]};
		return new Mat(data);
	}
	
	public float[] getData()
	{
		return data;
	}
	
	@Override
	public String toString()
	{
		return Arrays.toString(data);
	}
}
