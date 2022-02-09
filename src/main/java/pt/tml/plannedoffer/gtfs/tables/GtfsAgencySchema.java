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
import pt.powerqubit.validator.core.table.GtfsEntity;

import java.time.ZoneId;
import java.util.Locale;

@GtfsTable("agency.txt")
@Required
public interface GtfsAgencySchema extends GtfsEntity
{
    @FieldType(FieldTypeEnum.ID)
    @PrimaryKey
    @ConditionallyRequired
    String agencyId();

    @Required
    String agencyName();

    @FieldType(FieldTypeEnum.URL)
    String agencyUrl();

    ZoneId agencyTimezone();

    @ConditionallyRequired
    Locale agencyLang();

    @FieldType(FieldTypeEnum.PHONE_NUMBER)
    @ConditionallyRequired
    String agencyPhone();

    @FieldType(FieldTypeEnum.URL)
    @ConditionallyRequired
    String agencyFareUrl();

    @FieldType(FieldTypeEnum.EMAIL)
    @ConditionallyRequired
    String agencyEmail();

}
