package br.icev.vendas;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class UtilDinheiro {

    private UtilDinheiro() {
        // Classe utilitária não deve ser instanciada
    }

    /**
     * Arredonda um valor BigDecimal para 2 casas decimais usando
     * a regra HALF_UP (arredondamento bancário padrão).
     */
    public static BigDecimal arredondar(BigDecimal valor) {
        if (valor == null) {
            return BigDecimal.ZERO;
        }
        // O teste "somaItemComArredondamento" exige 2 casas decimais, HALF_UP
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
}