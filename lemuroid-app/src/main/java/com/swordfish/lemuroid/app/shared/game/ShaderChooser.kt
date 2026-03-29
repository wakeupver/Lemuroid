package com.swordfish.lemuroid.app.shared.game

import android.content.Context
import com.swordfish.lemuroid.app.shared.settings.HDModeQuality
import com.swordfish.lemuroid.app.utils.android.getGLSLVersion
import com.swordfish.lemuroid.lib.library.GameSystem
import com.swordfish.lemuroid.lib.library.SystemID
import com.swordfish.libretrodroid.ShaderConfig
import timber.log.Timber

object ShaderChooser {
    fun getShaderForSystem(
        context: Context,
        hdMode: Boolean,
        requestedHdModeQuality: HDModeQuality,
        screenFilter: String,
        system: GameSystem,
    ): ShaderConfig {
        Timber.i(
            "Choosing shader for this config: screenFilter= $screenFilter hdMode=$hdMode hdModeQuality=$requestedHdModeQuality",
        )
        val hdModeQuality =
            if (context.getGLSLVersion() >= 3) {
                requestedHdModeQuality
            } else {
                HDModeQuality.LOW
            }
        return when {
            hdMode -> getHDShaderForSystem(system, hdModeQuality)
            else ->
                when (screenFilter) {
                    "crt"    -> ShaderConfig.CRT
                    "lcd"    -> ShaderConfig.LCD
                    "smooth" -> ShaderConfig.Default
                    "sharp"  -> ShaderConfig.Sharp
                    else     -> getDefaultShaderForSystem(system)
                }
        }
    }

