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

import com.google.common.collect.Multimaps;
import pt.powerqubit.validator.core.annotation.GtfsValidator;
import pt.powerqubit.validator.core.notice.NoticeContainer;
import pt.powerqubit.validator.core.notice.SeverityLevel;
import pt.powerqubit.validator.core.notice.ValidationNotice;
import pt.powerqubit.validator.core.table.GtfsShape;
import pt.powerqubit.validator.core.table.GtfsShapeTableContainer;
import pt.powerqubit.validator.core.validator.FileValidator;

import javax.inject.Inject;
import java.util.List;

/**
 * Validates that shape_dist_traveled along a shape in "shapes.txt" are not decreasing.
 *
 * <p>Generated notice: {@link DecreasingOrEqualShapeDistanceNotice}.
 */
@GtfsValidator
public class ShapeIncreasingDistanceValidator extends FileValidator
{
    private final GtfsShapeTableContainer table;

    @Inject
    ShapeIncreasingDistanceValidator(GtfsShapeTableContainer table)
    {
        this.table = table;
    }

    @Override
    public void validate(NoticeContainer noticeContainer)
    {
        for (List<GtfsShape> shapeList : Multimaps.asMap(table.byShapeIdMap()).values())
        {
            // GtfsShape objects are sorted based on @SequenceKey annotation on shape_pt_sequence field.
            for (int i = 1; i < shapeList.size(); ++i)
            {
                GtfsShape prev = shapeList.get(i - 1);
                GtfsShape curr = shapeList.get(i);
                if (prev.hasShapeDistTraveled()
                        && curr.hasShapeDistTraveled()
                        && prev.shapeDistTraveled() >= curr.shapeDistTraveled())
                {
                    noticeContainer.addValidationNotice(
                            new DecreasingOrEqualShapeDistanceNotice(
                                    curr.shapeId(),
                                    curr.csvRowNumber(),
                                    curr.shapeDistTraveled(),
                                    curr.shapePtSequence(),
                                    prev.csvRowNumber(),
                                    prev.shapeDistTraveled(),
                                    prev.shapePtSequence()));
                }
            }
        }
    }

    /**
     * When sorted on `shapes.shape_pt_sequence` key, shape points should have strictly increasing
     * values for `shapes.shape_dist_traveled`
     *
     * <p>"Values must increase along with shape_pt_sequence."
     * (http://gtfs.org/reference/static/#shapestxt)
     *
     * <p>Severity: {@code SeverityLevel.ERROR}
     */
    static class DecreasingOrEqualShapeDistanceNotice extends ValidationNotice
    {
        private final String shapeId;
        private final long csvRowNumber;
        private final double shapeDistTraveled;
        private final int shapePtSequence;
        private final long prevCsvRowNumber;
        private final double prevShapeDistTraveled;
        private final int prevShapePtSequence;

        DecreasingOrEqualShapeDistanceNotice(
                String shapeId,
                long csvRowNumber,
                double shapeDistTraveled,
                int shapePtSequence,
                long prevCsvRowNumber,
                double prevShapeDistTraveled,
                int prevShapePtSequence)
        {
            super(SeverityLevel.ERROR);
            this.shapeId = shapeId;
            this.csvRowNumber = csvRowNumber;
            this.shapeDistTraveled = shapeDistTraveled;
            this.shapePtSequence = shapePtSequence;
            this.prevCsvRowNumber = prevCsvRowNumber;
            this.prevShapeDistTraveled = prevShapeDistTraveled;
            this.prevShapePtSequence = prevShapePtSequence;
        }
    }
}
