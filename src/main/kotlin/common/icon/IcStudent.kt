package common.icon

import YarmarkaIconPack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val YarmarkaIconPack.IcStudent: ImageVector
    get() {
        if (studentIcon != null) {
            return studentIcon!!
        }
        studentIcon = Builder(name = "User2", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp,
            viewportWidth = 512.0f, viewportHeight = 512.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, fillAlpha = 0.99f, strokeAlpha
            = 0.99f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(240.5f, 47.5f)
                curveTo(290.16f, 43.58f, 327.99f, 62.58f, 354.0f, 104.5f)
                curveTo(379.2f, 158.43f, 370.04f, 205.6f, 326.5f, 246.0f)
                curveTo(290.32f, 273.92f, 250.99f, 279.25f, 208.5f, 262.0f)
                curveTo(161.08f, 237.35f, 139.58f, 198.18f, 144.0f, 144.5f)
                curveTo(154.34f, 90.33f, 186.5f, 58.0f, 240.5f, 47.5f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, fillAlpha = 0.994f,
                strokeAlpha = 0.994f, strokeLineWidth = 0.0f, strokeLineCap = Butt,
                strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(156.5f, 263.5f)
                curveTo(202.16f, 303.28f, 253.49f, 312.78f, 310.5f, 292.0f)
                curveTo(326.89f, 285.25f, 341.39f, 275.75f, 354.0f, 263.5f)
                curveTo(426.91f, 305.51f, 463.25f, 368.84f, 463.0f, 453.5f)
                curveTo(461.17f, 458.0f, 458.0f, 461.17f, 453.5f, 463.0f)
                curveTo(321.5f, 463.67f, 189.5f, 463.67f, 57.5f, 463.0f)
                curveTo(53.0f, 461.17f, 49.83f, 458.0f, 48.0f, 453.5f)
                curveTo(47.79f, 368.99f, 83.96f, 305.65f, 156.5f, 263.5f)
                close()
            }
        }
            .build()
        return studentIcon!!
    }

private var studentIcon: ImageVector? = null