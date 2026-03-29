package com.swordfish.lemuroid.metadata.libretrodb

import com.swordfish.lemuroid.common.kotlin.filterNullable
import com.swordfish.lemuroid.lib.library.GameSystem
import com.swordfish.lemuroid.lib.library.SystemID
import com.swordfish.lemuroid.lib.library.metadata.GameMetadata
import com.swordfish.lemuroid.lib.library.metadata.GameMetadataProvider
import com.swordfish.lemuroid.lib.storage.StorageFile
import com.swordfish.lemuroid.metadata.libretrodb.db.LibretroDBManager
import com.swordfish.lemuroid.metadata.libretrodb.db.LibretroDatabase
import com.swordfish.lemuroid.metadata.libretrodb.db.entity.LibretroRom
import timber.log.Timber
import java.util.Locale

class LibretroDBMetadataProvider(private val ovgdbManager: LibretroDBManager) :
    GameMetadataProvider {

    companion object {
        private val THUMB_REPLACE = Regex("[&*/:`<>?\\\\|]")

        // Explicit Map<SystemID, String> type required to avoid Kotlin inference ambiguity.
        // Maps systems whose thumbnails live under a different folder name on the libretro
        // thumbnail server than their libretroFullName would suggest.
        private val THUMBNAIL_SYSTEM_OVERRIDES: Map<SystemID, String> = mapOf(
            SystemID.MAME2003PLUS        to "MAME",
            SystemID.FBNEO               to "FBNeo - Arcade Games",
            SystemID.DREAMCAST           to "Sega - Dreamcast",
            SystemID.GAMECUBE            to "Nintendo - GameCube",
            SystemID.WII                 to "Nintendo - Wii",
            SystemID.PS2                 to "Sony - PlayStation2",
            SystemID.ATARI_ST            to "Atari - ST",
            SystemID.ATARI_8BIT          to "Atari - 8-bit",
            SystemID.PC_88               to "NEC - PC-8800 Series",
            SystemID.PC_98               to "NEC - PC-98",
            SystemID.NEO_GEO             to "SNK - Neo Geo",
            SystemID.SHARP_X68000        to "Sharp - X68000",
            SystemID.ZX81                to "Sinclair - ZX 81",
            SystemID.FAIRCHILD_CHANNEL_F to "Fairchild - Channel F",
            SystemID.MEGA_DUCK           to "Welback Holdings - Mega Duck (Cougar Boy)",
            SystemID.SUPERVISION         to "Watara - Supervision",
            SystemID.THOMSON             to "Thomson - MOTO",
            SystemID.VIC20               to "Commodore - VIC-20",
            SystemID.C128                to "Commodore - 128",
            SystemID.PET                 to "Commodore - PET",
        )
    }

    private val sortedSystemIds: List<String> by lazy {
        SystemID.values()
            .map { it.dbname }
            .sortedByDescending { it.length }
    }

    override suspend fun retrieveMetadata(storageFile: StorageFile): GameMetadata? {
        val db = ovgdbManager.dbInstance

        Timber.d("Looking metadata for file: $storageFile")

        val metadata =
            runCatching {
                findByCRC(storageFile, db)
                    ?: findBySerial(storageFile, db)
                    ?: findByFilename(db, storageFile)
                    ?: findByPathAndFilename(db, storageFile)
                    ?: findByUniqueExtension(storageFile)
                    ?: findByKnownSystem(storageFile)
                    ?: findByPathAndSupportedExtension(storageFile)
            }.getOrElse {
                Timber.e("Error in retrieving $storageFile metadata: $it... Skipping.")
                null
            }

        metadata?.let { Timber.d("Metadata retrieved for item: $it") }

        return metadata
    }

    // Safe: unknown system IDs from libretro DB return null instead of crashing.
    private fun convertToGameMetadata(rom: LibretroRom): GameMetadata? {
        val systemId = rom.system ?: return null
        val system = GameSystem.findByIdOrNull(systemId) ?: run {
            Timber.w("Unknown system '$systemId' in libretro DB — skipping '${rom.name}'")
            return null
        }
        return GameMetadata(
            name = rom.name,
            romName = rom.romName,
            thumbnail = computeCoverUrl(system, rom.name),
            system = rom.system,
            developer = rom.developer,
        )
    }

    private suspend fun findByFilename(
        db: LibretroDatabase,
        file: StorageFile,
    ): GameMetadata? {
        return db.gameDao().findByFileName(file.name)
            .filterNullable { extractGameSystem(it) != null }
            .filterNullable { extractGameSystem(it)!!.scanOptions.scanByFilename }
            ?.let { convertToGameMetadata(it) }
    }

    private suspend fun findByPathAndFilename(
        db: LibretroDatabase,
        file: StorageFile,
    ): GameMetadata? {
        return db.gameDao().findByFileName(file.name)
            .filterNullable { extractGameSystem(it) != null }
            .filterNullable { extractGameSystem(it)!!.scanOptions.scanByPathAndFilename }
            .filterNullable { parentContainsSystem(file.path, extractGameSystem(it)!!.id.dbname) }
            ?.let { convertToGameMetadata(it) }
    }

    private fun findByPathAndSupportedExtension(file: StorageFile): GameMetadata? {
        val system =
            sortedSystemIds
                .filter { parentContainsSystem(file.path, it) }
                .mapNotNull { GameSystem.findByIdOrNull(it) }   // safe: skip unknown IDs
                .filter { it.scanOptions.scanByPathAndSupportedExtensions }
                .firstOrNull { it.supportedExtensions.contains(file.extension) }

        return system?.let {
            GameMetadata(
                name = file.extensionlessName,
                romName = file.name,
                thumbnail = null,
                system = it.id.dbname,
                developer = null,
            )
        }
    }

    private fun parentContainsSystem(
        parent: String?,
        dbname: String,
    ): Boolean {
        return parent?.lowercase(Locale.getDefault())?.contains(dbname) == true
    }

    private suspend fun findByCRC(
        file: StorageFile,
        db: LibretroDatabase,
    ): GameMetadata? {
        if (file.crc == null || file.crc == "0") return null
        return file.crc?.let { crc32 -> db.gameDao().findByCRC(crc32) }
            ?.let { convertToGameMetadata(it) }
    }

    private suspend fun findBySerial(
        file: StorageFile,
        db: LibretroDatabase,
    ): GameMetadata? {
        if (file.serial == null) return null
        return db.gameDao().findBySerial(file.serial!!)
            ?.let { convertToGameMetadata(it) }
    }

    private fun findByKnownSystem(file: StorageFile): GameMetadata? {
        if (file.systemID == null) return null
        return GameMetadata(
            name = file.extensionlessName,
            romName = file.name,
            thumbnail = null,
            system = file.systemID!!.dbname,
            developer = null,
        )
    }

    private fun findByUniqueExtension(file: StorageFile): GameMetadata? {
        val system = GameSystem.findByUniqueFileExtension(file.extension)

        if (system?.scanOptions?.scanByUniqueExtension == false) return null

        return system?.let {
            GameMetadata(
                name = file.extensionlessName,
                romName = file.name,
                thumbnail = null,
                system = it.id.dbname,
                developer = null,
            )
        }
    }

    // Safe lookup — unknown system in DB does not crash the scan pipeline.
    private fun extractGameSystem(rom: LibretroRom): GameSystem? {
        return GameSystem.findByIdOrNull(rom.system ?: return null)
    }

    private fun computeCoverUrl(system: GameSystem, name: String?): String? {
        if (name == null) return null
        val systemName = THUMBNAIL_SYSTEM_OVERRIDES[system.id] ?: system.libretroFullName
        val thumbGameName = name.replace(THUMB_REPLACE, "_")
        return "https://thumbnails.libretro.com/$systemName/Named_Boxarts/$thumbGameName.png"
    }
}
