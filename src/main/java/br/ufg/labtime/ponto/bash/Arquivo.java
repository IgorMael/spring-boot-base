package br.ufg.labtime.ponto.bash;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Rela&ccedil;&atilde;o dos arquivos de boot.
 *
 * @author rebola
 */
enum Arquivo {
    STARTING(".starting"),
    STARTED(".started"),
    FAILED(".failed"),
    STOPPED(".stopped"),
    PORT(".port") {
        @Override
        public void criar(String conteudo) {
            criar(conteudo, false);
        }
    };

    private final Path path;

    Arquivo(String nome) {
        path = new File(nome).toPath();
    }

    /**
     * Remove todos os outros arquivos de boot antes de criar com o conteudo especificado
     *
     * @param conteudo conte&uacute;do do arquivo.
     */
    public void criar(String conteudo) {
        criar(conteudo, true);
    }

    public void remover() {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

//    public String ler() {
//        try {
//            return Files.readString(path);
//        } catch (IOException e) {
//            throw new UncheckedIOException(e);
//        }
//    }

    protected void criar(String conteudo, boolean apagarDemais) {
        try {
            if (apagarDemais) removerTodos();
            Files.createFile(path);
            if (conteudo != null) {
                try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                    writer.write(conteudo);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void removerTodos() {
        for (Arquivo a : values()) {
            a.remover();
        }
    }
}
