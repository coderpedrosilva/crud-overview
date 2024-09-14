package com.example.demo.application.controller;

import com.example.demo.application.dto.ProdutoDTO;
import com.example.demo.application.dto.ProdutoUpdateDTO;
import com.example.demo.application.model.Produto;
import com.example.demo.application.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        List<Produto> produtos = produtoService.findAll();
        List<ProdutoDTO> produtoDTOs = produtos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.findById(id);
        return produto.map(p -> ResponseEntity.ok(convertToDTO(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> create(@RequestBody ProdutoUpdateDTO produtoUpdateDTO) {
        Produto produto = convertToEntity(produtoUpdateDTO);
        Produto savedProduto = produtoService.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedProduto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable Long id, @RequestBody ProdutoUpdateDTO produtoUpdateDTO) {
        Optional<Produto> optionalProduto = produtoService.findById(id);
        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            // Atualiza todos os campos
            produto.setNome(produtoUpdateDTO.getNome());
            produto.setPreco(produtoUpdateDTO.getPreco());
            produto.setDescricao(produtoUpdateDTO.getDescricao());
            produto.setCategoria(produtoUpdateDTO.getCategoria());
            produto.setFabricante(produtoUpdateDTO.getFabricante());
            produto.setCodigoDeBarras(produtoUpdateDTO.getCodigoDeBarras());
            produto.setQuantidadeEmEstoque(produtoUpdateDTO.getQuantidadeEmEstoque());

            // Salva o produto atualizado
            Produto updatedProduto = produtoService.save(produto);

            // Converte o produto atualizado para DTO e retorna
            ProdutoDTO updatedProdutoDTO = convertToDTO(updatedProduto);
            return ResponseEntity.ok(updatedProdutoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (produtoService.findById(id).isPresent()) {
            produtoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Converte um Produto para ProdutoDTO
    private ProdutoDTO convertToDTO(Produto produto) {
        return new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco(),
                produto.getDescricao(), produto.getCategoria(), produto.getFabricante());
    }

    // Converte um ProdutoUpdateDTO para Produto
    private Produto convertToEntity(ProdutoUpdateDTO produtoUpdateDTO) {
        return new Produto(produtoUpdateDTO.getNome(), produtoUpdateDTO.getPreco(), produtoUpdateDTO.getDescricao(),
                produtoUpdateDTO.getCategoria(), produtoUpdateDTO.getFabricante(), produtoUpdateDTO.getCodigoDeBarras(),
                produtoUpdateDTO.getQuantidadeEmEstoque());
    }
}
