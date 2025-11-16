-- Criação do banco de dados principal
CREATE DATABASE maos_amigas;
USE maos_amigas;

-- Temporariamente desabilita verificações de chave estrangeira durante a criação das tabelas
SET FOREIGN_KEY_CHECKS=0;

-- ========================
-- TABELA: Endereço
-- ========================
CREATE TABLE endereco (
    id_endereco INT AUTO_INCREMENT PRIMARY KEY,
    cep CHAR(8),
    logradouro VARCHAR(200),
    complemento VARCHAR(200),
    bairro VARCHAR(45),
    numero VARCHAR(10),
    cidade VARCHAR(45),
    uf CHAR(2),
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    versao INT DEFAULT 0
);

-- ========================
-- TABELA: Ficha de Usuário
-- ========================
CREATE TABLE ficha (
    id_ficha INT AUTO_INCREMENT PRIMARY KEY,
    fk_endereco int,
    nome VARCHAR(45),
    sobrenome VARCHAR(45),
    cpf CHAR(11) UNIQUE,
    renda_minima DECIMAL(10,2) DEFAULT 0,
    renda_maxima DECIMAL(10,2) DEFAULT 0,
    genero VARCHAR(20) CHECK (genero IN ('MASCULINO', 'FEMININO', 'OUTRO')),
    dt_nascim DATE,
    area_orientacao VARCHAR(255),
    como_soube VARCHAR(255),
    profissao VARCHAR(255),
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    versao INT DEFAULT 0,
    INDEX idx_fk_endereco (fk_endereco),
    CONSTRAINT fk_ficha_endereco
        FOREIGN KEY (fk_endereco) REFERENCES endereco(id_endereco)
        ON DELETE SET NULL ON UPDATE CASCADE
);

