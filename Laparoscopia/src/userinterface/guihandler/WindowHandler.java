package userinterface.guihandler;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;

import application.Main;
import application.Simulation;
import content.Arm;
import content.Patient;
import content.tool.Tool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class contains a list of methods that build the 2D user interface.
 * 
 * The gui is composed by a lateral area that contains the patient informations box, the tool selection 
 * menu and the 3d object meshes selection. 
 * 
 * @author Manuel Gallina
 */
public final class WindowHandler 
{
	/**
	 * The title of the window.
	 */
	public static final String WINDOW_TITLE = "Laparoscopy";
	
	/**
	 * The width resolution of the application window measured in pixels.
	 */
	public static final double MAIN_WINDOW_WIDTH_RESOLUTION = 1920;
	
	/**
	 * The height resolution of the application window measured in pixels.
	 */
	public static final double MAIN_WINDOW_HEIGHT_RESOLUTION = 1000;
	
	//If main width is 1024 this option should be set to 2/3.
	//If main width is 1920 this option should be set to 4/5.
	/**
	 * The width of the 3d view window measured in pixels.
	 */
	public static final double SCENE3D_WIDTH_RESOLUTION = MAIN_WINDOW_WIDTH_RESOLUTION * 4/5;
	
	/**
	 * The height of the 3d view window measured in pixels.
	 */
	public static final double SCENE3D_HEIGHT_RESOLUTION = MAIN_WINDOW_HEIGHT_RESOLUTION;
	
	private static final String INFO_BOX_TITLE = "Info paziente";
	private static final double INFO_BOX_WIDTH_RESOLUTION = MAIN_WINDOW_WIDTH_RESOLUTION - SCENE3D_WIDTH_RESOLUTION;
	private static final double INFO_BOX_HEIGHT_RESOLUTION = MAIN_WINDOW_HEIGHT_RESOLUTION;
	private static final double INFO_BOX_GRID_VGAP = 8;
	private static final double INFO_BOX_GRID_HGAP = INFO_BOX_WIDTH_RESOLUTION * 2 / 3 - 50;
		
	private static final double SEPARATOR_MAX_WIDTH = INFO_BOX_WIDTH_RESOLUTION - 20;
	
	private static final String TOOL_SELECTION_TITLE = "Selezione strumento";
	private static final double TOOL_GRID_HGAP = INFO_BOX_WIDTH_RESOLUTION / 4 - 10;
	private static final double TOOL_GRID_VGAP = 0;

	private static final String MESH_SELECTION_TITLE = "Selezione modelli";
	
	/*
	 * A private constructor to override the default public one.
	 * This is useful to prevent the instantiation of this class.
	 */
    private WindowHandler(){}
	
	/**
	 * This method creates the lateral window with the patient's informations and the 
	 * tool selection interface. 
	 * 
	 * @param patient The patient used in this simulation.
	 * @param leftArm The left arm.
	 * @param rightArm The right arm.
	 * 
	 * @return The lateral window.
	 */
	public static VBox lateralWindow(Patient patient, Arm leftArm, Arm rightArm)
	{
		VBox lateralWindow = new VBox();
			lateralWindow.setSpacing(10);
		
		VBox infoBox = infoBox(patient);
			infoBox.setId("infoBox");
				
		Separator separator = new Separator();
			separator.setId("separator");
			separator.setOrientation(Orientation.HORIZONTAL);
			separator.setMaxWidth(SEPARATOR_MAX_WIDTH);
			separator.setHalignment(HPos.CENTER);
			separator.setValignment(VPos.CENTER);
		
		lateralWindow.setPrefSize(INFO_BOX_WIDTH_RESOLUTION, INFO_BOX_HEIGHT_RESOLUTION);
		
		VBox toolSelection = toolSelection(leftArm, rightArm);
			toolSelection.setId("toolSelection");
		
		Separator separator2 = new Separator();
			separator2.setId("separator");
			separator2.setOrientation(Orientation.HORIZONTAL);
			separator2.setMaxWidth(SEPARATOR_MAX_WIDTH);
			separator2.setHalignment(HPos.CENTER);
			separator2.setValignment(VPos.CENTER);
				
		VBox meshSelection = objMeshSelection();
			meshSelection.setId("toolSelection");
			
		lateralWindow.getChildren().addAll(infoBox, separator, toolSelection, separator2, meshSelection);
		
		return lateralWindow;
	}
	
	/*
	 * This method creates the patient's info box.
	 */
	private static VBox infoBox(Patient patient)
	{
		VBox infoBox = new VBox();
		
		Label title = new Label(INFO_BOX_TITLE);
		
		GridPane grid = new GridPane();
			grid.setId("infoBoxGrid");
			grid.setHgap(INFO_BOX_GRID_HGAP);
			grid.setVgap(INFO_BOX_GRID_VGAP);
	
		Label[] attributes = new Label[patient.getInfosLength()];
		Label[] data = new Label[patient.getInfosLength()];
		
		for(int i = 0; i < patient.getInfosLength(); i++)
		{
			attributes[i] = new Label(patient.getLabel(i));
			data[i] = new Label(patient.getData(i));
			
			grid.add(attributes[i], 0, i);
			grid.add(data[i], 1, i);
		}
		
		infoBox.getChildren().addAll(title, grid);
		
		return infoBox;
	}

