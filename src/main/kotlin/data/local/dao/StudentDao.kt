package data.local.dao

import data.local.dao.base.Dao
import di.Preview
import di.Review
import domain.model.Student
import io.realm.kotlin.Realm
import javax.inject.Inject

class PreviewStudentDao @Inject constructor(
    @Preview realm: Realm
): StudentDao(realm)

class ReviewStudentDao @Inject constructor(
    @Review realm: Realm
): StudentDao(realm)

open class StudentDao(realm: Realm): Dao<Student>(realm)