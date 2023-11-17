create table dashboard_schedules
(
    id           bigint auto_increment
        primary key,
    dashboard_id bigint                                                                                      not null,
    day_of_week  enum ('FRIDAY', 'MONDAY', 'NONE', 'SATURDAY', 'SUNDAY', 'THURSDAY', 'TUESDAY', 'WEDNESDAY') null,
    start_time   time(6)                                                                                     not null,
    end_time     time(6)                                                                                     not null,
    repeatance   enum ('BIWEEKLY', 'DAILY', 'MONTHLY', 'NONE', 'WEEKLY', 'YEARLY')                           null,

    constraint FK1wt134p2ed43147s6kk22dvkw
        foreign key (dashboard_id) references dashboards (id)
);