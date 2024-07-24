package musicplayer.MusicPlayer.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Audio {
	
	private int audioId;
	private Media media;
	private MediaPlayer mediaPlayer;
	private String songsFolder = "audio/";
	
	public Audio(int audioId, String songFile) {
		
		this.setAudioId(audioId);
		
		this.media = new Media(getClass().getClassLoader().getResource(songsFolder + songFile).toExternalForm());
		this.mediaPlayer = new MediaPlayer(media);
	}
	
	public void play() {
		mediaPlayer.play();
	}
	
	public void pause() {
		mediaPlayer.pause();
	}
	
	public boolean isPlaying() {
		return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
	}

	public int getAudioId() {
		return audioId;
	}

	public void setAudioId(int audioId) {
		this.audioId = audioId;
	}
}
