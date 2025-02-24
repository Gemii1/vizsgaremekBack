CREATE TABLE blog (
    id              INT AUTO_INCREMENT       PRIMARY KEY,
    blog_type       VARCHAR(400)             NOT NULL,
    title           VARCHAR(400)             NOT NULL,
    header_text            TEXT                     NOT NULL,
    main_text            TEXT                     NOT NULL,
    image           VARCHAR(400),
    trainer_id      INT                      NOT NULL,

    foreign key (trainer_id) references trainer (id)
);