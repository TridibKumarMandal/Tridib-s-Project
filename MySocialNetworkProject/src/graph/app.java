package graph;

import java.util.LinkedList;

import util.GraphLoader;

public class app {
	public static void main(String args[])
	{
		SocialGraph graph = new SocialGraph();
		GraphLoader.loadGraph(graph,"data/facebook2");
		
		/*System.out.println(graph.getNumSocialUsers());
		System.out.println(graph.getNumFriendships());*/
		System.out.println("hello");
		LinkedList<SuggestedFriend> list = graph.getFriendsSuggestion(6,2 );
		for(SuggestedFriend f : list)
		{
			System.out.print(f.getUserId()+" ");
			System.out.print(f.getNumMutualFriends()+" [ ");
			for(Integer i : f.getMutualFriends())
			{
				System.out.print(i+" , ");
			}
			System.out.println("]");
			System.out.println();
		}
	}

}
