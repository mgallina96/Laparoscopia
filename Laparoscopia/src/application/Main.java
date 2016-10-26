package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import userinterface.graphichandler.Axis;
import userinterface.graphichandler.View;
import userinterface.guihandler.WindowHandler;
import userinterface.userinputshandlers.KeyboardInputsHandler;
import userinterface.userinputshandlers.MouseInputsHandler;
import userinterface.graphichandler.graphicobject.Object3D;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Michele Franceschetti
 * @author Manuel Gallina
 */
public class Main extends Application 
{
	/** The logger of the application. */
	public static final Logger LOG = Logger.getLogger(Main.class.getName());
	
	private HBox mainPane;
	private Scene guiScene;
	private AnchorPane lateralPanel;
	
	@Override
	public void start(Stage window) 
    {
		try 
		{	
			//Log settings
			LOG.setLevel(Level.CONFIG);		
			
			//3D view
			Group root3D = new Group();
		    Object3D world = new Object3D();
		    Axis axisMesh = new Axis(new Object3D());
		    View camera = new View(world);
		    
		    SubScene scene3D = new SubScene(
		    		root3D, WindowHandler.SCENE3D_WIDTH_RESOLUTION, 
		    		WindowHandler.SCENE3D_HEIGHT_RESOLUTION, 
		    		true, 
		    		SceneAntialiasing.BALANCED
		    		);
		    
	        world.getChildren().addAll(
	        		axisMesh.getParent(), 
	        		Simulation.PATIENT.getParent(), 
	        		Simulation.LEFT_ARM.getUpperArm(), 
	        		Simulation.RIGHT_ARM.getUpperArm()
	        		);
	        
	        root3D.getChildren().add(world);
	        
	        scene3D.setCamera(camera.getCamera());
			scene3D.setFill(Paint.valueOf("lightgray"));      
	        
			/////////////////////////////////////////////////////////////////////
			
			//Main window
	        mainPane = new HBox();
			
			lateralPanel = (AnchorPane) FXMLLoader.load(Main.class.getResource("/userinterface/guihandler/lateralpanel/LateralWindow.fxml"));
	        lateralPanel.getStylesheets().add(getClass().getResource("/userinterface/guihandler/lateralpanel/lateralWindow.css").toExternalForm());
			
	        guiScene = new Scene(mainPane, WindowHandler.MAIN_WINDOW_WIDTH_RESOLUTION, WindowHandler.MAIN_WINDOW_HEIGHT_RESOLUTION);
	        
	        new KeyboardInputsHandler().handleKeyboard(guiScene, Simulation.LEFT_ARM, Simulation.RIGHT_ARM, axisMesh);
		    new MouseInputsHandler().handleMouse(guiScene, camera);
	        
	        mainPane.getChildren().addAll(scene3D, lateralPanel);
			
			window.setScene(guiScene);
			window.setTitle(WindowHandler.WINDOW_TITLE);
			window.show();	
						
		}
		catch(Exception e) 
		{
			LOG.log(Level.WARNING, "EXCEPTION: ", e);
		}
		finally
		{
			mainPane.setVisible(false);
		}
    }

	/**
	 * The main method.
	 * 
	 * @param args The string arguments passed to the main from file or console.
	 */
	public static void main(String[] args) 
    {
		launch(args);
	}
}
