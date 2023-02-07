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

val YarmarkaIconPack.IcProject: ImageVector
    get() {
        if (projectIcon != null) {
            return projectIcon!!
        }
        projectIcon = Builder(
            name = "Rocket2", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp,
            viewportWidth = 512.0f, viewportHeight = 512.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(428.0f, 15.5f)
                curveToRelative(-76.0f, 8.7f, -143.0f, 40.3f, -194.1f, 91.4f)
                curveToRelative(-14.5f, 14.6f, -22.8f, 24.7f, -35.9f, 43.4f)
                curveToRelative(-10.0f, 14.3f, -10.4f, 14.7f, -13.4f, 14.2f)
                curveToRelative(-11.2f, -1.9f, -36.0f, -4.5f, -42.4f, -4.5f)
                curveToRelative(-21.9f, 0.1f, -44.3f, 6.9f, -62.2f, 18.8f)
                curveToRelative(-15.5f, 10.4f, -65.0f, 50.3f, -65.6f, 52.9f)
                curveToRelative(-0.5f, 1.6f, 0.0f, 3.3f, 1.2f, 5.0f)
                curveToRelative(1.1f, 1.4f, 20.4f, 13.8f, 43.0f, 27.6f)
                curveToRelative(22.6f, 13.9f, 42.1f, 25.9f, 43.3f, 26.7f)
                curveToRelative(1.4f, 0.9f, 3.9f, 5.6f, 6.6f, 12.3f)
                curveToRelative(17.8f, 44.4f, 55.8f, 82.5f, 99.4f, 99.8f)
                curveToRelative(5.8f, 2.3f, 11.1f, 4.7f, 11.8f, 5.3f)
                curveToRelative(0.6f, 0.6f, 12.7f, 20.0f, 26.8f, 43.1f)
                curveToRelative(14.1f, 23.1f, 26.6f, 43.0f, 27.7f, 44.3f)
                curveToRelative(1.2f, 1.2f, 3.2f, 2.2f, 4.4f, 2.2f)
                curveToRelative(1.3f, -0.0f, 3.4f, -1.0f, 4.7f, -2.2f)
                curveToRelative(3.0f, -2.8f, 42.1f, -52.4f, 48.3f, -61.2f)
                curveToRelative(12.1f, -17.5f, 18.9f, -37.5f, 20.1f, -59.6f)
                curveToRelative(0.5f, -9.3f, -1.8f, -35.5f, -4.3f, -48.1f)
                curveToRelative(-0.4f, -2.3f, 0.8f, -3.4f, 14.3f, -12.9f)
                curveToRelative(18.6f, -13.0f, 28.8f, -21.4f, 43.3f, -36.0f)
                curveToRelative(42.3f, -42.2f, 71.9f, -96.7f, 85.4f, -157.1f)
                curveToRelative(5.8f, -26.2f, 7.0f, -38.0f, 7.0f, -71.1f)
                curveToRelative(0.1f, -30.7f, 0.1f, -30.7f, -2.2f, -33.0f)
                curveToRelative(-2.2f, -2.3f, -2.6f, -2.3f, -29.0f, -2.5f)
                curveToRelative(-16.8f, -0.1f, -30.9f, 0.3f, -38.2f, 1.2f)
                close()
                moveTo(479.6f, 28.0f)
                lineToRelative(4.1f, -0.0f)
                lineToRelative(0.5f, 9.7f)
                curveToRelative(0.8f, 13.9f, -0.7f, 40.3f, -3.3f, 57.8f)
                curveToRelative(-1.9f, 13.1f, -10.0f, 48.9f, -11.4f, 50.3f)
                curveToRelative(-1.0f, 1.1f, -21.3f, -6.2f, -31.3f, -11.4f)
                curveToRelative(-26.1f, -13.2f, -49.4f, -37.0f, -62.2f, -63.4f)
                curveToRelative(-4.0f, -8.4f, -10.7f, -27.6f, -9.8f, -28.5f)
                curveToRelative(1.0f, -1.0f, 32.4f, -8.6f, 43.3f, -10.4f)
                curveToRelative(19.7f, -3.3f, 43.3f, -5.1f, 58.0f, -4.5f)
                curveToRelative(4.4f, 0.2f, 9.9f, 0.4f, 12.1f, 0.4f)
                close()
                moveTo(364.3f, 78.2f)
                curveToRelative(3.0f, 6.2f, 8.4f, 15.3f, 11.9f, 20.3f)
                curveToRelative(8.1f, 11.5f, 25.8f, 29.2f, 37.3f, 37.3f)
                curveToRelative(11.0f, 7.8f, 27.6f, 16.1f, 41.1f, 20.5f)
                lineToRelative(10.2f, 3.3f)
                lineToRelative(-4.2f, 10.4f)
                curveToRelative(-17.1f, 42.4f, -48.8f, 86.1f, -83.3f, 115.0f)
                curveToRelative(-5.1f, 4.2f, -40.6f, 29.6f, -79.0f, 56.5f)
                curveToRelative(-38.4f, 26.9f, -71.0f, 49.8f, -72.4f, 50.8f)
                curveToRelative(-2.7f, 1.9f, -2.8f, 1.9f, -8.5f, -0.3f)
                curveToRelative(-30.3f, -11.3f, -60.0f, -34.6f, -78.4f, -61.6f)
                curveToRelative(-10.7f, -15.7f, -22.2f, -39.7f, -20.4f, -42.6f)
                curveToRelative(3.3f, -5.3f, 100.8f, -143.8f, 105.2f, -149.3f)
                curveToRelative(3.0f, -3.9f, 12.5f, -14.0f, 21.1f, -22.5f)
                curveToRelative(28.4f, -28.3f, 60.9f, -49.9f, 97.1f, -64.6f)
                lineToRelative(10.5f, -4.2f)
                lineToRelative(3.1f, 9.9f)
                curveToRelative(1.7f, 5.4f, 5.6f, 15.0f, 8.7f, 21.1f)
                close()
                moveTo(163.4f, 175.6f)
                curveToRelative(7.2f, 0.9f, 13.4f, 2.0f, 13.9f, 2.3f)
                curveToRelative(1.0f, 1.0f, -68.1f, 99.6f, -69.7f, 99.5f)
                curveToRelative(-1.0f, -0.1f, -66.0f, -39.3f, -72.7f, -43.8f)
                curveToRelative(-1.4f, -1.0f, 2.0f, -4.1f, 22.3f, -20.1f)
                curveToRelative(28.6f, -22.5f, 29.5f, -23.1f, 37.8f, -27.7f)
                curveToRelative(19.4f, -10.7f, 39.6f, -13.7f, 68.4f, -10.2f)
                close()
                moveTo(336.2f, 347.1f)
                curveToRelative(3.8f, 28.3f, 1.4f, 46.9f, -8.9f, 67.7f)
                curveToRelative(-3.9f, 8.0f, -8.8f, 14.7f, -32.3f, 44.4f)
                curveToRelative(-13.1f, 16.6f, -15.6f, 19.3f, -16.6f, 17.9f)
                curveToRelative(-4.7f, -6.9f, -43.7f, -71.6f, -43.8f, -72.6f)
                curveToRelative(-0.1f, -1.4f, 97.3f, -70.4f, 98.9f, -70.1f)
                curveToRelative(0.5f, 0.2f, 1.7f, 5.8f, 2.7f, 12.7f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(301.5f, 143.9f)
                curveToRelative(-9.1f, 2.6f, -16.3f, 6.9f, -23.6f, 14.1f)
                curveToRelative(-14.7f, 14.5f, -19.7f, 36.7f, -12.7f, 56.1f)
                curveToRelative(13.6f, 37.3f, 61.1f, 48.0f, 88.8f, 20.0f)
                curveToRelative(10.8f, -10.9f, 16.0f, -23.3f, 16.0f, -38.1f)
                curveToRelative(0.0f, -24.7f, -16.7f, -46.3f, -40.6f, -52.5f)
                curveToRelative(-7.6f, -2.0f, -20.3f, -1.8f, -27.9f, 0.4f)
                close()
                moveTo(333.2f, 159.7f)
                curveToRelative(8.5f, 4.0f, 15.1f, 10.6f, 19.1f, 19.1f)
                curveToRelative(3.0f, 6.2f, 3.2f, 7.5f, 3.2f, 17.2f)
                curveToRelative(0.0f, 9.7f, -0.2f, 11.0f, -3.1f, 17.0f)
                curveToRelative(-3.9f, 8.3f, -10.6f, 15.0f, -19.2f, 19.2f)
                curveToRelative(-6.2f, 3.1f, -7.3f, 3.3f, -17.2f, 3.3f)
                curveToRelative(-9.3f, -0.0f, -11.1f, -0.3f, -16.3f, -2.7f)
                curveToRelative(-8.0f, -3.8f, -16.0f, -11.7f, -20.0f, -19.7f)
                curveToRelative(-3.0f, -6.1f, -3.2f, -7.2f, -3.2f, -17.1f)
                curveToRelative(0.0f, -9.9f, 0.2f, -10.9f, 3.3f, -17.2f)
                curveToRelative(7.5f, -15.4f, 20.1f, -22.9f, 37.2f, -22.5f)
                curveToRelative(8.3f, 0.2f, 10.3f, 0.6f, 16.2f, 3.4f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(46.8f, 370.7f)
                curveToRelative(-29.0f, 29.0f, -32.8f, 33.1f, -32.8f, 35.9f)
                curveToRelative(0.0f, 1.8f, 0.8f, 3.8f, 2.0f, 4.9f)
                curveToRelative(4.6f, 4.2f, 5.3f, 3.7f, 40.1f, -31.1f)
                curveToRelative(35.2f, -35.2f, 35.6f, -35.7f, 30.9f, -40.4f)
                curveToRelative(-4.6f, -4.6f, -5.2f, -4.2f, -40.2f, 30.7f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(53.3f, 449.2f)
                curveToRelative(-37.8f, 37.7f, -38.0f, 38.1f, -33.3f, 42.8f)
                curveToRelative(4.7f, 4.7f, 5.1f, 4.5f, 42.8f, -33.3f)
                curveToRelative(37.6f, -37.7f, 37.8f, -37.9f, 32.9f, -42.5f)
                curveToRelative(-4.8f, -4.5f, -4.9f, -4.4f, -42.4f, 33.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(131.6f, 455.9f)
                curveToRelative(-34.8f, 34.8f, -35.3f, 35.5f, -31.1f, 40.1f)
                curveToRelative(1.1f, 1.2f, 3.1f, 2.0f, 4.9f, 2.0f)
                curveToRelative(2.8f, -0.0f, 6.9f, -3.8f, 35.9f, -32.8f)
                curveToRelative(34.9f, -35.0f, 35.3f, -35.6f, 30.7f, -40.2f)
                curveToRelative(-4.7f, -4.7f, -5.2f, -4.3f, -40.4f, 30.9f)
                close()
            }
        }
            .build()
        return projectIcon!!
    }

private var projectIcon: ImageVector? = null
