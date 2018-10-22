package beans;

import core.Album;
import core.Artist;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.logging.Logger;

// bean for intermediate (many: many) table between Artist and Album
@ApplicationScoped
public class ArtistAlbumBean {

    private static final Logger artAlbLogger = Logger.getLogger(ArtistAlbumBean.class.getName());

    @PersistenceContext(unitName = "catalog-jpa")
    private EntityManager entityManager;

    @Transactional
    public boolean joinArtistAndAlbum(int idArtist, int idAlbum) {
        Artist artist = entityManager.find(Artist.class, idArtist);
        Album album = entityManager.find(Album.class, idAlbum);

        if (artist != null && album != null) {
            artAlbLogger.info(String.format("Joining artist with ID %d and album with id %d",
                    idArtist, idAlbum));
            artist.addAlbum(album);
            album.addArtist(artist);
            return true;
        }
        else {
            artAlbLogger.severe(String.format("FAILURE: Joining artist with ID %d and album with id %d",
                    idArtist, idAlbum));
            return false;
        }
    }

}
