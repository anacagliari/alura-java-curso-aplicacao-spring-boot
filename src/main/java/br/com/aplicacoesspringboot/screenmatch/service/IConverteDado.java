package br.com.aplicacoesspringboot.screenmatch.service;

public interface IConverteDado {
    <T> T obterDados(String json, Class<T> classe); // Usamos o <T> T quando não sabemos qual entidade será devolvida.
}
