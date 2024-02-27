package com.kliachenko.data.dashboard

import com.kliachenko.domain.dashboard.DashboardRepository
import com.kliachenko.domain.dashboard.DashboardResult

class BaseDashboardRepository: DashboardRepository {

    override suspend fun dashboardItems(): DashboardResult {
        TODO("Not yet implemented")
    }
}