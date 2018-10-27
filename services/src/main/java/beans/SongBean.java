package beans;

import core.Song;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SongBean {

    @PersistenceContext(unitName = "catalog-jpa")
    private EntityManager entityManager;

    public List<Song> getSongs() {
        TypedQuery<Song> songs = entityManager.createNamedQuery("Song.getAll", Song.class);
        return songs.getResultList();
    }

    public Song getSongById(int idSong) {
        return entityManager.find(Song.class, idSong);
    }

    @Transactional
    public Song addSong(Song song) {
        if(song == null)
            return null;

        entityManager.persist(song);
        return song;
    }

    /* Updates the Song instance with same id as `song`.
        If such an instance does not exist, it is created. */
    @Transactional
    public Song updateSong(Song song) {
        if(song == null)
            return null;

        entityManager.merge(song);
        return song;
    }

    @Transactional
    public boolean deleteSong(int idSong) {
        try {
            Song wantedSong = entityManager.find(Song.class, idSong);
            entityManager.remove(wantedSong);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
