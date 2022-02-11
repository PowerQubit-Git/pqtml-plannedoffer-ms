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

@GtfsTable("schedules.txt")
@Required
public interface GtfsSchedulesSchema extends GtfsEntity
{
    @FieldType(FieldTypeEnum.ID)
    @ForeignKey(table = "calendar_dates.txt", field = "service_id")
    @Required
    String serviceId();

    @FieldType(FieldTypeEnum.ID)
    @ForeignKey(table = "blocks.txt", field = "block_id")
    @ConditionallyRequired
    String blockId();

    @FieldType(FieldTypeEnum.ID)
    @ConditionallyRequired
    String shift_id();

    @FieldType(FieldTypeEnum.ID)
    @ForeignKey(table = "vehicles.txt", field = "vehicle_id")
    @ConditionallyRequired
    String vehicle_id();

    @FieldType(FieldTypeEnum.ID)
    @ConditionallyRequired
    String driver_id();

}
