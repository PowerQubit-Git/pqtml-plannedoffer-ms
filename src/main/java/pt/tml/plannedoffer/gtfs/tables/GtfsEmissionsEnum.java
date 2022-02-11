/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pt.tml.plannedoffer.gtfs.tables;

import pt.powerqubit.validator.core.annotation.GtfsEnumValue;

@GtfsEnumValue(name = "EURO_I", value = 0)
@GtfsEnumValue(name = "EURO_II", value = 1)
@GtfsEnumValue(name = "EURO_III", value = 2)
@GtfsEnumValue(name = "EURO_IV", value = 3)
@GtfsEnumValue(name = "EURO_V", value = 4)
public interface GtfsEmissionsEnum {}