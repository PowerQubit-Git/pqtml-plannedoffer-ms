CREATE
OR REPLACE PROCEDURE ms_planned_offer.generate_frequency_periods() AS
$$
BEGIN

    DECLARE
stime time := '00:00';
        span
time := interval '30 minute';



begin

TRUNCATE TABLE ms_planned_offer.frequency_periods;

for counter in 0..47
            loop
                INSERT INTO ms_planned_offer.frequency_periods(frequency_period_id, start_time, end_time)
                values (counter+1, stime + counter * span , (stime + (counter+1) * span) - interval '1 second');
end loop;
end;


END

$$
LANGUAGE plpgsql;

--
-- Generate table values
--
CALL ms_planned_offer.generate_frequency_periods();