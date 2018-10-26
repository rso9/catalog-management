package beans;

import core.Artist;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ArtistBean {

    @PersistenceContext(unitName = "catalog-jpa")
    private EntityManager entityManager;

    public List<Artist> getArtists() {
        TypedQuery<Artist> query = entityManager.createNamedQuery("Artist.getAll", Artist.class);
        return query.getResultList();
    }

    public Artist getArtist(int id) {
        return entityManager.find(Artist.class, id);
    }

    @Transactional
    public Artist addArtist(Artist artist) {
        if(artist == null)
            return null;

        entityManager.persist(artist);
        return artist;
    }

    /* Updates the Artist instance with same id as `artist`.
        If such an instance does not exist, it is created. */
    @Transactional
    public Artist updateArtist(Artist artist) {
        if(artist == null)
            return null;

        Artist existingArtist = null;

        Integer idArtist = artist.getId();
        if(idArtist != null)
            existingArtist = entityManager.find(Artist.class, idArtist);

        return existingArtist == null? addArtist(artist): entityManager.merge(artist);
    }

    @Transactional
    public boolean deleteArtist(int idArtist) {
        try {
            Artist wantedArtist = entityManager.find(Artist.class, idArtist);
            entityManager.remove(wantedArtist);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}