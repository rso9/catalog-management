package beans;

import core.Album;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class AlbumBean {

    @PersistenceContext(unitName = "catalog-jpa")
    private EntityManager entityManager;

    public Album getAlbumById(int idAlbum) {
        return entityManager.find(Album.class, idAlbum);
    }
}
