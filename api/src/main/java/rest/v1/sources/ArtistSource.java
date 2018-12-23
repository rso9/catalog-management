package rest.v1.sources;

import beans.ArtistAlbumBean;
import beans.ArtistBean;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import core.Artist;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import rest.v1.interceptors.annotations.LogContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Log
@LogContext
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("artist")
@ApplicationScoped
public class ArtistSource {

    private Logger logger = LogManager.getLogger(ArtistSource.class.getName());

    @Inject
    private ArtistBean artistBean;
    @Inject
    private ArtistAlbumBean artistAlbumBean;

    @Operation(
            summary = "Get all artists",
            description = "Get all artists",
            tags = "artist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Artists",
                            content = @Content(schema = @Schema(implementation = Artist.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No artists in the catalog",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            })
    @GET
    public Response getAllArtists() {
        List<Artist> artists = artistBean.getArtists();
        logger.info("Serving request to get all artists...");

        return artists == null? Response.status(Response.Status.NOT_FOUND).build():
                Response.status(Response.Status.OK).entity(artists).build();
    }

    @Operation(
            summary = "Get an artist by ID",
            description = "Get an artist by ID",
            tags = "artist",
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
                    )
            })
    @Path("{id}")
    @GET
    public Response getArtistById(@PathParam("id") int idArtist) {
        Artist artist = artistBean.getArtist(idArtist);
        return artist == null ? Response.status(Response.Status.NOT_FOUND).build(): Response.ok(artist).build();
    }

    @Operation(
            summary = "Add a new artist",
            description = "Add a new artist",
            tags = "artist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Artist",
                            content = @Content(schema = @Schema(implementation = Artist.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Could not add artist to catalog",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @POST
    public Response addArtist(@RequestBody Artist artist) {
        Artist newArtist = artistBean.addArtist(artist);

        return newArtist == null? Response.status(Response.Status.BAD_REQUEST).build():
                Response.status(Response.Status.OK).entity(newArtist).build();
    }

    @Operation(
            summary = "Update an artist entity",
            description = "Update an artist entity. If the artist does not exist, it is created. " +
                    "The operation is idempotent",
            tags = "artist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Artist",
                            content = @Content(schema = @Schema(implementation = Artist.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Updating the artist failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @PUT
    public Response addOrUpdateArtist(@RequestBody Artist artist) {
        Artist newArtist = artistBean.updateArtist(artist);

        return newArtist == null? Response.status(Response.Status.BAD_REQUEST).build():
                Response.status(Response.Status.OK).entity(newArtist).build();
    }

    @Operation(
            summary = "Delete an artist",
            description = "Delete artist by ID",
            tags = "artist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully removed artist"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Removal of artist failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @Path("{id}")
    @DELETE
    public Response deleteArtist(@PathParam("id") int idArtist) {
        boolean status = artistBean.deleteArtist(idArtist);

        return status? Response.status(Response.Status.OK).build():
                Response.status(Response.Status.BAD_REQUEST).build();
    }
}
