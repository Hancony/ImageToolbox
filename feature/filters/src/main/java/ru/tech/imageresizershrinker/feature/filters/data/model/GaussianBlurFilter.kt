/*
 * ImageToolbox is an image editor for android
 * Copyright (c) 2024 T8RIN (Malik Mukhametzyanov)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/LICENSE-2.0>.
 */

package ru.tech.imageresizershrinker.feature.filters.data.model

import android.graphics.Bitmap
import com.awxkee.aire.Aire
import com.awxkee.aire.GaussianPreciseLevel
import ru.tech.imageresizershrinker.core.domain.model.IntegerSize
import ru.tech.imageresizershrinker.core.domain.transformation.Transformation
import ru.tech.imageresizershrinker.core.filters.domain.model.BlurEdgeMode
import ru.tech.imageresizershrinker.core.filters.domain.model.Filter
import ru.tech.imageresizershrinker.feature.filters.data.utils.toEdgeMode


internal class GaussianBlurFilter(
    override val value: Triple<Float, Float, BlurEdgeMode> = Triple(
        25f,
        10f,
        BlurEdgeMode.Reflect101
    ),
) : Transformation<Bitmap>, Filter.GaussianBlur {

    override val cacheKey: String
        get() = value.hashCode().toString()

    override suspend fun transform(
        input: Bitmap,
        size: IntegerSize,
    ): Bitmap = Aire.gaussianBlur(
        bitmap = input,
        verticalKernelSize = 2 * value.first.toInt() + 1,
        horizontalKernelSize = 2 * value.first.toInt() + 1,
        verticalSigma = value.second,
        horizontalSigma = value.second,
        edgeMode = value.third.toEdgeMode(),
        gaussianPreciseLevel = GaussianPreciseLevel.INTEGRAL
    )

}