    private fun getDefaultShaderForSystem(system: GameSystem): ShaderConfig {
        return when (system.id) {
            // ── Nintendo ─────────────────────────────────────────────────────
            SystemID.NES            -> ShaderConfig.CRT
            SystemID.FDS            -> ShaderConfig.CRT
            SystemID.SNES           -> ShaderConfig.CRT
            SystemID.N64            -> ShaderConfig.CRT
            SystemID.GB             -> ShaderConfig.LCD
            SystemID.GBC            -> ShaderConfig.LCD
            SystemID.GBA            -> ShaderConfig.LCD
            SystemID.NDS            -> ShaderConfig.LCD
            SystemID.NINTENDO_3DS   -> ShaderConfig.LCD
            SystemID.VIRTUAL_BOY    -> ShaderConfig.CRT
            // ── Sega ─────────────────────────────────────────────────────────
            SystemID.SG1000         -> ShaderConfig.CRT
            SystemID.SMS            -> ShaderConfig.CRT
            SystemID.GENESIS        -> ShaderConfig.CRT
            SystemID.SEGACD         -> ShaderConfig.CRT
            SystemID.SEGA_32X       -> ShaderConfig.CRT
            SystemID.GG             -> ShaderConfig.LCD
            SystemID.SATURN         -> ShaderConfig.CRT
            // ── Sony ─────────────────────────────────────────────────────────
            SystemID.PSX            -> ShaderConfig.CRT
            SystemID.PSP            -> ShaderConfig.LCD
            // ── Atari ────────────────────────────────────────────────────────
            SystemID.ATARI2600      -> ShaderConfig.CRT
            SystemID.ATARI5200      -> ShaderConfig.CRT
            SystemID.ATARI7800      -> ShaderConfig.CRT
            SystemID.LYNX           -> ShaderConfig.LCD
            SystemID.JAGUAR         -> ShaderConfig.CRT
            // ── NEC ──────────────────────────────────────────────────────────
            SystemID.PC_ENGINE      -> ShaderConfig.CRT
            SystemID.SUPER_GRAFX    -> ShaderConfig.CRT
            SystemID.PC_ENGINE_CD   -> ShaderConfig.CRT
            SystemID.PC_FX          -> ShaderConfig.CRT
            // ── SNK ──────────────────────────────────────────────────────────
            SystemID.NGP            -> ShaderConfig.LCD
            SystemID.NGC            -> ShaderConfig.LCD
            SystemID.NEO_GEO_CD     -> ShaderConfig.CRT
            // ── Bandai ───────────────────────────────────────────────────────
            SystemID.WS             -> ShaderConfig.LCD
            SystemID.WSC            -> ShaderConfig.LCD
            SystemID.POKEMON_MINI   -> ShaderConfig.LCD
            // ── Other consoles ───────────────────────────────────────────────
            SystemID.PANASONIC_3DO  -> ShaderConfig.CRT
            SystemID.COLECOVISION   -> ShaderConfig.CRT
            SystemID.INTELLIVISION  -> ShaderConfig.CRT
            SystemID.VECTREX        -> ShaderConfig.CRT
            SystemID.ODYSSEY2       -> ShaderConfig.CRT
            // ── Home computers ───────────────────────────────────────────────
            SystemID.DOS            -> ShaderConfig.CRT
            SystemID.AMIGA          -> ShaderConfig.CRT
            SystemID.C64            -> ShaderConfig.CRT
            SystemID.MSX            -> ShaderConfig.CRT
            SystemID.MSX2           -> ShaderConfig.CRT
            SystemID.ZX_SPECTRUM    -> ShaderConfig.CRT
            SystemID.AMSTRAD_CPC    -> ShaderConfig.CRT
            // ── Arcade ───────────────────────────────────────────────────────
            SystemID.FBNEO          -> ShaderConfig.CRT
            SystemID.MAME2003PLUS   -> ShaderConfig.CRT
            // ── Sega Dreamcast ───────────────────────────────────────────────
            SystemID.DREAMCAST      -> ShaderConfig.Default
            // ── Nintendo GameCube / Wii ──────────────────────────────────────
            SystemID.GAMECUBE       -> ShaderConfig.Default
            SystemID.WII            -> ShaderConfig.Default
            // ── Sony PS2 ─────────────────────────────────────────────────────
            SystemID.PS2            -> ShaderConfig.Default
            // ── Atari ST ─────────────────────────────────────────────────────
            SystemID.ATARI_ST       -> ShaderConfig.CRT
            SystemID.ATARI_8BIT     -> ShaderConfig.CRT
            // ── NEC PC-88 / PC-98 ────────────────────────────────────────────
            SystemID.PC_88          -> ShaderConfig.CRT
            SystemID.PC_98          -> ShaderConfig.CRT
            // ── SNK Neo Geo AES/MVS ──────────────────────────────────────────
            SystemID.NEO_GEO        -> ShaderConfig.CRT
            // ── Sharp ────────────────────────────────────────────────────────
            SystemID.SHARP_X68000   -> ShaderConfig.CRT
            // ── Sinclair ZX-81 ───────────────────────────────────────────────
            SystemID.ZX81           -> ShaderConfig.CRT
            // ── Fairchild Channel F ──────────────────────────────────────────
            SystemID.FAIRCHILD_CHANNEL_F -> ShaderConfig.CRT
            // ── Mega Duck / Supervision ──────────────────────────────────────
            SystemID.MEGA_DUCK      -> ShaderConfig.LCD
            SystemID.SUPERVISION    -> ShaderConfig.LCD
            // ── Thomson ──────────────────────────────────────────────────────
            SystemID.THOMSON        -> ShaderConfig.CRT
            // ── Commodore extras ─────────────────────────────────────────────
            SystemID.VIC20          -> ShaderConfig.CRT
            SystemID.C128           -> ShaderConfig.CRT
            SystemID.PET            -> ShaderConfig.CRT
        }
    }

    private fun getHDShaderForSystem(
        system: GameSystem,
        hdModeQuality: HDModeQuality,
    ): ShaderConfig {
        return when (hdModeQuality) {
            HDModeQuality.LOW    -> getLowQualityHdMode(system)
            HDModeQuality.MEDIUM -> getMediumQualityHdMode(system)
            HDModeQuality.HIGH   -> getHighQualityHdMode(system)
        }
    }

