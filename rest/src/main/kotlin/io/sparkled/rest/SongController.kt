package io.sparkled.rest

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.spring.tx.annotation.Transactional
import io.sparkled.model.entity.v2.SongEntity
import io.sparkled.persistence.*
import io.sparkled.persistence.song.SongPersistenceService
import io.sparkled.rest.response.IdResponse
import io.sparkled.viewmodel.song.SongViewModel
import io.sparkled.viewmodel.song.SongViewModelConverter
import javax.inject.Named
import javax.sql.DataSource

@Controller("/api/songs")
open class SongController(
    private val db: DbService,
    private val songPersistenceService: SongPersistenceService,
    private val songViewModelConverter: SongViewModelConverter,
    private val objectMapper: ObjectMapper,
) {

    @Get("/")
    @Transactional(readOnly = true)
    open fun getAllSongs(): HttpResponse<Any> {
        val songs = db.getAll<SongEntity>(orderBy = "name")
        val viewModels = songs.asSequence().map(songViewModelConverter::toViewModel).toList()
        return HttpResponse.ok(viewModels)
    }

    @Get("/foo")
    @Transactional
    open fun foo(): HttpResponse<Any> {
        val song = db.getById<SongEntity>(1)!!
        db.delete(song)
        val deleted = db.getById<SongEntity>(1)

        var th = true
        if (th) {
            throw RuntimeException()
        }

        return HttpResponse.ok()
    }

    @Get("/{id}")
    @Transactional(readOnly = true)
    open fun getSong(id: Int): HttpResponse<Any> {
        val song = db.getById<SongEntity>(id)

        if (song != null) {
            val viewModel = songViewModelConverter.toViewModel(song)
            return HttpResponse.ok(viewModel)
        }

        return HttpResponse.notFound("Song not found.")
    }

    @Post("/", consumes = [MediaType.MULTIPART_FORM_DATA])
    @Transactional
    open fun createSong(song: String, mp3: CompletedFileUpload): HttpResponse<Any> {
        val songViewModel = objectMapper.readValue(song, SongViewModel::class.java)
        songViewModel.setId(null) // Prevent client-side ID tampering.

        val songModel = songViewModelConverter.toModel(songViewModel)
        val songId = db.insert(songModel)

        return HttpResponse.ok(IdResponse(songId.toInt()))
    }

    @Delete("/{id}")
    @Transactional
    open fun deleteSong(id: Int): HttpResponse<Any> {
        songPersistenceService.deleteSong(id)
        return HttpResponse.ok()
    }
}
