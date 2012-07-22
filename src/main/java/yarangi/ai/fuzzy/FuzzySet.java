package yarangi.ai.fuzzy;

import java.util.LinkedHashMap;

import yarangi.output.LogUtil;

public class FuzzySet 
{
	private LinkedHashMap <String, Float> set;
	
	private FuzzySet(LinkedHashMap <String, Float> set) {
		this.set = set;
	}
	
	public float getRatio(float key)
	{
		return set.get( key );
	}
	
	public int size() { return set.size(); }
	
	public static float [][] calcCartesianProduct(FuzzySet set1, FuzzySet set2)
	{
		float [][] matrix = new float [set1.size()][set2.size()];
		
		// TODO: efficiate
		
		int i = 0, j;
		for(float ki : set1.set.values()) {
			j = 0;
			for(float kj : set2.set.values()) 
			{
				matrix[i][j] = Math.min( ki,  kj );
				j ++;
			}	
			i ++;
		}
		
		return matrix;
	}

	
	public static class Builder {
		LinkedHashMap <String, Float> set = new LinkedHashMap<String, Float>();
		
		public Builder add(String key, float ratio)
		{
			set.put( key, ratio );
			return this;
		}
		
		public FuzzySet build() 
		{
			return new FuzzySet(set);
		}
	}
	
	public static void main(String ... args)
	{
		FuzzySet set1 = new FuzzySet.Builder().add( "x1", 0.1f ).add("x2", 0.5f).add("x3", 1f).build();
		FuzzySet set2 = new FuzzySet.Builder().add( "y1", 0.4f ).add("y2", 0.8f).build();
		
		System.out.println(LogUtil.toMatrixString( FuzzySet.calcCartesianProduct( set1, set2 ) ));
	}
}
