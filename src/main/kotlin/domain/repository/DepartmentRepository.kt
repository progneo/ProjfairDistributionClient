package domain.repository

import domain.model.Department
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface DepartmentRepository {
    val downloadFlow: MutableStateFlow<Float>
    fun getDepartments(): Flow<ResultsChange<Department>>
    suspend fun updateDepartment(departments: Department)
    suspend fun insertDepartment(department: Department)
    suspend fun insertDepartment(departments: List<Department>)
    suspend fun deleteDepartment(department: Department)
    suspend fun deleteAllDepartments()
    suspend fun uploadDepartments()
}