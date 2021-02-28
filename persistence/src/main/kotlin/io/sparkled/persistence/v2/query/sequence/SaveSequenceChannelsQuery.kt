package io.sparkled.persistence.v2.query.sequence

// TODO
// class SaveSequenceChannelsQuery(
//     private val sequence: Sequence,
//     private val sequenceChannels: List<SequenceChannel>,
//     private val objectMapper: ObjectMapper
// ) : DbQuery<Unit> {
//
//     override fun perform(queryFactory: QueryFactory) {
//         val sequenceChannelValidator = SequenceChannelValidator(objectMapper)
//         sequenceChannels.forEach { sc -> sc.setSequenceId(sequence.getId()!!) }
//         sequenceChannels.forEach(sequenceChannelValidator::validate)
//
//         if (uuidAlreadyInUse(queryFactory)) {
//             throw EntityValidationException("Sequence channel already exists on another sequence.")
//         } else {
//             val entityManager = queryFactory.entityManager
//             sequenceChannels.forEach { entityManager.merge(it) }
//             logger.info("Saved {} sequence channel(s) for sequence {}.", sequenceChannels.size, sequence.getId())
//
//             deleteRemovedSequenceChannels(queryFactory)
//         }
//     }
//
//     private fun uuidAlreadyInUse(queryFactory: QueryFactory): Boolean {
//         val uuidsToCheck = sequenceChannels
//             .map(SequenceChannel::getUuid)
//             .toList()
//             .ifEmpty { NO_UUIDS }
//
//         val uuidsInUse = queryFactory.select(sequenceChannel)
//             .from(sequenceChannel)
//             .where(sequenceChannel.sequenceId.ne(sequence.getId()).and(sequenceChannel.uuid.`in`(uuidsToCheck)))
//             .fetchCount()
//         return uuidsInUse > 0
//     }
//
//     private fun deleteRemovedSequenceChannels(queryFactory: QueryFactory) {
//         val uuidsToDelete = getSequenceChannelUuidsToDelete(queryFactory)
//         DeleteSequenceChannelsQuery(uuidsToDelete).perform(queryFactory)
//     }
//
//     private fun getSequenceChannelUuidsToDelete(queryFactory: QueryFactory): List<UUID> {
//         var uuidsToKeep = sequenceChannels.asSequence().map(SequenceChannel::getUuid).toList()
//         uuidsToKeep = if (uuidsToKeep.isEmpty()) NO_UUIDS else uuidsToKeep
//
//         return queryFactory
//             .select(sequenceChannel.uuid)
//             .from(sequenceChannel)
//             .where(sequenceChannel.sequenceId.eq(sequence.getId()).and(sequenceChannel.uuid.notIn(uuidsToKeep)))
//             .fetch()
//     }
//
//     companion object {
//         private val logger = LoggerFactory.getLogger(SaveSequenceChannelsQuery::class.java)
//     }
// }
