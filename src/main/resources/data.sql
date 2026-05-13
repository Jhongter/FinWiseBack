-- Usuários de exemplo (senha: 123456 — hash BCrypt)
-- ON CONFLICT DO NOTHING evita erro caso a linha já exista
-- (ex: ao reiniciar a aplicação com ddl-auto=update)
INSERT INTO usuarios (nome, email, senha)
VALUES ('Ana Souza',  'ana@exemplo.com',   '$2a$10$Dq1vqjHGXCgHC3sKqNV25eSt3sciGjeD4ft8GakoKaV/bB00tLHgi')
ON CONFLICT (email) DO NOTHING;

INSERT INTO usuarios (nome, email, senha)
VALUES ('Bruno Lima', 'bruno@exemplo.com', '$2a$10$glzR0wgSgE47dEizTdnV1eGjDjJhrT9uHxeD9TfmBIyA4Bv9LonNe')
ON CONFLICT (email) DO NOTHING;
