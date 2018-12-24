package rest.v1.sources;

import beans.PlaylistBean;
import com.kumuluz.ee.logs.cdi.Log;
import core.Playlist;
import core.User;
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

@Log
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("playlist")
@ApplicationScoped
public class PlaylistSource {

    @Inject
    private PlaylistBean playlistBean;

    @Operation(
            summary = "Get all playlists",
            description = "Get all playlists",
            tags = "playlist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Playlists",
                            content = @Content(schema = @Schema(implementation = Playlist.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No playlists in the catalog",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )})
    @GET
    public Response getAllPlaylists() {
        List<Playlist> playlists = playlistBean.getPlaylists();

        return playlists == null ? Response.status(Response.Status.NOT_FOUND).build():
                Response.status(Response.Status.OK).entity(playlists).build();
    }

    @Operation(
            summary = "Get a playlist by ID",
            description = "Get a playlist by ID",
            tags = "playlist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Playlist",
                            content = @Content(schema = @Schema(implementation = Playlist.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No playlist with given ID in the catalog",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @Path("{id}")
    @GET
    public Response getPlaylistById(@PathParam("id") int idPlaylist) {
        Playlist playlist = playlistBean.getPlaylistById(idPlaylist);
        return playlist == null? Response.status(Response.Status.NOT_FOUND).build():
                Response.status(Response.Status.OK).entity(playlist).build();
    }

    @Operation(
            summary = "Add a new playlist",
            description = "Add a new playlist",
            tags = "playlist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Playlist",
                            content = @Content(schema = @Schema(implementation = Playlist.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Could not add playlist to catalog",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @POST
    public Response addPlaylist(@RequestBody Playlist playlist) {
        Playlist newPlaylist = playlistBean.addPlaylist(playlist);

        return newPlaylist == null? Response.status(Response.Status.BAD_REQUEST).build():
                Response.status(Response.Status.OK).entity(newPlaylist).build();
    }

    @Operation(
            summary = "Update one or more playlist attributes",
            description = "Update one or more playlist attributes",
            tags = "playlist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Playlist",
                            content = @Content(schema = @Schema(implementation = Playlist.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Updating the playlist attributes failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @PATCH
    public Response updatePlaylistAttributes(@RequestBody Playlist playlist) {
        Playlist newPlaylist = playlistBean.updatePlaylist(playlist);

        return newPlaylist == null? Response.status(Response.Status.BAD_REQUEST).build():
                Response.status(Response.Status.OK).entity(newPlaylist).build();
    }

    @Operation(
            summary = "Delete a playlist",
            description = "Delete playlist by ID",
            tags = "album",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully removed playlist"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Removal of playlist failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @Path("{id}")
    @DELETE
    public Response deleteAlbum(@PathParam("id") int idPlaylist) {
        boolean status = playlistBean.deletePlaylist(idPlaylist);

        return status ? Response.status(Response.Status.OK).build():
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Add a new owner",
            description = "Add a new owner",
            tags = "playlist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Playlist",
                            content = @Content(schema = @Schema(implementation = Playlist.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Could not add owner to playlist",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @POST
    @Path("{id}/owner")
    public Response addOwner(@PathParam("id") int idPlaylist, final User user) {
        Playlist playlist = playlistBean.getPlaylistById(idPlaylist);
        boolean success = playlist.addOwner(user.getId());
        playlist = playlistBean.updatePlaylist(playlist);

        return success ? Response.status(Response.Status.OK).entity(playlist).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Add a new follower",
            description = "Add a new follower",
            tags = "playlist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Playlist",
                            content = @Content(schema = @Schema(implementation = Playlist.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Could not add follower to playlist",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @POST
    @Path("{id}/follower")
    public Response addFollower(@PathParam("id") int idPlaylist, final User user) {
        Playlist playlist = playlistBean.getPlaylistById(idPlaylist);
        boolean success = playlist.addFollower(user.getId());
        playlist = playlistBean.updatePlaylist(playlist);

        return success ? Response.status(Response.Status.OK).entity(playlist).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Remove an owner",
            description = "Remove an owner",
            tags = "playlist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Playlist",
                            content = @Content(schema = @Schema(implementation = Playlist.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Could not remove owner from playlist",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @DELETE
    @Path("{id}/owner")
    public Response removeOwner(@PathParam("id") int idPlaylist, final User user) {
        Playlist playlist = playlistBean.getPlaylistById(idPlaylist);
        boolean success = playlist.removeOwner(user.getId());
        playlist = playlistBean.mergePlaylist(playlist);

        return success ? Response.status(Response.Status.OK).entity(playlist).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Remove a follower",
            description = "Remove a follower",
            tags = "playlist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Playlist",
                            content = @Content(schema = @Schema(implementation = Playlist.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Could not remove owner from playlist",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @DELETE
    @Path("{id}/follower")
    public Response removeFollower(@PathParam("id") int idPlaylist, final User user) {
        Playlist playlist = playlistBean.getPlaylistById(idPlaylist);
        boolean success = playlist.removeFollower(user.getId());
        playlist = playlistBean.mergePlaylist(playlist);

        return success ? Response.status(Response.Status.OK).entity(playlist).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }
}
