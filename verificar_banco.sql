-- Verificar se a tabela auto_model já existe e qual sua estrutura
SELECT column_name, data_type, is_nullable 
FROM information_schema.columns 
WHERE table_name = 'auto_model' 
ORDER BY ordinal_position;

-- Verificar se existe tabela automodel (antiga)
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public' 
AND table_name IN ('auto_model', 'automodel', 'auto_model_old');
