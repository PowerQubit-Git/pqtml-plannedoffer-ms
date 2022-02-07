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

import pt.powerqubit.validator.core.annotation.CachedField;
import pt.powerqubit.validator.core.annotation.ConditionallyRequired;
import pt.powerqubit.validator.core.annotation.GtfsTable;
import pt.powerqubit.validator.core.table.GtfsEntity;

import java.util.Locale;

@GtfsTable("translations.txt")
@ConditionallyRequired
public interface GtfsTranslationSchema extends GtfsEntity
{

    //OMITTED
    @ConditionallyRequired
    @CachedField
    String tableName();

    //OMITTED
    @ConditionallyRequired
    @CachedField
    String fieldName();

    //OMITTED
    @ConditionallyRequired
    Locale language();

    //OMITTED
    @ConditionallyRequired
    String translation();

    //OMITTED
    @ConditionallyRequired
    @CachedField
    String recordId();

    //OMITTED
    @ConditionallyRequired
    @CachedField
    String recordSubId();

    //OMITTED
    @ConditionallyRequired
    @CachedField
    String fieldValue();

}
