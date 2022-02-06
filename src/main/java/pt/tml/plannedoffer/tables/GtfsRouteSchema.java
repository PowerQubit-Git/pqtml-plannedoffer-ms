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
import pt.powerqubit.validator.core.table.*;
import pt.powerqubit.validator.core.type.GtfsColor;

@GtfsTable("routes.txt")
@Required
public interface GtfsRouteSchema extends GtfsEntity
{

    @Required
    String lineId();

    @Required
    String LineShortName();

    @Required
    String lineLongName();

    @FieldType(FieldTypeEnum.ID)
    @PrimaryKey
    @Required
    String routeId();

    @FieldType(FieldTypeEnum.ID)
    @ForeignKey(table = "agency.txt", field = "agency_id")
    @Required
    String agencyId();

    @Required
    String routeOrigin();

    @Required
    String routeDestination();

    @ConditionallyRequired
    String routeShortName();

    @ConditionallyRequired
    String routeLongName();

    @ConditionallyRequired
    String routeDesc();

    @ConditionallyRequired
    String routeRemarks();

    @Required
    GtfsRouteType routeType();

    @ConditionallyRequired
    String contract();

    @ConditionallyRequired
    GtfsPathType pathType();

    @ConditionallyRequired
    GtfsCircular circular();

    @ConditionallyRequired
    GtfsSchool school();

    //OMITTED
    @FieldType(FieldTypeEnum.URL)
    @ConditionallyRequired
    String routeUrl();

    //OMITTED
    @DefaultValue("FFFFFF")
    @ConditionallyRequired
    GtfsColor routeColor();

    //OMITTED
    @DefaultValue("000000")
    @ConditionallyRequired
    GtfsColor routeTextColor();

    //OMITTED
    @NonNegative
    @ConditionallyRequired
    int routeSortOrder();

    @DefaultValue("1")
    @ConditionallyRequired
    GtfsContinuousPickupDropOff continuousPickup();

    @DefaultValue("1")
    @ConditionallyRequired
    GtfsContinuousPickupDropOff continuousDropOff();
}
