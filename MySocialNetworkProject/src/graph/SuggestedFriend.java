package graph;

import java.util.LinkedList;

public class SuggestedFriend implements Comparable<SuggestedFriend> {
	
	private int userId;
	private int numMutualFriends;
	private LinkedList<Integer> mutualFriends;
	
	/*This constructor takes a user ID of a suggested friend , and a list of integers, which
	 * are mutual friends with the given user and this suggested friend.*/
	public SuggestedFriend(int userId,LinkedList<Integer> mutual)
	{
		this.userId = userId;
		this.numMutualFriends = mutual.size();
		this.mutualFriends = new LinkedList<Integer>(mutual);
	}
	
	/*This method returns the user ID of this suggested friend.*/
	public int getUserId()
	{
		return userId;
	}
	
	/*This method returns the no. of mutual friends with the given user and this suggested friend.*/
	public int getNumMutualFriends()
	{
		return numMutualFriends;
	}
	
	/*This method returns a list of integers , which represent user IDs of mutual friends with the 
	 * given user and this suggested fiend.*/
	public LinkedList<Integer> getMutualFriends()
	{
		return new LinkedList<Integer>(mutualFriends);
	}
	
	/*This method compares this SuggestedFriend object with another SuggestedFriend object and returns
	 * an integer which is used for sorting.*/
	public int compareTo(SuggestedFriend m)
	{
		if(m.getNumMutualFriends() > numMutualFriends)
		{
			return 1;
		}
		else if(m.getNumMutualFriends() < numMutualFriends)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}
