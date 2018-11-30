package io.sparkled.renderer.effect

import io.sparkled.model.animation.easing.Easing
import io.sparkled.model.animation.easing.EasingTypeCode
import io.sparkled.model.animation.effect.Effect
import io.sparkled.model.animation.effect.EffectTypeCode
import io.sparkled.model.animation.fill.Fill
import io.sparkled.model.animation.fill.FillTypeCode
import io.sparkled.model.animation.param.Param
import io.sparkled.model.animation.param.ParamName
import io.sparkled.model.entity.StageProp
import io.sparkled.util.RenderUtils
import io.sparkled.util.matchers.SparkledMatchers.hasRenderedFrames
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import kotlin.intArrayOf as f

internal class ReverseStagePropRendererTest {

    @Test
    fun can_render_3_led_line_on_reversed_stage_prop() {
        val effect = Effect()
            .setType(EffectTypeCode.LINE)
            .setParams(
                listOf(Param().setName(ParamName.LENGTH).setValue(3))
            )
            .setEasing(
                Easing().setType(EasingTypeCode.LINEAR)
            )
            .setFill(
                Fill()
                    .setType(FillTypeCode.SOLID)
                    .setParams(
                        listOf(Param().setName(ParamName.COLOR).setValue("#ff0000"))
                    )
            )

        val stageProp =
            StageProp().setCode(RenderUtils.PROP_CODE).setUuid(RenderUtils.PROP_UUID).setLedCount(10).setReverse(true)
        val renderedStagePropData = RenderUtils.render(effect, 20, stageProp)

        assertThat(
            renderedStagePropData, hasRenderedFrames(
                f(0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000),
                f(0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0xAE0000),
                f(0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x5E0000, 0xFF0000),
                f(0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x0D0000, 0xFF0000, 0xFF0000),
                f(0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0xBC0000, 0xFF0000, 0xFF0000),
                f(0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x6B0000, 0xFF0000, 0xFF0000, 0x940000),
                f(0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x1B0000, 0xFF0000, 0xFF0000, 0xE40000, 0x000000),
                f(0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0xC90000, 0xFF0000, 0xFF0000, 0x360000, 0x000000),
                f(0x000000, 0x000000, 0x000000, 0x000000, 0x790000, 0xFF0000, 0xFF0000, 0x860000, 0x000000, 0x000000),
                f(0x000000, 0x000000, 0x000000, 0x280000, 0xFF0000, 0xFF0000, 0xD70000, 0x000000, 0x000000, 0x000000),
                f(0x000000, 0x000000, 0x000000, 0xD70000, 0xFF0000, 0xFF0000, 0x280000, 0x000000, 0x000000, 0x000000),
                f(0x000000, 0x000000, 0x860000, 0xFF0000, 0xFF0000, 0x790000, 0x000000, 0x000000, 0x000000, 0x000000),
                f(0x000000, 0x360000, 0xFF0000, 0xFF0000, 0xC90000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000),
                f(0x000000, 0xE40000, 0xFF0000, 0xFF0000, 0x1B0000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000),
                f(0x940000, 0xFF0000, 0xFF0000, 0x6B0000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000),
                f(0xFF0000, 0xFF0000, 0xBC0000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000),
                f(0xFF0000, 0xFF0000, 0x0D0000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000),
                f(0xFF0000, 0x5E0000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000),
                f(0xAE0000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000),
                f(0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000)
            )
        )
    }
}
