/*
 * Copyright 2021 MobilityData IO
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

package pt.tml.plannedoffer.gtfs.validators;

import pt.powerqubit.validator.core.annotation.GtfsValidator;
import pt.powerqubit.validator.core.notice.NoticeContainer;
import pt.powerqubit.validator.core.notice.SeverityLevel;
import pt.powerqubit.validator.core.notice.ValidationNotice;
import pt.powerqubit.validator.core.table.GtfsStopTimeTableContainer;
import pt.powerqubit.validator.core.table.GtfsTrip;
import pt.powerqubit.validator.core.table.GtfsTripTableContainer;
import pt.powerqubit.validator.core.validator.FileValidator;

import javax.inject.Inject;

/**
 * Validates that every trip in "trips.txt" is used by at least two stops from "stop_times.txt"
 *
 * <p>Generated notice: {@link UnusableTripNotice}.
 */
@GtfsValidator
public class TripUsabilityValidator extends FileValidator
{
    private final GtfsTripTableContainer tripTable;
    private final GtfsStopTimeTableContainer stopTimeTable;

    @Inject
    TripUsabilityValidator(
            GtfsTripTableContainer tripTable, GtfsStopTimeTableContainer stopTimeTable)
    {
        this.tripTable = tripTable;
        this.stopTimeTable = stopTimeTable;
    }

    @Override
    public void validate(NoticeContainer noticeContainer)
    {
        for (GtfsTrip trip : tripTable.getEntities())
        {
            String tripId = trip.tripId();
            if (stopTimeTable.byTripId(tripId).size() <= 1)
            {
                noticeContainer.addValidationNotice(new UnusableTripNotice(trip.csvRowNumber(), tripId));
            }
        }
    }

    /**
     * A {@code GtfsTrip} should be referred to by at least two {@code GtfsStopTime}
     *
     * <p>Severity: {@code SeverityLevel.WARNING}
     */
    static class UnusableTripNotice extends ValidationNotice
    {
        private final long csvRowNumber;
        private final String tripId;

        UnusableTripNotice(long csvRowNumber, String tripId)
        {
            super(SeverityLevel.WARNING);
            this.csvRowNumber = csvRowNumber;
            this.tripId = tripId;
        }
    }
}
