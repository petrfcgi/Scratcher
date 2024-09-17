package eu.petrfaruzel.scratcher.features.shared.domain

import eu.petrfaruzel.scratcher.core.data.DataRequestResult
import eu.petrfaruzel.scratcher.features.shared.data.CardDS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

class CardRepository(
    private val cardDS: CardDS,
) {
    suspend fun getScratchCode(): DataRequestResult<String> {
        delay(2.seconds)
        val generatedUUID = UUID.randomUUID().toString()
        setOwnedScratchCode(generatedUUID)
        return DataRequestResult.Success(generatedUUID)
    }

    suspend fun isCardActivated(): Flow<Boolean> = cardDS.isActivated
    suspend fun setCardActivated(isActivated: Boolean?) = cardDS.setIsActivated(isActivated)

    fun getOwnedScratchCode(): Flow<String?> = cardDS.scratchKey
    suspend fun setOwnedScratchCode(code: String?) = cardDS.setScratchKey(code)
}