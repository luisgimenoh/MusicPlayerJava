package musicplayer.MusicPlayer.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import musicplayer.MusicPlayer.model.Audio;
import musicplayer.MusicPlayer.model.Song;
import musicplayer.MusicPlayer.model.SongDAO;


public class SongsController {
	
	@FXML
	private TableView<Song> songsTable;
	
	@FXML
	private TableColumn<Song, Integer> songIdColumn;
	
	@FXML
	private TableColumn<Song, String> songCoverColumn;
	
	@FXML
	private TableColumn<Song, String> songNameColumn;
	
	@FXML
	private TableColumn<Song, String> songAuthorColumn;
	
	@FXML
	private TableColumn<Song, String> songAlbumColumn;
	
	@FXML
	private TableColumn<Song, Void> playSongColumn;
	
	@FXML
	private TableColumn<Song, Void> updateSongColumn;
	
	@FXML
	private TableColumn<Song, Void> deleteSongColumn;
	
	@FXML
	private ObservableList<Song> songs;
	
	private ArrayList<Audio> activeAudios = new ArrayList<Audio>();
	
	FontIcon playIcon = new FontIcon(FontAwesomeSolid.PLAY);
	FontIcon pauseIcon = new FontIcon(FontAwesomeSolid.PAUSE);
	FontIcon updateIcon = new FontIcon(FontAwesomeSolid.EDIT);
	FontIcon deleteIcon = new FontIcon(FontAwesomeSolid.TRASH);
	FontIcon lastIcon = new FontIcon();
		
	public void initialize() throws SQLException {
		
		songIdColumn.setCellValueFactory(new PropertyValueFactory<>("songId"));
		songNameColumn.setCellValueFactory(new PropertyValueFactory<>("songName"));
		songAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("songAuthor"));
		songAlbumColumn.setCellValueFactory(new PropertyValueFactory<>("songAlbum"));
		
		playSongColumn.setCellFactory(createButtonCellFactory("Play"));
		updateSongColumn.setCellFactory(createButtonCellFactory("Update"));
		deleteSongColumn.setCellFactory(createButtonCellFactory("Delete"));
		
		songCoverColumn.setCellFactory(createCover());

		
		songs = FXCollections.observableArrayList(SongDAO.getAllSongs());
		songsTable.setItems(songs);
	}
	
	private Callback<TableColumn<Song, String>, TableCell<Song, String>> createCover() {
	    
		return new Callback<TableColumn<Song, String>, TableCell<Song, String>>() {
	    
			@Override
			public TableCell<Song, String> call(TableColumn<Song, String> param) {
	        
				return new TableCell<Song, String>() {
	            
					@Override
	                protected void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    
	                    if (empty) {
	                        setGraphic(null);
	                    } else {
	                        Song song = getTableView().getItems().get(getIndex());
	                        String songCover = "covers/"+song.getSongCover();
	                        
	                        ImageView imageView = new ImageView();
	                        imageView.setFitHeight(100);
	                        imageView.setFitWidth(100);
	                        imageView.setPreserveRatio(true);
	                        
	                        Image image = new Image(getClass().getClassLoader().getResource(songCover).toExternalForm());
	                        imageView.setImage(image);
	                        setGraphic(imageView);
	                    }
	                }
	            };
	        }
	    };
	}


	private Callback<TableColumn<Song, Void>, TableCell<Song, Void>> createButtonCellFactory(String iconType){
		
		return new Callback<TableColumn<Song,Void>, TableCell<Song,Void>>() {
			
			@Override
			public TableCell<Song, Void> call(TableColumn<Song, Void> param) {
				
				return new TableCell<Song, Void>(){
					
					FontIcon icon;
					
					{
												
						if (iconType.equals("Play")) {
							icon = new FontIcon(FontAwesomeSolid.PLAY);
						}
						else if(iconType.equals("Update")){
							icon = new FontIcon(FontAwesomeSolid.EDIT);
						}
						else if(iconType.equals("Delete")){
							icon = new FontIcon(FontAwesomeSolid.TRASH);
						}
						
						icon.setOnMouseClicked(event -> {
							
							Song song = getTableView().getItems().get(getIndex());
							
							if (iconType.equals("Play")) {
																
								if (icon.getIconCode().equals(playIcon.getIconCode())) {
									handlePlaySong(song);
								}
								else if(icon.getIconCode().equals(pauseIcon.getIconCode())) {
									handlePauseSong();
								}
							
							}
							
							else if (iconType.equals("Update")) {
								handleUpdateSong(song);
							}
							
							else if (iconType.equals("Delete")) {
								handleDeleteSong(song);
							}
							
						});
					}
					
					@Override
					protected void updateItem(Void item, boolean empty) {
						// TODO Auto-generated method stub
						super.updateItem(item, empty);
						
						if (empty) {
							setGraphic(null);
						}
						else {
							setGraphic(icon);
						}
					}

					private void handlePauseSong() {
						
						//checks audio playing
						for(Audio audio : activeAudios) {
							
							if (audio.isPlaying()) {
								audio.pause();
								icon.setIconCode(FontAwesomeSolid.PLAY);
								setGraphic(icon);
								System.out.println("Pause audio!");
							}
						}
					}
					
					private void handlePlaySong(Song song) {
						
						//updates last played icon
						lastIcon.setIconCode(FontAwesomeSolid.PLAY);
						
						//checks audio playing
						handlePauseSong();
						
						Audio currentAudio = new Audio(song.getSongId(), song.getSongAudio());
						boolean savedAudio = false;
						
						//check if audio was saved before
						for(Audio audio : activeAudios) {
							
							if (audio.getAudioId() == currentAudio.getAudioId()) {
								
								savedAudio = true;
								
								audio.play();
								icon.setIconCode(FontAwesomeSolid.PAUSE);
								setGraphic(icon);
								System.out.println("Play old audio!");
								
								break;
							}
							
						}
						
						if (!savedAudio) {
							
							activeAudios.add(currentAudio);
							currentAudio.play();
							
							icon.setIconCode(FontAwesomeSolid.PAUSE);
							setGraphic(icon);
							System.out.println("Play new audio!");
						}
						
						//saves last played icon
						lastIcon = icon;
					}
					
					private void handleUpdateSong(Song song) {
						System.out.println(
								song.getSongId()+"\t\t\t"+
								song.getSongName()+"\t\t\t"+
								song.getSongAuthor()+"\t\t\t"
						);
						
					}
					
					private void handleDeleteSong(Song song) {
						System.out.println(
								song.getSongId()+"\t\t\t"+
								song.getSongName()+"\t\t\t"+
								song.getSongAuthor()+"\t\t\t"
						);
						
					}
					
				};
			}
		}; 
	}
		
}
