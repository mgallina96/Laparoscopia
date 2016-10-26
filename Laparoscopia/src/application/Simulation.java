package application;

import java.util.ArrayList;
import java.util.Arrays;

import content.Arm;
import content.Patient;
import content.tool.Forceps;
import content.tool.Scalpel;
import content.tool.Tool;
import content.tool.VacuumCleaner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.transform.Translate;

/**
 * This class represents a simulation setting and contains all the
 * parameters that are needed for the program to run it.
 * 
 * @author Manuel Gallina
 */
public final class Simulation
{
	/** The starting target position for the left arm. */
	public static final Translate LEFT_TARGET = new Translate(-100, 100, 0);
    /** The starting target position for the right arm. */
	public static final Translate RIGHT_TARGET = new Translate(100, 100, 0);
    
    /** The left arm. */
    public static final Arm LEFT_ARM = new Arm(LEFT_TARGET, 200, 200);
    /** The right arm. */
    public static final Arm RIGHT_ARM = new Arm(RIGHT_TARGET, 200, 200);
	
    /** The patient. */
    public static final Patient PATIENT = new Patient(
    		new String[]{"Nome", "Mario", "Cognome", "Rossi", "Età", "50", "Altezza", "1.70", "Peso", "65"}
    		);  
    
    /** The list of tools available. */
    public static final ArrayList<Tool> TOOL_LIST = new ArrayList<>(
    		Arrays.asList(
	    		new Forceps("Forcipe"), 
	    		new Scalpel("Bisturi"), 
	    		new VacuumCleaner("Aspiratore")
	    		)
    		);
    
    /*
	 * A private constructor to override the default public one.
	 * This is useful to prevent the instantiation of this class.
	 */
    private Simulation(){}
    
    /**
	 * Returns the list of available tools as an {@code ObservableList}.
	 * 
	 * @return The list of available tools.
	 */
	public static ObservableList<String> getObservableToolList()
	{
		ObservableList<String> options = FXCollections.observableArrayList();
		
		for(int i = 0; i < TOOL_LIST.size(); i++)
			options.add(TOOL_LIST.get(i).getType());
		
		return options;
	}
}
