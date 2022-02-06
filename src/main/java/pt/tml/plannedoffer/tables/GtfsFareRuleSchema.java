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
import pt.powerqubit.validator.core.table.GtfsEntity;

@GtfsTable("fare_rules.txt")
@ConditionallyRequired
public interface GtfsFareRuleSchema extends GtfsEntity
{

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @ConditionallyRequired
    @ForeignKey(table = "fare_attributes.txt", field = "fare_id")
    String fareId();

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @ForeignKey(table = "routes.txt", field = "route_id")
    @ConditionallyRequired
    String routeId();

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @ForeignKey(table = "stops.txt", field = "zone_id")
    @ConditionallyRequired
    String originId();

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @ForeignKey(table = "stops.txt", field = "zone_id")
    @ConditionallyRequired
    String destinationId();

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @ForeignKey(table = "stops.txt", field = "zone_id")
    @ConditionallyRequired
    String containsId();

}
