package data.repository

import com.grapecity.documents.excel.drawing.b.it
import data.local.dao.DepartmentDao
import data.mapper.departmentResponseToDepartment
import data.remote.api.AdminProjectFairApi
import data.remote.api.OrdinaryProjectFairApi
import domain.model.Department
import domain.repository.DepartmentRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DepartmentRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val departmentDao: DepartmentDao,
    private val projectFairApi: OrdinaryProjectFairApi,
) : DepartmentRepository {

    override val downloadFlow = MutableStateFlow<Float>(0f)

    override fun getDepartments(): Flow<ResultsChange<Department>> {
        return departmentDao.getAll()
    }

    override suspend fun updateDepartment(departments: Department) {
        //departmentDao.update(departments)
    }

    override suspend fun insertDepartment(department: Department) {
        withContext(ioDispatcher) {
            departmentDao.insert(department)
        }
    }

    override suspend fun insertDepartment(departments: List<Department>) {
        withContext(ioDispatcher) {
            departmentDao.insert(departments)
        }
    }

    override suspend fun deleteDepartment(department: Department) {
        departmentDao.delete<Department>(department)
    }

    override suspend fun deleteAllDepartments() {
        departmentDao.deleteAll<Department>()
    }

    override suspend fun uploadDepartments() {
        withContext(ioDispatcher) {
            val departments = projectFairApi.getDepartments().map { departmentResponseToDepartment(it)!! }
            insertDepartment(departments)
            downloadFlow.value = 1f
        }
    }
}