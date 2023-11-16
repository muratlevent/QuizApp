create table answers
(
    id          bigint auto_increment
        primary key,
    question_id bigint       null,
    answer_text varchar(255) not null,
    is_correct  tinyint(1)   not null,
    constraint answers_questions_id_fk
        foreign key (question_id) references questions (id)
);


