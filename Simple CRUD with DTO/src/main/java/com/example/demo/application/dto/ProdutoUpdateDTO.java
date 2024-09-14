package com.example.demo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoUpdateDTO {

    private String nome;
    private double preco;
    private String descricao;
    private String categoria;
    private String fabricante;
    private String codigoDeBarras;
    private int quantidadeEmEstoque;
}