-- ========================
-- TABELA: Telefone
-- ========================
CREATE TABLE telefone (
    id_telefone INT AUTO_INCREMENT PRIMARY KEY,
    fk_ficha INT NOT NULL,
    ddd CHAR(2),
    prefixo CHAR(5),
    sufixo CHAR(4),
    whatsapp TINYINT(1) DEFAULT 0,
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    versao INT DEFAULT 0,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_fk_ficha (fk_ficha),
    CONSTRAINT fk_telefone_ficha
        FOREIGN KEY (fk_ficha) REFERENCES ficha(id_ficha)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ========================
-- TABELA: Usuário do Sistema
-- ========================
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    fk_ficha INT UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(128) NOT NULL,
    tipo VARCHAR(20) CHECK (tipo IN ('administrador', 'voluntario', 'valor social', 'não classificado', 'gratuidade')),
    dt_cadastro DATE NOT NULL,
    ultimo_acesso datetime,
    foto_url varchar (255),
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    versao INT DEFAULT 0,
    INDEX idx_fk_ficha (fk_ficha),
    CONSTRAINT fk_usuario_ficha
        FOREIGN KEY (fk_ficha) REFERENCES ficha(id_ficha)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ========================
-- TABELA: Voluntário
-- ========================
CREATE TABLE voluntario (
    id_voluntario INT PRIMARY KEY,
    fk_usuario INT NOT NULL UNIQUE,
    funcao VARCHAR(45) NOT NULL,
    dt_cadastro DATE NOT NULL,
    biografia_profissional varchar (255),
    registro_profissional varchar (55),
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    versao INT DEFAULT 0,
    INDEX idx_fk_usuario (fk_usuario),
    CONSTRAINT fk_voluntario_usuario
        FOREIGN KEY (fk_usuario) REFERENCES usuario(id_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ========================
-- TABELA: Especialidades
-- ========================
CREATE TABLE especialidade (
    id_especialidade INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(45) NOT NULL UNIQUE,
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    versao INT DEFAULT 0
);

-- ========================
-- TABELA: Consulta
-- ========================
Create TABLE consulta (
    id_consulta INT AUTO_INCREMENT PRIMARY KEY,
    horario DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('AGENDADA', 'REAGENDADA', 'REALIZADA', 'CANCELADA')),
    modalidade VARCHAR(15) NOT NULL CHECK (modalidade IN ('ONLINE', 'PRESENCIAL')),
    local VARCHAR(45),
    observacoes VARCHAR(255),
    fk_especialidade INT NOT NULL,
    fk_especialista INT NOT NULL,
    fk_cliente INT NOT NULL,
    feedback_status VARCHAR(20) DEFAULT 'PENDENTE' CHECK (feedback_status IN ('PENDENTE', 'ENVIADO', 'NAO_NECESSARIO')),
    avaliacao_status VARCHAR(20) DEFAULT 'PENDENTE' CHECK (avaliacao_status IN ('PENDENTE', 'ENVIADO', 'NAO_NECESSARIO')),
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    versao INT DEFAULT 0,
    INDEX idx_fk_especialidade (fk_especialidade),
    INDEX idx_fk_especialista (fk_especialista),
    INDEX idx_fk_cliente (fk_cliente),
    CONSTRAINT fk_consulta_especialidade FOREIGN KEY (fk_especialidade) REFERENCES especialidade(id_especialidade),
    CONSTRAINT fk_consulta_especialista FOREIGN KEY (fk_especialista) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_consulta_cliente FOREIGN KEY (fk_cliente) REFERENCES usuario(id_usuario)
);

-- ========================
-- TABELA: Feedback de Consulta
-- ========================
CREATE TABLE feedback_consulta (
    id_feedback INT AUTO_INCREMENT PRIMARY KEY,
    fk_consulta INT NOT NULL,
    comentario TEXT NOT NULL,
    dt_feedback DATETIME DEFAULT CURRENT_TIMESTAMP,
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    versao INT DEFAULT 0,
    INDEX idx_fk_consulta_feedback (fk_consulta),
    CONSTRAINT fk_feedback_consulta FOREIGN KEY (fk_consulta) REFERENCES consulta(id_consulta)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ========================
-- TABELA: Avaliação de Consulta
-- ========================
CREATE TABLE avaliacao_consulta (
    id_avaliacao INT AUTO_INCREMENT PRIMARY KEY,
    fk_consulta INT NOT NULL,
    nota INT NOT NULL CHECK (nota BETWEEN 1 AND 5),
    dt_avaliacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    versao INT DEFAULT 0,
    INDEX idx_fk_consulta_avaliacao (fk_consulta),
    CONSTRAINT fk_avaliacao_consulta FOREIGN KEY (fk_consulta) REFERENCES consulta(id_consulta)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ========================
-- TABELA: Disponibilidade do Voluntário
-- ========================
CREATE TABLE disponibilidade_voluntario (
    id_disponibilidade INT AUTO_INCREMENT PRIMARY KEY,
    fk_voluntario INT NOT NULL,
    data_horario datetime not null,
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_fk_voluntario (fk_voluntario),
    CONSTRAINT fk_disponibilidade_voluntario FOREIGN KEY (fk_voluntario) REFERENCES voluntario(id_voluntario)
);

-- ========================
-- TABELA: Token OAuth2
-- ========================
CREATE TABLE oauth_token (
    id_oauth_token INT AUTO_INCREMENT PRIMARY KEY,
    fk_usuario INT NOT NULL,
    access_token VARCHAR(2048) NOT NULL,
    refresh_token VARCHAR(512),
    expira_em DATETIME NOT NULL,
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    versao INT DEFAULT 0,
    INDEX idx_fk_usuario_oauth (fk_usuario),
    INDEX idx_access_token (access_token(255)),
    CONSTRAINT fk_oauth_token_usuario FOREIGN KEY (fk_usuario) REFERENCES usuario(id_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ========================
-- TABELA: Controle de Acesso por Especialidade
-- ========================
CREATE TABLE acesso_usuario (
    id_acesso_usuario INT AUTO_INCREMENT PRIMARY KEY,
    fk_usuario INT NOT NULL,
    fk_especialidade INT NOT NULL,
    liberado TINYINT(1) DEFAULT 0,
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uq_usuario_especialidade (fk_usuario, fk_especialidade),
    INDEX idx_fk_usuario (fk_usuario),
    INDEX idx_fk_especialidade (fk_especialidade),
    CONSTRAINT fk_acesso_usuario_usuario FOREIGN KEY (fk_usuario) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_acesso_usuario_especialidade FOREIGN KEY (fk_especialidade) REFERENCES especialidade(id_especialidade)
);

-- Reativa verificação de integridade referencial
SET FOREIGN_KEY_CHECKS=1;

SELECT 
    -- Ficha do usuário
    f.id_ficha,
    f.nome,
    f.sobrenome,
    f.cpf,
    f.renda_minima,
    f.renda_maxima,
    f.genero,
    DATE_FORMAT(f.dt_nascim, '%d/%m/%Y') AS data_nascimento,
    f.area_orientacao,
    f.como_soube,
    f.profissao,
    f.criado_em AS ficha_criado_em,
    f.atualizado_em AS ficha_atualizado_em,

    -- Endereço
    e.id_endereco,
    e.cep,
    e.logradouro,
    e.complemento,
    e.bairro,
    e.numero,
    e.cidade,
    e.uf,
    e.criado_em AS endereco_criado_em,
    e.atualizado_em AS endereco_atualizado_em,

    -- Usuário do sistema
    u.id_usuario,
    u.email,
    u.tipo,
    u.senha,
    u.dt_cadastro,
    u.ultimo_acesso,
    u.foto_url,
    u.criado_em AS usuario_criado_em,
    u.atualizado_em AS usuario_atualizado_em,

    -- Telefone(s)
    t.id_telefone,
    t.ddd,
    t.prefixo,
    t.sufixo,
    t.whatsapp,
    t.criado_em AS telefone_criado_em,
    t.atualizado_em AS telefone_atualizado_em,

    -- Voluntário (informações profissionais)
    v.id_voluntario,
    v.funcao,
    v.dt_cadastro AS voluntario_dt_cadastro,
    v.biografia_profissional,
    v.registro_profissional,
    v.criado_em AS voluntario_criado_em,
    v.atualizado_em AS voluntario_atualizado_em,

    -- Acesso por Especialidade
    au.id_acesso_usuario,
    au.liberado,
    au.criado_em AS acesso_criado_em,
    au.atualizado_em AS acesso_atualizado_em,

    -- Especialidade
    esp.id_especialidade,
    esp.nome AS nome_especialidade,
    esp.criado_em AS especialidade_criado_em,
    esp.atualizado_em AS especialidade_atualizado_em

FROM ficha f
LEFT JOIN endereco e ON f.fk_endereco = e.id_endereco
LEFT JOIN usuario u ON u.fk_ficha = f.id_ficha
LEFT JOIN telefone t ON t.fk_ficha = f.id_ficha
LEFT JOIN voluntario v ON v.fk_usuario = u.id_usuario
LEFT JOIN acesso_usuario au ON au.fk_usuario = u.id_usuario
LEFT JOIN especialidade esp ON esp.id_especialidade = au.fk_especialidade;

SELECT * FROM avaliacao_consulta;
SELECT * FROM consulta;
SELECT * FROM endereco;
SELECT * FROM especialidade;
SELECT * FROM feedback_consulta;
SELECT * FROM ficha;
SELECT * FROM telefone;
SELECT * FROM usuario;
SELECT * FROM voluntario;


INSERT INTO especialidade (nome, criado_em, atualizado_em, versao)
VALUES ('Psicologia', NOW(), NOW(), 0);

UPDATE consulta
SET status = 'AGENDADA',
    atualizado_em = NOW()
WHERE id_consulta = 4;

SELECT
    -- Ficha e dados pessoais
    f.id_ficha,
    f.nome,
    f.sobrenome,
    f.cpf,
    f.renda_minima,
    f.renda_maxima,
    f.genero,
    DATE_FORMAT(f.dt_nascim, '%d/%m/%Y') AS data_nascimento,
    f.area_orientacao,
    f.como_soube,
    f.profissao,
    f.criado_em AS ficha_criado_em,
    f.atualizado_em AS ficha_atualizado_em,

    -- Endereço
    e.id_endereco,
    e.cep,
    e.logradouro,
    e.complemento,
    e.bairro,
    e.numero,
    e.cidade,
    e.uf,
    e.criado_em AS endereco_criado_em,
    e.atualizado_em AS endereco_atualizado_em,

    -- Usuário do sistema
    u.id_usuario,
    u.email,
    u.tipo AS tipo_usuario,
    u.senha,
    u.dt_cadastro,
    u.ultimo_acesso,
    u.foto_url,
    u.criado_em AS usuario_criado_em,
    u.atualizado_em AS usuario_atualizado_em,

    -- Telefones
    t.id_telefone,
    CONCAT('(', t.ddd, ') ', t.prefixo, '-', t.sufixo) AS telefone_completo,
    t.whatsapp,

    -- Voluntário
    v.id_voluntario,
    v.funcao,
    v.dt_cadastro AS voluntario_dt_cadastro,
    v.biografia_profissional,
    v.registro_profissional,
    v.criado_em AS voluntario_criado_em,
    v.atualizado_em AS voluntario_atualizado_em,

    -- Especialidades do voluntário
    esp.id_especialidade,
    esp.nome AS nome_especialidade,

    -- Controle de acesso do usuário
    au.id_acesso_usuario,
    au.liberado AS acesso_liberado,

    -- Consultas (onde o usuário pode ser cliente ou especialista)
    c.id_consulta,
    c.horario AS consulta_horario,
    c.status AS consulta_status,
    c.modalidade,
    c.local,
    c.observacoes,
    c.feedback_status,
    c.avaliacao_status,
    c.criado_em AS consulta_criado_em,
    c.atualizado_em AS consulta_atualizado_em,

    -- Feedback da consulta
    fc.id_feedback,
    fc.comentario AS feedback_comentario,
    fc.dt_feedback,

    -- Avaliação da consulta
    ac.id_avaliacao,
    ac.nota AS avaliacao_nota,
    ac.dt_avaliacao

FROM ficha f
LEFT JOIN endereco e ON e.id_endereco = f.fk_endereco
LEFT JOIN usuario u ON u.fk_ficha = f.id_ficha
LEFT JOIN telefone t ON t.fk_ficha = f.id_ficha
LEFT JOIN voluntario v ON v.fk_usuario = u.id_usuario
LEFT JOIN acesso_usuario au ON au.fk_usuario = u.id_usuario
LEFT JOIN especialidade esp ON esp.id_especialidade = au.fk_especialidade

-- Ligação com consultas (cliente ou especialista)
LEFT JOIN consulta c ON c.fk_cliente = u.id_usuario OR c.fk_especialista = u.id_usuario
LEFT JOIN feedback_consulta fc ON fc.fk_consulta = c.id_consulta
LEFT JOIN avaliacao_consulta ac ON ac.fk_consulta = c.id_consulta

ORDER BY f.id_ficha, c.id_consulta;

-- Dados de exemplo para testar o endpoint de voluntários
USE maos_amigas;

-- Inserir endereços de exemplo
INSERT INTO endereco (cep, logradouro, complemento, bairro, numero, cidade, uf) VALUES
('01234567', 'Rua das Flores', 'Apto 101', 'Centro', '123', 'São Paulo', 'SP'),
('98765432', 'Av. Principal', NULL, 'Jardim América', '456', 'Rio de Janeiro', 'RJ'),
('11223344', 'Rua da Paz', 'Casa 2', 'Vila Nova', '789', 'Belo Horizonte', 'MG');

-- Inserir fichas de exemplo
INSERT INTO ficha (fk_endereco, nome, sobrenome, cpf, genero, dt_nascim, area_orientacao, profissao) VALUES
(1, 'Maria', 'Silva', '12345678901', 'FEMININO', '1985-03-15', 'Psicologia Clínica', 'Psicóloga'),
(2, 'João', 'Santos', '98765432109', 'MASCULINO', '1980-07-22', 'Direito de Família', 'Advogado'),
(3, 'Ana', 'Costa', '11122233344', 'FEMININO', '1990-12-10', 'Assistência Social', 'Assistente Social');

-- Inserir usuários voluntários
INSERT INTO usuario (fk_ficha, email, senha, tipo, dt_cadastro, ultimo_acesso) VALUES
(1, 'maria.silva@email.com', '$2a$10$hashedpassword1', 'voluntario', '2024-01-15', '2024-12-15 14:30:00'),
(2, 'joao.santos@email.com', '$2a$10$hashedpassword2', 'voluntario', '2024-02-20', '2024-12-10 09:15:00'),
(3, 'ana.costa@email.com', '$2a$10$hashedpassword3', 'voluntario', '2024-03-10', NULL);

-- Inserir dados específicos de voluntários
INSERT INTO voluntario (id_voluntario, fk_usuario, funcao, dt_cadastro, biografia_profissional, registro_profissional) VALUES
(1, 1, 'Psicóloga', '2024-01-15', 'Especialista em terapia cognitivo-comportamental com 10 anos de experiência.', 'CRP 06/123456'),
(2, 2, 'Advogado', '2024-02-20', 'Advogado especializado em direito de família e mediação de conflitos.', 'OAB/SP 123456'),
(3, 3, 'Assistente Social', '2024-03-10', 'Assistente social com foco em vulnerabilidade social e proteção de direitos.', 'CRESS 9/12345');

-- Inserir telefones de exemplo
INSERT INTO telefone (fk_ficha, ddd, prefixo, sufixo, whatsapp) VALUES
(1, '11', '99999', '1234', 1),
(2, '21', '88888', '5678', 1),
(3, '31', '77777', '9012', 0);