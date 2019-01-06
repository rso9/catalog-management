package beans;

import core.Song;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
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

        // `merge` instead of `persist` so that the passed artists aren't created everytime
        // a song gets inserted into db
        entityManager.merge(song);
        entityManager.flush();
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

    @CircuitBreaker(requestVolumeThreshold = 1, delay = 15000, failureRatio = 0.1)
    @Fallback(fallbackMethod = "songFallback")
    public Song songTestFaultTolerance(Integer idSong) throws Exception {
        int i = (int)(Math.random() * 5) % 5;
        if(i == 0)
            throw new Exception("Unlucky");

        return getSongById(idSong);
    }

    public Song songFallback(Integer idSong) {
        Song defaultSong = new Song();
        defaultSong.setSongName("Despacito");
        return defaultSong;
    }
}
