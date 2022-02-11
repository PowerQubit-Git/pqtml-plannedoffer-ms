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

@GtfsTable("dead_runs.txt")
@Required
public interface GtfsDeadRunSchema extends GtfsEntity
{
    @FieldType(FieldTypeEnum.ID)
    @PrimaryKey
    @Required
    String deadRunId();

    @FieldType(FieldTypeEnum.ID)
    @Required
    @ForeignKey(table = "agency.txt", field = "agency_id")
    String agencyId();

    @FieldType(FieldTypeEnum.ID)
    @Required
    @ForeignKey(table = "calendar_dates.txt", field = "service_id")
    String serviceId();

    @Required
    @EndRange(field = "end_time", allowEqual = true)
    GtfsTime startTime();

    @Required
    GtfsTime endTime();

    @FieldType(FieldTypeEnum.ID)
    @Required
    @ForeignKey(table = "stops.txt", field = "stop_id")
    String startLocationId();

    @FieldType(FieldTypeEnum.ID)
    @Required
    @ForeignKey(table = "stops.txt", field = "stop_id")
    String endLocationId();

    @NonNegative
    @Required
    int distTraveled();

    @FieldType(FieldTypeEnum.ID)
    @ConditionallyRequired
    @ForeignKey(table = "blocks.txt", field = "block_id")
    String blockId();

    @FieldType(FieldTypeEnum.ID)
    @ConditionallyRequired
    String shiftId();

    @NonNegative
    @ConditionallyRequired
    int overtime();

}
