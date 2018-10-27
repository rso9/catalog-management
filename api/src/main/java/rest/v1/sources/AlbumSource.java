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
import java.util.List;

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
            summary = "Get all albums",
            description = "Get all albums",
            tags = "album",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Albums",
                            content = @Content(schema = @Schema(implementation = Album.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No albums in the catalog",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            })
    @GET
    public Response getAllAlbums() {
        List<Album> albums = albumBean.getAlbums();

        return albums == null? Response.status(Response.Status.NOT_FOUND).build():
                Response.status(Response.Status.OK).entity(albums).build();
    }

    @Operation(
            summary = "Get an album by ID",
            description = "Get an album by ID",
            tags = "album",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Album",
                            content = @Content(schema = @Schema(implementation = Album.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No album with given ID in the catalog",
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
            summary = "Add a new album",
            description = "Add a new album",
            tags = "album",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Album",
                            content = @Content(schema = @Schema(implementation = Album.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Could not add album to catalog",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @POST
    public Response addAlbum(@RequestBody Album album) {
        Album newAlbum = albumBean.addAlbum(album);

        return newAlbum == null? Response.status(Response.Status.BAD_REQUEST).build():
                Response.status(Response.Status.OK).entity(newAlbum).build();
    }

    @Operation(
            summary = "Update an album entity",
            description = "Update an album entity. If the album does not exist, it is created. " +
                    "The operation is idempotent",
            tags = "album",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Album",
                            content = @Content(schema = @Schema(implementation = Album.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Updating the album failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @PUT
    public Response addOrUpdateAlbum(@RequestBody Album album) {
        Album newAlbum = albumBean.updateAlbum(album);

        return newAlbum == null? Response.status(Response.Status.BAD_REQUEST).build():
                Response.status(Response.Status.OK).entity(newAlbum).build();
    }

    @Operation(
            summary = "Delete an album",
            description = "Delete album by ID",
            tags = "album",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully removed album"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Removal of album failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @Path("{id}")
    @DELETE
    public Response deleteAlbum(@PathParam("id") int idAlbum) {
        boolean status = albumBean.deleteAlbum(idAlbum);

        return status? Response.status(Response.Status.OK).build():
                Response.status(Response.Status.BAD_REQUEST).build();
    }
}
