package data.mapper

import data.dto.InstituteResponse
import domain.model.Institute

fun instituteResponseToInstitute(institute: InstituteResponse): Institute {
    return Institute(
        id = institute.id,
        name = institute.name
    )
}