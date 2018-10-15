package main;

import graph.SocialGraph;
import util.GraphLoader;

public class TestApp {
	
	public static void main(String args[])
	{
		SocialGraph graph = new SocialGraph();
		GraphLoader.loadGraph(graph,"data/facebook_ucsd");
		int a[] = graph.getMinimumDominatingSet();
		for(int i=0;i<a.length;i++)
		{
			System.out.println(a[i]);
		}
	}
}
