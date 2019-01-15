CREATE TABLE account (
    id   BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    balance decimal(12,2) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE transaction (
    id   BIGINT NOT NULL AUTO_INCREMENT,
    from_account_id BIGINT NOT NULL,
    to_account_id BIGINT NOT NULL,
    amount decimal(12,2) NOT NULL,
    create_ts timestamp NOT NULL,
    PRIMARY KEY (id)
);
