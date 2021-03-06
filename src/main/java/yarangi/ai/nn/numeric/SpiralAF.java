package yarangi.ai.nn.numeric;

public class SpiralAF implements NumericAF
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8227346298015362544L;
	
	private double w;
	
	public SpiralAF(double w)
	{
		this.w = w;
	}

	@Override
	public double calculate(double sigma)
	{
		return w * sigma*Math.cos( Math.PI * sigma );
	}

	@Override
	public double derivative(double sigma)
	{
		// TODO Auto-generated method stub
		return w * (Math.cos( Math.PI * sigma ) + sigma * Math.sin( Math.PI *  sigma ));
	}

}
