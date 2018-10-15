package graph;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.HashMap;

public class SocialGraph {
	
	private HashMap<Integer,NetworkUser> users;
	private int numSocialUsers;
	private int numFriendships;
	private boolean first;
	private boolean changed;
	private int[] ans;
	
	public SocialGraph()
	{
		users = new HashMap<Integer,NetworkUser>();
		numSocialUsers = 0;
		numFriendships = 0;
		first = true;
		changed = true;
		ans = null;
	}
	
	/*This method returns the no. of users(vertices) represented in this graph.*/
	public int getNumSocialUsers()
	{
		return numSocialUsers;
	}
	
	/*This method returns the no. of friendships(edges) represented in this graph.*/
	public int getNumFriendships()
	{
		return numFriendships;
	}
	
	/*This method add users(vertices) to this graph.*/
	public void addUser(int id)
	{
		if(id < 0 || users.containsKey(id))
		{
			throw new IllegalArgumentException("Id less than zero or Graph already contains this id !!!");
		}
		users.put(id, new NetworkUser(id));
		numSocialUsers ++;
		changed = true;
	}
	
	/*This method add friendships(edges) to this graph.*/
	public void addFriendship(int firstUser , int secondUser)
	{
		if( !(users.containsKey(firstUser) && users.containsKey(secondUser) ) )
		{
			throw new IllegalArgumentException("One or both users is not in graph!!!");
		}
		try
		{
			NetworkUser first = users.get(firstUser);
			NetworkUser second = users.get(secondUser);
			first.addSocialFriend(second);
			second.addSocialFriend(first);
			numFriendships ++;
			changed = true;
		}
		catch(IllegalArgumentException e)
		{
			//throw new IllegalArgumentException("Friendship is all ready present!!!");
		}
	}
	
	public void removeUser(int id)
	{
		if(id < 0 || !users.containsKey(id))
		{
			throw new IllegalArgumentException("Id less than zero or Graph do not contain this id !!!");
		}
		NetworkUser user = users.get(id);
		LinkedList<NetworkUser> userFrnds = user.getSocialFriends();
		for(NetworkUser nu : userFrnds)
		{
			nu.deleteSocialFriend(user);
		}
		users.remove(id);
		changed = true;
	}
	
	public void removeFriendship(int firstUser , int secondUser)
	{
		if( !(users.containsKey(firstUser) && users.containsKey(secondUser) ) )
		{
			throw new IllegalArgumentException("One or both users is not in graph!!!");
		}
		try
		{
			NetworkUser first = users.get(firstUser);
			NetworkUser second = users.get(secondUser);
			first.removeSocialFriend(second);
			second.removeSocialFriend(first);
			changed = true;
		}
		catch(IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Friendship do not exit!!!");
		}
	}
	
	private void refresh()
	{
		for(Integer i : users.keySet())
		{
			NetworkUser nu = users.get(i);
			nu.refresh();
		}
	}
	
	public int[] getMinimumDominatingSet()
	{
		if(first == false)
		{
			if(changed == false)
			{
				return Arrays.copyOf(ans, ans.length);
			}
			refresh();
		}
		LinkedList<NetworkUser> socialUsers = new LinkedList<NetworkUser>();
		LinkedList<NetworkUser> leastDominatingSet = new LinkedList<NetworkUser>();
		for(Integer i : users.keySet())
		{
			socialUsers.add(users.get(i));
		}
		while(!socialUsers.isEmpty())
		{
			NetworkUser max = null;
			int broadcastingStrength = 0;
			for(NetworkUser nu : socialUsers)
			{
				if(max != null)
				{
					int temp = nu.getBroadcastingStrength();
					if(temp > broadcastingStrength)
					{
						max = nu;
						broadcastingStrength = temp;
					}
				}
				else
				{
					max = nu;
					broadcastingStrength = nu.getBroadcastingStrength();
				}
			}
			LinkedList<NetworkUser> gotMessage = max.broadcastMessage();
			leastDominatingSet.add(max);
			socialUsers.remove(max);
			for(NetworkUser nu : gotMessage)
			{
				socialUsers.remove(nu);
			}
		}
		int result[] = new int[leastDominatingSet.size()];
		for(int i = 0;i < result.length; i++)
		{
			result[i] = leastDominatingSet.remove().getUserSocialId();
		}
		ans = Arrays.copyOf(result, result.length);
		first = false;
		changed = false;
		return result;
	}
}
