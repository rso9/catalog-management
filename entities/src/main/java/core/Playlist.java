package core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "playlist")
@NamedQueries(
        value = {
                @NamedQuery(name = "Playlist.getAll", query = "SELECT a from playlist a")
        }
)
public class Playlist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "playlist_song",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    @JsonIgnoreProperties("playlists")
    private List<Song> songs = new ArrayList<>();

    // TODO: add owners and followers

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaylistName() {
        return name;
    }

    public void setPlaylistName(String artistName) {
        this.name = artistName;
    }

    public List<Song> getSongs() { return this.songs; }

    public void addSong(Song song) {
        this.songs.add(song);
        song.getPlaylists().add(this);
    }

    public void removeSong(Song song) {
        this.songs.remove(song);
        song.getPlaylists().remove(this);
    }
}
