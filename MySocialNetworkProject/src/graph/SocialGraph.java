package graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class SocialGraph {
	
	private HashMap<Integer,NetworkUser> users;
	private int numSocialUsers;
	private int numFriendships;
	
	public SocialGraph()
	{
		users = new HashMap<Integer,NetworkUser>();
		numSocialUsers = 0;
		numFriendships = 0;
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
		}
		catch(IllegalArgumentException e)
		{
		}
	}
	
	/*This method takes an user ID and no. of friends suggestion as parameters.
	 * It returns a list of SuggestedFriend object sorted in decreasing order of no . of
	 * mutual friends.*/
	public LinkedList<SuggestedFriend> getFriendsSuggestion(int userId,int numOfFriendsSuggestion)
	{
		if(!users.containsKey(userId) || numOfFriendsSuggestion < 0)
		{
			throw new IllegalArgumentException("User ID is not present in graph or negative parameter passed !!!");
		}
		HashSet<NetworkUser> suggestion = getSuggestion(userId);
		LinkedList<SuggestedFriend> friendsSuggestion = getSuggestionWithWeight(userId,suggestion);
		Collections.sort(friendsSuggestion);
		LinkedList<SuggestedFriend> result = new LinkedList<SuggestedFriend>();
		for(int i=1;i<=numOfFriendsSuggestion;i++)
		{
			try
			{
				SuggestedFriend friend = friendsSuggestion.remove();
				result.add(friend);
			}
			catch(NoSuchElementException e)
			{
				break;
			}
		}
		return result;
	}
	
	/*This method takes a user Id as parameter.
	 * It returns a HashSet of NetworkUser as friends suggestion to the given user Id.*/
	private HashSet<NetworkUser> getSuggestion(int userId)
	{
		NetworkUser user = users.get(userId);
		LinkedList<NetworkUser> userFrnds = user.getSocialFriends();
		HashSet<NetworkUser> suggestion = new HashSet<NetworkUser>();
		for(NetworkUser userFrnd : userFrnds)
		{
			LinkedList<NetworkUser> frndList = userFrnd.getSocialFriends();
			for(NetworkUser suggest : frndList)
			{
				if(!(userFrnds.contains(suggest) || suggest == user))
				{
					suggestion.add(suggest);
				}
			}
		}
		return suggestion;
	}
	
	/*This method takes user Id and HashSet of NetworkUser as parameters.
	 * It returns a list of SuggestedFriend objects.*/
	private LinkedList<SuggestedFriend> getSuggestionWithWeight(int userId,HashSet<NetworkUser> suggestion)
	{
		NetworkUser user = users.get(userId);
		LinkedList<NetworkUser> userFrnds = user.getSocialFriends();
		Iterator<NetworkUser> iterate = suggestion.iterator();
		LinkedList<SuggestedFriend> friendsSuggestion = new LinkedList<SuggestedFriend>();
		while(iterate.hasNext())
		{
			NetworkUser n = iterate.next();
			LinkedList<Integer> mutualFriends = new LinkedList<Integer>();
			for(NetworkUser userFrnd : userFrnds)
			{
				if(n.isFriend(userFrnd))
				{
					mutualFriends.add(userFrnd.getUserSocialId());
				}
			}
			SuggestedFriend f = new SuggestedFriend(n.getUserSocialId(),mutualFriends);
			friendsSuggestion.add(f);
		}
		return friendsSuggestion;
	}
	
	
	
	/*This method takes the existing product,new product,seed,existing product reward and new product reward as parameters.
	 * It returns a Prediction object which stores valuable information about the viability of the new product at the market.*/
	public Prediction getPrediction(String existingProduct,String newProduct,int[] seed,double existingProReward,double newProReward)
	{
		if(existingProduct == null || newProduct == null || seed == null)
		{
			throw new NullPointerException("Some parameters are null!!!");
		}
		if(existingProReward <= 0 || newProReward <= 0 )
		{
			throw new IllegalArgumentException("Reward can't be Negative or Zero !!!");
		}
		for(int user : seed)
		{
			if(!users.containsKey(user))
			{
				throw new IllegalArgumentException("Seed user not present in graph !!!");
			}
		}
		setExistingProduct(existingProduct,existingProReward);
		setSeedProduct(newProduct,newProReward,seed);
		int[] changedUser = getUsersWhoChanged(newProduct,seed,newProReward);
		Prediction predict = new Prediction(seed,changedUser,users.size());
		return predict;
	}
	
	/*This method takes the existing product and its reward as parameters.
	 * It sets all users with the existing product and its reward.*/
	private void setExistingProduct(String existingProduct,double existingProReward)
	{
		for(Integer i : users.keySet())
		{
			NetworkUser nu = users.get(i);
			nu.setPreference(existingProduct);
			nu.setReward(existingProReward);
		}
	}
	
	/*This method takes the new product , its reward and seed as parameters.
	 * It sets the seed users with the new product and its reward.*/
	private void setSeedProduct(String newProduct,double newProReward,int[] seed)
	{
		for(int user : seed)
		{
			NetworkUser nu = users.get(user);
			nu.setPreference(newProduct);
			nu.setReward(newProReward);
		}
	}
	
	/*The method takes the new product, it's reward and seed as parameters.
	 * It returns an array of integers which represent ID of the users who changed 
	 * product after seeding.*/
	private int[] getUsersWhoChanged(String newProduct,int[] seed,double newProReward)
	{
		LinkedList<NetworkUser> seedUser = new LinkedList<NetworkUser>();
		for(int su : seed)
		{
			NetworkUser nu = users.get(su);
			seedUser.add(nu);
		}
		LinkedList<NetworkUser> userChanged = new LinkedList<NetworkUser>(seedUser);
		while(true)
		{
			int initialChangedUser = userChanged.size();
			LinkedList<NetworkUser> changedUserFrnds = friendsOfChangedUsers(userChanged);
			for(NetworkUser nu : changedUserFrnds)
			{
				if(nu.willChangeProduct(newProduct, newProReward))
				{
					userChanged.add(nu);
				}
			}
			int finalChangedUser = userChanged.size();
			if(finalChangedUser == initialChangedUser)
			{
				break;
			}
		}
		int[] result = new int[userChanged.size() - seedUser.size()];
		int i=0;
		for(NetworkUser nu : userChanged)
		{
			if(!seedUser.contains(nu))
			{
				result[i] = nu.getUserSocialId();
				i ++;
			}
		}
		return result;
	}
	
	/*This method takes a list of NetworkUser objects who are using the new product.
	 * It returns a list of NetworkUser objects who are friends with the users using 
	 * the new product.*/
	private LinkedList<NetworkUser> friendsOfChangedUsers(LinkedList<NetworkUser> changedUser)
	{
		HashSet<NetworkUser> changedUsersFrnds = new HashSet<NetworkUser>();
		for(NetworkUser nu : changedUser)
		{
			LinkedList<NetworkUser> friends = nu.getSocialFriends();
			for(NetworkUser frnd : friends)
			{
				if(!changedUser.contains(frnd))
				{
					changedUsersFrnds.add(frnd);
				}
			}
		}
		LinkedList<NetworkUser> result = new LinkedList<NetworkUser>();
		Iterator<NetworkUser> iterate = changedUsersFrnds.iterator();
		while(iterate.hasNext())
		{
			result.add(iterate.next());
		}
		return result;
	}
}
