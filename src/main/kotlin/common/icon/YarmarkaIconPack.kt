import androidx.compose.ui.graphics.vector.ImageVector
import common.icon.IcProject
import common.icon.IcStudent
import kotlin.collections.List as ____KtList

object YarmarkaIconPack

private var __AllIcons: ____KtList<ImageVector>? = null

val YarmarkaIconPack.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(IcProject, IcStudent)
        return __AllIcons!!
    }
