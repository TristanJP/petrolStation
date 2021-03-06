package aston.resources;
import java.util.*;
import aston.vehicles.*;

/**
 * 
 * This presents with the Information about the Vehicles in line.
 * 
 * @author Tristan P.
 * @author Matas B.
 * 
 * @version 19/04/2017
 *
 */
public class VehicleQueue {
	
	private Queue<Vehicle> q;
	private double queueSpace = Config.queueSize;
	public double spaceTaken = 0;
	private int numV = 0;
	
	/**
	 * Constructor<br>
	 * This creates a brand new LinkedList Array that stores in Vehicle Objects
	 */
	public VehicleQueue()
	{
		q = new LinkedList<Vehicle>();
	}
	
	/**
	 * Adds a vehicle to the queue if there is enough space for it to fit, adds the size of the vehicle to the space taken
	 * 
	 * @return boolean true if vehicle is added, otherwise false
	 * @param v The vehicle that is going to be added
	 */
	public boolean addVehicle(Vehicle v)
	{
		if (queueSpace >= v.getVehicleSize() + spaceTaken)
		{
			spaceTaken += v.getVehicleSize();
			q.add(v);
			numV++;
			v.setVehicleQueue(this);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Gets the vehicle at the front of the queue
	 * 
	 * @return Vehicle The vehicle at the front
	 */
	public Vehicle getFrontVehicle()
	{
		Vehicle v = q.peek();
		return v;
	}
	
	/**
	 * Removes a vehicle from the front of the queue and changes the ammount of space taken in the queue
	 */
	public void removeFrontVehicle()
	{
		Vehicle v = q.peek();
		if (v != null)
		{
			q.remove();
			numV--;
			spaceTaken -= v.getVehicleSize();
		}
	}
	
	/**
	 * Get the size of the queue
	 * 
	 * @return spaceTaken
	 */
	public double getSize()
	{
		return spaceTaken;
	}
	
	/**
	 * This outputs the string information to the console
	 * @return s String concatination
	 */
	public String toString()
	{
		String s = "Space Taken: " + spaceTaken;
		for (Vehicle v : q)
		{
			s += ", (" + v.textToString() + ")";
		}
		return s;
	}
	/**
	 * This prints the string information to the GUI 
	 * @return s String Concatination
	 */
	public String toGuiString()
	{
		String s = "";
		for (Vehicle v : q)
		{
			s += v.guiToString() + ",";
		}
		
		//add empty info if less cars
		for (int i = 4; i > numV; i--)
		{
			s += "empty,";
		}
		//System.out.println(numV);
		return s;
	}
	
}
