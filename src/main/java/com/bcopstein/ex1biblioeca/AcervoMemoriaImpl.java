package com.bcopstein.ex1biblioeca;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AcervoMemoriaImpl implements IAcervoRepository {
    private JdbcTemplate jdbcTemplate;

    public AcervoMemoriaImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Livro> getAll() { //Pesquisei e achei essa forma com lambda (RowMapping) para criar os objetos e por numa lista
        return this.jdbcTemplate.query(
            "SELECT * FROM livros",
            (rs, rowNum) ->
                new Livro(
                    rs.getInt("codigo"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("ano")
                )
        );
    }

    @Override
    public List<String> getTitulos() {
        return getAll()
            return this.jdbcTemplate.query(
            "SELECT titulo FROM livros",
            (rs, rowNum) -> rs.getString("titulo")
        );
    }

    @Override
    public List<String> getAutores() {
        return this.jdbcTemplate.query(
            "SELECT autor FROM livros",
            (rs, rowNum) -> rs.getString("autor")
        );
    }


    @Override
    public List<Livro> getLivrosDoAutor(String autor) {
        return this.jdbcTemplate.query(
            "SELECT * FROM livros WHERE autor = ?",
            new Object[]{autor},
            (rs, rowNum) ->
                new Livro(
                    rs.getInt("codigo"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("ano")
                )
        );
    }

    @Override
    public Livro getLivroTitulo(String titulo) {
        List<Livro> livros = this.jdbcTemplate.query(
            "SELECT * FROM livros WHERE titulo = ?",
            new Object[]{titulo},
            (rs, rowNum) ->
                new Livro(
                    rs.getInt("codigo"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("ano")
                )
        );
        return livros.isEmpty() ? null : livros.get(0);
    }

    @Override
    public boolean removeLivro(long codigo) {
        this.jdbcTemplate.update("DELETE FROM livros WHERE codigo = ?", codigo);
        return true;
    }

    @Override
    public boolean cadastraLivroNovo(Livro livro) {
        this.jdbcTemplate.update(
            "INSERT INTO livros(codigo, titulo, autor, ano) VALUES (?, ?, ?, ?)",
            livro.codigo(), livro.titulo(), livro.autor(), livro.ano()
        );
        return true;
    }
}
