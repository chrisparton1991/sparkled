package io.sparkled.rest.service.song

import org.glassfish.jersey.media.multipart.FormDataContentDisposition
import org.glassfish.jersey.media.multipart.FormDataParam

import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.io.IOException
import java.io.InputStream

@Path("/songs")
class SongRestService @Inject
constructor(private val handler: SongRestServiceHandler) {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(IOException::class)
    fun createSong(@FormDataParam("song") songViewModelJson: String,
                   @FormDataParam("mp3") inputStream: InputStream,
                   @FormDataParam("mp3") fileDetail: FormDataContentDisposition): Response {
        return handler.createSong(songViewModelJson, inputStream)
    }

    val allSongs: Response
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        get() = handler.getAllSongs()

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getSong(@PathParam("id") songId: Int): Response {
        return handler.getSong(songId)
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun deleteSong(@PathParam("id") id: Int): Response {
        return handler.deleteSong(id)
    }
}