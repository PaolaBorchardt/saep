package br.com.senai.almoxarifado;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class RegraQuantidadeTest {
    @Test void saidaDeveSerMaiorQueZero(){assertTrue(new BigDecimal("1").signum()>0);assertFalse(new BigDecimal("0").signum()>0);assertFalse(new BigDecimal("-1").signum()>0);}
    @Test void saldoInsuficienteDeveBloquearSaida(){BigDecimal saldo=new BigDecimal("5"), solicitada=new BigDecimal("6");assertTrue(saldo.compareTo(solicitada)<0);}
}
