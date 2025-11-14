package br.icev.vendas;

import java.math.BigDecimal;
import java.util.Map;

public class Pedido {

    // Enum Status já estava no esqueleto
    public enum Status { PAGO }

    private final Map<String, Integer> itensPorCodigo;
    private final String autorizacao;
    private final Status status;
    private final BigDecimal totalPago; // O teste 'checkoutComSucesso' exige isso

    public Pedido(Map<String, Integer> itensPorCodigo, String autorizacao, Status status, BigDecimal totalPago) {
        this.itensPorCodigo = itensPorCodigo;
        this.autorizacao = autorizacao;
        this.status = status;
        this.totalPago = totalPago;
    }

    // Teste: checkoutComSucesso (assert de R$ 40.00)
    public BigDecimal getTotalPago() {
        return this.totalPago;
    }

    // Teste: checkoutComSucesso (assert de "AUTH-123")
    public String getCodigoAutorizacao() {
        return this.autorizacao;
    }

    // Teste: checkoutComSucesso (assert de PAGO)
    public Status getStatus() {
        return this.status;
    }

    // Teste: checkoutComSucesso (assert de 2 itens)
    public int getQuantidadeItem(String codigo) {
        return this.itensPorCodigo.getOrDefault(codigo, 0);
    }
}