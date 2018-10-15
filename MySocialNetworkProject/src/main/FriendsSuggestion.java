package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import graph.SocialGraph;
import graph.SuggestedFriend;
import util.GraphLoader;

public class FriendsSuggestion {
	
	public static void main(String args[])throws IOException
	{
		BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
		System.out.println("Please , enter a valid user ID , whose friends suggestion you want to receive !!!");
		System.out.println();
		int userID = Integer.parseInt(input.readLine());
		System.out.println();
		System.out.println("Enter the number of friends suggestion you want to receive !!!");
		System.out.println();
		int numFriendsSuggestion = Integer.parseInt(input.readLine());
		System.out.println();
		SocialGraph graph = new SocialGraph();
		GraphLoader.loadGraph(graph,"data/facebook2");
		LinkedList<SuggestedFriend> suggestion = graph.getFriendsSuggestion(userID,numFriendsSuggestion);
		if(suggestion.size() < numFriendsSuggestion)
		{
			System.out.println("Sorry !!! Only "+suggestion.size()+" suggestions are found...");
			System.out.println();
		}
		for(SuggestedFriend f : suggestion)
		{
			System.out.print("User ID "+f.getUserId()+" has "+f.getNumMutualFriends()+" mutual friends with user ID "+userID+" , whose user IDs are [");
			for(int i : f.getMutualFriends())
			{
				System.out.print(" "+i+" ,");
			}
			System.out.println(" ]");
			System.out.println();
		}
	}

}
