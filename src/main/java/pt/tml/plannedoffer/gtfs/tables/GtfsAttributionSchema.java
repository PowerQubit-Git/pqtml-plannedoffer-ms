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
import pt.powerqubit.validator.core.table.GtfsAttributionRole;
import pt.powerqubit.validator.core.table.GtfsEntity;

@GtfsTable("attributions.txt")
@ConditionallyRequired
public interface GtfsAttributionSchema extends GtfsEntity
{

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @PrimaryKey
    @ConditionallyRequired
    String attributionId();

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @ForeignKey(table = "agency.txt", field = "agency_id")
    @ConditionallyRequired
    String agencyId();

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @ForeignKey(table = "routes.txt", field = "route_id")
    @ConditionallyRequired
    String routeId();

    //OMITTED
    @FieldType(FieldTypeEnum.ID)
    @ForeignKey(table = "trips.txt", field = "trip_id")
    @ConditionallyRequired
    String tripId();

    //OMITTED
    @ConditionallyRequired
    String organizationName();

    //OMITTED
    @ConditionallyRequired
    GtfsAttributionRole isProducer();

    //OMITTED
    @ConditionallyRequired
    GtfsAttributionRole isOperator();

    //OMITTED
    @ConditionallyRequired
    GtfsAttributionRole isAuthority();

    //OMITTED
    @ConditionallyRequired
    @FieldType(FieldTypeEnum.URL)
    String attributionUrl();

    //OMITTED
    @ConditionallyRequired
    @FieldType(FieldTypeEnum.EMAIL)
    String attributionEmail();

    //OMITTED
    @ConditionallyRequired
    @FieldType(FieldTypeEnum.PHONE_NUMBER)
    String attributionPhone();

}
