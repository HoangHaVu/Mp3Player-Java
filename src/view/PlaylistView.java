package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Mp3Player;



public class PlaylistView {
    public Mp3Player mp3Player;
    public Button playlistscenechange,openMultipleMP3,createPlaylist;
    public BorderPane playlistScene;
    public HBox listHB,LabelHB,bottomHB;
    public VBox listVB;
    public GridPane scene2TopGP;
    public Label PlaylistLabel, SongLabel;
    public ObservableList<String> SongList;
    public ObservableList<String> PlaylistList;
    public ListView<String> Songlistview;
    public ListView<String>PlaylistView;


    public PlaylistView(Mp3Player mp3Player){
        this.mp3Player = mp3Player;
        Elements();
        PlaylistLabel.setText("Playlisten:");
        SongLabel.setText("songs:");
        PlaylistLabel.getStyleClass().add("b");
        SongLabel.getStyleClass().add("b");
        LabelHB.getChildren().addAll(PlaylistLabel,SongLabel);
        LabelHB.setSpacing(120);
        LabelHB.setAlignment(Pos.CENTER);

        listHB.getChildren().addAll(PlaylistView,Songlistview);
        listHB.setAlignment(Pos.CENTER);
        listVB.getChildren().addAll(LabelHB,listHB);
        playlistscenechange.getStyleClass().add("toPlayerButton");
        playlistscenechange.getStyleClass().add("toPlayerButton:hover");
        openMultipleMP3.getStyleClass().add("exportButton");
        openMultipleMP3.getStyleClass().add("exportButton:hover");
        createPlaylist.setText("Create Playlist");

        bottomHB.getChildren().addAll(createPlaylist);
        bottomHB.setAlignment(Pos.CENTER);
        bottomHB.setPadding(new Insets(10,0,10,0));
        scene2TopGP.setHgap(25);
        scene2TopGP.setVgap(25);
        scene2TopGP.setPadding(new Insets(20,20,20,20));
        scene2TopGP.add(openMultipleMP3, 17,1,1,1);
        scene2TopGP.add(playlistscenechange,1,1,1,1);
        playlistScene.setTop(scene2TopGP);
        playlistScene.setCenter(listVB);
        playlistScene.setBottom(bottomHB);

    }

    public BorderPane getPlaylistView(){
        return playlistScene;
    }
    private void Elements(){
        listHB = new HBox();
        LabelHB = new HBox();
        bottomHB = new HBox();
        listVB= new VBox();
        playlistscenechange = new Button();
        openMultipleMP3 = new Button();
        playlistScene= new BorderPane();
        scene2TopGP=new GridPane();
        PlaylistLabel=new Label();
        SongLabel = new Label();
        createPlaylist= new Button();
        SongList = mp3Player.getActPlaylist().getSongnames();
        Songlistview = new ListView<>(SongList);
        PlaylistList = mp3Player.getPlaylisten();
        PlaylistView = new ListView(PlaylistList);

    }

}
