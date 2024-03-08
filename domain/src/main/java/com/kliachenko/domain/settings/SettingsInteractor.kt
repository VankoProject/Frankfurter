package com.kliachenko.domain.settings


interface SettingsInteractor {

    suspend fun allCurrencies(): List<String>

    suspend fun availableCurrenciesDestinations(from: String): List<String>

    suspend fun save(from: String, to: String): SaveResult

    class Base(
        private val settingsRepository: SettingsRepository,
        private val freeCountPair: Int,
        private val premiumUserStorage: PremiumUserStorage
    ) : SettingsInteractor {

        override suspend fun allCurrencies(): List<String> {
            return settingsRepository.allCurrencies()
        }

        override suspend fun availableCurrenciesDestinations(from: String): List<String> {
            return settingsRepository.availableCurrenciesDestinations(from)
        }

        override suspend fun save(from: String, to: String): SaveResult =
            if (settingsRepository.savedPairsCount() < freeCountPair
                || premiumUserStorage.isPremium()
            ) {
                settingsRepository.save(from, to)
                SaveResult.Success
            } else {
                SaveResult.RequirePremium
            }
    }
}

interface SaveResult {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapSuccessSave()

        fun mapRequirePremium()
    }

    object RequirePremium : SaveResult {
        override fun map(mapper: Mapper) {
            mapper.mapRequirePremium()
        }
    }

    object Success: SaveResult {
        override fun map(mapper: Mapper) {
            mapper.mapSuccessSave()
        }
    }

}
