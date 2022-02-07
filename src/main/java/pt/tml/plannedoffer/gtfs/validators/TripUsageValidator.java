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
import java.util.HashSet;
import java.util.Set;

/**
 * Validates that every trip in "trips.txt" is used by some stop from "stop_times.txt"
 *
 * <p>Generated notice: {@link UnusedTripNotice}.
 */
@GtfsValidator
public class TripUsageValidator extends FileValidator
{
    private final GtfsTripTableContainer tripTable;
    private final GtfsStopTimeTableContainer stopTimeTable;

    @Inject
    TripUsageValidator(GtfsTripTableContainer tripTable, GtfsStopTimeTableContainer stopTimeTable)
    {
        this.tripTable = tripTable;
        this.stopTimeTable = stopTimeTable;
    }

    @Override
    public void validate(NoticeContainer noticeContainer)
    {
        // Do not report the same trip_id multiple times.
        Set<String> reportedTrips = new HashSet<>();
        for (GtfsTrip trip : tripTable.getEntities())
        {
            String tripId = trip.tripId();
            if (reportedTrips.add(tripId) && stopTimeTable.byTripId(tripId).isEmpty())
            {
                noticeContainer.addValidationNotice(new UnusedTripNotice(tripId, trip.csvRowNumber()));
            }
        }
    }

    /**
     * A {@code GtfsTrip} should be referred to at least once in {@code GtfsStopTimeTableContainer}
     * station).
     *
     * <p>Severity: {@code SeverityLevel.WARNING}
     */
    static class UnusedTripNotice extends ValidationNotice
    {
        private final String tripId;
        private final long csvRowNumber;

        UnusedTripNotice(String tripId, long csvRowNumber)
        {
            super(SeverityLevel.WARNING);
            this.tripId = tripId;
            this.csvRowNumber = csvRowNumber;
        }
    }
}
