package com.swordfish.lemuroid.app.shared.game

import java.io.Serializable

/**
 * Aspect ratio modes applied at the Lemuroid level (not core-provided).
 *
 * - CORE_PROVIDED: the GLRetroView / libretro core handles its own aspect ratio (default behaviour).
 * - STRETCH: the viewport covers the entire GLRetroView surface so the image fills the screen
 *   with no letterboxing from Lemuroid's side.
 * - All other entries: Lemuroid computes a centred viewport of the requested ratio inside the
 *   available game-view area, producing outer letterboxes that are rendered black.
 */
enum class AspectRatioMode(
    /** Localisation key suffix used to look up a display string from resources. */
    val key: String,
    /**
     * The target width-to-height ratio, or null for CORE_PROVIDED / STRETCH which have
     * special handling.
     */
    val ratio: Float?,
) : Serializable {
    CORE_PROVIDED("core_provided", null),
    STRETCH("stretch", null),
    RATIO_4_3("4_3", 4f / 3f),
    RATIO_16_9("16_9", 16f / 9f),
    RATIO_16_10("16_10", 16f / 10f),
    RATIO_3_2("3_2", 3f / 2f),
    RATIO_1_1("1_1", 1f),
    RATIO_21_9("21_9", 21f / 9f),
}
