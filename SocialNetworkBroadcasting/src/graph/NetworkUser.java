package graph;

import java.util.LinkedList;

class NetworkUser {
	
	private int myUniqueSocialId;
	private LinkedList<NetworkUser> mySocialFriends;
	private boolean gotMessage;
	private LinkedList<NetworkUser> canBroadcast;
	
	/*This constructor takes an integer which is then used as the social ID of this user.*/
	public NetworkUser(int value)
	{
		myUniqueSocialId = value;
		mySocialFriends = new LinkedList<NetworkUser>();
		gotMessage = false;
		canBroadcast = null;
	}
	
	/*This method returns this user's social ID.*/
	public int getUserSocialId()
	{
		return myUniqueSocialId;
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
	
	/*This method takes another NetworkUser object and deletes it from its list of friends*/
	public void removeSocialFriend(NetworkUser socialFriend)
	{
		if(socialFriend == null || !mySocialFriends.contains(socialFriend))
		{
			throw new IllegalArgumentException("Null or No social friend exit!!!");
		}
		mySocialFriends.remove(socialFriend);
	}
	
	
	public void deleteSocialFriend(NetworkUser socialFriend)
	{
		if(socialFriend == null || !mySocialFriends.contains(socialFriend))
		{
			throw new IllegalArgumentException("Null or Social Friend do not exit!!!");
		}
		mySocialFriends.remove(socialFriend);
	}
	
	public LinkedList<NetworkUser> getSocialFriends()
	{
		return new LinkedList<NetworkUser>(mySocialFriends);
	}
	
	public boolean receivedMessage()
	{
		return gotMessage;
	}
	
	public void takeMessage()
	{
		gotMessage = true;
	}
	
	public int getBroadcastingStrength()
	{
		int i = 0;
		LinkedList<NetworkUser> list = new LinkedList<NetworkUser>();
		for(NetworkUser nu : mySocialFriends)
		{
			if(!nu.receivedMessage())
			{
				i++;
				list.add(nu);
			}
		}
		canBroadcast = list;
		return i;
	}
	
	public LinkedList<NetworkUser> broadcastMessage()
	{
		gotMessage = true;
		for(NetworkUser nu : canBroadcast)
		{
			nu.takeMessage();
		}
		return canBroadcast;
	}
	
	public void refresh()
	{
		gotMessage = false;
	}
}
