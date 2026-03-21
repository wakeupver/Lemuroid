package com.swordfish.lemuroid.app.mobile.feature.gamemenu.patchcodes

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swordfish.lemuroid.lib.cheats.PatchCodesManager
import com.swordfish.lemuroid.lib.library.db.entity.PatchCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GameMenuPatchCodesViewModel(
    private val gameId: Int,
    private val patchCodesManager: PatchCodesManager,
) : ViewModel() {

    val codes: StateFlow<List<PatchCode>> =
        patchCodesManager
            .getCodesForGame(gameId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** Import result state: null = idle, >= 0 = number of codes imported, -1 = error */
    private val _importResult = MutableStateFlow<ImportResult?>(null)
    val importResult: StateFlow<ImportResult?> = _importResult.asStateFlow()

    sealed class ImportResult {
        data class Success(val count: Int) : ImportResult()
        object Error : ImportResult()
        object Empty : ImportResult()
    }

    fun addCode(description: String, code: String) {
        viewModelScope.launch {
            patchCodesManager.saveCode(
                PatchCode(
                    gameId = gameId,
                    description = description.trim(),
                    code = code.trim().uppercase(),
                    enabled = true,
                ),
            )
        }
    }

    fun toggleCode(patch: PatchCode) {
        viewModelScope.launch {
            patchCodesManager.updateCode(patch.copy(enabled = !patch.enabled))
        }
    }

    fun deleteCode(patch: PatchCode) {
        viewModelScope.launch {
            patchCodesManager.deleteCode(patch)
        }
    }

    /**
     * Import patch codes from a file URI (e.g. picked via SAF from SD card).
     * Supports .cht (RetroArch), pipe-delimited, and code-only formats.
     */
    fun importFromUri(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                val codes = patchCodesManager.importCodesFromUri(context, uri, gameId)
                if (codes.isEmpty()) {
                    _importResult.value = ImportResult.Empty
                } else {
                    codes.forEach { patchCodesManager.saveCode(it) }
                    _importResult.value = ImportResult.Success(codes.size)
                }
            } catch (e: Exception) {
                _importResult.value = ImportResult.Error
            }
        }
    }

    fun clearImportResult() {
        _importResult.value = null
    }

    class Factory(
        private val gameId: Int,
        private val patchCodesManager: PatchCodesManager,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GameMenuPatchCodesViewModel(gameId, patchCodesManager) as T
        }
    }
}
