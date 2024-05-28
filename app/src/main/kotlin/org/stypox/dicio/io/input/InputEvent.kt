/*
 * Taken from /e/OS Assistant
 *
 * Copyright (C) 2024 MURENA SAS
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.stypox.dicio.io.input

/**
 * Input events are either generated by an LLM or by direct user input, and must abide by this rule:
 * if [Partial] is issued at some point, one of [Final], [None] or [Error] should follow when input
 * finishes being produced.
 */
sealed interface InputEvent {
    /**
     * Partial user input, e.g. while the user is talking.
     */
    data class Partial(
        val utterance: String
    ) : InputEvent

    /**
     * The actual final user input ready to be used. May contain more than one utterance
     * alternative, but the [utterances] list is sorted by confidence. Every item in [utterances]
     * is an utterance with its score from 1.0 (best) to 0.0 (worst).
     */
    data class Final(
        val utterances: List<Pair<String, Float>>
    ) : InputEvent

    /**
     * An input process was initiated (e.g. the user pressed on the STT button), but then nothing
     * was said.
     */
    data object None : InputEvent

    /**
     * Any error produced during the input process.
     */
    data class Error(
        val throwable: Throwable
    ) : InputEvent
}
