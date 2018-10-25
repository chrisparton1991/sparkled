package io.sparkled.renderer.effect

import io.sparkled.renderer.context.RenderContext

class SplitLineEffectRenderer : EffectRenderer() {

    @Override
    fun render(ctx: RenderContext) {
        val start = 0
        val end = ctx.getChannel().getLedCount() - 1
        val middle = end / 2

        val lineRenderer = LineEffectRenderer()
        lineRenderer.renderLine(ctx, start, middle, true)
    }
}