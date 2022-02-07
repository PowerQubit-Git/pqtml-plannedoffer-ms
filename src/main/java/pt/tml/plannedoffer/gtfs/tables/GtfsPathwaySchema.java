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
import pt.powerqubit.validator.core.table.GtfsPathwayIsBidirectional;
import pt.powerqubit.validator.core.table.GtfsPathwayMode;

@GtfsTable("pathways.txt")
@ConditionallyRequired
public interface GtfsPathwaySchema extends GtfsEntity
{

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @PrimaryKey
    @ConditionallyRequired
    String pathwayId();

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @Index
    @ForeignKey(table = "stops.txt", field = "stop_id")
    @ConditionallyRequired
    String fromStopId();

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @Index
    @ForeignKey(table = "stops.txt", field = "stop_id")
    @ConditionallyRequired
    String toStopId();

    //OMITTED
    @ConditionallyRequired
    GtfsPathwayMode pathwayMode();

    //OMITTED
    @ConditionallyRequired
    GtfsPathwayIsBidirectional isBidirectional();

    //OMITTED
    @NonNegative
    @ConditionallyRequired
    double length();

    //OMITTED
    @Positive
    @ConditionallyRequired
    int traversalTime();

    //OMITTED
    @NonZero
    @ConditionallyRequired
    int stairCount();

    //OMITTED
    @ConditionallyRequired
    double maxSlope();

    //OMITTED
    @Positive
    @ConditionallyRequired
    double minWidth();

    //OMITTED
    @ConditionallyRequired
    String signpostedAs();

    //OMITTED
    @ConditionallyRequired
    String reversedSignpostedAs();

}
