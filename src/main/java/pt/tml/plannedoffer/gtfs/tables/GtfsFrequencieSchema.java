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

@GtfsTable("frequencies.txt")
@ConditionallyRequired
public interface GtfsFrequencieSchema extends GtfsEntity
{

    //OMITTED
    @ForeignKey(table = "trips.txt", field = "trip_id")
    @Index
    @ConditionallyRequired
    String tripId();

    //OMITTED
    @EndRange(field = "end_time", allowEqual = false)
    @ConditionallyRequired
    GtfsTime startTime();

    //OMITTED
    @ConditionallyRequired
    GtfsTime endTime();

    //OMITTED
    @Positive
    @ConditionallyRequired
    int headwaySecs();

    //OMITTED
    @NonNegative
    @ConditionallyRequired
    int frequency();

    //OMITTED
    @ConditionallyRequired
    GtfsFrequencyExactTimes exactTimes();

    //OMITTED
    @ConditionallyRequired
    GtfsTypology typology();

    //OMITTED
    @ConditionallyRequired
    GtfsPropulsion propulsion();

    //OMITTED
    @ConditionallyRequired
    GtfsPassengerCounting passengerCounting();

    //OMITTED
    @ConditionallyRequired
    GtfsVideoSurveillance videoSurveillance();

}
