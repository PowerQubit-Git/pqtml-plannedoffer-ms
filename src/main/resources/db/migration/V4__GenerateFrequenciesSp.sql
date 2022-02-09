create
or replace procedure ms_planned_offer.sp_generate_frequencies(IN _feed_id character varying)
    language plpgsql
as
$$
BEGIN
    CREATE
TEMPORARY TABLE temp_seqs
    (
        feed_id varchar,
        trip_id varchar,
        min_seq int
    ) ON COMMIT DROP;

INSERT INTO temp_seqs
    (feed_id, trip_id, min_seq)
SELECT st.feed_id,
       st.trip_id,
       min(st.stop_sequence) AS mss
FROM ms_planned_offer.stop_times st
WHERE feed_id = _feed_id
    GROUP BY st.feed_id, st.trip_id;


IF
NOT exists(select 1 from ms_planned_offer.calendar c2 where c2.feed_id = _feed_id) AND
       NOT exists(select 1 from ms_planned_offer.calendar_dates d where d.feed_id = _feed_id)
    then
        -- create a silly calendar
        insert into ms_planned_offer.calendar as c3
        (csv_row_number,
         feed_id,
         calendar_name,
         end_date,
         friday,
         monday,
         period,
         saturday,
         service_id,
         start_date,
         sunday,
         thursday,
         tuesday,
         wednesday)

SELECT row_number() over (),
               _feed_id,
               concat('silly_', _feed_id),
               now(),
               1,
               1,
               1,
               1,
               t2.service_id,
               now() + interval '1 year',
               1,
               1,
               1,
               1

FROM ms_planned_offer.trips as t2
WHERE feed_id = _feed_id
group by service_id, _feed_id;
end if;

DELETE
FROM ms_planned_offer.frequencies
WHERE feed_id = _feed_id;

WITH calendar_base AS (
    SELECT c.service_id
    FROM ms_planned_offer.calendar c --TODO: Coerce to feed_id and join to other tables
    UNION
    SELECT cd.service_id
    FROM ms_planned_offer.calendar_dates cd
),
     base AS (
         SELECT r.route_id,
                t.trip_id,
                st.arrival_time,
                st.departure_time,
                t.service_id,
                a.feed_id
         FROM ms_planned_offer.agency a
                  JOIN ms_planned_offer.routes r
                       ON a.agency_id::text = r.agency_id::text AND a.feed_id::text = r.feed_id::text AND
    a.feed_id::text = _feed_id::text
    JOIN ms_planned_offer.trips t
ON r.route_id::text = t.route_id::text AND r.feed_id::text = t.feed_id::text
    JOIN ms_planned_offer.stop_times st
    ON t.trip_id::text = st.trip_id::text AND t.feed_id::text = st.feed_id::text
    JOIN calendar_base cb on t.service_id = CAST (cb.service_id as varchar)
    JOIN temp_seqs tmp
    ON tmp.feed_id = st.feed_id and tmp.trip_id = st.trip_id and tmp.min_seq = st.stop_sequence
    )
INSERT
INTO ms_planned_offer.frequencies as f (trip_id, start_time, end_time, frequency, csv_row_number, feed_id)

SELECT concat(b.route_id, '|', b.service_id) AS trip_id,
       fp.start_time,
       fp.end_time,
       count(*)                              AS frequency,
       ROW_NUMBER()                             OVER (PARTITION BY null), b.feed_id

FROM ms_planned_offer.frequency_periods fp
         JOIN base b
              ON ((fp.start_time, fp.end_time) OVERLAPS
                  (ms_planned_offer.time_from_timestring(b.departure_time), '00:00:00'::interval))
GROUP BY b.route_id, b.service_id, fp.start_time, fp.end_time, (concat(b.route_id, '|', b.service_id)), b.feed_id;

END

$$;

alter procedure ms_planned_offer.sp_generate_frequencies(varchar) owner to postgres;

