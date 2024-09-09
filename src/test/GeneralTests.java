package test;

import static br.eng.rodrigogml.rfw.terminal.Terminal.clear;
import static br.eng.rodrigogml.rfw.terminal.Terminal.emptyLine;
import static br.eng.rodrigogml.rfw.terminal.Terminal.listGraphicChars;
import static br.eng.rodrigogml.rfw.terminal.Terminal.moveCursor;
import static br.eng.rodrigogml.rfw.terminal.Terminal.removeTextFormat;
import static br.eng.rodrigogml.rfw.terminal.Terminal.reset;
import static br.eng.rodrigogml.rfw.terminal.Terminal.setTextBackgroundColor;
import static br.eng.rodrigogml.rfw.terminal.Terminal.setTextColor;
import static br.eng.rodrigogml.rfw.terminal.Terminal.setTextFormat;
import static br.eng.rodrigogml.rfw.terminal.Terminal.writeFigletCentralized;

import java.util.Map.Entry;

import br.eng.rodrigogml.rfw.terminal.Terminal.TextBackgroundColor;
import br.eng.rodrigogml.rfw.terminal.Terminal.TextColor;
import br.eng.rodrigogml.rfw.terminal.Terminal.TextFormat;
import br.eng.rodrigogml.rfw.terminal.parsers.CommandParser;
import br.eng.rodrigogml.rfw.terminal.parsers.CommandParser.ParsedCommand;
import br.eng.rodrigogml.rfw.terminal.utils.Figlet;
import br.eng.rodrigogml.rfw.terminal.utils.Figlet.FigletFontType;

/**
 * Description: Classe utilizada para alguns teste locais pelo desenvolvedor, não faz parte do teste unitário.<br>
 *
 * @author Rodrigo Leitão
 * @since (8 de set. de 2024)
 */
public class GeneralTests {

  public static void main(String[] args) throws Exception {
    reset();
    clear();
    moveCursor(1, 1);
    setTextColor(TextColor.BRIGHT_WHITE);
    setTextFormat(TextFormat.BOLD);
    writeFigletCentralized("BIS HUB", FigletFontType.SPEED);
    removeTextFormat(TextFormat.BOLD);
    System.exit(0);

    String customAsciiArt = Figlet.generateWithFont("BIS ERP", Figlet.FigletFontType.GRAFFITI);
    customAsciiArt = Figlet.centralize(customAsciiArt, 200);
    System.out.println(customAsciiArt);

    String cmd = "arg1 arg2 \"Um argumento extra com espaços!\" arg3 -param1 -param2=valor --param3 = \"Esse é um \\\"Valor\\\" do param3\" arg4 -param5= 'Este é outro \\'tipo de valor com Scape\\' de aspas e outras \" no conteúdo interno do texto'";
    ParsedCommand result = CommandParser.parse(cmd);

    for (String arg : result.arguments) {
      System.out.println("Arg: " + arg);
    }

    for (Entry<String, String> entry : result.parameters.entrySet()) {
      System.out.println("Param: " + entry.getKey() + " / Value: " + entry.getValue());
    }

    System.out.println("[" + ("-teste".replaceAll("^--*", "")) + "]");
    System.out.println("[" + ("--teste".replaceAll("^--*", "")) + "]");
    System.out.println("[" + ("---".replaceAll("^--*", "")) + "]");
    System.out.println("[" + ("-".replaceAll("^--*", "")) + "]");

    // Limpa a tela
    setTextBackgroundColor(TextBackgroundColor.BLUE);
    clear();

    // Move o cursor para o topo esquerdo da tela (linha 1, coluna 1)
    // System.out.print("\u001B[1;1H");
    moveCursor(1, 1);

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
