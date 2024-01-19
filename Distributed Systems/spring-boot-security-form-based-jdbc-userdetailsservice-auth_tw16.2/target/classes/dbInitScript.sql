
DROP TABLE IF EXISTS my_user_role CASCADE;
DROP TABLE IF EXISTS my_user CASCADE;
DROP TABLE IF EXISTS my_evento CASCADE;
DROP TABLE IF EXISTS my_inscricoes CASCADE;
DROP TABLE IF EXISTS my_times CASCADE;


CREATE TABLE IF NOT EXISTS my_user (
  user_name varchar(30) NOT NULL,
  user_pass varchar(255) NOT NULL,
  user_genero varchar(255) NOT NULL,
  enable smallint NOT NULL DEFAULT 1,
  PRIMARY KEY (user_name)
);

-- admin / admin
insert  into my_user (user_name,user_pass,user_genero,enable) values ('admin','$2a$10$WaEdZpejXALV1HH8Xc3H0OEXeRjqzVho32vc9vpBQpvG0l9XQTEYO','m',1);
-- user2 / teste123
insert  into my_user (user_name,user_pass,user_genero,enable) values ('user2','$2a$10$bKWhb9hIUD3xxxtzfhvodugWIK3Gbw4vRySYOnBqy2O4gtqZ78jUK','f',1);


CREATE TABLE IF NOT EXISTS my_user_role (
  user_name varchar(30) NOT NULL,
  user_role varchar(15) NOT NULL,
  FOREIGN KEY (user_name) REFERENCES my_user (user_name)
);

insert  into my_user_role (user_name,user_role) values ('admin','ROLE_ADMIN');
insert  into my_user_role (user_name,user_role) values ('user2','ROLE_USER');

CREATE TABLE IF NOT EXISTS my_evento (
    evento_name varchar(30) NOT NULL,
    evento_data Date NOT NULL,
    evento_valor float NOT NULL,
    evento_desc varchar(200) NOT NULL,
    PRIMARY KEY (evento_name)
);

insert into my_evento (evento_name, evento_data, evento_valor, evento_desc) values ('Maratona Lisboa', '2022-09-05', 13.8, 'Corrida pela cidade de Lisboa');
insert into my_evento (evento_name, evento_data, evento_valor, evento_desc) values ('Maratona Evora', '2022-01-20', 6, 'Corrida pela cidade de Évora');
insert into my_evento (evento_name, evento_data, evento_valor, evento_desc) values ('Color Run', '2021-04-05', 13.8, 'Corrida das correr por Lisboa');
insert into my_evento (evento_name, evento_data, evento_valor, evento_desc) values ('Party Run', '2021-09-05', 15.8, 'Melhor festa');
insert into my_evento (evento_name, evento_data, evento_valor, evento_desc) values ('Corrida Solidaria', '2021-10-15', 20, 'Corrida solidária Make-a-Wish');
insert into my_evento (evento_name, evento_data, evento_valor, evento_desc) values ('Dia De Correr', '2021-12-14', 29.8, 'Hoje é dia de correr');
insert into my_evento (evento_name, evento_data, evento_valor, evento_desc) values ('Corridas e Caminhos', '2021-02-01', 3, 'Muitas corridas, muitos caminhos');
insert into my_evento (evento_name, evento_data, evento_valor, evento_desc) values ('Correr por gosto', '2022-09-05', 2.4, 'Quem corre por gosto não cansa');
insert into my_evento (evento_name, evento_data, evento_valor, evento_desc) values ('Dia da saude', '2022-12-15', 4.5, 'Correr por uma saúde melhor');
insert into my_evento (evento_name, evento_data, evento_valor, evento_desc) values ('Maratona Faro', '2022-06-25', 5, 'Corrida pela cidade de Faro');
insert into my_evento (evento_name, evento_data, evento_valor, evento_desc) values ('Volta a Portimao', '2022-04-10', 13.8, 'Volta a Portimão, competição anual');
insert into my_evento (evento_name, evento_data, evento_valor, evento_desc) values ('Maratona Porto', '2021-09-05', 18, 'Corrida pela cidade de Porto');

CREATE TABLE IF NOT EXISTS my_inscricoes (
    user_name varchar(30) NOT NULL,
    evento_name varchar(30) NOT NULL,
    estado_pagamento char NOT NULL,
    dorsal int NOT NULL,
    FOREIGN KEY (user_name) REFERENCES my_user (user_name),
    FOREIGN KEY (evento_name) REFERENCES my_evento (evento_name),
    PRIMARY KEY (user_name, evento_name)
);

insert into my_inscricoes (user_name, evento_name, estado_pagamento, dorsal) values ('admin','Maratona Lisboa', 's', 0 );
insert into my_inscricoes (user_name, evento_name, estado_pagamento, dorsal) values ('user2','Maratona Lisboa', 'n', 1 );

CREATE TABLE IF NOT EXISTS my_times (
    dorsal int NOT NULL,
    evento_name varchar(30) NOT NULL,
    time_start timestamp,
    p1 timestamp,
    p2 timestamp,
    p3 timestamp,
    finish timestamp,
    FOREIGN KEY (evento_name) REFERENCES my_evento (evento_name),
    PRIMARY KEY (evento_name, dorsal)
);

insert into my_times (dorsal, evento_name, time_start, p1, p2, p3, finish) values (0, 'Maratona Lisboa','2022-09-05 19:10:25.007', '2022-09-05 19:30:25.007', '2022-09-05 19:45:25.007', '2022-09-05 20:00:25.007', '2022-09-05 20:10:25.007');
insert into my_times (dorsal, evento_name, time_start, p1, p2, p3) values (1, 'Maratona Lisboa','2022-09-05 19:10:27.407', '2022-09-05 19:33:25.407', '2022-09-05 19:46:25.407', '2022-09-05 19:57:25.407');


