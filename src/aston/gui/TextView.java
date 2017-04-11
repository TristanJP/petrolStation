package aston.gui;

import aston.simulator.Simulator;

public class TextView {
	
	public TextView(int steps, double p, double q, int pumps, int tills, int gallonPrice)
	{
		//create simulation
		Simulator s = new Simulator();
				
		//set config values
		s.config.setScProb(p);
		s.config.setMProb(p);
		s.config.setFsProb(q);
		s.config.setNumPumps(pumps);
		s.config.setNumTills(tills);
		s.config.setNumSteps(steps);
		s.config.setPencePerGallon(gallonPrice);
				
		//create PetrolStation
		s.createPetrolStation();
				
		//run simulation
		s.simulate(s.config.getNumSteps(),false);
	}

	
	public void showStatus()
	{
		
	}

}
