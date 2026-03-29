package com.swordfish.lemuroid.lib.library

import com.swordfish.lemuroid.common.graphics.ColorUtils
import com.swordfish.lemuroid.lib.R

fun GameSystem.metaSystemID() = MetaSystemID.fromSystemID(id)

/**
 * Meta systems represent a collection of systems which appear the same to the user.
 * (e.g. Arcade groups FBNeo + MAME2003Plus; Genesis groups MD + SCD + 32X; etc.)
 *
 * Drawable fallbacks: new systems without a dedicated icon reuse the closest visual match.
 */
enum class MetaSystemID(val titleResId: Int, val imageResId: Int, val systemIDs: List<SystemID>) {

    // ── Nintendo ──────────────────────────────────────────────────────────────
    NES(
        R.string.game_system_title_nes,
        R.drawable.game_system_nes,
        listOf(SystemID.NES, SystemID.FDS),
    ),
    SNES(
        R.string.game_system_title_snes,
        R.drawable.game_system_snes,
        listOf(SystemID.SNES),
    ),
    N64(
        R.string.game_system_title_n64,
        R.drawable.game_system_n64,
        listOf(SystemID.N64),
    ),
    GB(
        R.string.game_system_title_gb,
        R.drawable.game_system_gb,
        listOf(SystemID.GB),
    ),
    GBC(
        R.string.game_system_title_gbc,
        R.drawable.game_system_gbc,
        listOf(SystemID.GBC),
    ),
    GBA(
        R.string.game_system_title_gba,
        R.drawable.game_system_gba,
        listOf(SystemID.GBA),
    ),
    NDS(
        R.string.game_system_title_nds,
        R.drawable.game_system_ds,
        listOf(SystemID.NDS),
    ),
    NINTENDO_3DS(
        R.string.game_system_title_3ds,
        R.drawable.game_system_3ds,
        listOf(SystemID.NINTENDO_3DS),
    ),
    VIRTUAL_BOY(
        R.string.game_system_title_vb,
        R.drawable.game_system_nes,           // fallback
        listOf(SystemID.VIRTUAL_BOY),
    ),

    // ── Sega ──────────────────────────────────────────────────────────────────
    SMS(
        R.string.game_system_title_sms,
        R.drawable.game_system_sms,
        listOf(SystemID.SMS, SystemID.SG1000),
    ),
    GENESIS(
        R.string.game_system_title_genesis,
        R.drawable.game_system_genesis,
        listOf(SystemID.GENESIS, SystemID.SEGACD, SystemID.SEGA_32X),
    ),
    GG(
        R.string.game_system_title_gg,
        R.drawable.game_system_gg,
        listOf(SystemID.GG),
    ),
    SATURN(
        R.string.game_system_title_saturn,
        R.drawable.game_system_genesis,       // fallback
        listOf(SystemID.SATURN),
    ),

    // ── Sony ──────────────────────────────────────────────────────────────────
    PSX(
        R.string.game_system_title_psx,
        R.drawable.game_system_psx,
        listOf(SystemID.PSX),
    ),
    PSP(
        R.string.game_system_title_psp,
        R.drawable.game_system_psp,
        listOf(SystemID.PSP),
    ),

    // ── Atari ─────────────────────────────────────────────────────────────────
    ATARI2600(
        R.string.game_system_title_atari2600,
        R.drawable.game_system_atari2600,
        listOf(SystemID.ATARI2600),
    ),
    ATARI5200(
        R.string.game_system_title_atari5200,
        R.drawable.game_system_atari2600,     // fallback
        listOf(SystemID.ATARI5200),
    ),
    ATARI7800(
        R.string.game_system_title_atari7800,
        R.drawable.game_system_atari7800,
        listOf(SystemID.ATARI7800),
    ),
    LYNX(
        R.string.game_system_title_lynx,
        R.drawable.game_system_lynx,
        listOf(SystemID.LYNX),
    ),
    JAGUAR(
        R.string.game_system_title_jaguar,
        R.drawable.game_system_atari7800,     // fallback
        listOf(SystemID.JAGUAR),
    ),

    // ── NEC ───────────────────────────────────────────────────────────────────
    PC_ENGINE(
        R.string.game_system_title_pce,
        R.drawable.game_system_pce,
        listOf(SystemID.PC_ENGINE, SystemID.SUPER_GRAFX, SystemID.PC_ENGINE_CD),
    ),
    PC_FX(
        R.string.game_system_title_pcfx,
        R.drawable.game_system_pce,           // fallback
        listOf(SystemID.PC_FX),
    ),

    // ── SNK ───────────────────────────────────────────────────────────────────
    NGP(
        R.string.game_system_title_ngp,
        R.drawable.game_system_ngp,
        listOf(SystemID.NGP, SystemID.NGC, SystemID.NEO_GEO_CD),
    ),

    // ── Bandai ────────────────────────────────────────────────────────────────
    WS(
        R.string.game_system_title_ws,
        R.drawable.game_system_ws,
        listOf(SystemID.WS, SystemID.WSC),
    ),
    POKEMON_MINI(
        R.string.game_system_title_pokemini,
        R.drawable.game_system_gb,            // fallback
        listOf(SystemID.POKEMON_MINI),
    ),

