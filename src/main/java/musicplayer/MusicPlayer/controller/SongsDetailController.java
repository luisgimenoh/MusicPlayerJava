package musicplayer.MusicPlayer.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import musicplayer.MusicPlayer.App;
import musicplayer.MusicPlayer.model.Song;
import musicplayer.MusicPlayer.model.SongDAO;

public class SongsDetailController {

	@FXML
	private TextField nameTextfield;
	@FXML
	private TextField authorTextfield;
	@FXML
	private TextField albumTextfield;
	
	private static boolean updateMode = false;
	@FXML
	private static Song song;
	
	public void initialize() {
		
		if (updateMode) {
			nameTextfield.setText(song.getSongName());
			authorTextfield.setText(song.getSongAuthor());
			albumTextfield.setText(song.getSongAlbum());
		}
		
	}
	
	public void addOrUpdate() throws SQLException, IOException {
		
		String songName = nameTextfield.getText();
		String songAuthor = authorTextfield.getText();
		String songAlbum = albumTextfield.getText();
		
		Song song = new Song(songName, songAuthor, songAlbum);
		
		if (updateMode) {
			SongDAO.updateSong(song);
		}
		else {
			SongDAO.addSong(song);
		}
		
		nameTextfield.setText("");
		authorTextfield.setText("");
		albumTextfield.setText("");
		
		switchToSongsView();
		
	}
	
	
	@FXML
    private void switchToSongsView() throws IOException {
        App.setRoot("SongsView");
	}
	public static boolean getUpdateMode() {
		return updateMode;
	}
	public static void setUpdateMode(boolean updateMode) {
		SongsDetailController.updateMode = updateMode;
	}
	
	
}
