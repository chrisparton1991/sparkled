package io.sparkled.persistence.playlist

import io.sparkled.model.entity.Playlist
import io.sparkled.model.entity.PlaylistSequence
import io.sparkled.model.entity.Sequence
import io.sparkled.model.playlist.PlaylistSummary
import java.util.UUID

interface PlaylistPersistenceService {


    fun getSequencesByPlaylistId(playlistId: Int): List<Sequence>

    fun getPlaylistSequencesByPlaylistId(playlistId: Int): List<PlaylistSequence>

    fun getPlaylistSequenceByUuid(sequenceId: Int, uuid: UUID): PlaylistSequence?

    fun savePlaylist(playlist: Playlist, playlistSequences: List<PlaylistSequence>)

    fun deletePlaylist(playlistId: Int)

    fun getPlaylistNames(): Map<Int, String>
}
