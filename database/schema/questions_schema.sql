create table questions
(
    id                bigint auto_increment
        primary key,
    question_text     varchar(255) not null,
    correct_answer_id bigint       null,
    category          varchar(255) null
);


