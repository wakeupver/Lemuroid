package com.swordfish.lemuroid.lib.bios

import com.swordfish.lemuroid.common.files.safeDelete
import com.swordfish.lemuroid.common.kotlin.associateByNotNull
import com.swordfish.lemuroid.common.kotlin.writeToFile
import com.swordfish.lemuroid.lib.library.SystemCoreConfig
import com.swordfish.lemuroid.lib.library.SystemID
import com.swordfish.lemuroid.lib.library.db.entity.Game
import com.swordfish.lemuroid.lib.storage.DirectoriesManager
import com.swordfish.lemuroid.lib.storage.StorageFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.InputStream

class BiosManager(private val directoriesManager: DirectoriesManager) {
    private val crcLookup = SUPPORTED_BIOS.associateByNotNull { it.externalCRC32 }
    private val nameLookup = SUPPORTED_BIOS.associateByNotNull { it.externalName }

    fun getMissingBiosFiles(
        coreConfig: SystemCoreConfig,
        game: Game,
    ): List<String> {
        val regionalBiosFiles = coreConfig.regionalBIOSFiles

        val gameLabels =
            Regex("\\([A-Za-z]+\\)")
                .findAll(game.title)
                .map { it.value.drop(1).dropLast(1) }
                .filter { it.isNotBlank() }
                .toSet()

        Timber.d("Found game labels: $gameLabels")

        val requiredRegionalFiles =
            gameLabels.intersect(regionalBiosFiles.keys)
                .ifEmpty { regionalBiosFiles.keys }
                .mapNotNull { regionalBiosFiles[it] }

        Timber.d("Required regional files for game: $requiredRegionalFiles")

        return (coreConfig.requiredBIOSFiles + requiredRegionalFiles)
            .filter { !File(directoriesManager.getSystemDirectory(), it).exists() }
    }

    fun deleteBiosBefore(timestampMs: Long) {
        Timber.i("Pruning old bios files")
        SUPPORTED_BIOS
            .map { File(directoriesManager.getSystemDirectory(), it.libretroFileName) }
            .filter { it.lastModified() < normalizeTimestamp(timestampMs) }
            .forEach {
                Timber.d("Pruning old bios file: ${it.path}")
                it.safeDelete()
            }
    }

    @Deprecated("Use the suspend variant")
    fun getBiosInfo(): BiosInfo {
        val bios =
            SUPPORTED_BIOS.groupBy {
                File(directoriesManager.getSystemDirectory(), it.libretroFileName).exists()
            }.withDefault { listOf() }

        return BiosInfo(bios.getValue(true), bios.getValue(false))
    }

    suspend fun getBiosInfoAsync(): BiosInfo =
        withContext(Dispatchers.IO) {
            getBiosInfo()
        }

    fun tryAddBiosAfter(
        storageFile: StorageFile,
        inputStream: InputStream,
        timestampMs: Long,
    ): Boolean {
        val bios = findByCRC(storageFile) ?: findByName(storageFile) ?: return false

        Timber.i("Importing bios file: $bios")

        val biosFile = File(directoriesManager.getSystemDirectory(), bios.libretroFileName)
        if (biosFile.exists() && biosFile.setLastModified(normalizeTimestamp(timestampMs))) {
            Timber.d("Bios file already present. Updated last modification date.")
        } else {
            Timber.d("Bios file not available. Copying new file.")
            inputStream.writeToFile(biosFile)
        }
        return true
    }

    private fun findByCRC(storageFile: StorageFile): Bios? {
        return crcLookup[storageFile.crc]
    }

    private fun findByName(storageFile: StorageFile): Bios? {
        return nameLookup[storageFile.name]
    }

    private fun normalizeTimestamp(timestamp: Long) = (timestamp / 1000) * 1000

    data class BiosInfo(val detected: List<Bios>, val notDetected: List<Bios>)

