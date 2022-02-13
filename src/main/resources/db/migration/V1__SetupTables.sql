create table ms_planned_offer.frequency_periods
(
    frequency_period_id bigint not null
        primary key,
    end_time            time,
    start_time          time
);

alter table ms_planned_offer.frequency_periods
    owner to postgres;

create table ms_planned_offer.agency
(
    csv_row_number  bigint       not null,
    feed_id         varchar(255) not null,
    agency_id       varchar(255),
    agency_lang     varchar(255),
    agency_name     varchar(255),
    agency_timezone bytea,
    agency_url      varchar(255),
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.agency
    owner to postgres;

create table ms_planned_offer.attribution
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

create table ms_planned_offer.blocks
(
    csv_row_number     bigint       not null,
    feed_id            varchar(255) not null,
    available_seats    integer,
    available_standing integer,
    block_id           varchar(255),
    classes            integer,
    emission           integer,
    lowered_floor      integer,
    propulsion         integer,
    registration_date  varchar(255),
    typology           integer,
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.blocks
    owner to postgres;

create table ms_planned_offer.calendar
(
    csv_row_number bigint       not null,
    feed_id        varchar(255) not null,
    calendar_name  varchar(255),
    end_date       date,
    friday         integer,
    monday         integer,
    period         varchar(255),
    saturday       integer,
    service_id     varchar(255),
    start_date     date,
    sunday         integer,
    thursday       integer,
    tuesday        integer,
    wednesday      integer,
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.calendar
    owner to postgres;

create table ms_planned_offer.calendar_dates
(
    csv_row_number bigint       not null,
    feed_id        varchar(255) not null,
    calendar_name  varchar(255),
    date           date,
    exception_type integer,
    holiday        integer,
    period         integer,
    service_id     varchar(255),
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.calendar_dates
    owner to postgres;

create table ms_planned_offer.dead_runs
(
    csv_row_number    bigint       not null,
    feed_id           varchar(255) not null,
    agency_id         varchar(255),
    block_id          varchar(255),
    dead_run_id       varchar(255),
    dist_traveled     integer,
    end_location_id   varchar(255),
    end_time          varchar(255),
    overtime          integer,
    service_id        varchar(255),
    shift_id          varchar(255),
    start_location_id varchar(255),
    start_time        varchar(255),
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.dead_runs
    owner to postgres;

create table ms_planned_offer.fare_attributes
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

create table ms_planned_offer.fare_rules
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

create table ms_planned_offer.feed_info
(
    csv_row_number      bigint       not null,
    feed_id             varchar(255) not null,
    feed_contact_email  varchar(255),
    feed_contact_url    varchar(255),
    feed_desc           varchar(255),
    feed_end_date       varchar(255),
    feed_info_id        bigint,
    feed_lang           varchar(255),
    feed_publisher_name varchar(255),
    feed_publisher_url  varchar(255),
    feed_remarks        varchar(255),
    feed_start_date     varchar(255),
    feed_version        varchar(255),
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.feed_info
    owner to postgres;

create table ms_planned_offer.frequencies
(
    csv_row_number     bigint       not null,
    feed_id            varchar(255) not null,
    end_time           time,
    exact_times        integer,
    frequency          integer,
    headway_secs       integer,
    passenger_counting integer,
    propulsion         integer,
    start_time         time,
    trip_id            varchar(255),
    typology           integer,
    video_surveillance integer,
    agency_id          varchar(255),
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.frequencies
    owner to postgres;

create table ms_planned_offer.layovers
(
    csv_row_number bigint       not null,
    feed_id        varchar(255) not null,
    agency_id      varchar(255),
    block_id       varchar(255),
    end_duration   integer,
    end_overtime   integer,
    end_shift_id   varchar(255),
    end_time       varchar(255),
    layover_id     varchar(255),
    location_id    varchar(255),
    service_id     varchar(255),
    start_overtime integer,
    start_shift_id varchar(255),
    start_time     varchar(255),
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.layovers
    owner to postgres;

create table ms_planned_offer.level
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

create table ms_planned_offer.pathway
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

create table ms_planned_offer.routes
(
    csv_row_number      bigint       not null,
    feed_id             varchar(255) not null,
    line_short_name     varchar(255),
    agency_id           varchar(255),
    circular            integer,
    continuous_drop_off integer,
    continuous_pickup   integer,
    contract            varchar(255),
    line_id             varchar(255),
    line_long_name      varchar(255),
    path_type           integer,
    route_desc          varchar(255),
    route_destination   varchar(255),
    route_id            varchar(255),
    route_long_name     varchar(255),
    route_origin        varchar(255),
    route_remarks       varchar(255),
    route_short_name    varchar(255),
    route_sort_order    integer,
    route_type          integer,
    route_url           varchar(255),
    school              integer,
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.routes
    owner to postgres;

create table ms_planned_offer.schedules
(
    csv_row_number bigint       not null,
    feed_id        varchar(255) not null,
    block_id       varchar(255),
    driver_id      varchar(255),
    service_id     varchar(255),
    shift_id       varchar(255),
    vehicle_id     varchar(255),
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.schedules
    owner to postgres;

create table ms_planned_offer.shapes
(
    csv_row_number      bigint       not null,
    feed_id             varchar(255) not null,
    shape_dist_traveled double precision,
    line_id             varchar(255),
    shape_pt_lat        double precision,
    shape_pt_lon        double precision,
    shape_pt_sequence   integer,
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.shapes
    owner to postgres;

create table ms_planned_offer.stop_times
(
    csv_row_number      bigint       not null,
    feed_id             varchar(255) not null,
    arrival_time        varchar(255),
    continuous_drop_off integer,
    continuous_pickup   integer,
    departure_time      varchar(255),
    drop_off_type       integer,
    pickup_type         integer,
    shape_dist_traveled double precision,
    stop_headsign       varchar(255),
    stop_id             varchar(255),
    stop_sequence       integer,
    timepoint           integer,
    trip_id             varchar(255),
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.stop_times
    owner to postgres;

create table ms_planned_offer.stops
(
    csv_row_number        bigint       not null,
    feed_id               varchar(255) not null,
    bench                 integer,
    entrance_restriction  integer,
    equipment             varchar(255),
    exit_restriction      integer,
    location_type         integer,
    municipality          integer,
    municipality_fare1    integer,
    municipality_fare2    integer,
    network_map           integer,
    observations          varchar(255),
    parent_station        varchar(255),
    platform_code         varchar(255),
    preservation_state    integer,
    real_time_information integer,
    region                varchar(255),
    schedule              integer,
    shelter               integer,
    signalling            integer,
    slot                  integer,
    stop_code             varchar(255),
    stop_desc             varchar(255),
    stop_id               varchar(255),
    stop_id_stepp         varchar(255),
    stop_lat              double precision,
    stop_lon              double precision,
    stop_name             varchar(255),
    stop_remarks          varchar(255),
    tariff                integer,
    wheelchair_boarding   integer,
    zone_shift            integer,
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.stops
    owner to postgres;

create table ms_planned_offer.transfers
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

create table ms_planned_offer.trips
(
    csv_row_number        bigint       not null,
    feed_id               varchar(255) not null,
    bikes_allowed         integer,
    block_id              varchar(255),
    direction_id          integer,
    route_id              varchar(255),
    service_id            varchar(255),
    shape_id              varchar(255),
    trip_first            time,
    trip_headsign         varchar(255),
    trip_id               varchar(255),
    trip_last             time,
    trip_short_name       varchar(255),
    wheelchair_accessible integer,
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.trips
    owner to postgres;

create table ms_planned_offer.vehicles
(
    csv_row_number      bigint       not null,
    feed_id             varchar(255) not null,
    agency_id           varchar(255),
    available_seats     integer,
    available_standings integer,
    bicycles            integer,
    classes             integer,
    climatization       integer,
    consumption_meter   integer,
    corridor            integer,
    ecological          integer,
    emission            integer,
    end_date            varchar(255),
    external_sound      integer,
    folding_system      integer,
    front_display       integer,
    internal_sound      integer,
    kneeling            integer,
    license_plate       varchar(255),
    lowered_floor       integer,
    make                varchar(255),
    model               varchar(255),
    new_seminew         integer,
    onboard_monitor     integer,
    passenger_counting  integer,
    propulsion          integer,
    ramp                integer,
    rear_display        integer,
    registration_date   varchar(255),
    side_display        integer,
    start_date          varchar(255),
    static_information  integer,
    typology            integer,
    vehicle_id          varchar(255),
    video_surveillance  integer,
    wheelchair          integer,
    primary key (csv_row_number, feed_id)
);

alter table ms_planned_offer.vehicles
    owner to postgres;

