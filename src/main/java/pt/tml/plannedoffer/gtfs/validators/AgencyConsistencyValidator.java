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

package pt.tml.plannedoffer.gtfs.validators;

import pt.powerqubit.validator.core.annotation.GtfsValidator;
import pt.powerqubit.validator.core.notice.MissingRequiredFieldNotice;
import pt.powerqubit.validator.core.notice.NoticeContainer;
import pt.powerqubit.validator.core.notice.SeverityLevel;
import pt.powerqubit.validator.core.notice.ValidationNotice;
import pt.powerqubit.validator.core.table.GtfsAgency;
import pt.powerqubit.validator.core.table.GtfsAgencyTableContainer;
import pt.powerqubit.validator.core.table.GtfsAgencyTableLoader;
import pt.powerqubit.validator.core.validator.FileValidator;

import javax.inject.Inject;

/**
 * Validates that all agencies have the same timezone and language and that agency_id field is set
 * if there is more than 1 agency.
 *
 * <p>Generated notices:
 *
 * <ul>
 *   <li>{@link MissingRequiredFieldNotice} - multiple agencies present but no agency_id set
 *   <li>{@link InconsistentAgencyTimezoneNotice} - inconsistent timezone among the agencies
 *   <li>{@link InconsistentAgencyLangNotice} - inconsistent language among the agencies
 * </ul>
 */
@GtfsValidator
public class AgencyConsistencyValidator extends FileValidator
{
    private final GtfsAgencyTableContainer agencyTable;

    @Inject
    AgencyConsistencyValidator(GtfsAgencyTableContainer agencyTable)
    {
        this.agencyTable = agencyTable;
    }

    @Override
    public void validate(NoticeContainer noticeContainer)
    {
        final int agencyCount = agencyTable.entityCount();
        if (agencyCount < 2)
        {
            return;
        }

        for (GtfsAgency agency : agencyTable.getEntities())
        {
            // agency_id is required when there are 2 or more agencies.
            if (!agency.hasAgencyId())
            {
                noticeContainer.addValidationNotice(
                        new MissingRequiredFieldNotice(
                                agencyTable.gtfsFilename(),
                                agency.csvRowNumber(),
                                GtfsAgencyTableLoader.AGENCY_ID_FIELD_NAME));
            }
        }
    }

    /**
     * Inconsistent language among agencies.
     *
     * <p>Severity: {@code SeverityLevel.WARNING}
     */
    static class InconsistentAgencyLangNotice extends ValidationNotice
    {
        private final long csvRowNumber;
        private final String expected;
        private final String actual;

        InconsistentAgencyLangNotice(long csvRowNumber, String expected, String actual)
        {
            super(SeverityLevel.WARNING);
            this.csvRowNumber = csvRowNumber;
            this.expected = expected;
            this.actual = actual;
        }
    }

    /**
     * Inconsistent timezone among agencies.
     *
     * <p>Severity: {@code SeverityLevel.ERROR}
     */
    static class InconsistentAgencyTimezoneNotice extends ValidationNotice
    {
        private final long csvRowNumber;
        private final String expected;
        private final String actual;

        InconsistentAgencyTimezoneNotice(long csvRowNumber, String expected, String actual)
        {
            super(SeverityLevel.ERROR);
            this.csvRowNumber = csvRowNumber;
            this.expected = expected;
            this.actual = actual;
        }
    }
}
