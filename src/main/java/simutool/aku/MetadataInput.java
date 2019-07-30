package simutool.aku;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MetadataInput{

	public static SimpleStringProperty title = new SimpleStringProperty();
	public static SimpleStringProperty desc = new SimpleStringProperty();

	public List<ComboBox<Tulip>> relationBoxes = new ArrayList<ComboBox<Tulip>>();
	public static List<String> chosenRelations = new ArrayList<String>();
	public static String activity;
	public static List<Tulip> activities = new ArrayList<Tulip>();


	public ComboBox<Tulip> activityBox;


	public Stage stage;
	Button okBtn = new Button("OK");

	final class Tulip {
		public String tulipTitle;
		public String tulipId;
		public String tulipType;

		public Tulip(String id, String title, String type){
			tulipId = id.replaceAll("\"", "");
			tulipTitle = title.replaceAll("\"", "");
			tulipType = type.replaceAll("\"", "");
		}


		public String getTulipTitle() {
			return tulipTitle;
		}


		public void setTulipTitle(String tulipTitle) {
			this.tulipTitle = tulipTitle;
		}


		public String getTulipId() {
			return tulipId;
		}


		public void setTulipId(String tulipId) {
			this.tulipId = tulipId;
		}


		public String getTulipType() {
			return tulipType;
		}


		public void setTulipType(String tulipType) {
			this.tulipType = tulipType;
		}


		@Override
		public String toString() {
			return tulipTitle;
		}

	}
	public List<Tulip> relations = new ArrayList<Tulip>();



	public MetadataInput(Path path) {

		stage = new Stage();


		GridPane grid = new GridPane();

		Text scenetitle = new Text("Please enter data");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);


		Label titleLabel = new Label("Title:");
		titleLabel.setMinWidth(100);

		grid.add(titleLabel, 0, 2);
		TextField titleField = new TextField();
		titleField.setMinWidth(400);
		grid.add(titleField, 1, 2);

		Label descLabel = new Label("Description:");
		grid.add(descLabel, 0, 3);
		TextField descField = new TextField();
		grid.add(descField, 1, 3);


		Label kmsHostLabel = new Label("What Activity is this data related to?");
		kmsHostLabel.setPadding(new Insets(10,0,0,0));


		kmsHostLabel.setAlignment(Pos.CENTER);


		grid.add(kmsHostLabel, 0 ,4, 2, 1);


		HBox box = new HBox();


		activityBox = new ComboBox<Tulip>();
		activityBox.setMinWidth(475);

		for(JsonElement e : RestCalls.makeInheritanceQuery("Activity")) {

			Tulip t = new Tulip(e.getAsJsonObject().get("identifier").toString(), 
					e.getAsJsonObject().get("title").toString(), e.getAsJsonObject().get("type").getAsJsonArray().get(0).toString());
			activities.add(t); 
		}

		for(JsonElement e : RestCalls.makeInheritanceQuery("KBMSThing")) {

			Tulip t = new Tulip(e.getAsJsonObject().get("identifier").toString(), 
					e.getAsJsonObject().get("title").toString(), e.getAsJsonObject().get("type").getAsJsonArray().get(0).toString());
			relations.add(t); 
		}

		box.getChildren().add(activityBox);

		activityBox.getItems().addAll(activities);

		Button btn = new Button("New");
		btn.setMinWidth(55);
		btn.setMaxWidth(55);
		box.getChildren().add(btn);
		box.setMargin(btn, new Insets(0,0,0,25));

		Image imageRefresh = new Image(getClass().getResourceAsStream("icon4.png"));
		Button refresh = new Button("Refresh", new ImageView(imageRefresh));
		refresh.setMinWidth(30);
		refresh.setMaxWidth(30);
		refresh.setPadding(new Insets(5,5,5,5));
		refresh.setTooltip(new Tooltip("Refresh"));
		box.getChildren().add(refresh);
		box.setMargin(refresh, new Insets(0,0,0,15));

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				addNewActivity();
			}
		});

		refresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent ev) {
				activities.clear();
				for(JsonElement e : RestCalls.makeInheritanceQuery("Activity")) {

					Tulip t = new Tulip(e.getAsJsonObject().get("identifier").toString(), 
							e.getAsJsonObject().get("title").toString(), e.getAsJsonObject().get("type").getAsJsonArray().get(0).toString());
					activities.add(t); 
				}
				activityBox.getItems().clear();
				activityBox.getItems().addAll(activities);
				AutocompleteDecorator.autoCompleteComboBoxPlus(activityBox, (typedText, itemToCompare) -> itemToCompare.toString().toLowerCase().contains(typedText.toLowerCase()));
			}
		});

		grid.add(box, 0, 5, 2, 1);

		AutocompleteDecorator.autoCompleteComboBoxPlus(activityBox, (typedText, itemToCompare) -> itemToCompare.toString().toLowerCase().contains(typedText.toLowerCase()));


		Label relLabel = new Label("Relations");
		grid.add(relLabel, 0 ,6, 2, 1);

		generateRelation(grid);


		Bindings.bindBidirectional(titleField.textProperty(), title);
		Bindings.bindBidirectional(descField.textProperty(), desc);


		okBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				// System.out.println("title: " + title.get());
				// System.out.println("desc: " + desc.get());

				//System.out.print("activityBox: " + FxUtilTest.getComboBoxValue(activityBox));
				if(AutocompleteDecorator.getComboBoxValue(activityBox)!=null) {
					activity = AutocompleteDecorator.getComboBoxValue(activityBox).getTulipId();					
				}

				for(ComboBox b : relationBoxes) {
					// System.out.println("box: " + b);
					Tulip t = (Tulip)AutocompleteDecorator.getComboBoxValue(b);
					if(t!=null) {
						chosenRelations.add( t.getTulipId() );
					}
				}

				stage.hide();
				stage.close();
				FileService.syncFile(path);

			}
		});

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(15, 5, 5, 15));
		ScrollPane scrollPane = new ScrollPane(grid);

		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		grid.setHalignment(titleLabel, HPos.CENTER);

		grid.setAlignment(Pos.CENTER);        

		Scene scene = new Scene(scrollPane, 640, 500);
		stage.setScene(scene);		
		stage.setScene(scene);

		stage.showAndWait();
	}

	public void deleteRelation(HBox parentBox, ComboBox box, GridPane grid) {

		grid.getChildren().remove(parentBox);
		relationBoxes.remove(box);
	}

	int relationnum = 0;
	public void generateRelation(GridPane grid) {

		HBox box = new HBox();
		ComboBox comboBox2 = new ComboBox();
		comboBox2.setMinWidth(475);

		relationBoxes.add(comboBox2);
		comboBox2.getItems().addAll(relations);

		box.getChildren().add(comboBox2);

		Button btn = new Button("Add");
		btn.setMinWidth(100);
		box.getChildren().add(btn);
		box.setMargin(btn, new Insets(0,0,0,25));


		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Button b = (Button)e.getSource();
				if(b.getText().equals("Delete")) {
					deleteRelation(box, comboBox2, grid);
				}else if(AutocompleteDecorator.getComboBoxValue(comboBox2) != null){
					b.setText("Delete");
					generateRelation(grid);					
				}

			}
		});

		for(ComboBox b : relationBoxes) {
			b.setDisable(true);
		}
		relationBoxes.get(relationBoxes.size()-1).setDisable(false);

		grid.add(box, 0, relationnum+8, 2, 1);

		grid.getChildren().remove(okBtn);
		grid.setHalignment(okBtn, HPos.CENTER);
		okBtn.setMinWidth(100);
		grid.add(okBtn, 0, relationnum+11, 2,1);

		AutocompleteDecorator.autoCompleteComboBoxPlus(comboBox2, (typedText, itemToCompare) -> itemToCompare.toString().toLowerCase().contains(typedText.toLowerCase()));
		relationnum++;
	}
	public SimpleStringProperty getTitle() {
		return title;
	}
	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}
	public SimpleStringProperty getDesc() {
		return desc;
	}
	public void setDesc(SimpleStringProperty desc) {
		this.desc = desc;
	}
	public List<String> getChosenRelations() {
		return chosenRelations;
	}
	public void setChosenRelations(List<String> chosenRelations) {
		this.chosenRelations = chosenRelations;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}


	public static void addNewActivity() {
		Runtime rt = Runtime.getRuntime();
		String url = Config.getConfig().getCreateActivityEndpoint();
		try {
			rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

	}


}
