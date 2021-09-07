package com.jetbrains.handson.kmm.shared.cache

import com.jetbrains.handson.kmm.shared.entity.LinksData
import com.jetbrains.handson.kmm.shared.entity.RocketData
import com.jetbrains.handson.kmm.shared.entity.RocketLaunchData

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDataBaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllRockets()
            dbQuery.removeAllLaunches()
        }
    }

    internal fun getAllLaunches(): List<RocketLaunchData> {
        return dbQuery.selectAllLaunchesInfo(::mapLaunchSelecting).executeAsList()
    }

    internal fun createLaunches(launches: List<RocketLaunchData>) {
        dbQuery.transaction {
            launches.forEach {
                val rocket = dbQuery.selectRocketById(it.rocket.id).executeAsOneOrNull()
                if (rocket == null) {
                    insertRocket(it)
                }
                insertLaunch(it)
            }
        }
    }

    private fun insertRocket(launch: RocketLaunchData) {
        dbQuery.insertRocket(
            id = launch.rocket.id,
            name = launch.rocket.name,
            type = launch.rocket.type
        )
    }

    private fun insertLaunch(launch: RocketLaunchData) {
        dbQuery.insertLaunch(
            flightNumber = launch.flightNumber.toLong(),
            missionName = launch.missionName,
            launchYear = launch.launchYear,
            rocketId = launch.rocket.id,
            details = launch.details,
            launchSuccess = launch.launchSuccess ?: false,
            launchDateUTC = launch.launchDateUTC,
            missionPatchUrl = launch.links.missionPatchUrl,
            articleUrl = launch.links.articleUrl
        )
    }

    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String,
        launchYear: Int,
        rocketId: String,
        details: String?,
        launchSuccess: Boolean?,
        launchDateUTC: String,
        missionPatchUrl: String?,
        articleUrl: String?,
        id: String?,
        name: String?,
        type: String?
    ): RocketLaunchData {
        return  RocketLaunchData(
            flightNumber = flightNumber.toInt(),
            missionName = missionName,
            launchYear = launchYear,
            details = details,
            launchDateUTC = launchDateUTC,
            launchSuccess = launchSuccess,
            rocket = RocketData(
                id = rocketId,
                name = name!!,
                type = type!!
            ),
            links = LinksData(
                missionPatchUrl = missionPatchUrl,
                articleUrl = articleUrl
            )
        )
    }
}