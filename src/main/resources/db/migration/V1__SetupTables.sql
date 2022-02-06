CREATE TABLE agency
(
    feed_id         VARCHAR(255) NOT NULL,
    agency_id       VARCHAR(255),
    agency_name     VARCHAR(255),
    agency_url      VARCHAR(255),
    agency_timezone VARCHAR(255),
    agency_lang     VARCHAR(255),
    csv_row_number  BIGINT       NOT NULL,
    primary key (csv_row_number, feed_id)
);

CREATE TABLE calendar
(
    feed_id        VARCHAR(255) NOT NULL,
    service_id     VARCHAR(255),
    calendar_name  VARCHAR(255),
    period VARCHAR (255),
    monday         INTEGER,
    tuesday        INTEGER,
    wednesday      INTEGER,
    thursday       INTEGER,
    friday         INTEGER,
    saturday       INTEGER,
    sunday         INTEGER,
    start_date     date,
    end_date       date,
    csv_row_number BIGINT       NOT NULL,
    primary key (csv_row_number, feed_id)
);

CREATE TABLE calendar_dates
(
    feed_id        VARCHAR(255) NOT NULL,
    service_id     VARCHAR(255),
    calendar_name  VARCHAR(255),
    holiday        INTEGER,
    period INTEGER,
    date           date,
    csv_row_number BIGINT       NOT NULL,
    exception_type INTEGER,
    primary key (csv_row_number, feed_id)
);

CREATE TABLE feed_info
(
    feed_id             VARCHAR(255) NOT NULL,
    feed_info_id        BIGINT,
    feed_publisher_name VARCHAR(255),
    feed_publisher_url  VARCHAR(255),
    feed_lang           VARCHAR(255),
    feed_start_date     VARCHAR(255),
    feed_end_date       VARCHAR(255),
    feed_version        VARCHAR(255),
    feed_desc           VARCHAR(255),
    feed_remarks        VARCHAR(255),
    csv_row_number      BIGINT       NOT NULL,
    primary key (csv_row_number, feed_id)
);

CREATE TABLE frequencies
(
    feed_id            VARCHAR(255) NOT NULL,
    trip_id            VARCHAR(255),
    start_time         TIME WITHOUT TIME ZONE,
    end_time           TIME WITHOUT TIME ZONE,
    frequency          INTEGER,
    typology           INTEGER,
    propulsion         INTEGER,
    passenger_counting INTEGER,
    video_surveillance INTEGER,
    csv_row_number     BIGINT       NOT NULL,
    primary key (csv_row_number, feed_id)
);

CREATE TABLE routes
(
    feed_id             VARCHAR(255) NOT NULL,
    line_id             VARCHAR(255),
    line_short_name     VARCHAR(255),
    line_long_name      VARCHAR(255),
    route_id            VARCHAR(255),
    agency_id           VARCHAR(255),
    route_origin        VARCHAR(255),
    route_destination   VARCHAR(255),
    route_short_name    VARCHAR(255),
    route_long_name     VARCHAR(255),
    route_desc          VARCHAR(255),
    route_remarks       VARCHAR(255),
    route_type          INTEGER,
    contract            VARCHAR(255),
    path_type           INTEGER,
    circular            INTEGER,
    school              INTEGER,
    continuous_pickup   INTEGER,
    continuous_drop_off INTEGER,
    csv_row_number      BIGINT       NOT NULL,
    primary key (csv_row_number, feed_id)
);

CREATE TABLE shapes
(
    feed_id             VARCHAR(255) NOT NULL,
    line_id             VARCHAR(255),
    shape_pt_lat        DOUBLE PRECISION,
    shape_pt_lon        DOUBLE PRECISION,
    shape_pt_sequence   INTEGER,
    shape_dist_traveled DOUBLE PRECISION,
    csv_row_number      BIGINT       NOT NULL,
    primary key (csv_row_number, feed_id)
);

CREATE TABLE sp_rows_by_table
(
    gtfs_table VARCHAR(255) NOT NULL,
    counter    INTEGER,
    CONSTRAINT pk_sprowsbytable PRIMARY KEY (gtfs_table)
);

CREATE TABLE sp_trips_by_line
(
    line_id VARCHAR(255) NOT NULL,
    total   INTEGER,
    CONSTRAINT pk_sptripsbyline PRIMARY KEY (line_id)
);

CREATE TABLE stop_times
(
    feed_id             VARCHAR(255) NOT NULL,
    trip_id             VARCHAR(255),
    arrival_time        VARCHAR(255),
    departure_time      VARCHAR(255),
    stop_id             VARCHAR(255),
    stop_sequence       INTEGER,
    stop_headsign       VARCHAR(255),
    continuous_pickup   INTEGER,
    continuous_drop_off INTEGER,
    shape_dist_traveled DOUBLE PRECISION,
    pickup_type        INTEGER,
    drop_off_type       INTEGER,
    timepoint           INTEGER,
    csv_row_number      BIGINT       NOT NULL,
    primary key (csv_row_number, feed_id)
);

CREATE TABLE stops
(
    feed_id               VARCHAR(255) NOT NULL,
    stop_id               VARCHAR(255),
    stop_id_stepp         VARCHAR(255),
    stop_code             VARCHAR(255),
    stop_name             VARCHAR(255),
    stop_desc             VARCHAR(255),
    stop_remarks          VARCHAR(255),
    stop_lat              DOUBLE PRECISION,
    stop_lon              DOUBLE PRECISION,
    zone_shift            INTEGER,
    location_type         INTEGER,
    parent_station        VARCHAR(255),
    wheelchair_boarding   INTEGER,
    platform_code         VARCHAR(255),
    entrance_restriction  INTEGER,
    exit_restriction      INTEGER,
    slot                  INTEGER,
    signalling            INTEGER,
    shelter               INTEGER,
    bench                 INTEGER,
    network_map           INTEGER,
    schedule              INTEGER,
    real_time_information INTEGER,
    tariff                INTEGER,
    preservation_state    INTEGER,
    equipment             VARCHAR(255),
    observations          VARCHAR(255),
    region                VARCHAR(255),
    municipality          INTEGER,
    municipality_fare1    INTEGER,
    municipality_fare2    INTEGER,
    csv_row_number        BIGINT       NOT NULL,
    primary key (csv_row_number, feed_id)
);

CREATE TABLE trips
(
    feed_id               VARCHAR(255) NOT NULL,
    route_id              VARCHAR(255),
    service_id            VARCHAR(255),
    trip_id               VARCHAR(255),
    trip_first            TIME WITHOUT TIME ZONE,
    trip_last             TIME WITHOUT TIME ZONE,
    trip_headsign         VARCHAR(255),
    direction_id          INTEGER,
    shape_id              VARCHAR(255),
    wheelchair_accessible INTEGER,
    bikes_allowed         INTEGER,
    csv_row_number        BIGINT       NOT NULL,
    primary key (csv_row_number, feed_id)
);

CREATE TABLE frequency_periods
(
    frequency_period_id BIGINT NOT NULL,
    start_time          TIME WITHOUT TIME ZONE,
    end_time            TIME WITHOUT TIME ZONE,
    primary key (frequency_period_id)
);