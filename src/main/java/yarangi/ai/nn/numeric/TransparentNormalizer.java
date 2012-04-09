package yarangi.ai.nn.numeric;

public class TransparentNormalizer implements Normalizer 
{

	public double[] denormalizeOutput(double[] value) { return value; }

	public double[] normalizeInput(double[] value) { return value; }

	public double[] normalizeOutput(double[] value) { return value; }

	@Override
	public int inputSize() {
		return 0;
	}

}
