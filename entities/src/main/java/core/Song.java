package core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "song")
@NamedQueries(
        value = {
                @NamedQuery(name = "Song.getAll", query = "SELECT a from song a")
        }
)
public class Song implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer duration; // in seconds
    private Integer listens = 0;

    @ManyToMany(mappedBy = "songs")
    @JsonIgnoreProperties("songs")
    private List<Album> albums = new ArrayList<>();

    @ManyToMany(mappedBy = "songs")
    @JsonIgnoreProperties("songs")
    private List<Playlist> playlists = new ArrayList<>();

    @ManyToMany(mappedBy = "songs")
    @JsonIgnoreProperties("songs")
    private List<Artist> artists = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSongName() {
        return name;
    }

    public void setSongName(String artistName) {
        this.name = artistName;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getListens() {
        return this.listens;
    }

    public void setListens(Integer listens) {
        this.listens = listens;
    }

    public List<Album> getAlbums() { return this.albums; }

    public void addAlbum(Album album) {
        this.albums.add(album);
        album.getSongs().add(this);
    }

    public void removeAlbum(Album album) {
        this.albums.remove(album);
        album.getSongs().remove(this);
    }

    public List<Artist> getArtists() { return this.artists; }

    public void addArtist(Artist artist) {
        this.artists.add(artist);
        artist.getSongs().add(this);
    }

    public void removeArtist(Artist artist) {
        this.artists.remove(artist);
        artist.getSongs().remove(this);
    }

    public List<Playlist> getPlaylists() { return this.playlists; }
}
