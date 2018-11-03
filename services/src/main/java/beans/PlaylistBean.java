package beans;

import core.Playlist;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PlaylistBean {

    @PersistenceContext(unitName = "catalog-jpa")
    private EntityManager entityManager;

    public List<Playlist> getPlaylists() {
        TypedQuery<Playlist> playlists = entityManager.createNamedQuery("Playlist.getAll", Playlist.class);
        return playlists.getResultList();
    }

    public Playlist getPlaylistById(int idPlaylist) {
        return entityManager.find(Playlist.class, idPlaylist);
    }

    @Transactional
    public Playlist addPlaylist(Playlist playlist) {
        if(playlist == null)
            return null;

        entityManager.persist(playlist);
        return playlist;
    }

    @Transactional
    public Playlist updatePlaylist(Playlist playlist) {
        if(playlist == null)
            return null;

        Playlist existingPlaylist = entityManager.find(Playlist.class, playlist.getId());

        if(playlist.getPlaylistName() != null)
            existingPlaylist.setPlaylistName(playlist.getPlaylistName());

        if(!playlist.getSongs().isEmpty())
            existingPlaylist.setSongs(playlist.getSongs());

        if(!playlist.getFollowers().isEmpty())
            existingPlaylist.setFollowers(playlist.getFollowers());

        if(!playlist.getOwners().isEmpty())
            existingPlaylist.setOwners(playlist.getOwners());

        entityManager.merge(existingPlaylist);
        return existingPlaylist;
    }

    @Transactional
    public Playlist mergePlaylist(Playlist playlist) {
        if(playlist == null)
            return null;

        entityManager.merge(playlist);
        return playlist;
    }

    @Transactional
    public boolean deletePlaylist(int idPlaylist) {
        try {
            Playlist wantedPlaylist = entityManager.find(Playlist.class, idPlaylist);
            entityManager.remove(wantedPlaylist);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
