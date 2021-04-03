package io.sparkled.rest

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.spring.tx.annotation.Transactional
import io.sparkled.model.entity.v2.PlaylistEntity
import io.sparkled.persistence.*
import io.sparkled.persistence.v2.query.playlist.DeletePlaylistSequencesByPlaylistIdQuery
import io.sparkled.persistence.v2.query.playlist.GetPlaylistSequencesByPlaylistIdQuery
import io.sparkled.rest.response.IdResponse
import io.sparkled.viewmodel.ViewModelConverterService
import io.sparkled.viewmodel.playlist.PlaylistViewModel

@Controller("/api/playlists")
open class PlaylistController(
    private val db: DbService,
    private val vm: ViewModelConverterService
) {

    @Get("/")
    @Transactional(readOnly = true)
    open fun getAllPlaylists(): HttpResponse<Any> {
        val playlists = db.getAll<PlaylistEntity>(orderBy = "name")
        return HttpResponse.ok(vm.playlistSearch.toViewModels(playlists))
    }

    @Get("/{id}")
    @Transactional(readOnly = true)
    open fun getPlaylist(id: Int): HttpResponse<Any> {
        val playlist = db.getById<PlaylistEntity>(id)

        return if (playlist != null) {
            val viewModel = vm.playlist.toViewModel(playlist)

            val playlistSequences = db.query(GetPlaylistSequencesByPlaylistIdQuery(id))
                .map(vm.playlistSequence::toViewModel)
            viewModel.setSequences(playlistSequences)
            HttpResponse.ok(viewModel)
        } else {
            HttpResponse.notFound("Playlist not found.")
        }
    }

    @Post("/")
    @Transactional
    open fun createPlaylist(playlistViewModel: PlaylistViewModel): HttpResponse<Any> {
        val playlist = vm.playlist.toModel(playlistViewModel)
        val playlistId = db.insert(playlist).toInt()
        return HttpResponse.ok(IdResponse(playlistId))
    }

    @Put("/{id}")
    @Transactional
    open fun updatePlaylist(id: Int, playlistViewModel: PlaylistViewModel): HttpResponse<Any> {
        playlistViewModel.setId(id) // Prevent client-side ID tampering.

        val playlist = vm.playlist.toModel(playlistViewModel)
        val playlistSequences = playlistViewModel.getSequences()
            .map(vm.playlistSequence::toModel)
            .map { it.copy(playlistId = id) }

        db.update(playlist)
        db.query(DeletePlaylistSequencesByPlaylistIdQuery(id))

        // TODO batch insert.
        playlistSequences.forEach {
            db.insert(it)
        }

        return HttpResponse.ok()
    }

    @Delete("/{id}")
    @Transactional
    open fun deletePlaylist(id: Int): HttpResponse<Any> {
        db.getById<PlaylistEntity>(id)?.let { db.delete(it) }
        return HttpResponse.ok()
    }
}
