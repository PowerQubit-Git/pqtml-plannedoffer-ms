create or replace function ms_planned_offer.time_from_timestring(time_string character varying) returns time without time zone
    language plpgsql
as
$$
declare
res time without time zone;
    declare hh int;
    declare mm int;
    declare ss int;
begin
    hh:=cast(split_part(time_string, ':', 1) as int);
    mm:=cast(split_part(time_string, ':', 2) as int);
    ss:=cast(split_part(time_string, ':', 3) as int);

    if hh>23 then
        hh= hh-24;
end if;

    res = make_time(hh,mm,ss);
return res;
end;
$$;

alter function ms_planned_offer.time_from_timestring(varchar) owner to postgres;

create or replace procedure ms_planned_offer.generate_frequency_periods()
    language plpgsql
as
$$
BEGIN

    DECLARE
stime time := '00:00';
        span  time := interval '30 minute';



begin

TRUNCATE TABLE ms_planned_offer.frequency_periods;

for counter in 0..47
            loop
                INSERT INTO ms_planned_offer.frequency_periods(frequency_period_id, start_time, end_time)
                values (counter+1, stime + counter * span , (stime + (counter+1) * span) - interval '1 second');
end loop;
end;


END

$$;

alter procedure ms_planned_offer.generate_frequency_periods() owner to postgres;

create or replace procedure ms_planned_offer.sp_generate_frequencies(IN _feed_id character varying)
    language plpgsql
as
$$
BEGIN
    CREATE TEMPORARY TABLE temp_seqs
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


IF NOT exists(select 1 from ms_planned_offer.calendar c2 where c2.feed_id = _feed_id) AND
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

DELETE FROM ms_planned_offer.frequencies WHERE feed_id= _feed_id;

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
                a.feed_id,
                a.agency_id
         FROM ms_planned_offer.agency a
                  JOIN ms_planned_offer.routes r
                       ON a.agency_id::text = r.agency_id::text AND a.feed_id::text = r.feed_id::text AND
    a.feed_id::text = _feed_id::text
    JOIN ms_planned_offer.trips t
ON r.route_id::text = t.route_id::text AND r.feed_id::text = t.feed_id::text
    JOIN ms_planned_offer.stop_times st
    ON t.trip_id::text = st.trip_id::text AND t.feed_id::text = st.feed_id::text
    JOIN calendar_base cb on t.service_id = CAST(cb.service_id as varchar)
    JOIN temp_seqs tmp
    ON tmp.feed_id = st.feed_id and tmp.trip_id = st.trip_id and tmp.min_seq = st.stop_sequence
    )
INSERT
INTO ms_planned_offer.frequencies as f (trip_id, start_time, end_time, frequency, csv_row_number, feed_id, agency_id)

SELECT concat(b.route_id, '|', b.service_id) AS trip_id,
       fp.start_time,
       fp.end_time,
       count(*)                              AS frequency,
       ROW_NUMBER() OVER (PARTITION BY null),
        b.feed_id,
       b.agency_id


FROM ms_planned_offer.frequency_periods fp
         JOIN base b
              ON ((fp.start_time, fp.end_time) OVERLAPS
                  (ms_planned_offer.time_from_timestring(b.departure_time), '00:00:00'::interval))
GROUP BY b.route_id, b.service_id, fp.start_time, fp.end_time, (concat(b.route_id, '|', b.service_id)), b.feed_id, b.agency_id;

END

$$;

alter procedure ms_planned_offer.sp_generate_frequencies(varchar) owner to postgres;

create or replace procedure ms_planned_offer.sp_find_erase_plan_tables()
    language plpgsql
as
$$
DECLARE
rec record;
BEGIN

    RAISE NOTICE 'create or replace procedure ms_planned_offer.sp_erase_plan(IN _feed_id character varying) language plpgsql as  \$\$ BEGIN';

FOR rec IN
SELECT *
FROM pg_tables
WHERE tablename NOT LIKE 'pg\_%'
  AND tablename NOT LIKE 'flyway%'
  AND tablename NOT LIKE 'sp_%'
  AND tablename NOT LIKE 'frequency_periods%'
  AND schemaname = 'ms_planned_offer'
ORDER BY tablename
    LOOP
            RAISE NOTICE 'DELETE FROM %.% WHERE feed_id = _feed_id;', quote_ident(rec.schemaname),quote_ident(rec.tablename) ;
END LOOP;

    RAISE NOTICE
        'END \$\$ ;';
    RAISE NOTICE 'alter procedure ms_planned_offer.sp_erase_plan(varchar) owner to postgres;';
END
$$;

alter procedure ms_planned_offer.sp_find_erase_plan_tables() owner to postgres;

create or replace procedure ms_planned_offer.sp_erase_plan(IN _feed_id character varying)
    language plpgsql
as
$$
BEGIN
DELETE FROM ms_planned_offer.agency WHERE feed_id = _feed_id;
DELETE FROM ms_planned_offer.calendar WHERE feed_id = _feed_id;
DELETE FROM ms_planned_offer.calendar_dates WHERE feed_id = _feed_id;
DELETE FROM ms_planned_offer.feed_info WHERE feed_id = _feed_id;
DELETE FROM ms_planned_offer.frequencies WHERE feed_id = _feed_id;
DELETE FROM ms_planned_offer.routes WHERE feed_id = _feed_id;
DELETE FROM ms_planned_offer.shapes WHERE feed_id = _feed_id;
DELETE FROM ms_planned_offer.stop_times WHERE feed_id = _feed_id;
DELETE FROM ms_planned_offer.stops WHERE feed_id = _feed_id;
DELETE FROM ms_planned_offer.trips WHERE feed_id = _feed_id;
END
$$;

alter procedure ms_planned_offer.sp_erase_plan(varchar) owner to postgres;

