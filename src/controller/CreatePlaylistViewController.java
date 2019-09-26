package controller;


import view.CreatePlaylistView;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Mp3Player;
import model.Track;
import view.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreatePlaylistViewController {

    private Main application;
    private Mp3Player mp3Player;
    private Stage stage;
    private CreatePlaylistView createPlaylistView;
    private Button addPlaylist,getBack,openMultiMp3;
    private FileChooser fileChooser;
    private TextField titleInput;
    private ArrayList<Track> Songs;
    private ObservableList<String> Songnames;
    private ListView<String> Songlistview;



    public CreatePlaylistViewController(Main application, Mp3Player mp3Player, Stage primaryStage, CreatePlaylistView createPlaylistView){
        this.application = application;
        this.mp3Player = mp3Player;
        this.stage = primaryStage;
        this.createPlaylistView = createPlaylistView;
        this.addPlaylist = createPlaylistView.addPlaylist;
        this.getBack = createPlaylistView.getBack;
        this.openMultiMp3 = createPlaylistView.openMultiMp3;
        this.fileChooser = new FileChooser();
        this.titleInput = createPlaylistView.titleInput;
        this.Songs = new ArrayList<Track>();
        this.Songnames = createPlaylistView.Songnames;
        this.Songlistview = createPlaylistView.Songlistview;
        initialize();
    }

    public void initialize(){

        addPlaylist.setOnAction(event -> {
            if(titleInput.getText()!=null&&Songs.size()>2){
                mp3Player.createPlaylist(titleInput.getText(),Songs);
                application.switchScene("Playlisten");
                System.out.println("Kreiieren der Playlist war erfolgreich");
            }else{
                System.out.println("Das Kreieren der Playlist ist leider fehlgeschlagen");
                System.out.println("Bitte gebe der Playlist einen Titel und mindestens 3 songs");
            }

        });

        getBack.setOnAction(event -> {
            application.switchScene("Playlisten");
        });

        openMultiMp3.setOnAction(event -> {
            configureFileChooser(fileChooser);
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(extFilter);
            List<File> list =
                    fileChooser.showOpenMultipleDialog(stage);
            if (list != null) {
                for (File file : list) {
                    try {
                        Track newSong = new Track(file.getAbsolutePath());
                        Songs.add(newSong);
                        Songnames.add(newSong.getTitle());
                        Songlistview.setItems(Songnames);
                    } catch (InvalidDataException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (UnsupportedTagException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private static void configureFileChooser(final FileChooser fileChooser){
        fileChooser.setTitle("Open Playlist");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }
}
