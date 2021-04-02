package io.sparkled.persistence.playlist.impl

import io.sparkled.model.entity.Playlist
import io.sparkled.model.entity.PlaylistSequence
import io.sparkled.model.entity.Sequence
import io.sparkled.model.playlist.PlaylistSummary
import io.sparkled.persistence.QueryFactory
import io.sparkled.persistence.playlist.PlaylistPersistenceService
import io.sparkled.persistence.playlist.impl.query.DeletePlaylistQuery
import io.sparkled.persistence.playlist.impl.query.GetAllPlaylistsQuery
import io.sparkled.persistence.playlist.impl.query.GetPlaylistByIdQuery
import io.sparkled.persistence.playlist.impl.query.GetPlaylistNamesQuery
import io.sparkled.persistence.playlist.impl.query.GetPlaylistSequenceByUuidQuery
import io.sparkled.persistence.playlist.impl.query.GetPlaylistSequencesByPlaylistIdQuery
import io.sparkled.persistence.playlist.impl.query.GetSequenceAtPlaylistIndexQuery
import io.sparkled.persistence.playlist.impl.query.GetSequencesByPlaylistIdQuery
import io.sparkled.persistence.playlist.impl.query.SavePlaylistQuery
import io.sparkled.persistence.playlist.impl.query.SavePlaylistSequencesQuery
import java.util.UUID
import javax.inject.Singleton

@Singleton
class PlaylistPersistenceServiceImpl(private val queryFactory: QueryFactory) : PlaylistPersistenceService {

    override fun getAllPlaylists(): List<Playlist> {
        return GetAllPlaylistsQuery().perform(queryFactory)
    }

    override fun getPlaylistSummaries(): Map<Int, PlaylistSummary> {
        return GetPlaylistSummariesQuery().perform(queryFactory)
    }

    override fun getPlaylistNames(): Map<Int, String> {
        return GetPlaylistNamesQuery().perform(queryFactory)
    }

    override fun getPlaylistById(playlistId: Int): Playlist? {
        return GetPlaylistByIdQuery(playlistId).perform(queryFactory)
    }

    override fun getSequenceAtPlaylistIndex(playlistId: Int, index: Int): Sequence? {
        return GetSequenceAtPlaylistIndexQuery(playlistId, index).perform(queryFactory)
    }

    override fun getSequencesByPlaylistId(playlistId: Int): List<Sequence> {
        return GetSequencesByPlaylistIdQuery(playlistId).perform(queryFactory)
    }

    override fun getPlaylistSequencesByPlaylistId(playlistId: Int): List<PlaylistSequence> {
        return GetPlaylistSequencesByPlaylistIdQuery(playlistId).perform(queryFactory)
    }

    override fun getPlaylistSequenceByUuid(sequenceId: Int, uuid: UUID): PlaylistSequence? {
        return GetPlaylistSequenceByUuidQuery(sequenceId, uuid).perform(queryFactory)
    }

    override fun savePlaylist(playlist: Playlist, playlistSequences: List<PlaylistSequence>) {
        val savedPlaylist = SavePlaylistQuery(playlist).perform(queryFactory)
        SavePlaylistSequencesQuery(savedPlaylist, playlistSequences).perform(queryFactory)
    }

    override fun deletePlaylist(playlistId: Int) {
        DeletePlaylistQuery(playlistId).perform(queryFactory)
    }
}
