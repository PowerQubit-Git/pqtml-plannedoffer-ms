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

@GtfsEnumValue(name = "CONTINUOUS_DROP_OFF", value = 0)
@GtfsEnumValue(name = "NO_CONTINUOUS_DROP_OFF", value = 1)  // same as none
@GtfsEnumValue(name = "MUST_CALL_AGENCY", value = 2)
@GtfsEnumValue(name = "ON_REQUEST_TO_DRIVER", value = 3)

public interface GtfsDropOffTypeEnum
{
}