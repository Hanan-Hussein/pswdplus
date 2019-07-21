-- begin PSWDPLUS_PASSWORD_HISTORY
create table PSWDPLUS_PASSWORD_HISTORY (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime,
    CREATED_BY varchar(50),
    UPDATE_TS datetime,
    UPDATED_BY varchar(50),
    DELETE_TS datetime,
    DELETED_BY varchar(50),
    --
    USER_ID uniqueidentifier,
    CREATED_AT datetime,
    PASSWORD_HASH varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end PSWDPLUS_PASSWORD_HISTORY
