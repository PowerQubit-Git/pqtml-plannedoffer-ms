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
import pt.powerqubit.validator.core.table.GtfsEntity;
import pt.powerqubit.validator.core.type.GtfsDate;

import java.util.Locale;

@GtfsTable(value = "feed_info.txt", singleRow = true)
public interface GtfsFeedInfoSchema extends GtfsEntity
{

    //OMITTED
    @ConditionallyRequired
    String feedPublisherName();

    //OMITTED
    @FieldType(FieldTypeEnum.URL)
    @ConditionallyRequired
    String feedPublisherUrl();

    //OMITTED
    @ConditionallyRequired
    Locale feedLang();

    //OMITTED
    @ConditionallyRequired
    Locale defaultLang();


    @EndRange(field = "feed_end_date", allowEqual = true)
    @ConditionallyRequired
    GtfsDate feedStartDate();

    @ConditionallyRequired
    GtfsDate feedEndDate();

    @ConditionallyRequired
    String feedVersion();

    @ConditionallyRequired
    String feedDesc();

    @ConditionallyRequired
    String feedRemarks();

    //OMITTED
    @FieldType(FieldTypeEnum.EMAIL)
    @ConditionallyRequired
    String feedContactEmail();

    //OMITTED
    @FieldType(FieldTypeEnum.URL)
    @ConditionallyRequired
    String feedContactUrl();

}
