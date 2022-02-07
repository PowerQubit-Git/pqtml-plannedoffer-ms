package pt.tml.plannedoffer.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Data
@Table(name = "frequency_periods", schema = "ms_planned_offer")
@Entity
public class FrequencyPeriod
{
    @Id
    @Column(name = "frequency_period_id", nullable = false)
    private Long frequencyPeriodId;

    @javax.persistence.Convert(disableConversion = true)
    @Column(name = "start_time")
    private LocalTime startTime;

    @javax.persistence.Convert(disableConversion = true)
    @Column(name = "end_time")
    private LocalTime endTime;
}