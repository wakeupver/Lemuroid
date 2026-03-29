package com.swordfish.lemuroid.lib.library

enum class SystemID(val dbname: String) {
    // ── Nintendo ──────────────────────────────────────────────────────────────
    NES("nes"),
    FDS("fds"),                       // Famicom Disk System
    SNES("snes"),
    N64("n64"),
    GB("gb"),
    GBC("gbc"),
    GBA("gba"),
    NDS("nds"),
    NINTENDO_3DS("3ds"),
    GAMECUBE("gc"),
    WII("wii"),
    POKEMON_MINI("pokemini"),
    VIRTUAL_BOY("vb"),

    // ── Sega ──────────────────────────────────────────────────────────────────
    SMS("sms"),                       // Master System / Mark III
    SG1000("sg1000"),                 // SG-1000
    GENESIS("md"),                    // Mega Drive / Genesis
    SEGACD("scd"),                    // Mega-CD / Sega CD
    SEGA_32X("sega32x"),              // 32X
    GG("gg"),                         // Game Gear
    SATURN("saturn"),
    DREAMCAST("dc"),

    // ── Sony ──────────────────────────────────────────────────────────────────
    PSX("psx"),
    PSP("psp"),
    PS2("ps2"),                       // Play! core (not LRPS2)

    // ── Atari ─────────────────────────────────────────────────────────────────
    ATARI2600("atari2600"),
    ATARI5200("atari5200"),
    ATARI7800("atari7800"),
    LYNX("lynx"),
    JAGUAR("jaguar"),
    ATARI_ST("atarist"),

    // ── NEC ───────────────────────────────────────────────────────────────────
    PC_ENGINE("pce"),
    SUPER_GRAFX("sgfx"),
    PC_ENGINE_CD("pcecd"),
    PC_FX("pcfx"),
    PC_88("pc88"),
    PC_98("pc98"),

    // ── SNK ───────────────────────────────────────────────────────────────────
    NGP("ngp"),
    NGC("ngc"),                       // Neo Geo Pocket Color
    NEO_GEO_CD("neocd"),
    NEO_GEO("neogeo"),                // AES / MVS Cartridge (Geolith)

    // ── Bandai ────────────────────────────────────────────────────────────────
    WS("ws"),
    WSC("wsc"),

    // ── 3DO ───────────────────────────────────────────────────────────────────
    PANASONIC_3DO("3do"),

    // ── Home computers ────────────────────────────────────────────────────────
    DOS("dos"),
    AMIGA("amiga"),
    C64("c64"),
    C128("c128"),
    VIC20("vic20"),
    PET("pet"),
    MSX("msx"),
    MSX2("msx2"),
    ZX_SPECTRUM("zxspectrum"),
    ZX81("zx81"),
    AMSTRAD_CPC("amstradcpc"),
    SHARP_X68000("x68000"),
    SHARP_X1("x1"),
    ATARI_8BIT("atari800"),

    // ── Other consoles ────────────────────────────────────────────────────────
    COLECOVISION("colecovision"),
    INTELLIVISION("intv"),
    VECTREX("vectrex"),
    ODYSSEY2("odyssey2"),
    FAIRCHILD_CHANNEL_F("channelf"),
    MEGA_DUCK("megaduck"),
    SUPERVISION("supervision"),
    THOMSON("thomson"),

    // ── Arcade ────────────────────────────────────────────────────────────────
    FBNEO("fbneo"),
    MAME2003PLUS("mame2003plus"),
    MAME2000("mame2000"),
    MAME2010("mame2010"),
    MAME_CURRENT("mame"),
}
