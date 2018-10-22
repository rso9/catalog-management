package rest.v1.sources;

import beans.AlbumBean;
import beans.ArtistAlbumBean;
import core.Album;
import core.Artist;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("album")
@ApplicationScoped
public class AlbumSource {

    @Inject
    private ArtistAlbumBean artistAlbumBean;

    @Path("{idAlbum}/addArtist")
    @POST
    public Response addArtist(@PathParam("idAlbum") int idAlbum, @RequestBody Artist artist) {
        boolean successAdd = artistAlbumBean.joinArtistAndAlbum(artist.getId(), idAlbum);

        return successAdd? Response.status(Response.Status.OK).build():
                Response.status(Response.Status.BAD_REQUEST).build();
    }
}
