insert into tb_usuario(nome, email, apelido_url, senha, url_img) values('RobertoSouza','roberto@email.com','robss', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG',null)
insert into tb_usuario(nome, email, apelido_url, senha, url_img) values('RicardoSouza','ricardo@email.com','ricss', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG',null)


insert into tb_link (url, texto_botao, usuario_id) values ('https://www.facebook.com/', 'Meu feiso', 1)
insert into tb_link (url, texto_botao, usuario_id) values ('https://www.instagram.com/', 'Meu insto', 1)
insert into tb_link (url, texto_botao, usuario_id) values ('https://www.linkedin.com/', 'Meu linkedo', 1)
insert into tb_link (url, texto_botao, usuario_id) values ('https://www.linkedin.com/', 'Meu linkedo', 2)

insert into tb_regra(regra) values ('ROLE_USER');

insert into tb_usuario_regra (usuario_id, regra_id) values (1,1);
insert into tb_usuario_regra (usuario_id, regra_id) values (2,1);