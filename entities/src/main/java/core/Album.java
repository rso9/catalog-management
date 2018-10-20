package core;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "album")
@NamedQueries(
        value = {
                @NamedQuery(name = "Album.getAll", query = "SELECT a from album a")
        }
)
public class Album implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer year;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "album_song",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> songs = new ArrayList<>();

    @ManyToMany(mappedBy = "albums")
    private List<Artist> artists = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getYear() { return this.year; }

    public void setYear(Integer year) { this.year = year; }

    public List<Song> getSongs() { return this.songs; }

    public void addSong(Song song) {
        this.songs.add(song);
        song.getAlbums().add(this);
    }

    public void removeSong(Song song) {
        this.songs.remove(song);
        song.getAlbums().remove(this);
    }

    public List<Artist> getArtists() { return this.artists; }

    public void addArtist(Artist artist) {
        this.artists.add(artist);
        artist.getAlbums().add(this);
    }

    public void removeArtist(Artist artist) {
        this.artists.remove(artist);
        artist.getAlbums().remove(this);
    }
}
