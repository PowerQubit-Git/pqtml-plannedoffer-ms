package pt.tml.plannedoffer.tables;

import pt.powerqubit.validator.core.annotation.GtfsEnumValue;

@GtfsEnumValue(name = "Not_specified", value = 0)
@GtfsEnumValue(name = "School_period", value = 1)
@GtfsEnumValue(name = "School_holidays", value = 2)
public interface GtfsPeriodEnum
{
}