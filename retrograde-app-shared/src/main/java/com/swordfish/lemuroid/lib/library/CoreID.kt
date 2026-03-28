package com.swordfish.lemuroid.lib.library

import android.content.SharedPreferences
import com.swordfish.lemuroid.lib.core.CoreUpdater
import com.swordfish.lemuroid.lib.core.assetsmanager.NoAssetsManager
import com.swordfish.lemuroid.lib.core.assetsmanager.PPSSPPAssetsManager
import com.swordfish.lemuroid.lib.storage.DirectoriesManager

enum class CoreID(
    val coreName: String,
    val coreDisplayName: String,
    val libretroFileName: String,
) {
    // ── Atari ────────────────────────────────────────────────────────────────
    STELLA(
        "stella",
        "Stella",
        "libstella_libretro_android.so",
    ),
    ATARI800(
        "atari800",
        "Atari800",
        "libatari800_libretro_android.so",
    ),
    PROSYSTEM(
        "prosystem",
        "ProSystem",
        "libprosystem_libretro_android.so",
    ),
    HANDY(
        "handy",
        "Handy",
        "libhandy_libretro_android.so",
    ),
    VIRTUALJAGUAR(
        "virtualjaguar",
        "Virtual Jaguar",
        "libvirtualjaguar_libretro_android.so",
    ),

    // ── Nintendo ─────────────────────────────────────────────────────────────
    FCEUMM(
        "fceumm",
        "FCEUmm",
        "libfceumm_libretro_android.so",
    ),
    SNES9X(
        "snes9x",
        "Snes9x",
        "libsnes9x_libretro_android.so",
    ),
    GAMBATTE(
        "gambatte",
        "Gambatte",
        "libgambatte_libretro_android.so",
    ),
    MGBA(
        "mgba",
        "mGBA",
        "libmgba_libretro_android.so",
    ),
    MUPEN64_PLUS_NEXT(
        "mupen64plus_next_gles3",
        "Mupen64Plus",
        "libmupen64plus_next_gles3_libretro_android.so",
    ),
    DESMUME(
        "desmume",
        "DeSmuME (Deprecated)",
        "libdesmume_libretro_android.so",
    ),
    MELONDS(
        "melonds",
        "MelonDS",
        "libmelonds_libretro_android.so",
    ),
    CITRA(
        "citra",
        "Citra",
        "libcitra_libretro_android.so",
    ),
    MEDNAFEN_VB(
        "mednafen_vb",
        "Beetle VB",
        "libmednafen_vb_libretro_android.so",
    ),

    // ── Sega ─────────────────────────────────────────────────────────────────
    GENESIS_PLUS_GX(
        "genesis_plus_gx",
        "Genesis Plus GX",
        "libgenesis_plus_gx_libretro_android.so",
    ),
    PICODRIVE(
        "picodrive",
        "PicoDrive",
        "libpicodrive_libretro_android.so",
    ),
    YABAUSE(
        "yabause",
        "Yabause",
        "libyabause_libretro_android.so",
    ),

    // ── NEC ──────────────────────────────────────────────────────────────────
    MEDNAFEN_PCE_FAST(
        "mednafen_pce_fast",
        "PCEFast",
        "libmednafen_pce_fast_libretro_android.so",
    ),
    MEDNAFEN_SUPERGRAFX(
        "mednafen_supergrafx",
        "Beetle SuperGrafx",
        "libmednafen_supergrafx_libretro_android.so",
    ),
    MEDNAFEN_PCFX(
        "mednafen_pcfx",
        "Beetle PC-FX",
        "libmednafen_pcfx_libretro_android.so",
    ),

    // ── SNK ──────────────────────────────────────────────────────────────────
    MEDNAFEN_NGP(
        "mednafen_ngp",
        "Mednafen NGP",
        "libmednafen_ngp_libretro_android.so",
    ),
    NEOCD(
        "neocd",
        "NeoCD",
        "libneocd_libretro_android.so",
    ),

    // ── Bandai ───────────────────────────────────────────────────────────────
    MEDNAFEN_WSWAN(
        "mednafen_wswan",
        "Beetle Cygne",
        "libmednafen_wswan_libretro_android.so",
    ),
    POKEMINI(
        "pokemini",
        "PokeMini",
        "libpokemini_libretro_android.so",
    ),

    // ── Sony ─────────────────────────────────────────────────────────────────
    PCSX_REARMED(
        "pcsx_rearmed",
        "PCSXReARMed",
        "libpcsx_rearmed_libretro_android.so",
    ),
    PPSSPP(
        "ppsspp",
        "PPSSPP",
        "libppsspp_libretro_android.so",
    ),

    // ── 3DO ──────────────────────────────────────────────────────────────────
    OPERA(
        "opera",
        "Opera",
        "libopera_libretro_android.so",
    ),

    // ── Home computers ───────────────────────────────────────────────────────
    DOSBOX_PURE(
        "dosbox_pure",
        "DosBox Pure",
        "libdosbox_pure_libretro_android.so",
    ),
    PUAE(
        "puae",
        "PUAE",
        "libpuae_libretro_android.so",
    ),
    VICE_X64SC(
        "vice_x64sc",
        "VICE x64sc",
        "libvice_x64sc_libretro_android.so",
    ),
    BLUEMSX(
        "bluemsx",
        "blueMSX",
        "libbluemsx_libretro_android.so",
    ),
    FUSE(
        "fuse",
        "Fuse",
        "libfuse_libretro_android.so",
    ),
    CAP32(
        "cap32",
        "Cap32",
        "libcap32_libretro_android.so",
    ),

    // ── Other consoles ───────────────────────────────────────────────────────
    GEARCOLECO(
        "gearcoleco",
        "GearColeco",
        "libgearcoleco_libretro_android.so",
    ),
    FREEINTV(
        "freeintv",
        "FreeIntv",
        "libfreeintv_libretro_android.so",
    ),
    VECX(
        "vecx",
        "vecx",
        "libvecx_libretro_android.so",
    ),
    O2EM(
        "o2em",
        "O2EM",
        "libo2em_libretro_android.so",
    ),

    // ── Arcade ───────────────────────────────────────────────────────────────
    FBNEO(
        "fbneo",
        "FBNeo",
        "libfbneo_libretro_android.so",
    ),
    MAME2003PLUS(
        "mame2003_plus",
        "MAME2003 Plus",
        "libmame2003_plus_libretro_android.so",
    ),
    ;

    companion object {
        fun getAssetManager(coreID: CoreID): AssetsManager {
            return when (coreID) {
                PPSSPP -> PPSSPPAssetsManager()
                else -> NoAssetsManager()
            }
        }
    }

    interface AssetsManager {
        suspend fun retrieveAssetsIfNeeded(
            coreUpdaterApi: CoreUpdater.CoreManagerApi,
            directoriesManager: DirectoriesManager,
            sharedPreferences: SharedPreferences,
        )

        suspend fun clearAssets(directoriesManager: DirectoriesManager)
    }
}

fun findByName(query: String): CoreID? = CoreID.values().firstOrNull { it.coreName == query }
