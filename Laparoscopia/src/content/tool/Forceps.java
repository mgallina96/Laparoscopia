package content.tool;

import java.net.URL;

/**
 * This class represents a forceps and its behavior.
 * It also defines position, rotation and scale of the mesh in order to adjust the aspect of the tool.
 * 
 * @author Manuel Gallina 
 */
public class Forceps extends Tool
{		
	private static final double SCALE = 0.5;
	private static final String ACTION = "Clutching";
	/*
	 * The file path is relative to the bin folder.
	 */
	private static final String MESH_PATH = "/models/Forceps.obj";
	
	/**
	 * Constructor for the Forceps class.
	 * 
	 * @param type The tool type.
	 */
	public Forceps(String type)
	{
		super(type);
	
		setAction(ACTION);
		
		URL objFileUrl = this.getClass().getResource(MESH_PATH);
		
		super.changeMesh(objFileUrl);
		
		this.getParent().setScale(SCALE);
		this.getParent().setRotation(0.0, 45.0, 0.0);
		this.getParent().setPosition(25, 2.5, 0);
	}

	@Override
	public String act() 
	{
		return super.act();
	}

}