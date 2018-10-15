package graph;

import java.util.Arrays;

public class Prediction {
	
	int[] seedUsers;
	int[] usersLaterChanged;
	int totalUsers;
	
	/*This constructor takes three parameters as follows:-
	 * 1. int[] seed - array of integers which is used as seed users , who changed product first.
	 * 2. int[] usersLaterChanged - array of integers which represents users who changed product at a later time.
	 * 3. int totalUsers - No. of total users in graph.*/
	public Prediction(int[] seed,int[] usersLaterChanged,int totalUsers)
	{
		this.seedUsers = Arrays.copyOf(seed,seed.length);
		this.usersLaterChanged = Arrays.copyOf(usersLaterChanged,usersLaterChanged.length);
		this.totalUsers = totalUsers;
	}
	
	/*This method returns the total no. of users using new product at equilibrium state.*/
	public int totalUsersUsingNewProductAtEquilibrium()
	{
		return (seedUsers.length + usersLaterChanged.length);
	}
	
	/*This method returns the no. of users who changed product after seeding.*/
	public int numberOfUsersChangedProductAfterSeeding()
	{
		return usersLaterChanged.length;
	}
	
	/*This method returns the total no. of users using existing product at equilibrium state.*/
	public int totalUsersUsingExistingProductAtEquilibrium()
	{
		return (totalUsers - totalUsersUsingNewProductAtEquilibrium());
	}
	
	/*This method returns an array of integers which represent user IDs , who changed product
	 * after seeding.*/
	public int[] usersIdWhoChangedProductAfterSeeding()
	{
		return Arrays.copyOf(usersLaterChanged,usersLaterChanged.length);
	}
	
	/*This method returns an array of integers which represents user IDs , using new product
	 * at equilibrium.*/
	public int[] usersIdUsingNewProductAtEquilibrium()
	{
		int[] result = new int[seedUsers.length + usersLaterChanged.length];
		for(int j = 0;j < seedUsers.length;j ++)
		{
			result[j] = seedUsers[j];
		}
		int i = seedUsers.length;
		for(int k = 0;k < usersLaterChanged.length;k++)
		{
			result[i] = usersLaterChanged[k];
			i ++;
		}
		return result;
	}
	
	/*This method returns the percentage of users using new product at equilibrium.*/
	public double usersPercentageUsingNewProductAtEquilibrium()
	{
		double temp =((double)totalUsersUsingNewProductAtEquilibrium() / totalUsers) * 100;
		return temp;
	}
	
	/*This method returns the percentage of users using existing product at equilibrium.*/
	public double usersPercentageUsingExistingProductAtEquilibrium()
	{
		double temp =((double)totalUsersUsingExistingProductAtEquilibrium() / totalUsers) * 100;
		return temp;
	}
}
