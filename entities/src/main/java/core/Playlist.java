package core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Song> songs = new HashSet<>(); // TODO: change to set

    private Set<Integer> owners = new HashSet<>(); // TODO: change to set
    private Set<Integer> followers = new HashSet<>(); // TODO: change to set

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

    public Set<Song> getSongs() { return this.songs; }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song) {
        this.songs.add(song);
        song.getPlaylists().add(this);
    }

    public void removeSong(Song song) {
        this.songs.remove(song);
        song.getPlaylists().remove(this);
    }

    public Set<Integer> getOwners() {
        if (this.owners == null) {
            this.owners = new HashSet<>();
        }
        return owners;
    }

    public void setOwners(Set<Integer> owners) {
        this.owners = owners;
    }

    public boolean addOwner(Integer ownerId) {
        if (this.owners == null) {
            this.owners = new HashSet<>();
        }
        return this.owners.add(ownerId);
    }

    public boolean removeOwner(Integer ownerId) {
        if (this.owners == null) return false;
        return this.owners.remove(ownerId);
    }

    public Set<Integer> getFollowers() {
        if (this.followers == null) {
            this.followers = new HashSet<>();
        }
        return followers;
    }

    public void setFollowers(Set<Integer> followers) {
        this.followers = followers;
    }

    public boolean addFollower(Integer followerId) {
        if (this.followers == null) {
            this.followers = new HashSet<>();
        }
        return this.followers.add(followerId);
    }

    public boolean removeFollower(Integer followerId) {
        if (this.followers == null) return false;
        return this.followers.remove(followerId);
    }
}
