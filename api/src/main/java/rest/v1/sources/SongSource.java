package rest.v1.sources;

import beans.SongBean;
import com.kumuluz.ee.logs.cdi.Log;
import core.Song;
import core.SongRating;
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
@Path("song")
@ApplicationScoped
public class SongSource {
    @Inject
    private SongBean songBean;

    @Operation(
            summary = "Get all songs",
            description = "Get all songs",
            tags = "song",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Songs",
                            content = @Content(schema = @Schema(implementation = Song.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No songs in the catalog",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )})
    @GET
    public Response getAllSongs() {
        List<Song> songs = songBean.getSongs();

        return songs == null? Response.status(Response.Status.NOT_FOUND).build():
                Response.status(Response.Status.OK).entity(songs).build();
    }

    @Operation(
            summary = "Get a song by ID",
            description = "Get a song by ID",
            tags = "song",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Song",
                            content = @Content(schema = @Schema(implementation = Song.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No song with ID in the catalog",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            })
    @Path("{id}")
    @GET
    public Response getSongById(@PathParam("id") int idSong) {
        Song song = songBean.getSongById(idSong);
        return song == null ? Response.status(Response.Status.NOT_FOUND).build(): Response.ok(song).build();
    }

    @Operation(
            summary = "Add a new song",
            description = "Add a new song",
            tags = "song",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Song",
                            content = @Content(schema = @Schema(implementation = Song.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Could not add song to catalog",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @POST
    public Response addSong(@RequestBody Song song) {
        Song newSong = songBean.addSong(song);

        return newSong == null? Response.status(Response.Status.BAD_REQUEST).build():
                Response.status(Response.Status.OK).entity(newSong).build();
    }

    @Operation(
            summary = "Update a song entity",
            description = "Update a song entity. If the song does not exist, it is created. " +
                    "The operation is idempotent",
            tags = "song",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Song",
                            content = @Content(schema = @Schema(implementation = Song.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Updating the song failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @PUT
    public Response addOrUpdateSong(@RequestBody Song song) {
        Song newSong = songBean.updateSong(song);

        return newSong == null? Response.status(Response.Status.BAD_REQUEST).build():
                Response.status(Response.Status.OK).entity(newSong).build();
    }

    @Operation(
            summary = "Delete a song",
            description = "Delete song by ID",
            tags = "song",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully removed song"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Removal of song failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @Path("{id}")
    @DELETE
    public Response deleteSong(@PathParam("id") int idSong) {
        boolean status = songBean.deleteSong(idSong);
        return status? Response.status(Response.Status.OK).build():
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Add a new song rating",
            description = "Add a new song rating",
            tags = "rating",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Song rating added",
                            content = @Content(schema = @Schema(implementation = SongRating.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Could not add song rating to catalog",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @Path("{id}/rate")
    @POST
    public Response addSongRating(@PathParam("id") int idSong, @RequestBody SongRating songRating) {
        Song song = songBean.getSongById(idSong);
        boolean newSongRating = song.getSongRatings().add(songRating);
        songBean.updateSong(song);

        return newSongRating ? Response.status(Response.Status.OK).entity(songRating).build()
                : Response.status(Response.Status.BAD_REQUEST).build();

    }

    @Operation(
            summary = "Update a song rating",
            description = "Update a song rating. If the song rating does not exist, it is created. " +
                    "The operation is idempotent",
            tags = "rating",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Song",
                            content = @Content(schema = @Schema(implementation = SongRating.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Updating the song failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @Path("{id}/rate")
    @PUT
    public Response addOrUpdateSongRating(@PathParam("id") int idSong, @RequestBody SongRating songRating) {
        Song song = songBean.getSongById(idSong);
        List<SongRating> songRatings = song.getSongRatings();
        boolean success = false;

        // find if a song rating from current user exists
        for (SongRating sr : songRatings) {
            if (sr.getUserId() == songRating.getUserId()) {
                sr.setRating(songRating.getRating());
                song.setSongRatings(songRatings);
                success = true;
            }
        }
        // if it wasn't found, create it
        if (!success) {
            success = songRatings.add(songRating);
        }

        songBean.updateSong(song);

        return success ? Response.status(Response.Status.OK).entity(songRating).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Delete a song's rating",
            description = "Delete song rating by user ID",
            tags = "rating",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully removed rating"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Removal of rating failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @Path("{id}/rate")
    @DELETE
    public Response deleteSongRating(@PathParam("id") int idSong, @RequestBody SongRating songRating) {
        Song song = songBean.getSongById(idSong);
        List<SongRating> songRatings = song.getSongRatings();

        boolean success = songRatings.removeIf(sr -> sr.getUserId() == songRating.getUserId());
        songBean.updateSong(song);

        return success ? Response.status(Response.Status.OK).build():
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Path("circuit-breaker/{idSong}")
    @GET
    public Response testCircuitBreaker(@PathParam("idSong") Integer idSong) {
        try {
            Song res = songBean.songTestFaultTolerance(idSong);
            return Response.status(Response.Status.OK).entity(res).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
