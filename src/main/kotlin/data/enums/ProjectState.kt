package data.enums

enum class ProjectState(val id: Int) {
    COLLECT_PARTICIPATION(1),
    ACTIVE(2),
    UNDER_CONSIDERATION(3),
    ARCHIVE(4),
    PROCESSING_PARTICIPATION(5),
    DRAFT(6),
    REJECTED(7),
    APPROVED(8),
}
