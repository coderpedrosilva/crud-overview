package com.example.demo.application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double preco;
    private String descricao;
    private String categoria;
    private String fabricante;
    private String codigoDeBarras;
    private int quantidadeEmEstoque;

    // Construtor com todos os parâmetros, exceto o ID
    public Produto(String nome, double preco, String descricao, String categoria, String fabricante,
                   String codigoDeBarras, int quantidadeEmEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.categoria = categoria;
        this.fabricante = fabricante;
        this.codigoDeBarras = codigoDeBarras;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }
}
