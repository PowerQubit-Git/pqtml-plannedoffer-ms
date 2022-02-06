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

package pt.tml.plannedoffer.tables;

import pt.powerqubit.validator.core.annotation.*;
import pt.powerqubit.validator.core.table.GtfsBikesAllowed;
import pt.powerqubit.validator.core.table.GtfsEntity;
import pt.powerqubit.validator.core.table.GtfsTripDirectionId;
import pt.powerqubit.validator.core.table.GtfsWheelchairBoarding;
import pt.powerqubit.validator.core.type.GtfsTime;

@GtfsTable("trips.txt")
@Required
public interface GtfsTripSchema extends GtfsEntity
{
    @FieldType(FieldTypeEnum.ID)
    @Required
    @ForeignKey(table = "routes.txt", field = "route_id")
    String routeId();

    @FieldType(FieldTypeEnum.ID)
//  @Required
//  @ForeignKey(table = "calendar.txt", field = "service_id")
    String serviceId();

    @FieldType(FieldTypeEnum.ID)
    @Required
    @PrimaryKey
    String tripId();

    //OMITTED
    @ConditionallyRequired
    GtfsTime tripFirst();

    //OMITTED
    @ConditionallyRequired
    GtfsTime tripLast();

    @ConditionallyRequired
    String tripHeadsign();

    //OMITTED
    @ConditionallyRequired
    String tripShortName();

    @ConditionallyRequired
    GtfsTripDirectionId directionId();

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @Index
    @ConditionallyRequired
    String blockId();


    @FieldType(FieldTypeEnum.ID)
    @ForeignKey(table = "shapes.txt", field = "shape_id")
    @Index
    @Required
    String shapeId();

    @ConditionallyRequired
    GtfsWheelchairBoarding wheelchairAccessible();

    @ConditionallyRequired
    GtfsBikesAllowed bikesAllowed();
}
