create table s_role (
    id   varchar(36),
    nama varchar(100) not null,
    primary key (id),
    unique (nama)
);

create table pengguna (
    id              varchar(36),
    id_role         varchar(36)  not null,
    username        varchar(100) not null,
    hashed_password varchar(255) not null,
    nama            varchar(255) not null,
    primary key (id),
    unique (username),
    foreign key (id_role) references s_role (id)
);

create table transaksi (
    id              varchar(36),
    id_pengguna     varchar(36)    not null,
    waktu_transaksi timestamp       not null,
    keterangan      varchar(255)   not null,
    nilai           decimal(19, 2) not null,
    primary key (id),
    foreign key (id_pengguna) references pengguna (id)
);

create table audit_log (
    id                  varchar(36),
    id_pengguna_asli    varchar(36)  not null,
    id_pengguna_dipakai varchar(36)  not null,
    waktu_kegiatan      timestamp     not null,
    keterangan          varchar(255) not null,
    primary key (id),
    foreign key (id_pengguna_asli) references pengguna (id),
    foreign key (id_pengguna_dipakai) references pengguna (id)
);
