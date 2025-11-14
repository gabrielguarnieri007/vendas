package br.icev.vendas;

import br.icev.vendas.excecoes.QuantidadeInvalidaException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Carrinho {

    // Usamos um Map<Produto, Integer> para guardar os itens e suas quantidades.
    // Como Produto.java usa o "codigo" para equals/hashCode,
    // ele vai agrupar itens com mesmo código automaticamente.
    private final Map<Produto, Integer> itens = new HashMap<>();

    public void adicionar(Produto produto, int quantidade) throws QuantidadeInvalidaException {
        // Teste: naoPermiteQuantidadeInvalida
        if (quantidade <= 0) {
            throw new QuantidadeInvalidaException("Quantidade deve ser maior que zero.");
        }

        // Teste: acumulaQuantidadeMesmoCodigo
        // O .getOrDefault busca a quantidade atual ou 0 se o produto é novo
        int quantidadeAtual = this.itens.getOrDefault(produto, 0);
        this.itens.put(produto, quantidadeAtual + quantidade);
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;

        // Teste: somaItemComArredondamento
        for (Map.Entry<Produto, Integer> entry : this.itens.entrySet()) {
            Produto p = entry.getKey();
            int qtd = entry.getValue();

            // 1. Calcula o total do item (preço * quantidade)
            BigDecimal totalItem = p.getPrecoUnitario().multiply(new BigDecimal(qtd));
            
            // 2. Arredonda esse total para 2 casas (regra do UtilDinheiro)
            BigDecimal totalItemArredondado = UtilDinheiro.arredondar(totalItem);

            // 3. Soma ao subtotal
            subtotal = subtotal.add(totalItemArredondado);
        }
        
        // Retorna o subtotal final, que já é a soma de valores arredondados.
        return subtotal;
    }

    public BigDecimal getTotalComDesconto(PoliticaDesconto politica) {
        BigDecimal subtotal = this.getSubtotal();
        BigDecimal totalComDesconto = politica.aplicar(subtotal);

        // Teste: aplicaDescontoLouco (não permite total negativo)
        if (totalComDesconto.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }

        return totalComDesconto;
    }

    public int getTotalItens() {
        // Teste: acumulaQuantidadeMesmoCodigo
        // Soma todas as quantidades (values) do nosso mapa
        return this.itens.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}