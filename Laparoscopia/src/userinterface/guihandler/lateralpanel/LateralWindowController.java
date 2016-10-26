package userinterface.guihandler.lateralpanel;

import java.net.URL;
import java.util.ResourceBundle;

import application.Simulation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class LateralWindowController implements Initializable
{
	@FXML private ComboBox<String> leftToolSelector;
	@FXML private ComboBox<String> rightToolSelector;

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) 
	{
		leftToolSelector.setItems(Simulation.getObservableToolList());
		rightToolSelector.setItems(Simulation.getObservableToolList());
		
		leftToolSelector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
			{
				for(int i = 0; i < Simulation.TOOL_LIST.size(); i++)
					if(newValue == Simulation.TOOL_LIST.get(i).getType())
					{
						Simulation.LEFT_ARM.setSelectedTool(Simulation.TOOL_LIST.get(i));
					}
			}
		});
		
		rightToolSelector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
			{
				for(int i = 0; i < Simulation.TOOL_LIST.size(); i++)
					if(newValue == Simulation.TOOL_LIST.get(i).getType())
					{
						Simulation.RIGHT_ARM.setSelectedTool(Simulation.TOOL_LIST.get(i));
					}
			}
		});
	}
	
}
