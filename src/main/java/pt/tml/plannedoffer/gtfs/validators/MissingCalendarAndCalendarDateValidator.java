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
import pt.powerqubit.validator.core.table.GtfsCalendarDateTableContainer;
import pt.powerqubit.validator.core.table.GtfsCalendarTableContainer;
import pt.powerqubit.validator.core.validator.FileValidator;

import javax.inject.Inject;

/**
 * Validates at least one of the following files is provided: `calendar.txt` and
 * `calendar_dates.txt`.
 *
 * <p>Generated notices:
 *
 * <ul>
 *   <li>{@link MissingCalendarAndCalendarDateFilesNotice}.
 * </ul>
 */
@GtfsValidator
public class MissingCalendarAndCalendarDateValidator extends FileValidator
{
    private final GtfsCalendarTableContainer calendarTable;

    private final GtfsCalendarDateTableContainer calendarDateTable;

    @Inject
    MissingCalendarAndCalendarDateValidator(
            GtfsCalendarTableContainer calendarTable, GtfsCalendarDateTableContainer calendarDateTable)
    {
        this.calendarTable = calendarTable;
        this.calendarDateTable = calendarDateTable;
    }

    @Override
    public void validate(NoticeContainer noticeContainer)
    {
        if (calendarTable.isMissingFile() && calendarDateTable.isMissingFile())
        {
            noticeContainer.addValidationNotice(new MissingCalendarAndCalendarDateFilesNotice());
        }
    }

    /**
     * GTFS files `calendar.txt` and `calendar_dates.txt` cannot be missing from the GTFS archive.
     *
     * <p>Severity: {@code SeverityLevel.ERROR}
     */
    static class MissingCalendarAndCalendarDateFilesNotice extends ValidationNotice
    {
        MissingCalendarAndCalendarDateFilesNotice()
        {
            super(SeverityLevel.ERROR);
        }
    }
}
