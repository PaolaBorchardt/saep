package br.com.senai.almoxarifado.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="produtos")
public class Produto {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=120) private String nome;
    @Column(length=500) private String descricao;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal saldo=BigDecimal.ZERO;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal estoqueMinimo=BigDecimal.ZERO;
    @Column(nullable=false) private LocalDateTime criadoEm=LocalDateTime.now();
    @Version private Long versao;

    public Long getId(){return id;} public String getNome(){return nome;} public void setNome(String v){nome=v;}
    public String getDescricao(){return descricao;} public void setDescricao(String v){descricao=v;}
    public BigDecimal getSaldo(){return saldo;} public void setSaldo(BigDecimal v){saldo=v;}
    public BigDecimal getEstoqueMinimo(){return estoqueMinimo;} public void setEstoqueMinimo(BigDecimal v){estoqueMinimo=v;}
    public LocalDateTime getCriadoEm(){return criadoEm;}
}
