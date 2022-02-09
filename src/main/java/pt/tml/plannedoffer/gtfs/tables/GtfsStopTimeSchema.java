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
import pt.powerqubit.validator.core.table.*;
import pt.powerqubit.validator.core.type.GtfsTime;

@GtfsTable("stop_times.txt")
@Required
public interface GtfsStopTimeSchema extends GtfsEntity
{
    @FieldType(FieldTypeEnum.ID)
    @FirstKey
    @Required
    @ForeignKey(table = "trips.txt", field = "trip_id")
    String tripId();

    @Required
    @EndRange(field = "departure_time", allowEqual = true)
    GtfsTime arrivalTime();

    @Required
    GtfsTime departureTime();

    @FieldType(FieldTypeEnum.ID)
    @Index
    @Required
    @ForeignKey(table = "stops.txt", field = "stop_id")
    String stopId();

    @SequenceKey
    @Required
    @NonNegative
    int stopSequence();

    @ConditionallyRequired
    @CachedField
    String stopHeadsign();

    //OMITTED
    @ConditionallyRequired
    GtfsPickupType pickupType();

    //OMITTED
    @ConditionallyRequired
    GtfsDropOffType dropOffType();

    @ConditionallyRequired
    GtfsContinuousPickupDropOff continuousPickup();

    @ConditionallyRequired
    GtfsContinuousPickupDropOff continuousDropOff();

    @Required
    @NonNegative
    double shapeDistTraveled();

    @Required
    @DefaultValue("1")
    GtfsStopTimeTimepoint timepoint();
}
