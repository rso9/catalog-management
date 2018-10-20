package beans;

import core.Artist;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
}