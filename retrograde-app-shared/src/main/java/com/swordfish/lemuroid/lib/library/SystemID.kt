package com.swordfish.lemuroid.lib.library

enum class SystemID(val dbname: String) {
    NES("nes"),
    FDS("fds"),                   // Famicom Disk System
    SNES("snes"),
    N64("n64"),
    GB("gb"),
    GBC("gbc"),
    GBA("gba"),
    NDS("nds"),
    NINTENDO_3DS("3ds"),
    SMS("sms"),                   // Master System
    SG1000("sg1000"),             // SG-1000
    GENESIS("md"),                // Mega Drive / Genesis
    SEGACD("scd"),                // Mega-CD / Sega CD
    SEGA_32X("sega32x"),          // 32X
    GG("gg"),                     // Game Gear
    SATURN("saturn"),             // Saturn
    PSX("psx"),
    PSP("psp"),
    ATARI2600("atari2600"),
    ATARI5200("atari5200"),
    ATARI7800("atari7800"),
    LYNX("lynx"),
    JAGUAR("jaguar"),
    PC_ENGINE("pce"),
    SUPER_GRAFX("sgfx"),
    PC_ENGINE_CD("pcecd"),
    PC_FX("pcfx"),
    NGP("ngp"),
    NGC("ngc"),                   // Neo Geo Pocket Color
    NEO_GEO_CD("neocd"),
    WS("ws"),
    WSC("wsc"),
    POKEMON_MINI("pokemini"),
    PANASONIC_3DO("3do"),
    VIRTUAL_BOY("vb"),
    DOS("dos"),
    AMIGA("amiga"),
    C64("c64"),
    MSX("msx"),
    MSX2("msx2"),
    ZX_SPECTRUM("zxspectrum"),
    AMSTRAD_CPC("amstradcpc"),
    COLECOVISION("colecovision"),
    INTELLIVISION("intv"),
    VECTREX("vectrex"),
    ODYSSEY2("odyssey2"),
    FBNEO("fbneo"),
    MAME2003PLUS("mame2003plus"),
}
