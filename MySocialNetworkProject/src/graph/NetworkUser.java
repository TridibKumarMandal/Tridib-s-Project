package graph;

import java.util.LinkedList;

public class NetworkUser {
	
	private int myUniqueSocialId;
	private LinkedList<NetworkUser> mySocialFriends;
	private String myPreference;
	private double reward;
	
	/*This constructor takes an integer which is then used as the social ID of this user.*/
	public NetworkUser(int value)
	{
		myUniqueSocialId = value;
		mySocialFriends = new LinkedList<NetworkUser>();
	}
	
	/*This method takes another NetworkUser object and adds it to its list of friends.*/
	public void addSocialFriend(NetworkUser socialFriend)
	{
		if(socialFriend == null || mySocialFriends.contains(socialFriend))
		{
			throw new IllegalArgumentException("Null or Already contains social friend !!!");
		}
		mySocialFriends.add(socialFriend);
	}
	
	/*This method returns this user's social ID.*/
	public int getUserSocialId()
	{
		return myUniqueSocialId;
	}
	
	/*This method returns a list of NetworkUser objects which are friends with this NetworkUser object.*/
	public LinkedList<NetworkUser> getSocialFriends()
	{
		return new LinkedList<NetworkUser>(mySocialFriends);
	}
	
	/* This method sets the preferred product of this user.*/
	public void setPreference(String value)
	{
		myPreference = value;
	}
	
	/*This method returns the preferred product of this user.*/
	public String getPreference()
	{
		return myPreference;
	}
	
	/*This method sets the reward the user gets with the product.*/
	public void setReward(double value)
	{
		reward = value;
	}
	
	/*This method returns true if this NetworkUser object is friend with the NetworkUser 
	 * object passed , false otherwise.*/
	public boolean isFriend(NetworkUser u)
	{
		return mySocialFriends.contains(u);
	}
	
	/*This method takes the new product and reward of the new product as parameters.
	 * Returns true if this NetworkUser object changes to new product.
	 * Returns false if this NetworkUser object dosn't change to new product.*/
	public boolean willChangeProduct(String newProduct,double newProReward)
	{
		int numExistingProduct = 0;
		for(NetworkUser nu : mySocialFriends)
		{
			String frndPreference = nu.getPreference();
			if(myPreference.equals(frndPreference))
			{
				numExistingProduct ++;
			}
		}
		int numNewProduct = mySocialFriends.size() - numExistingProduct;
		double temp1 = (double)numNewProduct/mySocialFriends.size();
		double temp2 = reward/(reward + newProReward);
		if(temp1 > temp2)
		{
			myPreference = newProduct;
			reward = newProReward;
			return true;
		}
		return false;
	}
}
