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
    STELLA_2014(
        "stella2014",
        "Stella 2014",
        "libstella2014_libretro_android.so",
    ),
    ATARI800(
        "atari800",
        "Atari800",
        "libatari800_libretro_android.so",
    ),
    A5200(
        "a5200",
        "a5200",
        "liba5200_libretro_android.so",
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
    BEETLE_LYNX(
        "mednafen_lynx",
        "Beetle Lynx",
        "libmednafen_lynx_libretro_android.so",
    ),
    GEARLYNX(
        "gearlynx",
        "Gearlynx",
        "libgearlynx_libretro_android.so",
    ),
    HOLANI(
        "holani",
        "Holani",
        "libholani_libretro_android.so",
    ),
    VIRTUALJAGUAR(
        "virtualjaguar",
        "Virtual Jaguar",
        "libvirtualjaguar_libretro_android.so",
    ),

    // ── Nintendo NES ─────────────────────────────────────────────────────────
    FCEUMM(
        "fceumm",
        "FCEUmm",
        "libfceumm_libretro_android.so",
    ),
    NESTOPIA(
        "nestopia",
        "Nestopia UE",
        "libnestopia_libretro_android.so",
    ),
    MESEN(
        "mesen",
        "Mesen",
        "libmesen_libretro_android.so",
    ),
    QUICKNES(
        "quicknes",
        "QuickNES",
        "libquicknes_libretro_android.so",
    ),
    BNES(
        "bnes",
        "bNES",
        "libbnes_libretro_android.so",
    ),
    EMUX_NES(
        "emux_nes",
        "Emux NES",
        "libemux_nes_libretro_android.so",
    ),

    // ── Nintendo SNES ────────────────────────────────────────────────────────
    SNES9X(
        "snes9x",
        "Snes9x",
        "libsnes9x_libretro_android.so",
    ),
    SNES9X_2002(
        "snes9x_2002",
        "Snes9x 2002",
        "libsnes9x_2002_libretro_android.so",
    ),
    SNES9X_2005(
        "snes9x_2005",
        "Snes9x 2005",
        "libsnes9x_2005_libretro_android.so",
    ),
    SNES9X_2005_PLUS(
        "snes9x_2005_plus",
        "Snes9x 2005 Plus",
        "libsnes9x_2005_plus_libretro_android.so",
    ),
    SNES9X_2010(
        "snes9x_2010",
        "Snes9x 2010",
        "libsnes9x_2010_libretro_android.so",
    ),
    BSNES(
        "bsnes",
        "bsnes",
        "libbsnes_libretro_android.so",
    ),
    BSNES_JG(
        "bsnes_jg",
        "bsnes-jg",
        "libbsnes_jg_libretro_android.so",
    ),
    BSNES_HD_BETA(
        "bsnes_hd_beta",
        "bsnes-hd beta",
        "libbsnes_hd_beta_libretro_android.so",
    ),
    BSNES_2014_ACCURACY(
        "bsnes_accuracy",
        "bsnes 2014 Accuracy",
        "libbsnes_accuracy_libretro_android.so",
    ),
    BSNES_2014_BALANCED(
        "bsnes_balanced",
        "bsnes 2014 Balanced",
        "libbsnes_balanced_libretro_android.so",
    ),
    BSNES_2014_PERFORMANCE(
        "bsnes_performance",
        "bsnes 2014 Performance",
        "libbsnes_performance_libretro_android.so",
    ),
    BSNES_MERCURY_ACCURACY(
        "bsnes_mercury_accuracy",
        "bsnes-mercury Accuracy",
        "libbsnes_mercury_accuracy_libretro_android.so",
    ),
    BSNES_MERCURY_BALANCED(
        "bsnes_mercury_balanced",
        "bsnes-mercury Balanced",
        "libbsnes_mercury_balanced_libretro_android.so",
    ),
    BSNES_MERCURY_PERFORMANCE(
        "bsnes_mercury_performance",
        "bsnes-mercury Performance",
        "libbsnes_mercury_performance_libretro_android.so",
    ),
    BSNES_CPLUSPLUS98(
        "bsnes_cplusplus98",
        "bsnes C++98 (v085)",
        "libbsnes_cplusplus98_libretro_android.so",
    ),
    BEETLE_BSNES(
        "mednafen_snes",
        "Beetle bsnes",
        "libmednafen_snes_libretro_android.so",
    ),
    BEETLE_SUPAFAUST(
        "mednafen_supafaust",
        "Beetle Supafaust",
        "libmednafen_supafaust_libretro_android.so",
    ),
    HIGAN_ACCURACY(
        "higan_accuracy",
        "higan Accuracy",
        "libhigan_accuracy_libretro_android.so",
    ),
    NSIDE_BALANCED(
        "nside_balanced",
        "nSide Balanced",
        "libnside_balanced_libretro_android.so",
    ),
    MESEN_S(
        "mesen-s",
        "Mesen-S",
        "libmesen_s_libretro_android.so",
    ),

    // ── Nintendo GB / GBC ────────────────────────────────────────────────────
    GAMBATTE(
        "gambatte",
        "Gambatte",
        "libgambatte_libretro_android.so",
    ),
    SAMEBOY(
        "sameboy",
        "SameBoy",
        "libsameboy_libretro_android.so",
    ),
    TGBDUAL(
        "tgbdual",
        "TGB Dual",
        "libtgbdual_libretro_android.so",
    ),
    GEARBOY(
        "gearboy",
        "Gearboy",
        "libgearboy_libretro_android.so",
    ),
    EMUX_GB(
        "emux_gb",
        "Emux GB",
        "libemux_gb_libretro_android.so",
    ),

    // ── Nintendo GBA ─────────────────────────────────────────────────────────
    MGBA(
        "mgba",
        "mGBA",
        "libmgba_libretro_android.so",
    ),
    VBA_NEXT(
        "vba_next",
        "VBA Next",
        "libvba_next_libretro_android.so",
    ),
    VBA_M(
        "vbam",
        "VBA-M",
        "libvbam_libretro_android.so",
    ),
    GPSP(
        "gpsp",
        "gpSP",
        "libgpsp_libretro_android.so",
    ),
    BEETLE_GBA(
        "mednafen_gba",
        "Beetle GBA",
        "libmednafen_gba_libretro_android.so",
    ),
    METEOR(
        "meteor",
        "Meteor",
        "libmeteor_libretro_android.so",
    ),
    TEMPGBA(
        "tempgba",
        "TempGBA",
        "libtempgba_libretro_android.so",
    ),

    // ── Nintendo N64 ─────────────────────────────────────────────────────────
    MUPEN64_PLUS_NEXT(
        "mupen64plus_next_gles3",
        "Mupen64Plus-Next GLES3",
        "libmupen64plus_next_gles3_libretro_android.so",
    ),
    MUPEN64_PLUS_NEXT_GLES2(
        "mupen64plus_next_gles2",
        "Mupen64Plus-Next GLES2",
        "libmupen64plus_next_gles2_libretro_android.so",
    ),
    PARALLEL_N64(
        "parallel_n64",
        "ParaLLEl N64",
        "libparallel_n64_libretro_android.so",
    ),

    // ── Nintendo DS ──────────────────────────────────────────────────────────
    DESMUME(
        "desmume",
        "DeSmuME (Deprecated)",
        "libdesmume_libretro_android.so",
    ),
    DESMUME_2015(
        "desmume2015",
        "DeSmuME 2015",
        "libdesmume2015_libretro_android.so",
    ),
    MELONDS(
        "melonds",
        "melonDS 2021",
        "libmelonds_libretro_android.so",
    ),
    MELONDS_DS(
        "melonds_ds",
        "melonDS DS",
        "libmelonds_ds_libretro_android.so",
    ),

    // ── Nintendo 3DS ─────────────────────────────────────────────────────────
    CITRA(
        "citra",
        "Citra",
        "libcitra_libretro_android.so",
    ),
    CITRA_CANARY(
        "citra_canary",
        "Citra Canary",
        "libcitra_canary_libretro_android.so",
    ),

    // ── Nintendo GameCube / Wii ───────────────────────────────────────────────
    DOLPHIN(
        "dolphin",
        "Dolphin",
        "libdolphin_libretro_android.so",
    ),

    // ── Nintendo other ───────────────────────────────────────────────────────
    POKEMINI(
        "pokemini",
        "PokeMini",
        "libpokemini_libretro_android.so",
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
    GENESIS_PLUS_GX_WIDE(
        "genesis_plus_gx_wide",
        "Genesis Plus GX Wide",
        "libgenesis_plus_gx_wide_libretro_android.so",
    ),
    PICODRIVE(
        "picodrive",
        "PicoDrive",
        "libpicodrive_libretro_android.so",
    ),
    BLASTEM(
        "blastem",
        "BlastEm",
        "libblastem_libretro_android.so",
    ),
    CLOWNMDEMU(
        "clownmdemu",
        "ClownMDEmu",
        "libclownmdemu_libretro_android.so",
    ),
    GEARSYSTEM(
        "gearsystem",
        "Gearsystem",
        "libgearsystem_libretro_android.so",
    ),
    SMS_PLUS_GX(
        "smsplus",
        "SMS Plus GX",
        "libsmsplus_libretro_android.so",
    ),
    EMUX_SMS(
        "emux_sms",
        "Emux SMS",
        "libemux_sms_libretro_android.so",
    ),
    YABAUSE(
        "yabause",
        "Yabause",
        "libyabause_libretro_android.so",
    ),
    YABASANSHIRO(
        "yabasanshiro",
        "YabaSanshiro",
        "libyabasanshiro_libretro_android.so",
    ),
    KRONOS(
        "kronos",
        "Kronos",
        "libkronos_libretro_android.so",
    ),
    BEETLE_SATURN(
        "mednafen_saturn",
        "Beetle Saturn",
        "libmednafen_saturn_libretro_android.so",
    ),
    FLYCAST(
        "flycast",
        "Flycast",
        "libflycast_libretro_android.so",
    ),
    FLYCAST_GLES2(
        "flycast_gles2",
        "Flycast GLES2",
        "libflycast_gles2_libretro_android.so",
    ),

    // ── Sony ─────────────────────────────────────────────────────────────────
    PCSX_REARMED(
        "pcsx_rearmed",
        "PCSX ReARMed",
        "libpcsx_rearmed_libretro_android.so",
    ),
    MEDNAFEN_PSX(
        "mednafen_psx",
        "Beetle PSX",
        "libmednafen_psx_libretro_android.so",
    ),
    MEDNAFEN_PSX_HW(
        "mednafen_psx_hw",
        "Beetle PSX HW",
        "libmednafen_psx_hw_libretro_android.so",
    ),
    DUCKSTATION(
        "duckstation",
        "DuckStation",
        "libduckstation_libretro_android.so",
    ),
    SWANSTATION(
        "swanstation",
        "SwanStation",
        "libswanstation_libretro_android.so",
    ),
    PPSSPP(
        "ppsspp",
        "PPSSPP",
        "libppsspp_libretro_android.so",
    ),
    PLAY(
        "play",
        "Play!",
        "libplay_libretro_android.so",
    ),

    // ── 3DO ──────────────────────────────────────────────────────────────────
    OPERA(
        "opera",
        "Opera",
        "libopera_libretro_android.so",
    ),

    // ── NEC ──────────────────────────────────────────────────────────────────
    MEDNAFEN_PCE_FAST(
        "mednafen_pce_fast",
        "Beetle PCE Fast",
        "libmednafen_pce_fast_libretro_android.so",
    ),
    MEDNAFEN_PCE(
        "mednafen_pce",
        "Beetle PCE",
        "libmednafen_pce_libretro_android.so",
    ),
    MEDNAFEN_SUPERGRAFX(
        "mednafen_supergrafx",
        "Beetle SuperGrafx",
        "libmednafen_supergrafx_libretro_android.so",
    ),
    GEARGRAFX(
        "geargrafx",
        "Geargrafx",
        "libgeargrafx_libretro_android.so",
    ),
    MEDNAFEN_PCFX(
        "mednafen_pcfx",
        "Beetle PC-FX",
        "libmednafen_pcfx_libretro_android.so",
    ),
    QUASI88(
        "quasi88",
        "QUASI88",
        "libquasi88_libretro_android.so",
    ),
    NEKO_PROJECT_II_KAI(
        "nekop2",
        "Neko Project II Kai",
        "libnekop2_libretro_android.so",
    ),

    // ── SNK ──────────────────────────────────────────────────────────────────
    MEDNAFEN_NGP(
        "mednafen_ngp",
        "Beetle NeoPop",
        "libmednafen_ngp_libretro_android.so",
    ),
    RACE(
        "race",
        "RACE",
        "librace_libretro_android.so",
    ),
    NEOCD(
        "neocd",
        "NeoCD",
        "libneocd_libretro_android.so",
    ),
    GEOLITH(
        "geolith",
        "Geolith",
        "libgeolith_libretro_android.so",
    ),

    // ── Bandai ───────────────────────────────────────────────────────────────
    MEDNAFEN_WSWAN(
        "mednafen_wswan",
        "Beetle Cygne",
        "libmednafen_wswan_libretro_android.so",
    ),

    // ── Home computers ───────────────────────────────────────────────────────
    DOSBOX_PURE(
        "dosbox_pure",
        "DOSBox Pure",
        "libdosbox_pure_libretro_android.so",
    ),
    DOSBOX(
        "dosbox",
        "DOSBox",
        "libdosbox_libretro_android.so",
    ),
    DOSBOX_SVN(
        "dosbox_svn",
        "DOSBox-SVN",
        "libdosbox_svn_libretro_android.so",
    ),
    VIRTUALXT(
        "virtualxt",
        "VirtualXT",
        "libvirtualxt_libretro_android.so",
    ),
    PUAE(
        "puae",
        "PUAE",
        "libpuae_libretro_android.so",
    ),
    PUAE_2021(
        "puae2021",
        "PUAE 2021",
        "libpuae2021_libretro_android.so",
    ),
    VICE_X64SC(
        "vice_x64sc",
        "VICE x64sc",
        "libvice_x64sc_libretro_android.so",
    ),
    VICE_X64(
        "vice_x64",
        "VICE x64",
        "libvice_x64_libretro_android.so",
    ),
    VICE_X128(
        "vice_x128",
        "VICE x128",
        "libvice_x128_libretro_android.so",
    ),
    VICE_XVIC(
        "vice_xvic",
        "VICE xvic",
        "libvice_xvic_libretro_android.so",
    ),
    VICE_XPET(
        "vice_xpet",
        "VICE xpet",
        "libvice_xpet_libretro_android.so",
    ),
    VICE_XPLUS4(
        "vice_xplus4",
        "VICE xplus4",
        "libvice_xplus4_libretro_android.so",
    ),
    VICE_XCBM2(
        "vice_xcbm2",
        "VICE xcbm2",
        "libvice_xcbm2_libretro_android.so",
    ),
    VICE_XCBM5X0(
        "vice_xcbm5x0",
        "VICE xcbm5x0",
        "libvice_xcbm5x0_libretro_android.so",
    ),
    VICE_XSCPU64(
        "vice_xscpu64",
        "VICE xscpu64",
        "libvice_xscpu64_libretro_android.so",
    ),
    BLUEMSX(
        "bluemsx",
        "blueMSX",
        "libbluemsx_libretro_android.so",
    ),
    FMSX(
        "fmsx",
        "fMSX",
        "libfmsx_libretro_android.so",
    ),
    FUSE(
        "fuse",
        "Fuse",
        "libfuse_libretro_android.so",
    ),
    EIGHTYONE(
        "81",
        "EightyOne",
        "lib81_libretro_android.so",
    ),
    CAP32(
        "cap32",
        "Caprice32",
        "libcap32_libretro_android.so",
    ),
    CROCODS(
        "crocods",
        "CrocoDS",
        "libcrocods_libretro_android.so",
    ),
    PX68K(
        "px68k",
        "PX68k",
        "libpx68k_libretro_android.so",
    ),
    HATARI(
        "hatari",
        "Hatari",
        "libhatari_libretro_android.so",
    ),

    // ── Other consoles ───────────────────────────────────────────────────────
    GEARCOLECO(
        "gearcoleco",
        "Gearcoleco",
        "libgearcoleco_libretro_android.so",
    ),
    JOLLYCV(
        "jollycv",
        "JollyCV",
        "libjollycv_libretro_android.so",
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
    FREECHAF(
        "freechaf",
        "FreeChaF",
        "libfreechaf_libretro_android.so",
    ),
    SAMEDUCK(
        "sameduck",
        "SameDuck",
        "libsameduck_libretro_android.so",
    ),
    POTATOR(
        "potator",
        "Potator",
        "libpotator_libretro_android.so",
    ),
    THEODORE(
        "theodore",
        "Theodore",
        "libtheodore_libretro_android.so",
    ),

    // ── Arcade ───────────────────────────────────────────────────────────────
    FBNEO(
        "fbneo",
        "FinalBurn Neo",
        "libfbneo_libretro_android.so",
    ),
    MAME2003PLUS(
        "mame2003_plus",
        "MAME 2003-Plus",
        "libmame2003_plus_libretro_android.so",
    ),
    MAME2003(
        "mame2003",
        "MAME 2003",
        "libmame2003_libretro_android.so",
    ),
    MAME2000(
        "mame2000",
        "MAME 2000",
        "libmame2000_libretro_android.so",
    ),
    MAME2010(
        "mame2010",
        "MAME 2010",
        "libmame2010_libretro_android.so",
    ),
    MAME2015(
        "mame2015",
        "MAME 2015",
        "libmame2015_libretro_android.so",
    ),
    MAME2016(
        "mame2016",
        "MAME 2016",
        "libmame2016_libretro_android.so",
    ),
    MAME_CURRENT(
        "mame",
        "MAME (Current)",
        "libmame_libretro_android.so",
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
