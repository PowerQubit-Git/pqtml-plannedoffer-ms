
create table if not exists ms_planned_offer.agency
(
    feed_id         varchar(255) not null,
    agency_id       varchar(255),
    agency_name     varchar(255),
    agency_url      varchar(255),
    agency_timezone varchar(255),
    agency_lang     varchar(255),
    csv_row_number  bigint       not null,
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.agency
    owner to postgres;

create table if not exists ms_planned_offer.calendar
(
    feed_id        varchar(255) not null,
    service_id     varchar(255),
    calendar_name  varchar(255),
    period         varchar(255),
    monday         integer,
    tuesday        integer,
    wednesday      integer,
    thursday       integer,
    friday         integer,
    saturday       integer,
    sunday         integer,
    start_date     date,
    end_date       date,
    csv_row_number bigint       not null,
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.calendar
    owner to postgres;

create table if not exists ms_planned_offer.calendar_dates
(
    feed_id        varchar(255) not null,
    service_id     varchar(255),
    calendar_name  varchar(255),
    holiday        integer,
    period         integer,
    date           date,
    csv_row_number bigint       not null,
    exception_type integer,
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.calendar_dates
    owner to postgres;

create table if not exists ms_planned_offer.feed_info
(
    feed_id             varchar(255) not null,
    feed_info_id        bigint,
    feed_publisher_name varchar(255),
    feed_publisher_url  varchar(255),
    feed_lang           varchar(255),
    feed_start_date     varchar(255),
    feed_end_date       varchar(255),
    feed_version        varchar(255),
    feed_desc           varchar(255),
    feed_remarks        varchar(255),
    csv_row_number      bigint       not null,
    feed_contact_email  varchar(255),
    feed_contact_url    varchar(255),
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.feed_info
    owner to postgres;

create table if not exists ms_planned_offer.frequencies
(
    feed_id            varchar(255) not null,
    trip_id            varchar(255),
    start_time         time,
    end_time           time,
    frequency          integer,
    typology           integer,
    propulsion         integer,
    passenger_counting integer,
    video_surveillance integer,
    csv_row_number     bigint       not null,
    exact_times        integer,
    headway_secs       integer,
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.frequencies
    owner to postgres;

create table if not exists ms_planned_offer.routes
(
    feed_id             varchar(255) not null,
    line_id             varchar(255),
    line_short_name     varchar(255),
    line_long_name      varchar(255),
    route_id            varchar(255),
    agency_id           varchar(255),
    route_origin        varchar(255),
    route_destination   varchar(255),
    route_short_name    varchar(255),
    route_long_name     varchar(255),
    route_desc          varchar(255),
    route_remarks       varchar(255),
    route_type          integer,
    contract            varchar(255),
    path_type           integer,
    circular            integer,
    school              integer,
    continuous_pickup   integer,
    continuous_drop_off integer,
    csv_row_number      bigint       not null,
    route_sort_order    integer,
    route_url           varchar(255),
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.routes
    owner to postgres;

create table if not exists ms_planned_offer.shapes
(
    feed_id             varchar(255) not null,
    line_id             varchar(255),
    shape_pt_lat        double precision,
    shape_pt_lon        double precision,
    shape_pt_sequence   integer,
    shape_dist_traveled double precision,
    csv_row_number      bigint       not null,
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.shapes
    owner to postgres;

create table if not exists ms_planned_offer.sp_rows_by_table
(
    gtfs_table varchar(255) not null
    constraint pk_sprowsbytable
    primary key,
    counter    integer
    );

alter table ms_planned_offer.sp_rows_by_table
    owner to postgres;

create table if not exists ms_planned_offer.sp_trips_by_line
(
    line_id varchar(255) not null
    constraint pk_sptripsbyline
    primary key,
    total   integer
    );

alter table ms_planned_offer.sp_trips_by_line
    owner to postgres;

create table if not exists ms_planned_offer.stop_times
(
    feed_id             varchar(255) not null,
    trip_id             varchar(255),
    arrival_time        varchar(255),
    departure_time      varchar(255),
    stop_id             varchar(255),
    stop_sequence       integer,
    stop_headsign       varchar(255),
    continuous_pickup   integer,
    continuous_drop_off integer,
    shape_dist_traveled double precision,
    pickup_type         integer,
    drop_off_type       integer,
    timepoint           integer,
    csv_row_number      bigint       not null,
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.stop_times
    owner to postgres;

create table if not exists ms_planned_offer.stops
(
    feed_id               varchar(255) not null,
    stop_id               varchar(255),
    stop_id_stepp         varchar(255),
    stop_code             varchar(255),
    stop_name             varchar(255),
    stop_desc             varchar(255),
    stop_remarks          varchar(255),
    stop_lat              double precision,
    stop_lon              double precision,
    zone_shift            integer,
    location_type         integer,
    parent_station        varchar(255),
    wheelchair_boarding   integer,
    platform_code         varchar(255),
    entrance_restriction  integer,
    exit_restriction      integer,
    slot                  integer,
    signalling            integer,
    shelter               integer,
    bench                 integer,
    network_map           integer,
    schedule              integer,
    real_time_information integer,
    tariff                integer,
    preservation_state    integer,
    equipment             varchar(255),
    observations          varchar(255),
    region                varchar(255),
    municipality          integer,
    municipality_fare1    integer,
    municipality_fare2    integer,
    csv_row_number        bigint       not null,
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.stops
    owner to postgres;

create table if not exists ms_planned_offer.trips
(
    feed_id               varchar(255) not null,
    route_id              varchar(255),
    service_id            varchar(255),
    trip_id               varchar(255),
    trip_first            time,
    trip_last             time,
    trip_headsign         varchar(255),
    direction_id          integer,
    shape_id              varchar(255),
    wheelchair_accessible integer,
    bikes_allowed         integer,
    csv_row_number        bigint       not null,
    block_id              varchar(255),
    trip_short_name       varchar(255),
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.trips
    owner to postgres;

create table if not exists ms_planned_offer.frequency_periods
(
    frequency_period_id bigint not null
    primary key,
    start_time          time,
    end_time            time
);

alter table ms_planned_offer.frequency_periods
    owner to postgres;

create table if not exists ms_planned_offer.attribution
(
    csv_row_number    bigint       not null,
    feed_id           varchar(255) not null,
    agency_id         varchar(255),
    attribution_email varchar(255),
    attribution_id    varchar(255),
    attribution_phone varchar(255),
    attribution_url   varchar(255),
    is_authority      integer,
    is_operator       integer,
    is_producer       integer,
    organization_name varchar(255),
    route_id          varchar(255),
    trip_id           varchar(255),
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.attribution
    owner to postgres;

create table if not exists ms_planned_offer.fare_attributes
(
    csv_row_number    bigint       not null,
    feed_id           varchar(255) not null,
    agency_id         varchar(255),
    currency_type     varchar(255),
    fare_id           varchar(255),
    payment_method    integer,
    price             numeric(19, 2),
    transfer_duration integer,
    transfers         integer,
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.fare_attributes
    owner to postgres;

create table if not exists ms_planned_offer.fare_rules
(
    csv_row_number bigint       not null,
    feed_id        varchar(255) not null,
    contains_id    varchar(255),
    destination_id varchar(255),
    fare_id        varchar(255),
    origin_id      varchar(255),
    route_id       varchar(255),
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.fare_rules
    owner to postgres;

create table if not exists ms_planned_offer.level
(
    csv_row_number bigint       not null,
    feed_id        varchar(255) not null,
    level_id       varchar(255),
    level_index    double precision,
    level_name     varchar(255),
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.level
    owner to postgres;

create table if not exists ms_planned_offer.pathway
(
    csv_row_number         bigint       not null,
    feed_id                varchar(255) not null,
    from_stop_id           varchar(255),
    is_bidirectional       integer,
    length                 double precision,
    max_slope              double precision,
    min_width              double precision,
    pathway_id             varchar(255),
    pathway_mode           integer,
    reversed_signposted_as varchar(255),
    signposted_as          varchar(255),
    stair_count            integer,
    to_stop_id             varchar(255),
    traversal_time         integer,
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.pathway
    owner to postgres;

create table if not exists ms_planned_offer.transfers
(
    csv_row_number    bigint       not null,
    feed_id           varchar(255) not null,
    from_stop_id      varchar(255),
    min_transfer_time varchar(255),
    to_stop_id        varchar(255),
    transfer_type     varchar(255),
    primary key (csv_row_number, feed_id)
    );

alter table ms_planned_offer.transfers
    owner to postgres;

