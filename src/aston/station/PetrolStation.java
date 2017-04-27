package aston.station;

import java.util.*;
import aston.resources.*;
import aston.vehicles.*;

/**
 * This creates the current utilized GUI that the user will simulate 
 * 
 * @author Tristan P
 * @author Matas B.
 * @author Jordan L.
 * 
 * @version 19/04/2017
 *
 */

public class PetrolStation {
	
	//variables
	Config config;
	Vehicle generatedV;
	
	//instances
	public Random rand = new Random(Config.randomSeed); //temp static
	private Pump[] pumps;
	private Shop shop;
	public Output output = new Output();
	
	
	/**
	 * Constructor<br>
	 * This creates the inital Petrol Station of the Simulation 
	 * @param c Configuration class
	 */
	public PetrolStation(Config c)
	{
		config = c;
		int numOfPumps = config.getNumPumps();
		pumps = new Pump[numOfPumps];
		for (int i = 0; i < numOfPumps; i++)
		{
			Pump p = new Pump(i+1);
			pumps[i] = p;
		}
		shop = new Shop(config.getNumTills());
	}
	
	/**
	 * Main run method, runs every step/tick.
	 */
	public String run(boolean gui)
	{
		String information = "";
		if (!gui)
		{
			information += ("\nStep: " + output.getNumSteps());
		}
		else
		{
			information += (output.getNumSteps() + ",");
		}
		
		//customers
		shop.passTime();
		
		//make each Pump pump fuel
		for (Pump p : pumps)
		{
			p.pumpFuel();
		}
		
		//create a new vehicle
		if (spawnVehicle())
		{
			Vehicle v = generatedV;
			boolean added = false;
			double size = pumps[0].getQueueSize();
			Pump shortestQueue = pumps[0];
			for (Pump p : pumps)
			{
				if (p.getQueueSize() < size)
				{
					shortestQueue = p;
				}
	
			}
			if (shortestQueue.addVehicleToQueue(v))
			{
				added = true;
			}
			else
			{
				//System.out.println("vehicle leaves as no space at pump");
			}
		}
		else
		{
			//System.out.println("no v");
		}
		
		//update output and print info
		int totalPumped = 0;
		for (Pump p : pumps)
		{
			totalPumped += p.getNumOfGallons();
			if (!gui)
			{
				information += (p.textToString());
			}
			else
			{
				information += (p.guiToString());
			}
			
		}
		
		if (gui)
		{
			for (int i = 4; i > pumps.length; i--)
			{
				information += "empty,empty,empty,empty,";
			}
		}
		else
		{
			output.setNumGallons(totalPumped);
			output.setFuelMoney((int) totalPumped*config.getPencePerGallon());
			//System.out.println(output.getGallons());
		}
		
		return information;
	}
	
	/**
	 * Send the customer to the store
	 * @param c Customer Class
	 */
	public void goToShop(Customer c)
	{
		shop.enter(c);
	}
	
	/**
	 * Creates one random subclass of the vehicle class, based on probabilities in config.
	 * 
	 * @return true if the number is equivalent to the probablities or false if its greater.
	 */
	private boolean spawnVehicle()
	{
		double num = rand.nextDouble();
		//System.out.println(num);
		
		//chose a vehicle
		if (num < config.getScProb())
		{
			output.addSC();
			generatedV = new SmallCar(Integer.toString(output.getSC()),this);
			return true;
		}
		else if (num < (config.getScProb() + config.getMProb()))
		{
			output.addM();
			generatedV = new Motorbike(Integer.toString(output.getM()),this);
			return true;
		}
		else if (num < (config.getScProb() + config.getMProb() + config.getFsProb()))
		{
			output.addFS();
			generatedV = new FamilySedan(Integer.toString(output.getFS()),this);
			return true;
		}
		else if (num < config.getTruckProb()  ){
			
			output.addTruck();
			generatedV = new Truck(Integer.toString(output.getTruck()),this);
			return true;
		}
		else	
		{
			return false;
		}
	}
}
