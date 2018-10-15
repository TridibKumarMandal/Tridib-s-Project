package graph;

import util.GraphLoader;

public class test {
	public static void main(String args[])
	{
		SocialGraph graph = new SocialGraph();
		GraphLoader.loadGraph(graph,"data/facebook3");
		int[] seed = {25};
		System.out.println("Started....");
		Prediction i = graph.getPrediction("Eclipse", "NetBeans", seed, 1, 1);
		int[] j = i.usersIdUsingNewProductAtEquilibrium();
		for(int k : j)
		{
			System.out.println(k);
		}
	}

}
