package musicplayer.MusicPlayer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SongDAO {

	static Statement statement = ConnectionDB.getConnection();
	static ArrayList<Song> songs;
	
	public static ArrayList<Song> getAllSongs() throws SQLException {
		
		songs = new ArrayList<Song>();
		
		ResultSet resultSet = statement.executeQuery("SELECT * FROM songs");
		
		while (resultSet.next()) {

			int songId = resultSet.getInt("songId");
			String songName = resultSet.getString("songName");
			String songAuthor = resultSet.getString("songAuthor");
			String songAlbum = resultSet.getString("songAlbum");
			String songCover = resultSet.getString("songCover");
			String songAudio = resultSet.getString("songAudio");
			
			Song song = new Song(songId, songName, songAuthor, songAlbum, songCover, songAudio);
			songs.add(song);
		}
		
		return songs;
	}
	
	public static String getSongAudio(Song song) {
		
		String result = "";
		
		try {
			
			ResultSet resultSet = statement.executeQuery("SELECT songAudio from songs WHERE songId="+song.getSongId());
			
			if (resultSet.next()) result = resultSet.getString("songAudio");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public static String getSongCover(Song song) {
		
		String result = "";
		
		try {
			
			ResultSet resultSet = statement.executeQuery("SELECT songCover from songs WHERE songId="+song.getSongId());
			
			if (resultSet.next()) result = resultSet.getString("songCover");
			System.out.println(result);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public static void updateSong(Song song) throws SQLException {
		
		statement.executeQuery("UPDATE `songs` SET "
				+ "`songName`='"+song.getSongName()+"',"
				+ "`songAuthor`='"+song.getSongAuthor()+"',"
				+ "`songAlbum`='"+song.getSongAlbum()+"',"
				+ "`songCover`='"+song.getSongCover()+"',"
				+ "`songAudio`='"+song.getSongAudio()+"' "
				+ "WHERE `songId`="+song.getSongId());
	}
	
	public static void addSong(Song song) throws SQLException {
		
		statement.executeQuery("INSERT INTO `songs`"
				+ "(`songName`, `songAuthor`, `songAlbum`, `songCover`, `songAudio`) VALUES ('"
				+song.getSongName()+"','"
				+song.getSongAuthor()+"','"
				+song.getSongAlbum()+"','"
				+song.getSongCover()+"','"
				+song.getSongAudio()+"')");
	}
	
	
}
