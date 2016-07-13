package userinterface.graphichandler.graphicobject;

import java.net.URL;

import javafx.scene.shape.MeshView;

/**
 * This interface provides a method to import of a mesh.
 * 
 * @author Manuel Gallina
 */
public interface ObjImporter 
{	
	/**
	 * Imports the mesh from a .obj file.
	 * 
	 * @param path The file path.
	 * @return The mesh.
	 */
	public MeshView importObjMesh(URL path);
}
