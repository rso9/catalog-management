package rest.v1.sources;

import beans.AlbumBean;
import beans.ArtistAlbumBean;
import core.Album;
import core.Artist;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
    @Inject
    private AlbumBean albumBean;

    @Operation(
            description = "Get an album by ID",
            tags = "album",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Artist",
                            content = @Content(schema = @Schema(implementation = Artist.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No artist with ID in the catalog",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @Path("{id}")
    @GET
    public Response getAlbumById(@PathParam("id") int idAlbum) {
        Album album = albumBean.getAlbumById(idAlbum);
        return album == null? Response.status(Response.Status.NOT_FOUND).build():
                Response.status(Response.Status.OK).entity(album).build();
    }

    @Operation(
            description = "Add artist to an existing album",
            tags = {"album", "artist"}
            // TODO: change what the function returns and document the responses
            )
    @Path("{id}/addArtist")
    @POST
    public Response addArtistToAlbum(@PathParam("id") int idAlbum, @RequestBody Artist artist) {
        boolean successAdd = artistAlbumBean.joinArtistAndAlbum(artist.getId(), idAlbum);

        return successAdd? Response.status(Response.Status.OK).build():
                Response.status(Response.Status.BAD_REQUEST).build();
    }
}
