package br.com.senai.almoxarifado.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="movimentacoes", indexes=@Index(columnList="dataHora"))
public class Movimentacao {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false, fetch=FetchType.EAGER) @JoinColumn(name="produto_id", nullable=false) private Produto produto;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private TipoMovimentacao tipo;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal quantidade;
    @ManyToOne(optional=false, fetch=FetchType.EAGER) @JoinColumn(name="usuario_id", nullable=false) private Usuario responsavel;
    @Column(nullable=false) private LocalDateTime dataHora=LocalDateTime.now();
    @Column(length=500) private String observacao;
    public Long getId(){return id;} public Produto getProduto(){return produto;} public void setProduto(Produto v){produto=v;}
    public TipoMovimentacao getTipo(){return tipo;} public void setTipo(TipoMovimentacao v){tipo=v;}
    public BigDecimal getQuantidade(){return quantidade;} public void setQuantidade(BigDecimal v){quantidade=v;}
    public Usuario getResponsavel(){return responsavel;} public void setResponsavel(Usuario v){responsavel=v;}
    public LocalDateTime getDataHora(){return dataHora;} public String getObservacao(){return observacao;} public void setObservacao(String v){observacao=v;}
}
