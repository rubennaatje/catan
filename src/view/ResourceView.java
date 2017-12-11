package view;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.PlayerUser;
import model.TileType;
import view.javaFXTemplates.PaneTemplate;

public class ResourceView extends PaneTemplate implements Observer
{
	@FXML private TextField erts;
	@FXML private TextField hooi;
	@FXML private TextField schaap;
	@FXML private TextField hout;
	@FXML private TextField steen;
	
	public ResourceView()
	{
		super(ResourceView.class.getResource("fxml/Resources.fxml"));
	}
	//grondstoffenview
	@Override
	public void update(Observable arg0, Object arg1)
	{
		PlayerUser playerData = (PlayerUser)arg0;
		HashMap<TileType, Integer> data = playerData.getResources();
		erts.setText(data.get(TileType.E).toString());
		hooi.setText(data.get(TileType.G).toString());
		schaap.setText(data.get(TileType.W).toString());
		hout.setText(data.get(TileType.H).toString());
		steen.setText(data.get(TileType.B).toString());
	}
}
