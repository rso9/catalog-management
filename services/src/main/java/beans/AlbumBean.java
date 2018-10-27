package beans;

import core.Album;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AlbumBean {

    @PersistenceContext(unitName = "catalog-jpa")
    private EntityManager entityManager;

    public Album getAlbumById(int idAlbum) {
        return entityManager.find(Album.class, idAlbum);
    }

    public List<Album> getAlbums() {
        TypedQuery<Album> query = entityManager.createNamedQuery("Album.getAll", Album.class);
        return query.getResultList();
    }

    @Transactional
    public Album addAlbum(Album album) {
        if(album == null)
            return null;

        entityManager.persist(album);
        return album;
    }

    /* Updates the Album instance with same id as `album`.
        If such an instance does not exist, it is created. */
    @Transactional
    public Album updateAlbum(Album album) {
        if(album == null)
            return null;

        entityManager.merge(album);
        return album;
    }

    @Transactional
    public boolean deleteAlbum(int idAlbum) {
        try {
            Album wantedAlbum = entityManager.find(Album.class, idAlbum);
            entityManager.remove(wantedAlbum);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
