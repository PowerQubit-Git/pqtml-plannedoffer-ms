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
import pt.powerqubit.validator.core.type.GtfsDate;

@GtfsTable("vehicles.txt")
@Required
public interface GtfsVehiclesSchema extends GtfsEntity
{
    @FieldType(FieldTypeEnum.ID)
    @PrimaryKey
    @Required
    String vehicleId();

    @FieldType(FieldTypeEnum.ID)
    @Required
    @ForeignKey(table = "agency.txt", field = "agency_id")
    String agencyId();

    @ConditionallyRequired
    GtfsDate startDate();

    @ConditionallyRequired
    GtfsDate endDate();

    @Required
    String licensePlate();

    @Required
    String make();

    @Required
    String model();

    @Required
    GtfsDate registrationDate();

    @Required
    int availableSeats();

    @Required
    int availableStandings();

    @Required
    GtfsTypology typology();

    @Required
    GtfsClasses classes();

    @Required
    GtfsPropulsion propulsion();

    @Required
    GtfsEmissions emission();

    @Required
    GtfsNewSeminew newSeminew();

    @Required
    GtfsEcological ecological();

    @Required
    GtfsClimatization climatization();

    @Required
    GtfsWheelchair wheelchair();

    @Required
    GtfsCorridor corridor();

    @Required
    GtfsLoweredFloor loweredFloor();

    @Required
    GtfsRamp ramp();

    @Required
    GtfsFoldingSystem foldingSystem();

    @Required
    GtfsKneeling kneeling();

    @Required
    GtfsStaticInformation staticInformation();

    @Required
    GtfsOnBoardMonitor onboardMonitor();

    @Required
    GtfsFrontDisplay frontDisplay();

    @Required
    GtfsRearDisplay  rearDisplay();

    @Required
    GtfsSideDisplay  sideDisplay();

    @Required
    GtfsInternalSound  internalSound();

    @Required
    GtfsExternalSound  externalSound();

    @Required
    GtfsConsumptionMeter  consumptionMeter();

    @Required
    GtfsBicycles bicycles();

    @ConditionallyRequired
    GtfsPassengerCounting  passengerCounting();

    @ConditionallyRequired
    GtfsVideoSurveillance  videoSurveillance();

}