    private fun getLowQualityHdMode(system: GameSystem): ShaderConfig {
        val upscale8BitsMobile =
            ShaderConfig.CUT(
                blendMinContrastEdge = 0.00f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.85f,
            )
        val upscale8Bits =
            ShaderConfig.CUT(
                blendMinContrastEdge = 0.00f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
            )
        val upscale16BitsMobile =
            ShaderConfig.CUT(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.60f,
                blendMaxSharpness = 0.85f,
            )
        val upscale16Bits =
            ShaderConfig.CUT(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.60f,
                blendMaxSharpness = 0.75f,
            )
        val upscale32Bits =
            ShaderConfig.CUT(
                blendMinContrastEdge = 0.25f,
                blendMaxContrastEdge = 0.75f,
                blendMaxSharpness = 0.75f,
            )
        val modern =
            ShaderConfig.CUT(
                blendMinContrastEdge = 0.25f,
                blendMaxContrastEdge = 0.75f,
                blendMaxSharpness = 0.50f,
            )
        return getConfigForSystem(
            system,
            upscale16BitsMobile,
            upscale8BitsMobile,
            upscale32Bits,
            upscale16Bits,
            upscale8Bits,
            modern,
        )
    }

    private fun getMediumQualityHdMode(system: GameSystem): ShaderConfig {
        val upscale8BitsMobile =
            ShaderConfig.CUT2(
                blendMinContrastEdge = 0.00f,
                blendMaxContrastEdge = 0.30f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.50f,
            )
        val upscale8Bits =
            ShaderConfig.CUT2(
                blendMinContrastEdge = 0.00f,
                blendMaxContrastEdge = 0.30f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.50f,
            )
        val upscale16BitsMobile =
            ShaderConfig.CUT2(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.75f,
            )
        val upscale16Bits =
            ShaderConfig.CUT2(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.25f,
            )
        val upscale32Bits =
            ShaderConfig.CUT2(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.25f,
            )
        val modern =
            ShaderConfig.CUT2(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.50f,
                hardEdgesSearchMaxError = 0.25f,
            )
        return getConfigForSystem(
            system,
            upscale16BitsMobile,
            upscale8BitsMobile,
            upscale32Bits,
            upscale16Bits,
            upscale8Bits,
            modern,
        )
    }

    private fun getHighQualityHdMode(system: GameSystem): ShaderConfig {
        val upscale8BitsMobile =
            ShaderConfig.CUT3(
                blendMinContrastEdge = 0.00f,
                blendMaxContrastEdge = 0.30f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.50f,
            )
        val upscale8Bits =
            ShaderConfig.CUT3(
                blendMinContrastEdge = 0.00f,
                blendMaxContrastEdge = 0.30f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.50f,
            )
        val upscale16BitsMobile =
            ShaderConfig.CUT3(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.25f,
            )
        val upscale16Bits =
            ShaderConfig.CUT3(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.25f,
            )
        val upscale32Bits =
            ShaderConfig.CUT3(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.25f,
            )
        val modern =
            ShaderConfig.CUT3(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.25f,
            )
        return getConfigForSystem(
            system,
            upscale16BitsMobile,
            upscale8BitsMobile,
            upscale32Bits,
            upscale16Bits,
            upscale8Bits,
            modern,
        )
    }

