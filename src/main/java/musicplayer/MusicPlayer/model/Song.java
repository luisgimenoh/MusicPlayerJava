package musicplayer.MusicPlayer.model;

public class Song {
	
	private int songId;
	private String songName;
	private String songAuthor;
	private String songAlbum;
	private String songCover;
	private String songAudio;
	
	public Song(int id, String name, String author, String album, String cover, String audio) {
		this.songId = id;
		this.songName = name;
		this.songAuthor = author;
		this.songAlbum = album;
		this.songCover = cover;
		this.songAudio = audio;
	}
	
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	
	public String getSongAuthor() {
		return songAuthor;
	}
	public void setSongAuthor(String songAuthor) {
		this.songAuthor = songAuthor;
	}
	
	public String getSongAlbum() {
		return songAlbum;
	}
	public void setSongAlbum(String songAlbum) {
		this.songAlbum = songAlbum;
	}
	
	public String getSongCover() {
		return songCover;
	}
	public void setSongCover(String songCover) {
		this.songCover = songCover;
	}
	
	public String getSongAudio() {
		return songAudio;
	}
	public void setSongAudio(String songAudio) {
		this.songAudio = songAudio;
	}
	
}