	/*
	 * Creates the tool selection interface.
	 */
	private static VBox toolSelection(Arm leftArm, Arm rightArm) 
	{
		VBox toolSelection = new VBox();
	
		Label title = new Label(TOOL_SELECTION_TITLE);
	
		GridPane grid = new GridPane();
			grid.setId("toolSelectionGrid");
			grid.setHgap(TOOL_GRID_HGAP);
			grid.setVgap(TOOL_GRID_VGAP);
	
		ObservableList<String> leftOptions = FXCollections.observableArrayList();
		ObservableList<String> rightOptions = FXCollections.observableArrayList();
		
		for(int i = 0; i < leftArm.getToolList().size(); i++)
		{
			leftOptions.add(leftArm.getToolList().get(i).getType());
		}
		
		for(int i = 0; i < rightArm.getToolList().size(); i++)
		{
			rightOptions.add(rightArm.getToolList().get(i).getType());
		}
		
		ComboBox<String> rightTool = new ComboBox<>(rightOptions);
			rightTool.setId("tool");
			rightTool.getSelectionModel().selectedItemProperty().addListener(new  
				ChangeListener<String>() 
				{
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
				{
					for(int i = 0; i < rightArm.getToolList().size(); i++)
						if(newValue == rightArm.getToolList().get(i).getType())
						{
							rightArm.setSelectedTool(rightArm.getToolList().get(i));
						}
				}
				});	
		
			ComboBox<String> leftTool = new ComboBox<>(leftOptions);
			leftTool.setId("tool");
			leftTool.getSelectionModel().selectedItemProperty().addListener(new  
				ChangeListener<String>() 
				{
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
				{
					for(int i = 0; i < leftArm.getToolList().size(); i++)
						if(newValue == leftArm.getToolList().get(i).getType())
						{
							leftArm.setSelectedTool(leftArm.getToolList().get(i));
						}
				}
			});	
			
		Label selectionRight = new Label("Strumento dx");
		Label selectionLeft = new Label("Strumento sx");
		
		grid.add(selectionRight, 0, 0);
		grid.add(selectionLeft, 0, 1);
		grid.add(rightTool, 1, 0);
		grid.add(leftTool, 1, 1);
		
		toolSelection.getChildren().addAll(title, grid);
		
		return toolSelection;
	}
	
	/*
	 * 
	 */
	private static VBox objMeshSelection()
	{
		VBox box = new VBox();
	
		Label title = new Label(MESH_SELECTION_TITLE);
		
		GridPane grid = new GridPane();
			grid.setId("toolSelectionGrid");
			grid.setHgap(TOOL_GRID_HGAP);
			grid.setVgap(TOOL_GRID_VGAP);

		
		ArrayList<File> modelsList = new ArrayList<>();
		
		File folder = new File("bin/models");
		if(folder.isDirectory())
		{
			for(File f : folder.listFiles())
			{
				modelsList.add(f);
			}
			
			for(int i = 0; i < modelsList.size(); i++)
			{
				int index = modelsList.get(i).getName().lastIndexOf('.');
				
				if (index > 0) 
				{
				    if(modelsList.get(i).getName().substring(i+1) != "obj")
				    {
				    	modelsList.remove(i);
				    }

					Main.LOG.log(Level.INFO, modelsList.get(i).toString());
				}
			}
			
			ObservableList<String> modelsOptions = FXCollections.observableArrayList();
			
			for(int i = 0; i < modelsList.size(); i++)
			{
				modelsOptions.add(modelsList.get(i).toString());
			}
			
			//// Patient
			
			Label patientModelLabel = new Label("Paziente");
			grid.add(patientModelLabel, 0, 0);
			
			ComboBox<String> patientModel = new ComboBox<>(modelsOptions);
			patientModel.setId("tool");
			patientModel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
			{
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
				{
					for(int i = 0; i < modelsList.size(); i++)
						if(newValue == modelsList.get(i).toString())
						{
							
						}
				}
			});
			
			grid.add(patientModel, 1, 0);
			
			//// Tools
			
			Label leftToolModelLabel[] = new Label[Simulation.TOOL_LIST.size()];
			
			
			
			for(int i = 0; i < Simulation.TOOL_LIST.size(); i++)
			{
				leftToolModelLabel[i] = new Label(Simulation.TOOL_LIST.get(i).getType());
				grid.add(leftToolModelLabel[i], 0, i + 1);
					
				ComboBox<String> leftToolModel = new ComboBox<>(modelsOptions);
				leftToolModel.setId("tool");
				
				final int temp = i;
				
				leftToolModel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
				{
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
					{
						for(int k = 0; k < modelsList.size(); k++)
						{
							if(newValue == modelsList.get(k).toString())
							{
								try
								{
									Simulation.TOOL_LIST.get(temp).changeMesh(modelsList.get(k).toURL());
									Main.LOG.log(Level.INFO, "Model changed");
								}
								catch(Exception e) {}
							}
						}
					}
				});
				
				grid.add(leftToolModel, 1, i + 1);
			}
		}
		
		box.getChildren().addAll(title, grid);
		
		return box;
				
	}
}