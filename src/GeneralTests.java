import static br.eng.rodrigogml.rfw.cmd.Terminal.beep;
import static br.eng.rodrigogml.rfw.cmd.Terminal.clear;
import static br.eng.rodrigogml.rfw.cmd.Terminal.emptyLine;
import static br.eng.rodrigogml.rfw.cmd.Terminal.listGraphicChars;
import static br.eng.rodrigogml.rfw.cmd.Terminal.moveCursor;
import static br.eng.rodrigogml.rfw.cmd.Terminal.reset;
import static br.eng.rodrigogml.rfw.cmd.Terminal.setTextBackgroundColor;
import static br.eng.rodrigogml.rfw.cmd.Terminal.setTextColor;

import br.eng.rodrigogml.rfw.cmd.Terminal.TextBackgroundColor;
import br.eng.rodrigogml.rfw.cmd.Terminal.TextColor;

/**
 * Description: Classe utilizada para alguns teste locais pelo desenvolvedor, não faz parte do teste unitário.<br>
 *
 * @author Rodrigo Leitão
 * @since (8 de set. de 2024)
 */
public class GeneralTests {

  public static void main(String[] args) throws Exception {
    // Limpa a tela
    clear();

    // Move o cursor para o topo esquerdo da tela (linha 1, coluna 1)
    // System.out.print("\u001B[1;1H");
    moveCursor(1, 1);

    setTextBackgroundColor(TextBackgroundColor.BLUE);
    setTextColor(TextColor.WHITE);
    System.out.println("Aqui vai um texto de teste.");
    System.out.print("Aqui limparmos até o fim da linha.");
    emptyLine();
    emptyLine();

    // // Texto em verde
    // System.out.print("\u001B[32mTexto em verde\n");
    //
    // // Move para uma nova linha e escreve texto em negrito
    // System.out.print("\u001B[1B\u001B[1mTexto em negrito\n");
    //
    // // Move o cursor para baixo 2 linhas e escreve texto sublinhado
    // System.out.print("\u001B[2B\u001B[4mTexto sublinhado\n");
    //
    // // Espera 2 segundos
    // Thread.sleep(2000);
    //
    // // Salva a posição atual do cursor
    // System.out.print("\u001B[s");
    //
    // // Move o cursor para baixo e para a direita
    // System.out.print("\u001B[3B\u001B[10CTexto em uma nova posição\n");
    //
    // // Restaura a posição anterior do cursor
    // System.out.print("\u001B[u");
    //
    // // Escreve o texto restaurado na posição original
    // System.out.println("Texto na posição restaurada");
    //

    emptyLine(3);

    drawBoxInGraphicMode();
    listGraphicChars();

    beep();
    beep();
    beep();

    // Resetar todas as formatações antes de sair da aplicação
    reset();
  }

  /**
   * Ativa o modo gráfico e desenha uma caixa no terminal.
   */
  public static void drawBoxInGraphicMode() {
    // Ativar modo gráfico
    System.out.print("\u001B(0");

    // Desenhar caixa usando caracteres gráficos
    System.out.print("lqqqqqqqqqqk\n"); // Topo da caixa
    System.out.print("x          x\n"); // Laterais
    System.out.print("x          x\n"); // Laterais
    System.out.print("mqqqqqqqqqqj\n"); // Fundo da caixa

    // Voltar ao modo normal
    System.out.print("\u001B(B");
  }

}
