package content.tool;

import java.net.URL;

/**
 * This class represents a scalpel and its behavior.
 * It also defines position, rotation and scale of the mesh in order to adjust the aspect of the tool.
 * 
 * @author Manuel Gallina
 */
public class Scalpel extends Tool
{	
	private static final double SCALE = 2.0;
	private static final String ACTION = "Cutting";
	/*
	 * The file path is relative to the bin folder.
	 */
	private static final String MESH_PATH = "/models/Knife.obj";
	
	/**
	 * Constructor for the Forceps class.
	 * 
	 * @param type The tool type.
	 */
	public Scalpel(String type)
	{
		super(type);
		
		setAction(ACTION);
		
		URL objFileUrl = this.getClass().getResource(MESH_PATH);
		
		super.changeMesh(objFileUrl);
		
		this.getParent().setScale(SCALE);
		this.getParent().setRotation(0, 180.0, 0.0);
		this.getParent().setPosition(-5.0, 0.0, -5.9);
	}
	
	@Override
	public String act() 
	{		
		return super.act();
	}

}
