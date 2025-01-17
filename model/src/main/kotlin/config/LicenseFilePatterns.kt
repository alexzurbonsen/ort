/*
 * Copyright (C) 2020 The ORT Project Authors (see <https://github.com/oss-review-toolkit/ort/blob/main/NOTICE>)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

package org.ossreviewtoolkit.model.config

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * A class that holds various filename patterns for files that typically contain license-related information. All
 * patterns are supposed to be used case-insensitively.
 */
data class LicenseFilePatterns(
    /**
     * A set of globs that match typical license filenames.
     */
    val licenseFilenames: Set<String>,

    /**
     * A set of globs that match typical patent filenames.
     */
    val patentFilenames: Set<String>,

    /**
     * A set of globs that match files that often contain the license of a project, but that are no license files and
     * are therefore not contained in [licenseFilenames].
     */
    val otherLicenseFilenames: Set<String>
) {
    /**
     * A set of globs that match all kind of license filenames, equaling the union of [licenseFilenames],
     * [patentFilenames] and [otherLicenseFilenames].
     */
    @JsonIgnore
    val allLicenseFilenames = licenseFilenames + patentFilenames + otherLicenseFilenames

    companion object {
        val DEFAULT = LicenseFilePatterns(
            licenseFilenames = setOf(
                "copying*",
                "copyright",
                "licence*",
                "license*",
                "*.licence",
                "*.license",
                "unlicence",
                "unlicense"
            ),
            patentFilenames = setOf(
                "patents"
            ),
            otherLicenseFilenames = setOf(
                "readme*"
            )
        )

        private var instance: LicenseFilePatterns = DEFAULT

        @Synchronized
        fun configure(patterns: LicenseFilePatterns) {
            instance = patterns
        }

        @Synchronized
        fun getInstance(): LicenseFilePatterns = instance
    }
}
