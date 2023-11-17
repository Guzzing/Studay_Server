create table dashboards
(
    id            bigint auto_increment
        primary key,
    child_id      bigint         not null,
    lesson_id     bigint         not null,
    education_fee bigint         null,
    book_fee      bigint         null,
    shuttle_fee   bigint         null,
    etc_fee       bigint         null,
    payment_day   date           null,
    simple_memos  varbinary(255) not null,
    active        bit            not null,
    deleted       bit            not null
);