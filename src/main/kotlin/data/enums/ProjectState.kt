package data.enums

enum class ProjectState(val id: Int) {
    COLLECT_PARTICIPATION(1),
    ACTIVE(2),
    ARCHIVE(3),
    PROCESSING_PARTICIPATION(4),
    UNDER_CONSIDERATION(5),
    DRAFT(6),
    REJECTED(7),
    APPROVED(8)
}