    // ── Other consoles ────────────────────────────────────────────────────────
    PANASONIC_3DO(
        R.string.game_system_title_3do,
        R.drawable.game_system_psx,           // fallback
        listOf(SystemID.PANASONIC_3DO),
    ),
    COLECOVISION(
        R.string.game_system_title_colecovision,
        R.drawable.game_system_atari2600,     // fallback
        listOf(SystemID.COLECOVISION),
    ),
    INTELLIVISION(
        R.string.game_system_title_intellivision,
        R.drawable.game_system_atari2600,     // fallback
        listOf(SystemID.INTELLIVISION),
    ),
    VECTREX(
        R.string.game_system_title_vectrex,
        R.drawable.game_system_arcade,        // fallback
        listOf(SystemID.VECTREX),
    ),
    ODYSSEY2(
        R.string.game_system_title_odyssey2,
        R.drawable.game_system_atari2600,     // fallback
        listOf(SystemID.ODYSSEY2),
    ),

    // ── Home computers ────────────────────────────────────────────────────────
    DOS(
        R.string.game_system_title_dos,
        R.drawable.game_system_dos,
        listOf(SystemID.DOS),
    ),
    AMIGA(
        R.string.game_system_title_amiga,
        R.drawable.game_system_dos,           // fallback
        listOf(SystemID.AMIGA),
    ),
    C64(
        R.string.game_system_title_c64,
        R.drawable.game_system_dos,           // fallback
        listOf(SystemID.C64),
    ),
    MSX(
        R.string.game_system_title_msx,
        R.drawable.game_system_dos,           // fallback
        listOf(SystemID.MSX, SystemID.MSX2),
    ),
    ZX_SPECTRUM(
        R.string.game_system_title_zxspectrum,
        R.drawable.game_system_dos,           // fallback
        listOf(SystemID.ZX_SPECTRUM),
    ),
    AMSTRAD_CPC(
        R.string.game_system_title_amstradcpc,
        R.drawable.game_system_dos,           // fallback
        listOf(SystemID.AMSTRAD_CPC),
    ),

    // ── Arcade ────────────────────────────────────────────────────────────────
    ARCADE(
        R.string.game_system_title_arcade,
        R.drawable.game_system_arcade,
        listOf(SystemID.FBNEO, SystemID.MAME2003PLUS),
    ),
    ;

    fun color(): Int = ColorUtils.color(ordinal.toFloat() / values().size)

    companion object {
        fun fromSystemID(systemID: SystemID): MetaSystemID = when (systemID) {
            // Nintendo
            SystemID.NES            -> NES
            SystemID.FDS            -> NES
            SystemID.SNES           -> SNES
            SystemID.N64            -> N64
            SystemID.GB             -> GB
            SystemID.GBC            -> GBC
            SystemID.GBA            -> GBA
            SystemID.NDS            -> NDS
            SystemID.NINTENDO_3DS   -> NINTENDO_3DS
            SystemID.VIRTUAL_BOY    -> VIRTUAL_BOY
            // Sega
            SystemID.SG1000         -> SMS
            SystemID.SMS            -> SMS
            SystemID.GENESIS        -> GENESIS
            SystemID.SEGACD         -> GENESIS
            SystemID.SEGA_32X       -> GENESIS
            SystemID.GG             -> GG
            SystemID.SATURN         -> SATURN
            // Sony
            SystemID.PSX            -> PSX
            SystemID.PSP            -> PSP
            // Atari
            SystemID.ATARI2600      -> ATARI2600
            SystemID.ATARI5200      -> ATARI5200
            SystemID.ATARI7800      -> ATARI7800
            SystemID.LYNX           -> LYNX
            SystemID.JAGUAR         -> JAGUAR
            // NEC
            SystemID.PC_ENGINE      -> PC_ENGINE
            SystemID.SUPER_GRAFX    -> PC_ENGINE
            SystemID.PC_ENGINE_CD   -> PC_ENGINE
            SystemID.PC_FX          -> PC_FX
            // SNK
            SystemID.NGP            -> NGP
            SystemID.NGC            -> NGP
            SystemID.NEO_GEO_CD     -> NGP
            // Bandai
            SystemID.WS             -> WS
            SystemID.WSC            -> WS
            SystemID.POKEMON_MINI   -> POKEMON_MINI
            // Other consoles
            SystemID.PANASONIC_3DO  -> PANASONIC_3DO
            SystemID.COLECOVISION   -> COLECOVISION
            SystemID.INTELLIVISION  -> INTELLIVISION
            SystemID.VECTREX        -> VECTREX
            SystemID.ODYSSEY2       -> ODYSSEY2
            // Home computers
            SystemID.DOS            -> DOS
            SystemID.AMIGA          -> AMIGA
            SystemID.C64            -> C64
            SystemID.MSX            -> MSX
            SystemID.MSX2           -> MSX
            SystemID.ZX_SPECTRUM    -> ZX_SPECTRUM
            SystemID.AMSTRAD_CPC    -> AMSTRAD_CPC
            // Arcade
            SystemID.FBNEO          -> ARCADE
            SystemID.MAME2003PLUS   -> ARCADE
        }
    }
}
