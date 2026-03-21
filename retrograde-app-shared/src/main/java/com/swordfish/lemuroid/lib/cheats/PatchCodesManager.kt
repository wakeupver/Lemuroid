package com.swordfish.lemuroid.lib.cheats

import android.content.Context
import android.net.Uri
import com.swordfish.lemuroid.lib.library.db.RetrogradeDatabase
import com.swordfish.lemuroid.lib.library.db.entity.PatchCode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PatchCodesManager @Inject constructor(
    private val retrogradeDatabase: RetrogradeDatabase,
) {
    fun getCodesForGame(gameId: Int): Flow<List<PatchCode>> =
        retrogradeDatabase.patchCodeDao().getCodesForGame(gameId)

    suspend fun getAllCodesForGame(gameId: Int): List<PatchCode> =
        retrogradeDatabase.patchCodeDao().getAllCodesForGame(gameId)

    suspend fun getEnabledCodesForGame(gameId: Int): List<PatchCode> =
        retrogradeDatabase.patchCodeDao().getEnabledCodesForGame(gameId)

    suspend fun saveCode(code: PatchCode): Long =
        retrogradeDatabase.patchCodeDao().insert(code)

    suspend fun updateCode(code: PatchCode) =
        retrogradeDatabase.patchCodeDao().update(code)

    suspend fun deleteCode(code: PatchCode) =
        retrogradeDatabase.patchCodeDao().delete(code)

    /**
     * Import patch codes from a URI (file on SD card / external storage).
     *
     * Supported formats:
     *
     * 1. RetroArch .cht format:
     *    cheats = 2
     *    cheat0_desc = "Infinite Lives"
     *    cheat0_code = "007E1940:63"
     *    cheat0_enable = true
     *
     * 2. Simple pipe-delimited format (one per line):
     *    Infinite Lives|007E1940:63
     *
     * 3. Simple code-only format (one per line, no pipe):
     *    007E1940:63
     *
     * Returns a list of imported PatchCode objects (not yet saved to DB).
     * Caller should call saveCode() for each to persist them.
     */
    suspend fun importCodesFromUri(context: Context, uri: Uri, gameId: Int): List<PatchCode> {
        val lines = context.contentResolver.openInputStream(uri)?.use { stream ->
            stream.bufferedReader(Charsets.UTF_8).readLines()
        } ?: return emptyList()

        return when {
            lines.any { it.trimStart().startsWith("cheat") || it.trimStart().startsWith("cheats") } ->
                parseRetroArchFormat(lines, gameId)
            lines.any { it.contains("|") } ->
                parsePipeFormat(lines, gameId)
            else ->
                parseCodeOnlyFormat(lines, gameId)
        }
    }

    /** Parse RetroArch .cht file format */
    private fun parseRetroArchFormat(lines: List<String>, gameId: Int): List<PatchCode> {
        // Build a map: index -> (desc, code, enabled)
        data class Entry(var desc: String = "", var code: String = "", var enabled: Boolean = true)
        val entries = mutableMapOf<Int, Entry>()

        val descRegex  = Regex("""^cheat(\d+)_desc\s*=\s*"?([^"]*)"?\s*$""")
        val codeRegex  = Regex("""^cheat(\d+)_code\s*=\s*"?([^"]*)"?\s*$""")
        val enableReg  = Regex("""^cheat(\d+)_enable\s*=\s*(\w+)\s*$""")

        for (line in lines) {
            descRegex.matchEntire(line.trim())?.let { m ->
                val idx = m.groupValues[1].toInt()
                entries.getOrPut(idx) { Entry() }.desc = m.groupValues[2].trim()
            }
            codeRegex.matchEntire(line.trim())?.let { m ->
                val idx = m.groupValues[1].toInt()
                entries.getOrPut(idx) { Entry() }.code = m.groupValues[2].trim()
            }
            enableReg.matchEntire(line.trim())?.let { m ->
                val idx = m.groupValues[1].toInt()
                entries.getOrPut(idx) { Entry() }.enabled = m.groupValues[2].lowercase() == "true"
            }
        }

        return entries.values
            .filter { it.code.isNotBlank() }
            .map { e ->
                PatchCode(
                    gameId = gameId,
                    description = e.desc.ifBlank { "Imported code" },
                    code = e.code.uppercase(),
                    enabled = e.enabled,
                )
            }
    }

    /** Parse simple "Description|CODE" format (one entry per line) */
    private fun parsePipeFormat(lines: List<String>, gameId: Int): List<PatchCode> =
        lines
            .map { it.trim() }
            .filter { it.isNotBlank() && !it.startsWith("#") && it.contains("|") }
            .mapNotNull { line ->
                val parts = line.split("|", limit = 2)
                val desc = parts[0].trim()
                val code = parts.getOrNull(1)?.trim()?.uppercase() ?: return@mapNotNull null
                if (code.isBlank()) return@mapNotNull null
                PatchCode(gameId = gameId, description = desc.ifBlank { "Imported code" }, code = code, enabled = true)
            }

    /** Parse plain code-per-line format (no description) */
    private fun parseCodeOnlyFormat(lines: List<String>, gameId: Int): List<PatchCode> =
        lines
            .map { it.trim() }
            .filter { it.isNotBlank() && !it.startsWith("#") }
            .mapIndexed { idx, code ->
                PatchCode(
                    gameId = gameId,
                    description = "Code ${idx + 1}",
                    code = code.uppercase(),
                    enabled = true,
                )
            }
}
