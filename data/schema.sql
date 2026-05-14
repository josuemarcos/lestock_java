INSERT INTO application_user (email, password, roles, user_name)
values ('josue.fernandes@ee.ufcg.edu.br', '$2a$10$AaPPRrmIwMUbQ1Uda/mQFex8VtiYaKqbV0ldBRUxFEhniDg/LIk4C',
'{USER, ADMIN}', 'josuembf');

INSERT INTO supplier (address, contact, description, name, social_media)
values ('Centro', 'fornecedor@email', 'fornecedor dos principais materiais',
'Casa do Colegial', 'fornecedor_media');

INSERT INTO material_type (brand, metric_unit, name)
values ('coala', 'g', 'Cola Branca');