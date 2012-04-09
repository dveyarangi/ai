package yarangi.ai.nn.fast;

import java.util.ArrayList;

import yarangi.ai.nn.numeric.NumericAF;

public class FullNetwork
{
	private ArrayList <FullLayer> layers;
	
	private NumericAF af;
	
	public FullNetwork (NumericAF af){
		layers = new ArrayList <FullLayer> ();
		
		this.af = af;
	}
	
	public void addLayer(FullLayer layer)
	{
		layers.add(layer);
	}
	
	public double [] activate(double [] networkInput)
	{
		double [] buffer = networkInput;
		for(FullLayer layer : layers) {
			buffer = layer.activate( buffer, af);
		}
		
		return buffer;
	}

	protected ArrayList <FullLayer> getLayers()
	{
		return layers;
	}

}
