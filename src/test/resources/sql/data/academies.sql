ALTER TABLE academies ADD FULLTEXT INDEX ft_index (academy_name) WITH PARSER ngram;