    companion object {
        private val SUPPORTED_BIOS =
            listOf(
                // ── PlayStation 1 ────────────────────────────────────────────
                Bios(
                    "scph101.bin",
                    "6E3735FF4C7DC899EE98981385F6F3D0",
                    "PS One 4.5 NTSC-U/C",
                    SystemID.PSX,
                    "171BDCEC",
                ),
                Bios(
                    "scph7001.bin",
                    "1E68C231D0896B7EADCAD1D7D8E76129",
                    "PS Original 4.1 NTSC-U/C",
                    SystemID.PSX,
                    "502224B6",
                ),
                Bios(
                    "scph5501.bin",
                    "490F666E1AFB15B7362B406ED1CEA246",
                    "PS Original 3.0 NTSC-U/C (Beetle PSX)",
                    SystemID.PSX,
                    "8D8CB7E4",
                ),
                Bios(
                    "scph1001.bin",
                    "924E392ED05558FFDB115408C263DCCF",
                    "PS Original 2.2 NTSC-U/C",
                    SystemID.PSX,
                    "37157331",
                ),
                // PS1 PAL BIOSes
                Bios(
                    "scph1002.bin",
                    "54847E693405AB67BC5B47D3CF5A9B03",
                    "PS Original 2.2 PAL",
                    SystemID.PSX,
                    "7E9B4B16",
                    "scph1002.bin",
                ),
                Bios(
                    "scph5502.bin",
                    "32736F17079D0B2B7024407C39BD3050",
                    "PS Original 3.0 PAL (Beetle PSX)",
                    SystemID.PSX,
                    "E10A3571",
                ),
                Bios(
                    "scph5500.bin",
                    "8DD7D5296A650FAC7319BCE665A6A53C",
                    "PS Original 3.0 NTSC-J (Beetle PSX)",
                    SystemID.PSX,
                    "4BACA4C3",
                ),

                // ── Atari Lynx ───────────────────────────────────────────────
                Bios(
                    "lynxboot.img",
                    "FCD403DB69F54290B51035D82F835E7B",
                    "Lynx Boot Image",
                    SystemID.LYNX,
                    "0D973C9D",
                ),

                // ── Sega CD ──────────────────────────────────────────────────
                Bios(
                    "bios_CD_E.bin",
                    "E66FA1DC5820D254611FDCDBA0662372",
                    "Sega CD BIOS (Europe)",
                    SystemID.SEGACD,
                    "529AC15A",
                ),
                Bios(
                    "bios_CD_J.bin",
                    "278A9397D192149E84E820AC621A8EDD",
                    "Sega CD BIOS (Japan)",
                    SystemID.SEGACD,
                    "9D2DA8F2",
                ),
                Bios(
                    "bios_CD_U.bin",
                    "2EFD74E3232FF260E371B99F84024F7F",
                    "Sega CD BIOS (USA)",
                    SystemID.SEGACD,
                    "C6D10268",
                ),

                // ── Sega Saturn ──────────────────────────────────────────────
                Bios(
                    "saturn_bios.bin",
                    "AF5828FDFF51384F99B3C4926BE27762",
                    "Sega Saturn BIOS",
                    SystemID.SATURN,
                    "AF5828FD",
                ),
                Bios(
                    "sega_101.bin",
                    "85AB8D08BD3C5D7B03EB56CC9BF040B3",
                    "Sega Saturn BIOS (JP v1.01)",
                    SystemID.SATURN,
                    "224B752C",
                    "sega_101.bin",
                ),
                Bios(
                    "mpr-17933.bin",
                    "3240872C70984B6CBFDA1586CAB68DBE",
                    "Sega Saturn BIOS (US/EU)",
                    SystemID.SATURN,
                    "4AFCF0FA",
                    "mpr-17933.bin",
                ),

                // ── Nintendo DS ──────────────────────────────────────────────
                Bios(
                    "bios7.bin",
                    "DF692A80A5B1BC90728BC3DFC76CD948",
                    "Nintendo DS ARM7 BIOS",
                    SystemID.NDS,
                    "1280F0D5",
                ),
                Bios(
                    "bios9.bin",
                    "A392174EB3E572FED6447E956BDE4B25",
                    "Nintendo DS ARM9 BIOS",
                    SystemID.NDS,
                    "2AB23573",
                ),
                Bios(
                    "firmware.bin",
                    "E45033D9B0FA6B0DE071292BBA7C9D13",
                    "Nintendo DS Firmware",
                    SystemID.NDS,
                    "945F9DC9",
                    "nds_firmware.bin",
                ),

                // ── NEC PC Engine CD ─────────────────────────────────────────
                Bios(
                    "syscard3.pce",
                    "FF1A674273893314EA8F29B1E66A675E",
                    "PC Engine CD BIOS (Super CD-ROM2 v3.0)",
                    SystemID.PC_ENGINE_CD,
                    "6D773AB7",
                ),
                Bios(
                    "syscard2.pce",
                    "3B13AF61585E0D6B4E43E2D6D5B63AE1",
                    "PC Engine CD BIOS (CD-ROM2 v2.1)",
                    SystemID.PC_ENGINE_CD,
                    "8B026E12",
                    "syscard2.pce",
                ),
                Bios(
                    "syscard1.pce",
                    "2B11E0BC1B2A6CD6A30A4D2FF7AFBA7B",
                    "PC Engine CD BIOS (CD-ROM2 v1.0)",
                    SystemID.PC_ENGINE_CD,
                    "5A87E59B",
                    "syscard1.pce",
                ),

                // ── NEC PC-FX ────────────────────────────────────────────────
                Bios(
                    "pcfx.rom",
                    "08E36EDBEA28A017F79F8D4F7FF9B6D7",
                    "NEC PC-FX BIOS v1.00",
                    SystemID.PC_FX,
                    "37BAD1C4",
                ),

                // ── SNK Neo Geo CD ───────────────────────────────────────────
                Bios(
                    "neocd.bin",
                    "8834880C33164CCBE6476B559F3E37DE",
                    "Neo Geo CD BIOS",
                    SystemID.NEO_GEO_CD,
                    "DF9DE490",
                ),
                Bios(
                    "front-sp1.bin",
                    "33697B98BAA0F1BD5E55EB19DD6EC0C5",
                    "Neo Geo CD BIOS (Front Loader)",
                    SystemID.NEO_GEO_CD,
                    "9905E1D0",
                    "front-sp1.bin",
                ),
                Bios(
                    "top-sp1.bin",
                    "2F1F81D7A7C99F5CF6A0B29F97D25D68",
                    "Neo Geo CD BIOS (Top Loader)",
                    SystemID.NEO_GEO_CD,
                    "B9F01436",
                    "top-sp1.bin",
                ),

                // ── Panasonic 3DO ────────────────────────────────────────────
                Bios(
                    "panafz10.bin",
                    "51F2F43AE2F3508A14D9F56597E2D3CE",
                    "3DO BIOS (FZ-10 v1.0)",
                    SystemID.PANASONIC_3DO,
                    "51F2F43A",
                ),
                Bios(
                    "panafz1.bin",
                    "F47264DD47FE30F73AB3C010015C155B",
                    "3DO BIOS (FZ-1 v1.0)",
                    SystemID.PANASONIC_3DO,
                    "4D4CB08A",
                    "panafz1.bin",
                ),
                Bios(
                    "goldstar.bin",
                    "8639FD5E549BD6238CFE07F1061F6C6B",
                    "3DO BIOS (GoldStar GDO-101P)",
                    SystemID.PANASONIC_3DO,
                    "8639FD5E",
                    "goldstar.bin",
                ),

                // ── Coleco ColecoVision ──────────────────────────────────────
                Bios(
                    "colecovision.rom",
                    "2C66F5911E5B42B8EBE113403548EEE7",
                    "ColecoVision BIOS",
                    SystemID.COLECOVISION,
                    "00000000",
                ),

                // ── Mattel Intellivision ─────────────────────────────────────
                Bios(
                    "exec.bin",
                    "62E761035CB657903761800F4437B8AF",
                    "Intellivision Executive ROM",
                    SystemID.INTELLIVISION,
                    "CBB9B5A4",
                ),
                Bios(
                    "grom.bin",
                    "0CD5946C6473E42E8E4C2137785E3D01",
                    "Intellivision Graphics ROM",
                    SystemID.INTELLIVISION,
                    "1CB13F62",
                ),

                // ── Magnavox Odyssey2 ────────────────────────────────────────
                Bios(
                    "o2rom.bin",
                    "562D5EBEF9C0D3BAAFA7E4E8B40B4B9B",
                    "Odyssey2 BIOS (USA)",
                    SystemID.ODYSSEY2,
                    "00000000",
                ),

                // ── Nintendo Famicom Disk System ─────────────────────────────
                Bios(
                    "disksys.rom",
                    "CA30B50F880EB660A320674ED365EF7A",
                    "Famicom Disk System BIOS",
                    SystemID.FDS,
                    "5E607DCF",
                ),

                // ── Sega Dreamcast ───────────────────────────────────────────
                Bios(
                    "dc/dc_boot.bin",
                    "E10A3C10D209BB657BE1A49161994D62",
                    "Dreamcast BIOS (v1.01d)",
                    SystemID.DREAMCAST,
                    "89F2B1A1",
                ),
                Bios(
                    "dc/dc_flash.bin",
                    "0A93F7940C455905BEA0CFA9A80E9293",
                    "Dreamcast Flash (v1.01d)",
                    SystemID.DREAMCAST,
                    "9F0D1BE4",
                    "dc/dc_flash.bin",
                ),

                // ── Sega Saturn extras ───────────────────────────────────────
                Bios(
                    "sega_101.bin",
                    "85AB8D08BD3C5D7B03EB56CC9BF040B3",
                    "Sega Saturn BIOS JP (v1.01)",
                    SystemID.SATURN,
                    "224B752C",
                    "sega_101.bin",
                ),
                Bios(
                    "mpr-17933.bin",
                    "3240872C70984B6CBFDA1586CAB68DBE",
                    "Sega Saturn BIOS US/EU",
                    SystemID.SATURN,
                    "4AFCF0FA",
                    "mpr-17933.bin",
                ),

                // ── SNK Neo Geo CD extras ────────────────────────────────────
                Bios(
                    "front-sp1.bin",
                    "33697B98BAA0F1BD5E55EB19DD6EC0C5",
                    "Neo Geo CD BIOS (Front Loader)",
                    SystemID.NEO_GEO_CD,
                    "9905E1D0",
                    "front-sp1.bin",
                ),
                Bios(
                    "top-sp1.bin",
                    "2F1F81D7A7C99F5CF6A0B29F97D25D68",
                    "Neo Geo CD BIOS (Top Loader)",
                    SystemID.NEO_GEO_CD,
                    "B9F01436",
                    "top-sp1.bin",
                ),

                // ── PS1 extras (Beetle PSX) ──────────────────────────────────
                Bios(
                    "scph5500.bin",
                    "8DD7D5296A650FAC7319BCE665A6A53C",
                    "PS Original 3.0 NTSC-J (Beetle PSX)",
                    SystemID.PSX,
                    "4BACA4C3",
                ),
                Bios(
                    "scph5502.bin",
                    "32736F17079D0B2B7024407C39BD3050",
                    "PS Original 3.0 PAL (Beetle PSX)",
                    SystemID.PSX,
                    "E10A3571",
                ),
                Bios(
                    "scph1002.bin",
                    "54847E693405AB67BC5B47D3CF5A9B03",
                    "PS Original 2.2 PAL",
                    SystemID.PSX,
                    "7E9B4B16",
                    "scph1002.bin",
                ),

                // ── 3DO alternates ───────────────────────────────────────────
                Bios(
                    "panafz1.bin",
                    "F47264DD47FE30F73AB3C010015C155B",
                    "3DO BIOS (FZ-1 v1.0)",
                    SystemID.PANASONIC_3DO,
                    "4D4CB08A",
                    "panafz1.bin",
                ),
                Bios(
                    "goldstar.bin",
                    "8639FD5E549BD6238CFE07F1061F6C6B",
                    "3DO BIOS (GoldStar GDO-101P)",
                    SystemID.PANASONIC_3DO,
                    "8639FD5E",
                    "goldstar.bin",
                ),

                // ── PC Engine CD extras ──────────────────────────────────────
                Bios(
                    "syscard2.pce",
                    "3B13AF61585E0D6B4E43E2D6D5B63AE1",
                    "PC Engine CD BIOS (CD-ROM2 v2.1)",
                    SystemID.PC_ENGINE_CD,
                    "8B026E12",
                    "syscard2.pce",
                ),
                Bios(
                    "syscard1.pce",
                    "2B11E0BC1B2A6CD6A30A4D2FF7AFBA7B",
                    "PC Engine CD BIOS (CD-ROM2 v1.0)",
                    SystemID.PC_ENGINE_CD,
                    "5A87E59B",
                    "syscard1.pce",
                ),
            )
    }
}
