package common.database

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.sql.Connection

object DatabaseConnection {

    private var connectedDatabase: Database? = null

    fun connect() {
        if (connectedDatabase == null) {
            TransactionManager.manager.defaultIsolationLevel =
                Connection.TRANSACTION_SERIALIZABLE
            connectedDatabase = Database.connect(
                url = "jdbc:sqlite:database/StudentDistributionDB.db",
                driver = "org.sqlite.JDBC",
                user = "root",
                password = "root"
            )
        }
        runBlocking {
            newSuspendedTransaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(
                    data.local.entity.Speciality,
                    data.local.entity.Student,
                    data.local.entity.Project,
                    data.local.entity.Participation,
                    data.local.entity.Supervisor,
                    data.local.entity.ProjectSupervisor,
                    data.local.entity.ProjectSpeciality,
                    data.local.entity.GeneratedDistribution,
                )
            }
        }
    }
}