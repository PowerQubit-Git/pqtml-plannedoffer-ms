--
-- Create Function "time_from_timestring"
--

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

alter function ms_intended_offer.time_from_timestring(varchar) owner to postgres;

