DROP TABLE IF EXISTS my_user_role CASCADE;
DROP TABLE IF EXISTS my_user CASCADE;
DROP TABLE IF EXISTS my_evento CASCADE;
DROP TABLE IF EXISTS my_inscricoes CASCADE;
DROP TABLE IF EXISTS my_times CASCADE;


CREATE TABLE IF NOT EXISTS my_user (
  user_name varchar(30) NOT NULL,
  user_pass varchar(255) NOT NULL,
  enable smallint NOT NULL DEFAULT 1,
  PRIMARY KEY (user_name)
);

-- admin / admin
insert  into my_user (user_name,user_pass,enable) values ('admin','$2a$10$WaEdZpejXALV1HH8Xc3H0OEXeRjqzVho32vc9vpBQpvG0l9XQTEYO',1);
-- user2 / teste123
insert  into my_user (user_name,user_pass,enable) values ('user2','$2a$10$bKWhb9hIUD3xxxtzfhvodugWIK3Gbw4vRySYOnBqy2O4gtqZ78jUK',1);


CREATE TABLE IF NOT EXISTS my_user_role (
  user_name varchar(30) NOT NULL,
  user_role varchar(15) NOT NULL,
  FOREIGN KEY (user_name) REFERENCES my_user (user_name)
);

insert  into my_user_role (user_name,user_role) values ('admin','ROLE_ADMIN');
insert  into my_user_role (user_name,user_role) values ('user2','ROLE_USER');

CREATE TABLE IF NOT EXISTS my_evento (
    evento_name varchar(30) NOT NULL,
    evento_valor DECIMAL(5,2) NOT NULL,
    evento_data Date NOT NULL,
    evento_desc varchar(200) NOT NULL,
    PRIMARY KEY (evento_name)
);

insert into my_evento (evento_name, evento_valor, evento_data, evento_desc) values ('Maratona Lisboa', 13.4, '2022-01-26', 'Corrida pela cidade de Lisboa');
insert into my_evento (evento_name, evento_valor, evento_data, evento_desc) values ('Maratona Evora',12.3, '2022-01-26', 'Corrida pela cidade de Évora');
insert into my_evento (evento_name, evento_valor, evento_data, evento_desc) values ('Color Run', 10,'2022-01-26', 'Corrida das correr por Lisboa');
insert into my_evento (evento_name, evento_valor, evento_data, evento_desc) values ('Party Run',5.3, '2021-01-26', 'Melhor festa');
insert into my_evento (evento_name, evento_valor, evento_data, evento_desc) values ('Corrida Solidaria',9.8, '2021-10-15', 'Corrida solidária Make-a-Wish');
insert into my_evento (evento_name, evento_valor, evento_data, evento_desc) values ('Dia De Correr', 10.3,'2021-12-14','Hoje é dia de correr');
insert into my_evento (evento_name, evento_valor, evento_data, evento_desc) values ('Corridas e Caminhos',14.6, '2021-02-01', 'Muitas corridas, muitos caminhos');
insert into my_evento (evento_name, evento_valor, evento_data, evento_desc) values ('Correr por gosto',19, '2022-09-05', 'Quem corre por gosto não cansa');
insert into my_evento (evento_name, evento_valor, evento_data, evento_desc) values ('Dia da saude', 24,'2022-12-15', 'Correr por uma saúde melhor');
insert into my_evento (evento_name, evento_valor, evento_data, evento_desc) values ('Maratona Faro',29.3, '2022-06-25', 'Corrida pela cidade de Faro');
insert into my_evento (evento_name, evento_valor, evento_data, evento_desc) values ('Volta a Portimao',1.4, '2022-04-10', 'Volta a Portimão, competição anual');
insert into my_evento (evento_name, evento_valor, evento_data, evento_desc) values ('Maratona Porto', 1,'2021-09-05', 'Corrida pela cidade de Porto');

CREATE TABLE IF NOT EXISTS my_inscricoes (
    inscrito_name varchar(30) NOT NULL,
    inscrito_escalao varchar(255) NOT NULL,
    inscrito_genero varchar(255) NOT NULL,
    evento_name varchar(30) NOT NULL,
    referencia varchar(100),
    estado_pagamento varchar(30) NOT NULL,
    dorsal int NOT NULL,
    user_name varchar(30) NOT NULL,
    FOREIGN KEY (user_name) REFERENCES my_user(user_name),
    FOREIGN KEY (evento_name) REFERENCES my_evento (evento_name),
    PRIMARY KEY (inscrito_name, evento_name)
);

insert into my_inscricoes (inscrito_name,inscrito_escalao,inscrito_genero, evento_name, referencia, estado_pagamento, dorsal, user_name) values ('Joao','Junior', 'f', 'Maratona Lisboa', '{"mb_amount": "13.40", "status": "ok", "mb_entity": "21067", "mb_reference": "900007528"}','paga', 0, 'user2');
insert into my_inscricoes (inscrito_name,inscrito_escalao,inscrito_genero, evento_name, referencia, estado_pagamento, dorsal, user_name) values ('Maria Mendes','Senior', 'm', 'Maratona Lisboa', '{"mb_amount": "13.40", "status": "ok", "mb_entity": "21267", "mb_reference": "900007529"}','nao paga', 1, 'user2');

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


