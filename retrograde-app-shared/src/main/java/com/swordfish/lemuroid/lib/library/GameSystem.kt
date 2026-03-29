package com.swordfish.lemuroid.lib.library

import androidx.annotation.StringRes
import com.swordfish.lemuroid.lib.R
import com.swordfish.lemuroid.lib.core.CoreVariable
import java.util.Locale

data class GameSystem(
    val id: SystemID,
    val libretroFullName: String,
    @StringRes val titleResId: Int,
    @StringRes val shortTitleResId: Int,
    val systemCoreConfigs: List<SystemCoreConfig>,
    val uniqueExtensions: List<String>,
    val scanOptions: ScanOptions = ScanOptions(),
    val supportedExtensions: List<String> = uniqueExtensions,
    val hasMultiDiskSupport: Boolean = false,
    val fastForwardSupport: Boolean = true,
    val hasTouchScreen: Boolean = false,
) {
    companion object {

        // ── Shared scan options ───────────────────────────────────────────────
        private val DISC_SCAN = ScanOptions(
            scanByFilename = false,
            scanByUniqueExtension = false,
            scanByPathAndSupportedExtensions = true,
        )
        private val DISC_SCAN_WITH_SERIAL = ScanOptions(
            scanByFilename = false,
            scanByUniqueExtension = false,
            scanByPathAndSupportedExtensions = true,
            scanBySimilarSerial = true,
        )
        private val ARCADE_SCAN = ScanOptions(
            scanByFilename = false,
            scanByUniqueExtension = false,
            scanByPathAndFilename = true,
            scanByPathAndSupportedExtensions = false,
        )

        // ── Shared Genesis Plus GX NTSC filter setting ───────────────────────
        private val GENESIS_NTSC_FILTER = ExposedSetting(
            "genesis_plus_gx_blargg_ntsc_filter",
            R.string.setting_genesis_plus_gx_blargg_ntsc_filter,
            arrayListOf(
                ExposedSetting.Value("disabled",  R.string.value_genesis_plus_gx_blargg_ntsc_filter_disabled),
                ExposedSetting.Value("monochrome", R.string.value_genesis_plus_gx_blargg_ntsc_filter_monochrome),
                ExposedSetting.Value("composite",  R.string.value_genesis_plus_gx_blargg_ntsc_filter_composite),
                ExposedSetting.Value("svideo",     R.string.value_genesis_plus_gx_blargg_ntsc_filter_svideo),
                ExposedSetting.Value("rgb",        R.string.value_genesis_plus_gx_blargg_ntsc_filter_rgb),
            ),
        )
        private val GENESIS_OVERSCAN = ExposedSetting(
            "genesis_plus_gx_overscan",
            R.string.setting_genesis_plus_gx_overscan,
            arrayListOf(
                ExposedSetting.Value("disabled",    R.string.value_genesis_plus_gx_overscan_disabled),
                ExposedSetting.Value("top/bottom",  R.string.value_genesis_plus_gx_overscan_topbottom),
                ExposedSetting.Value("left/right",  R.string.value_genesis_plus_gx_overscan_leftright),
                ExposedSetting.Value("full",        R.string.value_genesis_plus_gx_overscan_full),
            ),
        )
        private val GENESIS_SPRITE_LIMIT = ExposedSetting(
            "genesis_plus_gx_no_sprite_limit",
            R.string.setting_genesis_plus_gx_no_sprite_limit,
        )

        @Suppress("LongMethod")
        private val SYSTEMS = listOf(

            // ──────────────────────────────────────────────────────────────────
            // ATARI
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.ATARI2600,
                "Atari - 2600",
                R.string.game_system_title_atari2600,
                R.string.game_system_abbr_atari2600,
                listOf(SystemCoreConfig(
                    coreID = CoreID.STELLA,
                    exposedSettings = listOf(
                        ExposedSetting("stella_filter", R.string.setting_stella_filter, arrayListOf(
                            ExposedSetting.Value("disabled",       R.string.value_stella_filter_disabled),
                            ExposedSetting.Value("composite",      R.string.value_stella_filter_composite),
                            ExposedSetting.Value("s-video",        R.string.value_stella_filter_svideo),
                            ExposedSetting.Value("rgb",            R.string.value_stella_filter_rgb),
                            ExposedSetting.Value("badly adjusted", R.string.value_stella_filter_badlyadjusted),
                        )),
                        ExposedSetting("stella_crop_hoverscan", R.string.setting_stella_crop_hoverscan),
                    ),
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.ATARI_2600)),
                )),
                uniqueExtensions = listOf("a26"),
            ),

            GameSystem(
                SystemID.ATARI5200,
                "Atari - 5200",
                R.string.game_system_title_atari5200,
                R.string.game_system_abbr_atari5200,
                listOf(SystemCoreConfig(
                    coreID = CoreID.ATARI800,
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.ATARI_2600)),
                )),
                uniqueExtensions = listOf("a52"),
                supportedExtensions = listOf("a52", "xex", "atr"),
            ),

            GameSystem(
                SystemID.ATARI7800,
                "Atari - 7800",
                R.string.game_system_title_atari7800,
                R.string.game_system_abbr_atari7800,
                listOf(SystemCoreConfig(
                    coreID = CoreID.PROSYSTEM,
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.ATARI7800)),
                )),
                uniqueExtensions = listOf("a78"),
                supportedExtensions = listOf("bin"),
            ),

            GameSystem(
                SystemID.LYNX,
                "Atari - Lynx",
                R.string.game_system_title_lynx,
                R.string.game_system_abbr_lynx,
                listOf(SystemCoreConfig(
                    coreID = CoreID.HANDY,
                    requiredBIOSFiles = listOf("lynxboot.img"),
                    exposedSettings = listOf(
                        ExposedSetting("handy_rot", R.string.setting_handy_rot, arrayListOf(
                            ExposedSetting.Value("None", R.string.value_handy_rot_none),
                            ExposedSetting.Value("90",   R.string.value_handy_rot_90),
                            ExposedSetting.Value("270",  R.string.value_handy_rot_270),
                        )),
                    ),
                    defaultSettings = listOf(
                        CoreVariable("handy_rot", "None"),
                        CoreVariable("handy_refresh_rate", "60"),
                    ),
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.LYNX)),
                )),
                uniqueExtensions = listOf("lnx"),
            ),

            GameSystem(
                SystemID.JAGUAR,
                "Atari - Jaguar",
                R.string.game_system_title_jaguar,
                R.string.game_system_abbr_jaguar,
                listOf(SystemCoreConfig(
                    coreID = CoreID.VIRTUALJAGUAR,
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                    statesSupported = false,
                )),
                uniqueExtensions = listOf("j64", "jag"),
                supportedExtensions = listOf("j64", "jag", "rom", "abs", "cof", "bin"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // NINTENDO
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.NES,
                "Nintendo - Nintendo Entertainment System",
                R.string.game_system_title_nes,
                R.string.game_system_abbr_nes,
                listOf(
                    SystemCoreConfig(
                        CoreID.FCEUMM,
                        exposedSettings = listOf(
                            ExposedSetting("fceumm_overscan_h", R.string.setting_fceumm_overscan_h),
                            ExposedSetting("fceumm_overscan_v", R.string.setting_fceumm_overscan_v),
                        ),
                        exposedAdvancedSettings = listOf(
                            ExposedSetting("fceumm_nospritelimit", R.string.setting_fceumm_nospritelimit),
                        ),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                    ),
                    SystemCoreConfig(
                        CoreID.NESTOPIA,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                    ),
                    SystemCoreConfig(
                        CoreID.MESEN,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                    ),
                    SystemCoreConfig(
                        CoreID.QUICKNES,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                    ),
                ),
                uniqueExtensions = listOf("nes"),
            ),

            GameSystem(
                SystemID.FDS,
                "Nintendo - Family Computer Disk System",
                R.string.game_system_title_fds,
                R.string.game_system_abbr_fds,
                listOf(SystemCoreConfig(
                    CoreID.FCEUMM,
                    requiredBIOSFiles = listOf("disksys.rom"),
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                )),
                uniqueExtensions = listOf("fds"),
            ),

            GameSystem(
                SystemID.SNES,
                "Nintendo - Super Nintendo Entertainment System",
                R.string.game_system_title_snes,
                R.string.game_system_abbr_snes,
                listOf(
                    SystemCoreConfig(
                        CoreID.SNES9X,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                    ),
                    SystemCoreConfig(
                        CoreID.SNES9X_2010,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                    ),
                    SystemCoreConfig(
                        CoreID.SNES9X_2005,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                    ),
                    SystemCoreConfig(
                        CoreID.BSNES,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                    ),
                    SystemCoreConfig(
                        CoreID.BSNES_MERCURY_ACCURACY,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                    ),
                    SystemCoreConfig(
                        CoreID.MESEN_S,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                    ),
                ),
                uniqueExtensions = listOf("smc", "sfc"),
            ),

            GameSystem(
                SystemID.GB,
                "Nintendo - Game Boy",
                R.string.game_system_title_gb,
                R.string.game_system_abbr_gb,
                listOf(
                    SystemCoreConfig(
                        CoreID.GAMBATTE,
                        exposedSettings = listOf(
                            ExposedSetting("gambatte_gb_colorization",  R.string.setting_gambatte_gb_colorization),
                            ExposedSetting("gambatte_gb_internal_palette", R.string.setting_gambatte_gb_internal_palette),
                            ExposedSetting("gambatte_mix_frames", R.string.setting_gambatte_mix_frames, arrayListOf(
                                ExposedSetting.Value("disabled",          R.string.value_gambatte_mix_frames_disabled),
                                ExposedSetting.Value("mix",               R.string.value_gambatte_mix_frames_mix),
                                ExposedSetting.Value("lcd_ghosting",      R.string.value_gambatte_mix_frames_lcd_ghosting),
                                ExposedSetting.Value("lcd_ghosting_fast", R.string.value_gambatte_mix_frames_lcd_ghosting_fast),
                            )),
                            ExposedSetting("gambatte_dark_filter_level", R.string.setting_gambatte_dark_filter_level),
                        ),
                        defaultSettings = listOf(
                            CoreVariable("gambatte_gb_colorization",   "internal"),
                            CoreVariable("gambatte_gb_internal_palette", "GB - Pocket"),
                        ),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GB)),
                    ),
                    SystemCoreConfig(
                        CoreID.SAMEBOY,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GB)),
                    ),
                    SystemCoreConfig(
                        CoreID.TGBDUAL,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GB)),
                    ),
                ),
                uniqueExtensions = listOf("gb"),
            ),

            GameSystem(
                SystemID.GBC,
                "Nintendo - Game Boy Color",
                R.string.game_system_title_gbc,
                R.string.game_system_abbr_gbc,
                listOf(
                    SystemCoreConfig(
                        CoreID.GAMBATTE,
                        exposedSettings = listOf(
                            ExposedSetting("gambatte_mix_frames", R.string.setting_gambatte_mix_frames, arrayListOf(
                                ExposedSetting.Value("disabled",          R.string.value_gambatte_mix_frames_disabled),
                                ExposedSetting.Value("mix",               R.string.value_gambatte_mix_frames_mix),
                                ExposedSetting.Value("lcd_ghosting",      R.string.value_gambatte_mix_frames_lcd_ghosting),
                                ExposedSetting.Value("lcd_ghosting_fast", R.string.value_gambatte_mix_frames_lcd_ghosting_fast),
                            )),
                            ExposedSetting("gambatte_gbc_color_correction", R.string.setting_gambatte_gbc_color_correction, arrayListOf(
                                ExposedSetting.Value("disabled", R.string.value_gambatte_gbc_color_correction_disabled),
                                ExposedSetting.Value("always",   R.string.value_gambatte_gbc_color_correction_always),
                            )),
                            ExposedSetting("gambatte_dark_filter_level", R.string.setting_gambatte_dark_filter_level),
                        ),
                        rumbleSupported = true,
                        defaultSettings = listOf(CoreVariable("gambatte_gbc_color_correction", "disabled")),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GB)),
                    ),
                    SystemCoreConfig(
                        CoreID.SAMEBOY,
                        rumbleSupported = true,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GB)),
                    ),
                    SystemCoreConfig(
                        CoreID.TGBDUAL,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GB)),
                    ),
                ),
                uniqueExtensions = listOf("gbc"),
            ),

            GameSystem(
                SystemID.GBA,
                "Nintendo - Game Boy Advance",
                R.string.game_system_title_gba,
                R.string.game_system_abbr_gba,
                listOf(
                    SystemCoreConfig(
                        CoreID.MGBA,
                        exposedSettings = listOf(
                            ExposedSetting("mgba_solar_sensor_level", R.string.setting_mgba_solar_sensor_level),
                            ExposedSetting("mgba_interframe_blending", R.string.setting_mgba_interframe_blending, arrayListOf(
                                ExposedSetting.Value("OFF",               R.string.value_mgba_interframe_blending_off),
                                ExposedSetting.Value("mix",               R.string.value_mgba_interframe_blending_mix),
                                ExposedSetting.Value("lcd_ghosting",      R.string.value_mgba_interframe_blending_lcd_ghosting),
                                ExposedSetting.Value("lcd_ghosting_fast", R.string.value_mgba_interframe_blending_lcd_ghosting_fast),
                            )),
                            ExposedSetting("mgba_frameskip", R.string.setting_mgba_frameskip, arrayListOf(
                                ExposedSetting.Value("disabled", R.string.value_mgba_frameskip_disabled),
                                ExposedSetting.Value("auto",     R.string.value_mgba_frameskip_auto),
                            )),
                            ExposedSetting("mgba_color_correction", R.string.setting_mgba_color_correction, arrayListOf(
                                ExposedSetting.Value("OFF", R.string.value_mgba_color_correction_off),
                                ExposedSetting.Value("GBA", R.string.value_mgba_color_correction_gba),
                            )),
                        ),
                        rumbleSupported = true,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GBA)),
                    ),
                    SystemCoreConfig(
                        CoreID.VBA_NEXT,
                        rumbleSupported = true,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GBA)),
                    ),
                    SystemCoreConfig(
                        CoreID.VBA_M,
                        rumbleSupported = true,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GBA)),
                    ),
                    SystemCoreConfig(
                        CoreID.GPSP,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GBA)),
                    ),
                ),
                uniqueExtensions = listOf("gba"),
            ),

            GameSystem(
                SystemID.N64,
                "Nintendo - Nintendo 64",
                R.string.game_system_title_n64,
                R.string.game_system_abbr_n64,
                listOf(
                    SystemCoreConfig(
                        CoreID.MUPEN64_PLUS_NEXT,
                        exposedSettings = listOf(
                            ExposedSetting("mupen64plus-43screensize", R.string.setting_mupen64plus_43screensize),
                            ExposedSetting("mupen64plus-cpucore", R.string.setting_mupen64plus_cpucore, arrayListOf(
                                ExposedSetting.Value("dynamic_recompiler", R.string.value_mupen64plus_cpucore_dynamicrecompiler),
                                ExposedSetting.Value("pure_interpreter",   R.string.value_mupen64plus_cpucore_pureinterpreter),
                                ExposedSetting.Value("cached_interpreter", R.string.value_mupen64plus_cpucore_cachedinterpreter),
                            )),
                            ExposedSetting("mupen64plus-BilinearMode", R.string.setting_mupen64plus_BilinearMode, arrayListOf(
                                ExposedSetting.Value("standard", R.string.value_mupen64plus_bilinearmode_standard),
                                ExposedSetting.Value("3point",   R.string.value_mupen64plus_bilinearmode_3point),
                            )),
                            ExposedSetting("mupen64plus-pak1", R.string.setting_mupen64plus_pak1, arrayListOf(
                                ExposedSetting.Value("memory", R.string.value_mupen64plus_mupen64plus_pak1_memory),
                                ExposedSetting.Value("rumble", R.string.value_mupen64plus_mupen64plus_pak1_rumble),
                                ExposedSetting.Value("none",   R.string.value_mupen64plus_mupen64plus_pak1_none),
                            )),
                            ExposedSetting("mupen64plus-pak2", R.string.setting_mupen64plus_pak2, arrayListOf(
                                ExposedSetting.Value("none",   R.string.value_mupen64plus_mupen64plus_pak2_none),
                                ExposedSetting.Value("rumble", R.string.value_mupen64plus_mupen64plus_pak2_rumble),
                            )),
                        ),
                        defaultSettings = listOf(
                            CoreVariable("mupen64plus-43screensize", "320x240"),
                            CoreVariable("mupen64plus-FrameDuping",  "True"),
                        ),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.N64)),
                        rumbleSupported = true,
                        skipDuplicateFrames = false,
                    ),
                    SystemCoreConfig(
                        CoreID.PARALLEL_N64,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.N64)),
                        rumbleSupported = true,
                        skipDuplicateFrames = false,
                    ),
                ),
                uniqueExtensions = listOf("n64", "z64"),
            ),

            GameSystem(
                SystemID.NDS,
                "Nintendo - Nintendo DS",
                R.string.game_system_title_nds,
                R.string.game_system_abbr_nds,
                listOf(
                    SystemCoreConfig(
                        CoreID.MELONDS,
                        exposedSettings = listOf(
                            ExposedSetting("melonds_screen_layout1", R.string.setting_melonds_screen_layout, arrayListOf(
                                ExposedSetting.Value("top-bottom",  R.string.value_melonds_screen_layout_topbottom),
                                ExposedSetting.Value("left-right",  R.string.value_melonds_screen_layout_leftright),
                            )),
                            ExposedSetting("melonds_mic_input", R.string.setting_melonds_mic_input, arrayListOf(
                                ExposedSetting.Value("microphone", R.string.value_melonds_mic_input_microphone),
                                ExposedSetting.Value("blow",       R.string.value_melonds_mic_input_blow),
                            )),
                        ),
                        exposedAdvancedSettings = listOf(
                            ExposedSetting("melonds_threaded_renderer", R.string.setting_melonds_threaded_renderer),
                            ExposedSetting("melonds_jit_enable",        R.string.setting_melonds_jit_enable),
                        ),
                        defaultSettings = listOf(
                            CoreVariable("melonds_number_of_screen_layouts", "1"),
                            CoreVariable("melonds_touch_mode",                "Touch"),
                            CoreVariable("melonds_threaded_renderer",         "enabled"),
                        ),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.MELONDS)),
                        statesVersion = 2,
                        supportsMicrophone = true,
                    ),
                    SystemCoreConfig(
                        CoreID.MELONDS_DS,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.MELONDS)),
                        supportsMicrophone = true,
                    ),
                    SystemCoreConfig(
                        CoreID.DESMUME,
                        exposedSettings = listOf(
                            ExposedSetting("desmume_screens_layout", R.string.setting_desmume_screens_layout, arrayListOf(
                                ExposedSetting.Value("top/bottom",  R.string.value_desmume_screens_layout_topbottom),
                                ExposedSetting.Value("left/right",  R.string.value_desmume_screens_layout_leftright),
                            )),
                            ExposedSetting("desmume_frameskip", R.string.setting_desmume_frameskip),
                        ),
                        defaultSettings = listOf(
                            CoreVariable("desmume_pointer_type", "touch"),
                            CoreVariable("desmume_frameskip",    "1"),
                        ),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.DESMUME)),
                        skipDuplicateFrames = false,
                    ),
                    SystemCoreConfig(
                        CoreID.DESMUME_2015,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.DESMUME)),
                        skipDuplicateFrames = false,
                    ),
                ),
                uniqueExtensions = listOf("nds"),
                hasTouchScreen = true,
            ),

            GameSystem(
                SystemID.NINTENDO_3DS,
                "Nintendo - Nintendo 3DS",
                R.string.game_system_title_3ds,
                R.string.game_system_abbr_3ds,
                listOf(
                    SystemCoreConfig(
                        CoreID.CITRA,
                        exposedSettings = listOf(
                            ExposedSetting("citra_layout_option", R.string.setting_citra_layout_option, arrayListOf(
                                ExposedSetting.Value("Default Top-Bottom Screen", R.string.value_citra_layout_option_topbottom),
                                ExposedSetting.Value("Side by Side",              R.string.value_citra_layout_option_sidebyside),
                            )),
                            ExposedSetting("citra_resolution_factor",  R.string.setting_citra_resolution_factor),
                            ExposedSetting("citra_use_acc_mul",         R.string.setting_citra_use_acc_mul),
                            ExposedSetting("citra_use_acc_geo_shaders", R.string.setting_citra_use_acc_geo_shaders),
                        ),
                        defaultSettings = listOf(
                            CoreVariable("citra_use_acc_mul",           "disabled"),
                            CoreVariable("citra_touch_touchscreen",     "enabled"),
                            CoreVariable("citra_mouse_touchscreen",     "disabled"),
                            CoreVariable("citra_render_touchscreen",    "disabled"),
                            CoreVariable("citra_use_hw_shader_cache",   "disabled"),
                        ),
                        statesSupported = false,
                        supportsLibretroVFS = true,
                        supportedOnlyArchitectures = setOf("arm64-v8a"),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NINTENDO_3DS)),
                    ),
                    SystemCoreConfig(
                        CoreID.CITRA_CANARY,
                        statesSupported = false,
                        supportsLibretroVFS = true,
                        supportedOnlyArchitectures = setOf("arm64-v8a"),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NINTENDO_3DS)),
                    ),
                ),
                uniqueExtensions = listOf("3ds"),
                hasTouchScreen = true,
            ),

            GameSystem(
                SystemID.VIRTUAL_BOY,
                "Nintendo - Virtual Boy",
                R.string.game_system_title_vb,
                R.string.game_system_abbr_vb,
                listOf(SystemCoreConfig(
                    CoreID.MEDNAFEN_VB,
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                )),
                uniqueExtensions = listOf("vb", "vboy"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // SEGA
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.SG1000,
                "Sega - SG-1000",
                R.string.game_system_title_sg1000,
                R.string.game_system_abbr_sg1000,
                listOf(SystemCoreConfig(
                    CoreID.GENESIS_PLUS_GX,
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SMS)),
                )),
                uniqueExtensions = listOf("sg"),
            ),

            GameSystem(
                SystemID.SMS,
                "Sega - Master System - Mark III",
                R.string.game_system_title_sms,
                R.string.game_system_abbr_sms,
                listOf(
                    SystemCoreConfig(
                        CoreID.GENESIS_PLUS_GX,
                        exposedSettings = listOf(GENESIS_NTSC_FILTER),
                        exposedAdvancedSettings = listOf(GENESIS_SPRITE_LIMIT, GENESIS_OVERSCAN),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SMS)),
                    ),
                    SystemCoreConfig(
                        CoreID.GEARSYSTEM,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SMS)),
                    ),
                    SystemCoreConfig(
                        CoreID.SMS_PLUS_GX,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SMS)),
                    ),
                    SystemCoreConfig(
                        CoreID.PICODRIVE,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SMS)),
                    ),
                ),
                uniqueExtensions = listOf("sms"),
            ),

            GameSystem(
                SystemID.GENESIS,
                "Sega - Mega Drive - Genesis",
                R.string.game_system_title_genesis,
                R.string.game_system_abbr_genesis,
                listOf(
                    SystemCoreConfig(
                        CoreID.GENESIS_PLUS_GX,
                        exposedSettings = listOf(GENESIS_NTSC_FILTER),
                        exposedAdvancedSettings = listOf(GENESIS_SPRITE_LIMIT, GENESIS_OVERSCAN),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            1 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            2 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            3 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                        ),
                    ),
                    SystemCoreConfig(
                        CoreID.GENESIS_PLUS_GX_WIDE,
                        exposedSettings = listOf(GENESIS_NTSC_FILTER),
                        exposedAdvancedSettings = listOf(GENESIS_SPRITE_LIMIT, GENESIS_OVERSCAN),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            1 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            2 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            3 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                        ),
                    ),
                    SystemCoreConfig(
                        CoreID.PICODRIVE,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            1 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                        ),
                    ),
                ),
                uniqueExtensions = listOf("gen", "smd", "md"),
            ),

            GameSystem(
                SystemID.SEGACD,
                "Sega - Mega-CD - Sega CD",
                R.string.game_system_title_scd,
                R.string.game_system_abbr_scd,
                listOf(
                    SystemCoreConfig(
                        CoreID.GENESIS_PLUS_GX,
                        exposedSettings = listOf(GENESIS_NTSC_FILTER),
                        exposedAdvancedSettings = listOf(GENESIS_SPRITE_LIMIT, GENESIS_OVERSCAN),
                        regionalBIOSFiles = mapOf(
                            "Europe" to "bios_CD_E.bin",
                            "Japan"  to "bios_CD_J.bin",
                            "USA"    to "bios_CD_U.bin",
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            1 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            2 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            3 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                        ),
                    ),
                    SystemCoreConfig(
                        CoreID.GENESIS_PLUS_GX_WIDE,
                        exposedSettings = listOf(GENESIS_NTSC_FILTER),
                        exposedAdvancedSettings = listOf(GENESIS_SPRITE_LIMIT, GENESIS_OVERSCAN),
                        regionalBIOSFiles = mapOf(
                            "Europe" to "bios_CD_E.bin",
                            "Japan"  to "bios_CD_J.bin",
                            "USA"    to "bios_CD_U.bin",
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            1 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            2 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            3 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                        ),
                    ),
                    SystemCoreConfig(
                        CoreID.PICODRIVE,
                        regionalBIOSFiles = mapOf(
                            "Europe" to "bios_CD_E.bin",
                            "Japan"  to "bios_CD_J.bin",
                            "USA"    to "bios_CD_U.bin",
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                            1 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                        ),
                    ),
                ),
                scanOptions = DISC_SCAN_WITH_SERIAL,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("cue", "iso", "chd"),
            ),

            GameSystem(
                SystemID.SEGA_32X,
                "Sega - 32X",
                R.string.game_system_title_32x,
                R.string.game_system_abbr_32x,
                listOf(SystemCoreConfig(
                    CoreID.PICODRIVE,
                    controllerConfigs = hashMapOf(
                        0 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                        1 to arrayListOf(ControllerConfigs.GENESIS_3, ControllerConfigs.GENESIS_6),
                    ),
                )),
                uniqueExtensions = listOf("32x"),
                supportedExtensions = listOf("32x", "bin"),
            ),

            GameSystem(
                SystemID.GG,
                "Sega - Game Gear",
                R.string.game_system_title_gg,
                R.string.game_system_abbr_gg,
                listOf(
                    SystemCoreConfig(
                        CoreID.GENESIS_PLUS_GX,
                        exposedSettings = listOf(
                            ExposedSetting("genesis_plus_gx_lcd_filter", R.string.setting_genesis_plus_gx_lcd_filter),
                        ),
                        exposedAdvancedSettings = listOf(GENESIS_SPRITE_LIMIT),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GG)),
                    ),
                    SystemCoreConfig(
                        CoreID.GEARSYSTEM,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GG)),
                    ),
                    SystemCoreConfig(
                        CoreID.SMS_PLUS_GX,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GG)),
                    ),
                ),
                uniqueExtensions = listOf("gg"),
            ),

            GameSystem(
                SystemID.SATURN,
                "Sega - Saturn",
                R.string.game_system_title_saturn,
                R.string.game_system_abbr_saturn,
                listOf(
                    SystemCoreConfig(
                        CoreID.YABAUSE,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GENESIS_6),
                            1 to arrayListOf(ControllerConfigs.GENESIS_6),
                        ),
                        statesSupported = false,
                        skipDuplicateFrames = false,
                    ),
                    SystemCoreConfig(
                        CoreID.YABASANSHIRO,
                        requiredBIOSFiles = listOf("saturn_bios.bin"),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GENESIS_6),
                            1 to arrayListOf(ControllerConfigs.GENESIS_6),
                        ),
                        statesSupported = false,
                        skipDuplicateFrames = false,
                    ),
                    SystemCoreConfig(
                        CoreID.KRONOS,
                        requiredBIOSFiles = listOf("saturn_bios.bin"),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GENESIS_6),
                            1 to arrayListOf(ControllerConfigs.GENESIS_6),
                        ),
                        statesSupported = false,
                        skipDuplicateFrames = false,
                    ),
                ),
                scanOptions = DISC_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("cue", "iso", "chd", "bin"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // SONY
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.PSX,
                "Sony - PlayStation",
                R.string.game_system_title_psx,
                R.string.game_system_abbr_psx,
                listOf(
                    SystemCoreConfig(
                        CoreID.PCSX_REARMED,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.PSX_STANDARD, ControllerConfigs.PSX_DUALSHOCK),
                            1 to arrayListOf(ControllerConfigs.PSX_STANDARD, ControllerConfigs.PSX_DUALSHOCK),
                            2 to arrayListOf(ControllerConfigs.PSX_STANDARD, ControllerConfigs.PSX_DUALSHOCK),
                            3 to arrayListOf(ControllerConfigs.PSX_STANDARD, ControllerConfigs.PSX_DUALSHOCK),
                        ),
                        exposedSettings = listOf(
                            ExposedSetting("pcsx_rearmed_frameskip", R.string.setting_pcsx_rearmed_frameskip),
                        ),
                        exposedAdvancedSettings = listOf(
                            ExposedSetting("pcsx_rearmed_drc", R.string.setting_pcsx_rearmed_drc),
                        ),
                        defaultSettings = listOf(CoreVariable("pcsx_rearmed_drc", "disabled")),
                        rumbleSupported = true,
                        supportsLibretroVFS = true,
                        skipDuplicateFrames = false,
                    ),
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_PSX,
                        requiredBIOSFiles = listOf("scph5500.bin", "scph5501.bin", "scph5502.bin"),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.PSX_STANDARD, ControllerConfigs.PSX_DUALSHOCK),
                            1 to arrayListOf(ControllerConfigs.PSX_STANDARD, ControllerConfigs.PSX_DUALSHOCK),
                        ),
                        rumbleSupported = true,
                        skipDuplicateFrames = false,
                    ),
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_PSX_HW,
                        requiredBIOSFiles = listOf("scph5500.bin", "scph5501.bin", "scph5502.bin"),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.PSX_STANDARD, ControllerConfigs.PSX_DUALSHOCK),
                            1 to arrayListOf(ControllerConfigs.PSX_STANDARD, ControllerConfigs.PSX_DUALSHOCK),
                        ),
                        rumbleSupported = true,
                        skipDuplicateFrames = false,
                    ),
                    SystemCoreConfig(
                        CoreID.DUCKSTATION,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.PSX_STANDARD, ControllerConfigs.PSX_DUALSHOCK),
                            1 to arrayListOf(ControllerConfigs.PSX_STANDARD, ControllerConfigs.PSX_DUALSHOCK),
                        ),
                        rumbleSupported = true,
                        supportsLibretroVFS = true,
                        skipDuplicateFrames = false,
                    ),
                ),
                scanOptions = DISC_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("iso", "pbp", "chd", "cue", "m3u"),
                hasMultiDiskSupport = true,
            ),

            GameSystem(
                SystemID.PSP,
                "Sony - PlayStation Portable",
                R.string.game_system_title_psp,
                R.string.game_system_abbr_psp,
                listOf(SystemCoreConfig(
                    CoreID.PPSSPP,
                    defaultSettings = listOf(CoreVariable("ppsspp_frame_duplication", "enabled")),
                    exposedSettings = listOf(
                        ExposedSetting("ppsspp_auto_frameskip", R.string.setting_ppsspp_auto_frameskip),
                        ExposedSetting("ppsspp_frameskip",      R.string.setting_mgba_frameskip),
                    ),
                    exposedAdvancedSettings = listOf(
                        ExposedSetting("ppsspp_cpu_core", R.string.setting_ppsspp_cpu_core, arrayListOf(
                            ExposedSetting.Value("JIT",         R.string.value_ppsspp_cpu_core_jit),
                            ExposedSetting.Value("IR JIT",      R.string.value_ppsspp_cpu_core_irjit),
                            ExposedSetting.Value("Interpreter", R.string.value_ppsspp_cpu_core_interpreter),
                        )),
                        ExposedSetting("ppsspp_internal_resolution",   R.string.setting_ppsspp_internal_resolution),
                        ExposedSetting("ppsspp_texture_scaling_level", R.string.setting_ppsspp_texture_scaling_level),
                    ),
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.PSP)),
                    supportsLibretroVFS = true,
                )),
                scanOptions = DISC_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("iso", "cso", "pbp"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // NEC
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.PC_ENGINE,
                "NEC - PC Engine - TurboGrafx 16",
                R.string.game_system_title_pce,
                R.string.game_system_abbr_pce,
                listOf(
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_PCE_FAST,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.PCE)),
                    ),
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_PCE,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.PCE)),
                    ),
                ),
                uniqueExtensions = listOf("pce"),
                supportedExtensions = listOf("pce", "bin"),
            ),

            GameSystem(
                SystemID.SUPER_GRAFX,
                "NEC - PC Engine SuperGrafx",
                R.string.game_system_title_sgfx,
                R.string.game_system_abbr_sgfx,
                listOf(SystemCoreConfig(
                    CoreID.MEDNAFEN_SUPERGRAFX,
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.PCE)),
                )),
                uniqueExtensions = listOf("sgx"),
            ),

            GameSystem(
                SystemID.PC_ENGINE_CD,
                "NEC - PC Engine CD - TurboGrafx-CD",
                R.string.game_system_title_pcecd,
                R.string.game_system_abbr_pcecd,
                listOf(
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_PCE_FAST,
                        requiredBIOSFiles = listOf("syscard3.pce"),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.PCE)),
                    ),
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_PCE,
                        requiredBIOSFiles = listOf("syscard3.pce"),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.PCE)),
                    ),
                ),
                scanOptions = DISC_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("cue", "chd", "iso"),
            ),

            GameSystem(
                SystemID.PC_FX,
                "NEC - PC-FX",
                R.string.game_system_title_pcfx,
                R.string.game_system_abbr_pcfx,
                listOf(SystemCoreConfig(
                    CoreID.MEDNAFEN_PCFX,
                    requiredBIOSFiles = listOf("pcfx.rom"),
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                    statesSupported = false,
                    skipDuplicateFrames = false,
                )),
                scanOptions = DISC_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("cue", "chd", "toc", "ccd"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // SNK
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.NGP,
                "SNK - Neo Geo Pocket",
                R.string.game_system_title_ngp,
                R.string.game_system_abbr_ngp,
                listOf(
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_NGP,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NGP)),
                    ),
                    SystemCoreConfig(
                        CoreID.RACE,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NGP)),
                    ),
                ),
                uniqueExtensions = listOf("ngp"),
            ),

            GameSystem(
                SystemID.NGC,
                "SNK - Neo Geo Pocket Color",
                R.string.game_system_title_ngc,
                R.string.game_system_abbr_ngc,
                listOf(
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_NGP,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NGP)),
                    ),
                    SystemCoreConfig(
                        CoreID.RACE,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NGP)),
                    ),
                ),
                uniqueExtensions = listOf("ngc"),
            ),

            GameSystem(
                SystemID.NEO_GEO_CD,
                "SNK - Neo Geo CD",
                R.string.game_system_title_neocd,
                R.string.game_system_abbr_neocd,
                listOf(SystemCoreConfig(
                    CoreID.NEOCD,
                    requiredBIOSFiles = listOf("neocd.bin"),
                    controllerConfigs = hashMapOf(
                        0 to arrayListOf(ControllerConfigs.FB_NEO_4, ControllerConfigs.FB_NEO_6),
                    ),
                    skipDuplicateFrames = false,
                )),
                scanOptions = DISC_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("cue", "chd", "iso"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // BANDAI
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.WS,
                "Bandai - WonderSwan",
                R.string.game_system_title_ws,
                R.string.game_system_abbr_ws,
                listOf(SystemCoreConfig(
                    CoreID.MEDNAFEN_WSWAN,
                    exposedSettings = listOf(
                        ExposedSetting("wswan_rotate_display", R.string.setting_wswan_rotate_display, arrayListOf(
                            ExposedSetting.Value("landscape", R.string.value_wswan_rotate_display_landscape),
                            ExposedSetting.Value("portrait",  R.string.value_wswan_rotate_display_portrait),
                        )),
                        ExposedSetting("wswan_mono_palette", R.string.setting_wswan_mono_palette),
                    ),
                    defaultSettings = listOf(
                        CoreVariable("wswan_rotate_display", "landscape"),
                        CoreVariable("wswan_mono_palette",   "wonderswan"),
                    ),
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.WS_LANDSCAPE, ControllerConfigs.WS_PORTRAIT)),
                )),
                uniqueExtensions = listOf("ws"),
            ),

            GameSystem(
                SystemID.WSC,
                "Bandai - WonderSwan Color",
                R.string.game_system_title_wsc,
                R.string.game_system_abbr_wsc,
                listOf(SystemCoreConfig(
                    CoreID.MEDNAFEN_WSWAN,
                    exposedSettings = listOf(
                        ExposedSetting("wswan_rotate_display", R.string.setting_wswan_rotate_display, arrayListOf(
                            ExposedSetting.Value("landscape", R.string.value_wswan_rotate_display_landscape),
                            ExposedSetting.Value("portrait",  R.string.value_wswan_rotate_display_portrait),
                        )),
                    ),
                    defaultSettings = listOf(CoreVariable("wswan_rotate_display", "landscape")),
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.WS_LANDSCAPE, ControllerConfigs.WS_PORTRAIT)),
                )),
                uniqueExtensions = listOf("wsc"),
            ),

            GameSystem(
                SystemID.POKEMON_MINI,
                "Nintendo - Pokemon Mini",
                R.string.game_system_title_pokemini,
                R.string.game_system_abbr_pokemini,
                listOf(SystemCoreConfig(
                    CoreID.POKEMINI,
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GB)),
                )),
                uniqueExtensions = listOf("min"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // 3DO
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.PANASONIC_3DO,
                "The 3DO Company - 3DO",
                R.string.game_system_title_3do,
                R.string.game_system_abbr_3do,
                listOf(SystemCoreConfig(
                    CoreID.OPERA,
                    requiredBIOSFiles = listOf("panafz10.bin"),
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                    statesSupported = false,
                    skipDuplicateFrames = false,
                )),
                scanOptions = DISC_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("iso", "bin", "chd", "cue"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // OTHER CONSOLES
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.COLECOVISION,
                "Coleco - ColecoVision",
                R.string.game_system_title_colecovision,
                R.string.game_system_abbr_colecovision,
                listOf(SystemCoreConfig(
                    CoreID.GEARCOLECO,
                    requiredBIOSFiles = listOf("colecovision.rom"),
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.ATARI_2600)),
                )),
                uniqueExtensions = listOf("col", "cv"),
                supportedExtensions = listOf("col", "cv", "bin", "rom"),
            ),

            GameSystem(
                SystemID.INTELLIVISION,
                "Mattel - Intellivision",
                R.string.game_system_title_intellivision,
                R.string.game_system_abbr_intellivision,
                listOf(SystemCoreConfig(
                    CoreID.FREEINTV,
                    requiredBIOSFiles = listOf("exec.bin", "grom.bin"),
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                )),
                uniqueExtensions = listOf("int"),
                supportedExtensions = listOf("int", "bin", "rom"),
            ),

            GameSystem(
                SystemID.VECTREX,
                "GCE - Vectrex",
                R.string.game_system_title_vectrex,
                R.string.game_system_abbr_vectrex,
                listOf(SystemCoreConfig(
                    CoreID.VECX,
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                )),
                uniqueExtensions = listOf("vec"),
                supportedExtensions = listOf("vec", "bin", "rom"),
            ),

            GameSystem(
                SystemID.ODYSSEY2,
                "Magnavox - Odyssey2",
                R.string.game_system_title_odyssey2,
                R.string.game_system_abbr_odyssey2,
                listOf(SystemCoreConfig(
                    CoreID.O2EM,
                    requiredBIOSFiles = listOf("o2rom.bin"),
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.ATARI_2600)),
                )),
                uniqueExtensions = listOf("bin"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // HOME COMPUTERS
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.DOS,
                "DOS",
                R.string.game_system_title_dos,
                R.string.game_system_abbr_dos,
                listOf(SystemCoreConfig(
                    CoreID.DOSBOX_PURE,
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.DOS_AUTO)),
                    statesSupported = false,
                )),
                fastForwardSupport = false,
                uniqueExtensions = listOf("dosz"),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = true,
                    scanByPathAndFilename = false,
                    scanByPathAndSupportedExtensions = true,
                ),
            ),

            GameSystem(
                SystemID.AMIGA,
                "Commodore - Amiga",
                R.string.game_system_title_amiga,
                R.string.game_system_abbr_amiga,
                listOf(
                    SystemCoreConfig(
                        CoreID.PUAE,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                        statesSupported = false,
                    ),
                    SystemCoreConfig(
                        CoreID.PUAE_2021,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                        statesSupported = false,
                    ),
                ),
                uniqueExtensions = listOf("adf"),
                supportedExtensions = listOf("adf", "hdf", "dms", "lha", "zip"),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = true,
                    scanByPathAndFilename = false,
                    scanByPathAndSupportedExtensions = true,
                ),
            ),

            GameSystem(
                SystemID.C64,
                "Commodore - 64",
                R.string.game_system_title_c64,
                R.string.game_system_abbr_c64,
                listOf(
                    SystemCoreConfig(
                        CoreID.VICE_X64SC,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                        statesSupported = false,
                    ),
                    SystemCoreConfig(
                        CoreID.VICE_X64,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                        statesSupported = false,
                    ),
                ),
                uniqueExtensions = listOf("d64"),
                supportedExtensions = listOf("d64", "t64", "prg", "p00", "crt"),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = true,
                    scanByPathAndFilename = false,
                    scanByPathAndSupportedExtensions = true,
                ),
            ),

            GameSystem(
                SystemID.MSX,
                "Microsoft - MSX",
                R.string.game_system_title_msx,
                R.string.game_system_abbr_msx,
                listOf(
                    SystemCoreConfig(
                        CoreID.BLUEMSX,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                    ),
                    SystemCoreConfig(
                        CoreID.FMSX,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                    ),
                ),
                uniqueExtensions = listOf("mx1"),
                supportedExtensions = listOf("rom", "mx1", "col", "dsk"),
            ),

            GameSystem(
                SystemID.MSX2,
                "Microsoft - MSX2",
                R.string.game_system_title_msx2,
                R.string.game_system_abbr_msx2,
                listOf(
                    SystemCoreConfig(
                        CoreID.BLUEMSX,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                    ),
                    SystemCoreConfig(
                        CoreID.FMSX,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                    ),
                ),
                uniqueExtensions = listOf("mx2"),
                supportedExtensions = listOf("rom", "mx2", "dsk"),
            ),

            GameSystem(
                SystemID.ZX_SPECTRUM,
                "Sinclair - ZX Spectrum",
                R.string.game_system_title_zxspectrum,
                R.string.game_system_abbr_zxspectrum,
                listOf(SystemCoreConfig(
                    CoreID.FUSE,
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                )),
                uniqueExtensions = listOf("tzx", "tap"),
                supportedExtensions = listOf("tzx", "tap", "z80", "rzx", "scl", "trd"),
            ),

            GameSystem(
                SystemID.AMSTRAD_CPC,
                "Amstrad - CPC",
                R.string.game_system_title_amstradcpc,
                R.string.game_system_abbr_amstradcpc,
                listOf(SystemCoreConfig(
                    CoreID.CAP32,
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                )),
                uniqueExtensions = listOf("dsk"),
                supportedExtensions = listOf("dsk", "sna", "cdt", "voc"),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = false,
                    scanByPathAndFilename = true,
                    scanByPathAndSupportedExtensions = false,
                ),
            ),

            // ──────────────────────────────────────────────────────────────────
            // ARCADE
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.FBNEO,
                "FBNeo - Arcade Games",
                R.string.game_system_title_arcade_fbneo,
                R.string.game_system_abbr_arcade_fbneo,
                listOf(SystemCoreConfig(
                    CoreID.FBNEO,
                    exposedSettings = listOf(
                        ExposedSetting("fbneo-frameskip",        R.string.setting_fbneo_frameskip),
                        ExposedSetting("fbneo-cpu-speed-adjust", R.string.setting_fbneo_cpu_speed_adjust),
                    ),
                    controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.FB_NEO_4, ControllerConfigs.FB_NEO_6)),
                )),
                scanOptions = ARCADE_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("zip"),
            ),

            GameSystem(
                SystemID.MAME2003PLUS,
                "MAME 2003-Plus",
                R.string.game_system_title_arcade_mame2003_plus,
                R.string.game_system_abbr_arcade_mame2003_plus,
                listOf(
                    SystemCoreConfig(
                        CoreID.MAME2003PLUS,
                        statesSupported = false,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.MAME_2003_4, ControllerConfigs.MAME_2003_6)),
                    ),
                    SystemCoreConfig(
                        CoreID.MAME2003,
                        statesSupported = false,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.MAME_2003_4, ControllerConfigs.MAME_2003_6)),
                    ),
                    SystemCoreConfig(
                        CoreID.MAME2000,
                        statesSupported = false,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.MAME_2003_4, ControllerConfigs.MAME_2003_6)),
                    ),
                    SystemCoreConfig(
                        CoreID.MAME2010,
                        statesSupported = false,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.MAME_2003_4, ControllerConfigs.MAME_2003_6)),
                    ),
                    SystemCoreConfig(
                        CoreID.MAME2015,
                        statesSupported = false,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.MAME_2003_4, ControllerConfigs.MAME_2003_6)),
                    ),
                    SystemCoreConfig(
                        CoreID.MAME2016,
                        statesSupported = false,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.MAME_2003_4, ControllerConfigs.MAME_2003_6)),
                    ),
                    SystemCoreConfig(
                        CoreID.MAME_CURRENT,
                        statesSupported = false,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.MAME_2003_4, ControllerConfigs.MAME_2003_6)),
                    ),
                ),
                scanOptions = ARCADE_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("zip"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // SEGA DREAMCAST
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.DREAMCAST,
                "Sega - Dreamcast",
                R.string.game_system_title_dc,
                R.string.game_system_abbr_dc,
                listOf(
                    SystemCoreConfig(
                        CoreID.FLYCAST,
                        requiredBIOSFiles = listOf("dc/dc_boot.bin"),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GENESIS_6),
                            1 to arrayListOf(ControllerConfigs.GENESIS_6),
                        ),
                        statesSupported = false,
                        skipDuplicateFrames = false,
                        supportsLibretroVFS = true,
                    ),
                    SystemCoreConfig(
                        CoreID.FLYCAST_GLES2,
                        requiredBIOSFiles = listOf("dc/dc_boot.bin"),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GENESIS_6),
                            1 to arrayListOf(ControllerConfigs.GENESIS_6),
                        ),
                        statesSupported = false,
                        skipDuplicateFrames = false,
                        supportsLibretroVFS = true,
                    ),
                ),
                scanOptions = DISC_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("cdi", "gdi", "chd", "cue"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // NINTENDO GAMECUBE / WII
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.GAMECUBE,
                "Nintendo - GameCube",
                R.string.game_system_title_gc,
                R.string.game_system_abbr_gc,
                listOf(
                    SystemCoreConfig(
                        CoreID.DOLPHIN,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                        statesSupported = false,
                        skipDuplicateFrames = false,
                        supportedOnlyArchitectures = setOf("arm64-v8a"),
                    ),
                ),
                scanOptions = DISC_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("iso", "gcz", "rvz", "wbfs"),
            ),

            GameSystem(
                SystemID.WII,
                "Nintendo - Wii",
                R.string.game_system_title_wii,
                R.string.game_system_abbr_wii,
                listOf(
                    SystemCoreConfig(
                        CoreID.DOLPHIN,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                        statesSupported = false,
                        skipDuplicateFrames = false,
                        supportedOnlyArchitectures = setOf("arm64-v8a"),
                    ),
                ),
                scanOptions = DISC_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("iso", "wbfs", "gcz", "rvz"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // SONY PLAYSTATION 2 (Play! only — no LRPS2)
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.PS2,
                "Sony - PlayStation 2",
                R.string.game_system_title_ps2,
                R.string.game_system_abbr_ps2,
                listOf(
                    SystemCoreConfig(
                        CoreID.PLAY,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.PSX_DUALSHOCK),
                            1 to arrayListOf(ControllerConfigs.PSX_DUALSHOCK),
                        ),
                        statesSupported = false,
                        skipDuplicateFrames = false,
                        supportsLibretroVFS = true,
                    ),
                ),
                scanOptions = DISC_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("iso", "chd", "cso"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // ATARI ST
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.ATARI_ST,
                "Atari - ST",
                R.string.game_system_title_atari_st,
                R.string.game_system_abbr_atari_st,
                listOf(
                    SystemCoreConfig(
                        CoreID.HATARI,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                        statesSupported = false,
                    ),
                ),
                uniqueExtensions = listOf("st"),
                supportedExtensions = listOf("st", "msa", "stx", "dim", "ipf"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // NEC PC-88 / PC-98
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.PC_88,
                "NEC - PC-8000/PC-8800 series",
                R.string.game_system_title_pc88,
                R.string.game_system_abbr_pc88,
                listOf(
                    SystemCoreConfig(
                        CoreID.QUASI88,
                        requiredBIOSFiles = listOf("quasi88/n88.rom"),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                        statesSupported = false,
                    ),
                ),
                uniqueExtensions = listOf("d88"),
                supportedExtensions = listOf("d88", "u88", "m3u"),
            ),

            GameSystem(
                SystemID.PC_98,
                "NEC - PC-98",
                R.string.game_system_title_pc98,
                R.string.game_system_abbr_pc98,
                listOf(
                    SystemCoreConfig(
                        CoreID.NEKO_PROJECT_II_KAI,
                        requiredBIOSFiles = listOf("np2kai/bios.rom"),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                        statesSupported = false,
                    ),
                ),
                uniqueExtensions = listOf("d98"),
                supportedExtensions = listOf("d98", "98d", "fdi", "fdd", "2hd", "tfd", "d88", "88d", "hdm", "xdi", "dup", "cmd", "hdi", "thd", "nhd", "hdd"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // SNK NEO GEO AES/MVS
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.NEO_GEO,
                "SNK - Neo Geo AES/MVS",
                R.string.game_system_title_neogeo,
                R.string.game_system_abbr_neogeo,
                listOf(
                    SystemCoreConfig(
                        CoreID.GEOLITH,
                        requiredBIOSFiles = listOf("neogeo.zip"),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.FB_NEO_4, ControllerConfigs.FB_NEO_6),
                        ),
                    ),
                ),
                scanOptions = ARCADE_SCAN,
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("zip"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // SNK NEO GEO POCKET (add RACE core)
            // Note: NGP/NGC systems already defined above, this adds RACE
            // ──────────────────────────────────────────────────────────────────

            // ──────────────────────────────────────────────────────────────────
            // SHARP X68000
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.SHARP_X68000,
                "Sharp - X68000",
                R.string.game_system_title_x68000,
                R.string.game_system_abbr_x68000,
                listOf(
                    SystemCoreConfig(
                        CoreID.PX68K,
                        requiredBIOSFiles = listOf("keropi/iplrom.dat"),
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.SNES)),
                        statesSupported = false,
                    ),
                ),
                uniqueExtensions = listOf("dim"),
                supportedExtensions = listOf("dim", "img", "d88", "88d", "hdm", "dup", "2hd", "xdf", "hdf", "cmd", "m3u"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // SINCLAIR ZX 81
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.ZX81,
                "Sinclair - ZX 81",
                R.string.game_system_title_zx81,
                R.string.game_system_abbr_zx81,
                listOf(
                    SystemCoreConfig(
                        CoreID.EIGHTYONE,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                    ),
                ),
                uniqueExtensions = listOf("p"),
                supportedExtensions = listOf("p", "tzx", "t81"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // FAIRCHILD CHANNEL F
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.FAIRCHILD_CHANNEL_F,
                "Fairchild - Channel F",
                R.string.game_system_title_channelf,
                R.string.game_system_abbr_channelf,
                listOf(
                    SystemCoreConfig(
                        CoreID.FREECHAF,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                    ),
                ),
                uniqueExtensions = listOf("chf"),
                supportedExtensions = listOf("bin", "chf"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // MEGA DUCK
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.MEGA_DUCK,
                "Welback Holdings - Mega Duck",
                R.string.game_system_title_megaduck,
                R.string.game_system_abbr_megaduck,
                listOf(
                    SystemCoreConfig(
                        CoreID.SAMEDUCK,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GB)),
                    ),
                ),
                uniqueExtensions = listOf("bin"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // WATARA SUPERVISION
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.SUPERVISION,
                "Watara - Supervision",
                R.string.game_system_title_supervision,
                R.string.game_system_abbr_supervision,
                listOf(
                    SystemCoreConfig(
                        CoreID.POTATOR,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.GB)),
                    ),
                ),
                uniqueExtensions = listOf("sv"),
                supportedExtensions = listOf("sv", "bin"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // THOMSON MO/TO
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.THOMSON,
                "Thomson - MO/TO",
                R.string.game_system_title_thomson,
                R.string.game_system_abbr_thomson,
                listOf(
                    SystemCoreConfig(
                        CoreID.THEODORE,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                        statesSupported = false,
                    ),
                ),
                uniqueExtensions = listOf("fd"),
                supportedExtensions = listOf("fd", "sap", "k7", "m7", "m5", "rom"),
            ),

            // ──────────────────────────────────────────────────────────────────
            // COMMODORE VIC-20 / C128 / PET
            // ──────────────────────────────────────────────────────────────────
            GameSystem(
                SystemID.VIC20,
                "Commodore - VIC-20",
                R.string.game_system_title_vic20,
                R.string.game_system_abbr_vic20,
                listOf(
                    SystemCoreConfig(
                        CoreID.VICE_XVIC,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                        statesSupported = false,
                    ),
                ),
                uniqueExtensions = listOf("20"),
                supportedExtensions = listOf("20", "40", "60", "a0", "b0", "d64", "g64", "x64", "t64", "tap", "prg", "p00", "crt", "bin"),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = true,
                    scanByPathAndFilename = false,
                    scanByPathAndSupportedExtensions = true,
                ),
            ),

            GameSystem(
                SystemID.C128,
                "Commodore - 128",
                R.string.game_system_title_c128,
                R.string.game_system_abbr_c128,
                listOf(
                    SystemCoreConfig(
                        CoreID.VICE_X128,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                        statesSupported = false,
                    ),
                ),
                uniqueExtensions = listOf("d81"),
                supportedExtensions = listOf("d64", "d71", "d81", "g64", "x64", "t64", "tap", "prg", "p00", "crt"),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = true,
                    scanByPathAndFilename = false,
                    scanByPathAndSupportedExtensions = true,
                ),
            ),

            GameSystem(
                SystemID.PET,
                "Commodore - PET",
                R.string.game_system_title_pet,
                R.string.game_system_abbr_pet,
                listOf(
                    SystemCoreConfig(
                        CoreID.VICE_XPET,
                        controllerConfigs = hashMapOf(0 to arrayListOf(ControllerConfigs.NES)),
                        statesSupported = false,
                    ),
                ),
                uniqueExtensions = listOf("p00"),
                supportedExtensions = listOf("20", "40", "60", "d64", "t64", "tap", "prg", "p00"),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = true,
                    scanByPathAndFilename = false,
                    scanByPathAndSupportedExtensions = true,
                ),
            ),
        )

        private val byIdCache by lazy { mapOf(*SYSTEMS.map { it.id.dbname to it }.toTypedArray()) }
        private val byExtensionCache by lazy {
            val map = mutableMapOf<String, GameSystem>()
            for (system in SYSTEMS) {
                for (ext in system.uniqueExtensions) {
                    map[ext.lowercase(Locale.US)] = system
                }
            }
            map.toMap()
        }

        fun findById(id: String): GameSystem = byIdCache.getValue(id)
        fun all() = SYSTEMS
        fun getSupportedExtensions(): List<String> = SYSTEMS.flatMap { it.supportedExtensions }
        fun findSystemForCore(coreID: CoreID): List<GameSystem> =
            all().filter { s -> s.systemCoreConfigs.any { it.coreID == coreID } }
        fun findByUniqueFileExtension(fileExtension: String): GameSystem? =
            byExtensionCache[fileExtension.lowercase(Locale.US)]

        data class ScanOptions(
            val scanByFilename: Boolean = true,
            val scanByUniqueExtension: Boolean = true,
            val scanByPathAndFilename: Boolean = false,
            val scanByPathAndSupportedExtensions: Boolean = true,
            val scanBySimilarSerial: Boolean = false,
        )
    }
}
