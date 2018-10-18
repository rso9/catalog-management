package rest.v1.sources;

import beans.ArtistBean;
import core.Artist;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("artist")
@ApplicationScoped
public class ArtistSource {

    @Inject
    private ArtistBean artistBean;

    @GET
    public Response getAllArtists() {
        List<Artist> artists = artistBean.getArtists();

        return artists == null? Response.status(Response.Status.NOT_FOUND).build():
                Response.status(Response.Status.OK).entity(artists).build();
    }
}
