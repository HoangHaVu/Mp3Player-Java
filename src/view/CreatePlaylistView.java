package view;


import javafx.scene.control.ListView;
import model.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.util.ArrayList;


public class CreatePlaylistView {
    public Button addPlaylist,getBack,openMultiMp3;
    public BorderPane CreateListPane;
    public VBox listVB;
    public HBox upperHB,bottomHB;
    public FileChooser fileChooser;
    public Label Playlistname,songs;
    public TextField titleInput;
    public ArrayList<Track>Songs;
    public ObservableList<String>Songnames;
    public ListView<String> Songlistview;
    public ObservableList<String> SongList;

    public CreatePlaylistView(){
        this.CreateListPane = new BorderPane();
        this.addPlaylist = new Button();
        this.getBack = new Button();
        this.openMultiMp3 = new Button();
        this.listVB = new VBox();
        this.upperHB = new HBox();
        this.bottomHB= new HBox();
        this.fileChooser = new FileChooser();
        this.Playlistname = new Label();
        this.songs=new Label();
        this.titleInput= new TextField();
        this.Songnames = FXCollections.observableArrayList();
        this.Songs = new ArrayList<Track>();
        this.SongList = null;
        this.Songlistview = new ListView<String>(SongList);
        initialize();
    }

    public BorderPane getCreateListPane(){
        return CreateListPane;
    }

    public void initialize(){
        Playlistname.getStyleClass().add("k");
        songs.getStyleClass().add("k");
        Playlistname.setText("Enter Playlistname:");
        openMultiMp3.getStyleClass().add("exportButton");
        openMultiMp3.getStyleClass().add("exportButton:hover");
        upperHB.getChildren().addAll(Playlistname,titleInput,openMultiMp3);
        upperHB.setAlignment(Pos.CENTER);
        getBack.getStyleClass().add("toPlayerButton");
        getBack.getStyleClass().add("toPlayerButton:hover");
        addPlaylist.getStyleClass().add("addPlaylistButton");
        addPlaylist.getStyleClass().add("addPlaylistButton:hover");
        bottomHB.getChildren().addAll(getBack,addPlaylist);
        bottomHB.setSpacing(500);
        bottomHB.setPadding(new Insets(20,0,20,0));
        bottomHB.setAlignment(Pos.CENTER);
        songs.setText("Selected songs:");
        listVB.getChildren().addAll(songs,Songlistview,bottomHB);

        CreateListPane.setPadding(new Insets(20,20,20,20));
        CreateListPane.setTop(upperHB);
        CreateListPane.setCenter(listVB);
    }

}
