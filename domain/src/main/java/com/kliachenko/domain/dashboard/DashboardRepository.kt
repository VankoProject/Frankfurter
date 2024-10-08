package com.kliachenko.domain.dashboard

interface DashboardRepository {

    suspend fun dashboardItems(): DashboardResult

    suspend fun removeItem(from: String, to: String): DashboardResult
}

interface DashboardResult {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapSuccess(items: List<DashBoardItem>)

        fun mapError(message: String)

        fun mapEmpty()
    }

    object Empty : DashboardResult {
        override fun map(mapper: Mapper) {
            mapper.mapEmpty()
        }
    }

    data class Error(private val message: String) : DashboardResult {
        override fun map(mapper: Mapper) {
            mapper.mapError(message = message)
        }
    }

    data class Success(private val items: List<DashBoardItem>) : DashboardResult {
        override fun map(mapper: Mapper) {
            mapper.mapSuccess(items = items)
        }
    }

}