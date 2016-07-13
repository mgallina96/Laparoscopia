package content.tool;

import java.net.URL;

/**
 * This class represents a vacuum cleaner and its behavior.
 * It also defines position, rotation and scale of the mesh in order to adjust the aspect of the tool.
 * 
 * @author Manuel Gallina
 */
public class VacuumCleaner extends Tool
{	
	private static final double SCALE = 0.6;
	private static final String ACTION = "Vacuuming";
	/*
	 * The file path is relative to the bin folder.
	 */
	private static final String MESH_PATH = "/models/VacuumCleaner.obj";
	
	/**
	 * Constructor for the Forceps class.
	 * 
	 * @param type The tool type.
	 */
	public VacuumCleaner(String type)
	{
		super(type);
		
		setAction(ACTION);
		
		URL objFileUrl = this.getClass().getResource(MESH_PATH);
		
		super.changeMesh(objFileUrl);

		this.getParent().setScale(SCALE);
		this.getParent().setRotation(0.0, 0.0, 270.0);
		this.getParent().setPosition(30, 0, 0);
	}
	
	@Override
	public String act() 
	{
		return super.act();
	}
}