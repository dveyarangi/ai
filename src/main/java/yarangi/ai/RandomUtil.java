package yarangi.ai;

import java.util.Random;

public class RandomUtil 
{

	private static Random random = new Random();
	
	public static int getRandomInt(int n)
	{ 
		return random.nextInt(n) ;
	}

	public static double getRandomDouble(double d) {
		return d*random.nextDouble();
	}
	
	public static float N(float mean, float sigma)
	{
		return mean + sigma * (float)random.nextGaussian();
	} 
	public static double N(double mean, double sigma)
	{
		return mean + sigma * random.nextGaussian();
	} 
	
	public static boolean oneOf(int num)
	{
		return getRandomInt(num) == 0;
	}
	
	public static boolean P(float p) {
		assert p >= 0 && p <= 1;
		return random.nextFloat() <= p;
	}
}
