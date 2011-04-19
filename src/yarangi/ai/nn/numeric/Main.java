package yarangi.ai.nn.numeric;

import yarangi.ai.nn.GraphModel;
import yarangi.ai.nn.init.InitializerFactory;
import yarangi.ai.nn.init.RandomWeightsInitializer;
import yarangi.graphics.quadraturin.Swing2DContainer;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		InitializerFactory.setWeightsInitializer(new RandomWeightsInitializer(-1, 1));
		
		BackpropNetwork net = new BackpropNetwork(1);
		
		int width = 400;
		int height = 200;
		
		GraphModel model = new GraphModel();
		
		Swing2DContainer frame = new Swing2DContainer();
		int id = frame.addScene(model);
//		frame.setSize(1200, 500);
		frame.setVisible(true);

		frame.start();
		
		frame.activateScene(id);
		
//		net.addLayer(new CompleteNeuronLayer(3, new TanHAF(), 0.01));
		net.addLayer(new CompleteNeuronLayer(5, new TanHAF(), 1));
		net.addLayer(new CompleteNeuronLayer(3, new TanHAF(), 1));
//		net.addLayer(new CompleteNeuronLayer(4, new TanHAF(), 1));
//		net.addLayer(new CompleteNeuronLayer(10, new LogisticAF(), 1));
//		net.addLayer(new CompleteNeuronLayer(4, new LogisticAF(), 1));
//	    net.addLayer(new CompleteNeuronLayer(4, new LogisticAF(), 1));
//	    net.addLayer(new CompleteNeuronLayer(10, new LogisticAF(), 0.01));
//		net.addLayer(new CompleteNeuronLayer(4, new TransparentAF(), 0));
//		net.addLayer(new CompleteNeuronLayer(4, new TransparentAF(), 0));
//		net.addLayer(new CompleteNeuronLayer(4, new TransparentAF(), 0));
		
		NumericAF af = new LogisticAF();
		
		double minx = -2*Math.PI;
		double maxx = 2*Math.PI;
		double noiseDB = 0.3;
		
		int trainingSetSize = 500;
		double [][] tys = new double [trainingSetSize][1];
		double [][] txs = new double [trainingSetSize][1];
		double outputs [][] = new double [trainingSetSize][1];
		double inputs [][] = new double [trainingSetSize][1];
		double miny = Double.POSITIVE_INFINITY, maxy = Double.NEGATIVE_INFINITY;
		for(int idx = 0; idx < trainingSetSize; idx ++)
		{
			double x = Math.random() * (maxx-minx) + minx;
//			double y = Math.random() * (maxx-minx) + minx;
			txs[idx] = new double [] {x};
			tys[idx] = new double []{x*x*Math.sin(x)/10 + Math.random() * noiseDB - noiseDB/2};
			
			if(tys[idx][0] > maxy)
				maxy = tys[idx][0];
			if(tys[idx][0] < miny)
				miny = tys[idx][0];
		}
		
		Normalizer normalizer = new ScalingNormalizer(new double [] {minx}, new double [] {maxx},
												   new double [] {miny},new double [] {maxy});
		
		NeuralNetworkRunner runner = new NeuralNetworkRunner(normalizer, net);
		
		// normalizing
		for(int idx = 0; idx < trainingSetSize; idx ++)
		{
			inputs[idx] = normalizer.normalizeInput(txs[idx]);
			outputs[idx] = normalizer.normalizeOutput(tys[idx]);;
		}
		
		model.setTrainingSet(txs, tys, minx, miny, maxx, maxy);

		
		ArrayInput input = new ArrayInput(1);
		for(int iIdx = 0; iIdx < inputs[0].length; iIdx ++)
			input.add(iIdx,  new NumericNeuronInput());
		
		input.add(inputs[0].length, new NumericNeuronInput(1));
		net.addInput(input);
		
///		input.add(1,  new NumericNeuronInput());
//		input.add(2,  new NumericNeuronInput());
//		input.add(3,  new NumericNeuronInput());
		
		ArrayInput output = (ArrayInput)net.getOutput();
		for(int epoch = 0; epoch < 1000000; epoch ++)
		{
			double learningRate = 0.1;
			int iterNum = 1;
			for(int idx = 0; idx < trainingSetSize; idx ++)
			{
				for(int i = 0; i < inputs[idx].length; i ++)
					input.setValue(i, inputs[idx][i]);
				
				net.activate();
				for(int i = 0; i < outputs[idx].length; i ++)
				{
					double error = outputs[idx][i]-output.getValue(i);
	//				System.out.println("E" + epoch + "P"+ iterNum++ + " NET: " + output.getValue(i) + " REAL: " + outputs[idx][i] + " ERR: " + error);
				}
				
				net.propagateError(outputs[idx], learningRate);
			}
			
			double errorSum = 0;
//			
//			
//			double errorSum += error * error;

			learningRate *= 1.001;
			double [] nx = new double[(int)((maxx-minx)/0.05)+1];
			double [] ny = new double[nx.length];
			      
			int idx = 0;
			for(double x = minx; x < maxx; x += 0.05)
			{
				nx[idx] = x;
				ny[idx] = runner.run(new double [] {x })[0];
				idx ++;
			}

			model.updateNetworkGraph(nx, ny);
			
		}
	}

}