    private fun getConfigForSystem(
        system: GameSystem,
        upscale16BitsMobile: ShaderConfig,
        upscale8BitsMobile: ShaderConfig,
        upscale32Bits: ShaderConfig,
        upscale16Bits: ShaderConfig,
        upscale8Bits: ShaderConfig,
        modern: ShaderConfig,
    ): ShaderConfig {
        return when (system.id) {
            // ── Nintendo ─────────────────────────────────────────────────────
            SystemID.NES            -> upscale8Bits
            SystemID.FDS            -> upscale8Bits
            SystemID.SNES           -> upscale16Bits
            SystemID.N64            -> upscale32Bits
            SystemID.GB             -> upscale8BitsMobile
            SystemID.GBC            -> upscale8BitsMobile
            SystemID.GBA            -> upscale16BitsMobile
            SystemID.NDS            -> upscale32Bits
            SystemID.NINTENDO_3DS   -> modern
            SystemID.VIRTUAL_BOY    -> upscale8Bits
            // ── Sega ─────────────────────────────────────────────────────────
            SystemID.SG1000         -> upscale8Bits
            SystemID.SMS            -> upscale8Bits
            SystemID.GENESIS        -> upscale16Bits
            SystemID.SEGACD         -> upscale16Bits
            SystemID.SEGA_32X       -> upscale32Bits
            SystemID.GG             -> upscale8BitsMobile
            SystemID.SATURN         -> upscale32Bits
            // ── Sony ─────────────────────────────────────────────────────────
            SystemID.PSX            -> upscale32Bits
            SystemID.PSP            -> modern
            // ── Atari ────────────────────────────────────────────────────────
            SystemID.ATARI2600      -> upscale8Bits
            SystemID.ATARI5200      -> upscale8Bits
            SystemID.ATARI7800      -> upscale8Bits
            SystemID.LYNX           -> upscale8BitsMobile
            SystemID.JAGUAR         -> upscale32Bits
            // ── NEC ──────────────────────────────────────────────────────────
            SystemID.PC_ENGINE      -> upscale16Bits
            SystemID.SUPER_GRAFX    -> upscale16Bits
            SystemID.PC_ENGINE_CD   -> upscale16Bits
            SystemID.PC_FX          -> upscale32Bits
            // ── SNK ──────────────────────────────────────────────────────────
            SystemID.NGP            -> upscale8BitsMobile
            SystemID.NGC            -> upscale8BitsMobile
            SystemID.NEO_GEO_CD     -> upscale32Bits
            // ── Bandai ───────────────────────────────────────────────────────
            SystemID.WS             -> upscale16BitsMobile
            SystemID.WSC            -> upscale16BitsMobile
            SystemID.POKEMON_MINI   -> upscale8BitsMobile
            // ── Other consoles ───────────────────────────────────────────────
            SystemID.PANASONIC_3DO  -> upscale32Bits
            SystemID.COLECOVISION   -> upscale8Bits
            SystemID.INTELLIVISION  -> upscale8Bits
            SystemID.VECTREX        -> upscale8Bits
            SystemID.ODYSSEY2       -> upscale8Bits
            // ── Home computers ───────────────────────────────────────────────
            SystemID.DOS            -> upscale32Bits
            SystemID.AMIGA          -> upscale32Bits
            SystemID.C64            -> upscale8Bits
            SystemID.MSX            -> upscale8Bits
            SystemID.MSX2           -> upscale8Bits
            SystemID.ZX_SPECTRUM    -> upscale8Bits
            SystemID.AMSTRAD_CPC    -> upscale8Bits
            // ── Arcade ───────────────────────────────────────────────────────
            SystemID.FBNEO          -> upscale32Bits
            SystemID.MAME2003PLUS   -> upscale32Bits
            // ── Sega Dreamcast ───────────────────────────────────────────────
            SystemID.DREAMCAST      -> modern
            // ── Nintendo GameCube / Wii ──────────────────────────────────────
            SystemID.GAMECUBE       -> modern
            SystemID.WII            -> modern
            // ── Sony PS2 ─────────────────────────────────────────────────────
            SystemID.PS2            -> modern
            // ── Atari ST / 8-bit ─────────────────────────────────────────────
            SystemID.ATARI_ST       -> upscale16Bits
            SystemID.ATARI_8BIT     -> upscale8Bits
            // ── NEC PC-88 / PC-98 ────────────────────────────────────────────
            SystemID.PC_88          -> upscale8Bits
            SystemID.PC_98          -> upscale16Bits
            // ── SNK Neo Geo AES/MVS ──────────────────────────────────────────
            SystemID.NEO_GEO        -> upscale32Bits
            // ── Sharp ────────────────────────────────────────────────────────
            SystemID.SHARP_X68000   -> upscale16Bits
            // ── Sinclair ZX-81 ───────────────────────────────────────────────
            SystemID.ZX81           -> upscale8Bits
            // ── Fairchild Channel F ──────────────────────────────────────────
            SystemID.FAIRCHILD_CHANNEL_F -> upscale8Bits
            // ── Mega Duck / Supervision ──────────────────────────────────────
            SystemID.MEGA_DUCK      -> upscale8BitsMobile
            SystemID.SUPERVISION    -> upscale8BitsMobile
            // ── Thomson ──────────────────────────────────────────────────────
            SystemID.THOMSON        -> upscale8Bits
            // ── Commodore extras ─────────────────────────────────────────────
            SystemID.VIC20          -> upscale8Bits
            SystemID.C128           -> upscale8Bits
            SystemID.PET            -> upscale8Bits
        }
    }
}
