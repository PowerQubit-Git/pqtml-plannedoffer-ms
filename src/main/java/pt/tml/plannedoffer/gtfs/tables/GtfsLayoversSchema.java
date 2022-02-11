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

import pt.powerqubit.validator.core.annotation.*;
import pt.powerqubit.validator.core.table.GtfsEntity;
import pt.powerqubit.validator.core.type.GtfsTime;

@GtfsTable("layovers.txt")
@Required
public interface GtfsLayoversSchema extends GtfsEntity
{
    @FieldType(FieldTypeEnum.ID)
    @PrimaryKey
    @Required
    String layoverId();

    @ForeignKey(table = "agency.txt", field = "agency_id")
    @Required
    String agencyId();

    @ForeignKey(table = "calendar_dates.txt", field = "service_id")
    @Required
    String serviceId();

    @Required
    GtfsTime startTime();

    @Required
    GtfsTime endTime();

    @ForeignKey(table = "stops.txt", field = "stop_id")
    @Required
    String locationId();

    @ForeignKey(table = "blocks.txt", field = "block_id")
    @ConditionallyRequired
    String blockId();

    @FieldType(FieldTypeEnum.ID)
    @ConditionallyRequired
    String startShiftId();

    @NonNegative
    @ConditionallyRequired
    int startOvertime();

    @FieldType(FieldTypeEnum.ID)
    @ConditionallyRequired
    String endShiftId();

    @NonNegative
    @ConditionallyRequired
    int endDuration();

    @NonNegative
    @ConditionallyRequired
    int endOvertime();

}
