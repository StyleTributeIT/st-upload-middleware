create table upload
(
  upload_id serial not null
    constraint upload_pkey
      primary key,
  flash_air_id varchar(200) not null,
  flash_air_pass varchar(500) not null,
  status varchar(50) not null,
  magento_user varchar(200),
  sku varchar(100),
  label varchar(100)
)
;

create unique index upload_upload_id_uindex
  on upload (upload_id)
;

create unique index upload_flash_air_id_uindex
  on upload (flash_air_id)
;

