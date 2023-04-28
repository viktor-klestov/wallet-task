----liquibase formatted sql
--changeset klestov_v:1
create table wallet
(
    id      bigserial not null primary key,
    name    text      not null,
    balance numeric   not null check ( balance >= 0 )
);

alter sequence wallet_id_seq increment by 50;