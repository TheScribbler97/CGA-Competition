package cga.competition.math;

import java.util.Arrays;

public class Mat
{
	private float[][] data;
	
	public Mat(int rows, int columns)
	{
		data = new float[rows][];
		for(int r = 0; r < rows; r++)
			this.data[r] = new float[columns];
		for(int r = 0; r < data.length; r++)
			for(int c = 0; c < data.length; c++)
				this.data[r][c] = r==c?1:0;
	}
	
	public Mat(float[][] data)
	{
		this.data = new float[data.length][];
		for(int r = 0; r < data.length; r++)
			this.data[r] = new float[data[r].length];
		for(int r = 0; r < data.length; r++)
			for(int c = 0; c < data[0].length; c++)
				this.data[r][c] = data[r][c];
	}
	
	public static Mat multiply(Mat mat1, Mat mat2)
	{
		if(mat1.data[0].length!=mat2.data.length)
			throw new MathException("mat1 columncount doesn't match mat2 rowcount");
		
		float[][] ret = new float[mat1.data.length][];
		for(int r = 0; r < mat1.data.length; r++)
			ret[r] = new float[mat2.data[0].length];
		
		float sum;
		for(int r = 0; r < mat1.data.length; r++)
		{
			for(int c = 0; c < mat2.data[0].length; c++)
			{
				sum = 0;
				for(int k = 0; k < mat1.data[0].length; k++)
					sum += mat1.data[r][k]*mat2.data[k][c];
				ret[r][c] = sum;
			}
		}
		
		return new Mat(ret);
	}
	
	public static Vec multiply(Mat mat1, Vec vec)
	{
		if(mat1.data[0].length!=vec.getData().length)
			throw new MathException("mat1 columncount doesn't match vec rowcount");
		Mat mat2 = Vec.toMat(vec);
		return Mat.toVec(Mat.multiply(mat1,mat2));
	}
	
	public static Mat multiply(Vec vec, Mat mat2)
	{
		if(mat2.data[0].length!=1)
			throw new MathException("mat2 rowcount isn't 1");
		Mat mat1 = Vec.toMat(vec);
		return Mat.multiply(mat1,mat2);
	}
	
	public static Vec toVec(Mat mat)
	{
		if(mat.data[0].length!=1)
			throw new MathException("matrix rowcount isn't 1");
		float[] vec = new float[mat.data.length];
		for(int r = 0; r < mat.data.length; r++)
			vec[r] = mat.data[r][0];
		return new Vec(vec);
	}
	
	public static Mat createProjectionMatrix(float fov, float ar, float near, float far)
	{
		float tanhalffov = (float)Math.tan(Math.toRadians(fov/2f));
		return new Mat(new float[][]{{1f/(tanhalffov*ar),0,0,0},
									 {0,1f/tanhalffov,0,0},
									 {0,0,(near+far)/(near-far),(2*far*near)/(near-far)},
									 {0,0,-1,0}});
	}
	
	public static Mat createScalationMatrix(float x, float y, float z)
	{
		return new Mat(new float[][]{{x,0,0,0},
									 {0,y,0,0},
									 {0,0,z,0},
									 {0,0,0,1}});
	}
	
	public static Mat createRotationMatrix(float x, float y, float z)
	{
		Mat rotX = new Mat(new float[][]{{1,0,0,0},
				{0,(float)Math.cos(Math.toRadians(x)),(float)-Math.sin(Math.toRadians(x)),0},
				{0,(float)Math.sin(Math.toRadians(x)),(float)Math.cos(Math.toRadians(x)),0},
				{0,0,0,1}});
		Mat rotY = new Mat(new float[][]{{(float)Math.cos(Math.toRadians(y)),0,(float)-Math.sin(Math.toRadians(y)),0},
				{0,1,0,0},
				{(float)Math.sin(Math.toRadians(y)),0,(float)Math.cos(Math.toRadians(y)),0},
				{0,0,0,1}});
		Mat rotZ = new Mat(new float[][]{{(float)Math.cos(Math.toRadians(z)),(float)-Math.sin(Math.toRadians(z)),0,0},
				{(float)Math.sin(Math.toRadians(z)),(float)Math.cos(Math.toRadians(z)),0,0},
				{0,0,1,0},
				{0,0,0,1}});
		return multiply(multiply(rotX,rotZ),rotY);
	}
	
	public static Mat createTranslationMatrix(float x, float y, float z)
	{
		return new Mat(new float[][]{{1,0,0,x},
									 {0,1,0,y},
									 {0,0,1,z},
									 {0,0,0,1}});
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for(float[] row : data)
		{
			builder.append(Arrays.toString(row));
			builder.append("\n");
		}
		return builder.toString();
	}
	
	public float[][] getData()
	{
		return Arrays.copyOf(data,data.length);
	}
	
	public float[] toArray()
	{
		float[] ret = new float[data.length*data[0].length];
		for(int r = 0; r < data.length; r++)
			for(int c = 0; c < data[0].length; c++)
				ret[r*data[0].length+c] = data[r][c];
		return ret;
	}
}