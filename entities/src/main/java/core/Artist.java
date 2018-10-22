package core;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "artist")
@NamedQueries(
        value = {
                @NamedQuery(name = "Artist.getAll", query = "SELECT a from artist a")
        }
)
public class Artist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "artist_song",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    public List<Song> songs = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "artist_album",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id")
    )
    public List<Album> albums = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArtistName() {
        return name;
    }

    public void setArtistName(String artistName) {
        this.name = artistName;
    }

    public List<Song> getSongs() { return this.songs; }

    public void addSong(Song song) {
        this.songs.add(song);
        song.getArtists().add(this);
    }

    public void removeSong(Song song) {
        this.songs.remove(song);
        song.getArtists().remove(song);
    }

    public List<Album> getAlbums() { return this.albums; }

    public void addAlbum(Album album) {
        this.albums.add(album);
    }

    public void removeAlbum(Album album) {
        this.albums.remove(album);
    }

}
