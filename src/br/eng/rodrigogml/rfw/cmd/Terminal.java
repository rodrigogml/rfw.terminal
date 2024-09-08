package br.eng.rodrigogml.rfw.cmd;

/**
 * Description: Classe estática com os comandos e constantes necessários para manipulação do terminal.<br>
 * O objetivo dessa classe é manter todos os comandos e métodos auxiliares par rápida importação, sugerindo que a importação seja estática dos métodos para mais clareza do código.
 *
 * @author Rodrigo Leitão
 * @since (8 de set. de 2024)
 */
public class Terminal {
  /**
   * Enumeração para definir as cores de texto no terminal.
   */
  public static enum TextColor {

    /** Cor de texto preta. */
    BLACK("\u001B[30m"),

    /** Cor de texto vermelha. */
    RED("\u001B[31m"),

    /** Cor de texto verde. */
    GREEN("\u001B[32m"),

    /** Cor de texto amarela. */
    YELLOW("\u001B[33m"),

    /** Cor de texto azul. */
    BLUE("\u001B[34m"),

    /** Cor de texto magenta. */
    MAGENTA("\u001B[35m"),

    /** Cor de texto ciano. */
    CYAN("\u001B[36m"),

    /** Cor de texto branca. */
    WHITE("\u001B[37m"),

    /** Cor de texto preta brilhante. */
    BRIGHT_BLACK("\u001B[90m"),

    /** Cor de texto vermelha brilhante. */
    BRIGHT_RED("\u001B[91m"),

    /** Cor de texto verde brilhante. */
    BRIGHT_GREEN("\u001B[92m"),

    /** Cor de texto amarela brilhante. */
    BRIGHT_YELLOW("\u001B[93m"),

    /** Cor de texto azul brilhante. */
    BRIGHT_BLUE("\u001B[94m"),

    /** Cor de texto magenta brilhante. */
    BRIGHT_MAGENTA("\u001B[95m"),

    /** Cor de texto ciano brilhante. */
    BRIGHT_CYAN("\u001B[96m"),

    /** Cor de texto branca brilhante. */
    BRIGHT_WHITE("\u001B[97m");

    private final String activationCode;

    /**
     * Construtor da enumeração, atribuindo os códigos de ativação e desativação ANSI.
     *
     * @param activationCode O código ANSI para ativar a cor de texto.
     */
    TextColor(String activationCode) {
      this.activationCode = activationCode;
    }

    /**
     * Retorna o código ANSI para ativar a cor de texto.
     *
     * @return O código ANSI para ativar a cor de texto.
     */
    public String getActivationCode() {
      return this.activationCode;
    }

  }

  /**
   * Enumeração para definir as cores de fundo no terminal.
   */
  public static enum TextBackgroundColor {

    /** Cor de fundo preta. */
    BLACK("\u001B[40m"),

    /** Cor de fundo vermelha. */
    RED("\u001B[41m"),

    /** Cor de fundo verde. */
    GREEN("\u001B[42m"),

    /** Cor de fundo amarela. */
    YELLOW("\u001B[43m"),

    /** Cor de fundo azul. */
    BLUE("\u001B[44m"),

    /** Cor de fundo magenta. */
    MAGENTA("\u001B[45m"),

    /** Cor de fundo ciano. */
    CYAN("\u001B[46m"),

    /** Cor de fundo branca. */
    WHITE("\u001B[47m"),

    /** Cor de fundo preta brilhante. */
    BRIGHT_BLACK("\u001B[100m"),

    /** Cor de fundo vermelha brilhante. */
    BRIGHT_RED("\u001B[101m"),

    /** Cor de fundo verde brilhante. */
    BRIGHT_GREEN("\u001B[102m"),

    /** Cor de fundo amarela brilhante. */
    BRIGHT_YELLOW("\u001B[103m"),

    /** Cor de fundo azul brilhante. */
    BRIGHT_BLUE("\u001B[104m"),

    /** Cor de fundo magenta brilhante. */
    BRIGHT_MAGENTA("\u001B[105m"),

    /** Cor de fundo ciano brilhante. */
    BRIGHT_CYAN("\u001B[106m"),

    /** Cor de fundo branca brilhante. */
    BRIGHT_WHITE("\u001B[107m");

    private final String activationCode;

    /**
     * Construtor da enumeração, atribuindo os códigos de ativação e desativação ANSI.
     *
     * @param activationCode O código ANSI para ativar a cor de fundo.
     */
    TextBackgroundColor(String activationCode) {
      this.activationCode = activationCode;
    }

    /**
     * Retorna o código ANSI para ativar a cor de fundo.
     *
     * @return O código ANSI para ativar a cor de fundo.
     */
    public String getActivationCode() {
      return this.activationCode;
    }
  }

  /**
   * Enumeração para definir formatações de texto no terminal.
   */
  public enum TextFormat {

    /** Formatação de texto em negrito. */
    BOLD("\u001B[1m", "\u001B[22m"),

    /** Formatação de texto sublinhado. */
    UNDERLINE("\u001B[4m", "\u001B[24m"),

    /** Formatação de texto piscante. */
    BLINK("\u001B[5m", "\u001B[25m"),

    /** Inverte as cores do texto e do fundo. */
    INVERT("\u001B[7m", "\u001B[27m");

    private final String activationCode;
    private final String deactivationCode;

    /**
     * Construtor da enumeração, atribuindo os códigos de ativação e desativação ANSI.
     *
     * @param activationCode O código ANSI para ativar a formatação.
     * @param deactivationCode O código ANSI para desativar a formatação.
     */
    TextFormat(String activationCode, String deactivationCode) {
      this.activationCode = activationCode;
      this.deactivationCode = deactivationCode;
    }

    /**
     * Retorna o código ANSI para ativar a formatação.
     *
     * @return O código ANSI para ativar a formatação.
     */
    public String getActivationCode() {
      return this.activationCode;
    }

    /**
     * Retorna o código ANSI para desativar a formatação.
     *
     * @return O código ANSI para desativar a formatação.
     */
    public String getDeactivationCode() {
      return this.deactivationCode;
    }
  }

  /**
   * Define a cor do texto no terminal.
   *
   * @param color A cor do texto a ser definida, baseada na enumeração TextColor.
   */
  public static void setTextColor(TextColor color) {
    System.out.print(color.getActivationCode());
  }

  /**
   * Define a cor de fundo no terminal.
   *
   * @param backgroundColor A cor de fundo a ser definida, baseada na enumeração TextBackgroundColor.
   */
  public static void setTextBackgroundColor(TextBackgroundColor backgroundColor) {
    System.out.print(backgroundColor.getActivationCode());
  }

  /**
   * Define múltiplas formatações de texto no terminal.
   *
   * @param formats Um ou mais valores da enumeração TextFormat que devem ser aplicados ao texto.
   */
  public static void setTextFormat(TextFormat... formats) {
    StringBuilder formatCodes = new StringBuilder();
    for (TextFormat format : formats) {
      formatCodes.append(format.getActivationCode());
    }
    System.out.print(formatCodes.toString());
  }

  /**
   * Remove múltiplas formatações de texto no terminal.
   *
   * @param formats Um ou mais valores da enumeração TextFormat que devem ser removidos do texto.
   */
  public static void removeTextFormat(TextFormat... formats) {
    StringBuilder formatCodes = new StringBuilder();
    for (TextFormat format : formats) {
      formatCodes.append(format.getDeactivationCode());
    }
    System.out.print(formatCodes.toString());
  }

  /**
   * Remove todas as formatações de texto aplicadas, retornando ao formato padrão do terminal.
   */
  public static void resetTextFormat() {
    System.out.print(TextFormat.BOLD.getDeactivationCode() +
        TextFormat.UNDERLINE.getDeactivationCode() +
        TextFormat.BLINK.getDeactivationCode() +
        TextFormat.INVERT.getDeactivationCode());
  }

  /**
   * Remove a cor atual do texto, retornando à cor padrão do terminal.
   */
  public static void resetTextColor() {
    System.out.print("\u001B[39m");
  }

  /**
   * Remove a cor atual de fundo, retornando ao fundo padrão do terminal.
   */
  public static void resetTextBackgroundColor() {
    System.out.print("\u001B[49m");
  }

  /**
   * Envia um comando para limpar o terminal.
   */
  public static void clear() {
    System.out.print("\u001B[2J");
  }

  /**
   * Envia um comando para limpar o restante da linha.<br>
   * Pode ser utilizado para limpar o restante da linha quando estamos sobreescrevendo algum conteúdo, ou para forçar o preenchimento com a cor de fundo em todo o restante da linha, inependente do tamanho do terminal.
   */
  public static void clearRemainingLine() {
    System.out.print("\u001B[K"); // Limpa até o final da linha (preenchendo com o background vermelho)
  }

  /**
   * Reseta o terminal para o estado padrão, removendo qualquer formatação de texto ou cor.
   */
  public static void reset() {
    System.out.print("\u001B[0m");
  }

  /**
   * Move o cursor para uma posição específica no terminal.
   *
   * @param row A linha para onde o cursor deve ser movido (1 é a primeira linha).
   * @param col A coluna para onde o cursor deve ser movido (1 é a primeira coluna).
   */
  public static void moveCursor(int row, int col) {
    System.out.print(String.format("\u001B[%d;%dH", row, col));
  }

  /**
   * Move o cursor para cima em um número específico de linhas.
   *
   * @param lines O número de linhas para mover o cursor para cima.
   */
  public static void moveCursorUp(int lines) {
    System.out.print(String.format("\u001B[%dA", lines));
  }

  /**
   * Move o cursor para baixo em um número específico de linhas.
   *
   * @param lines O número de linhas para mover o cursor para baixo.
   */
  public static void moveCursorDown(int lines) {
    System.out.print(String.format("\u001B[%dB", lines));
  }

  /**
   * Move o cursor para a direita em um número específico de colunas.
   *
   * @param columns O número de colunas para mover o cursor para a direita.
   */
  public static void moveCursorRight(int columns) {
    System.out.print(String.format("\u001B[%dC", columns));
  }

  /**
   * Move o cursor para a esquerda em um número específico de colunas.
   *
   * @param columns O número de colunas para mover o cursor para a esquerda.
   */
  public static void moveCursorLeft(int columns) {
    System.out.print(String.format("\u001B[%dD", columns));
  }

  /**
   * Este método faz a limpeza do restante da linha atual ({@link #clearRemainingLine()} e imprime uma quebra de linha para garantir que passará para a próxima.<br>
   * Se utilizado no começo de uma linha, fará uma linha em branco com a cor de fundo definida. Se utilizada em uma linha já com conteúdo terminará de incluir essa linha.
   */
  public static void emptyLine() {
    System.out.println("\u001B[0K");
  }

  /**
   * Este método faz a limpeza do restante da linha atual e imprime uma quebra de linha para garantir que passará para a próxima. Se utilizado no começo de uma linha, fará uma linha em branco com a cor de fundo definida. Se utilizado em uma linha já com conteúdo, terminará de incluir essa linha.
   *
   * @param numberOfLines A quantidade de linhas vazias a serem inseridas.
   */
  public static void emptyLine(int numberOfLines) {
    // ANSI code to clear the remaining line
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < numberOfLines; i++) {
      sb.append("\u001B[0K\n");
    }
    System.out.print(sb.toString());
  }

  /**
   * Limpa a linha atual do início até a posição do cursor.
   */
  public static void clearLineFromStart() {
    System.out.print("\u001B[1K");
  }

  /**
   * Limpa toda a linha atual.
   */
  public static void clearLine() {
    System.out.print("\u001B[2K");
  }

  /**
   * Limpa a tela a partir da posição do cursor até o final da tela.
   */
  public static void clearScreenFromCursor() {
    System.out.print("\u001B[0J");
  }

  /**
   * Limpa a tela do início até a posição atual do cursor.
   */
  public static void clearScreenToCursor() {
    System.out.print("\u001B[1J");
  }

  /**
   * Salva a posição atual do cursor no terminal.
   * <p>
   * Nota: Apenas uma posição pode ser salva. Se o método for chamado novamente, a posição salva anteriormente será sobrescrita.
   * </p>
   */
  public static void saveCursorPosition() {
    System.out.print("\u001B[s");
  }

  /**
   * Restaura a posição do cursor previamente salva no terminal.
   * <p>
   * Nota: Se a posição do cursor não tiver sido salva anteriormente, este comando não terá efeito.
   * </p>
   */
  public static void restoreCursorPosition() {
    System.out.print("\u001B[u");
  }

  /**
   * Oculta o cursor no terminal.
   */
  public static void hideCursor() {
    System.out.print("\u001B[?25l");
  }

  /**
   * Exibe o cursor no terminal, caso esteja oculto.
   */
  public static void showCursor() {
    System.out.print("\u001B[?25h");
  }

  /**
   * Realiza um beep (som) no terminal.
   */
  public static void beep() {
    System.out.print("\u0007");
  }

  /**
   * Move o cursor para o início da linha atual, sem mudar de linha.
   */
  public static void returnToStartOfLine() {
    System.out.print("\u001B[G");
  }

  /**
   * Rola o conteúdo da tela para cima em um número específico de linhas.
   *
   * @param lines O número de linhas para rolar a tela para cima.
   */
  public static void scrollUp(int lines) {
    System.out.print(String.format("\u001B[%dS", lines));
  }

  /**
   * Rola o conteúdo da tela para baixo em um número específico de linhas.
   *
   * @param lines O número de linhas para rolar a tela para baixo.
   */
  public static void scrollDown(int lines) {
    System.out.print(String.format("\u001B[%dT", lines));
  }

  /**
   * Define uma região de rolagem no terminal, restringindo a rolagem entre uma linha superior e inferior.
   *
   * @param top A linha superior da região de rolagem.
   * @param bottom A linha inferior da região de rolagem.
   */
  public static void setScrollRegion(int top, int bottom) {
    System.out.print(String.format("\u001B[%d;%dr", top, bottom));
  }

  /**
   * Reseta a região de rolagem, permitindo rolagem em toda a tela.
   */
  public static void resetScrollRegion() {
    System.out.print("\u001B[r");
  }

  /**
   * Define o título da janela do terminal, caso o terminal suporte esta funcionalidade.
   *
   * @param title O título a ser definido para a janela do terminal.
   */
  public static void setWindowTitle(String title) {
    System.out.print(String.format("\u001B]0;%s\u0007", title));
  }

  /**
   * Coloca o terminal em modo de tela cheia, caso suportado.
   */
  public static void enterFullScreen() {
    System.out.print("\u001B[?1049h");
  }

  /**
   * Sai do modo de tela cheia no terminal, caso suportado.
   */
  public static void exitFullScreen() {
    System.out.print("\u001B[?1049l");
  }

  /**
   * Entra no modo alternativo de buffer de tela no terminal.
   */
  public static void enableAlternateBuffer() {
    System.out.print("\u001B[?1049h");
  }

  /**
   * Sai do modo alternativo de buffer de tela no terminal.
   */
  public static void disableAlternateBuffer() {
    System.out.print("\u001B[?1049l");
  }

  /**
   * Desabilita o echo no terminal para ocultar a entrada de texto do usuário.
   */
  public static void disableEcho() {
    System.out.print("\u001B[12l");
  }

  /**
   * Habilita o echo no terminal para mostrar a entrada de texto do usuário.
   */
  public static void enableEcho() {
    System.out.print("\u001B[12h");
  }

  /**
   * Oculta a barra de rolagem do terminal, se suportado.
   */
  public static void hideScrollBar() {
    System.out.print("\u001B[?30l");
  }

  /**
   * Exibe a barra de rolagem do terminal, se suportado.
   */
  public static void showScrollBar() {
    System.out.print("\u001B[?30h");
  }

  /**
   * Ativa o modo de sobrescrita no terminal.
   */
  public static void enableOverwriteMode() {
    System.out.print("\u001B[4l");
  }

  /**
   * Ativa o modo de inserção no terminal.
   */
  public static void enableInsertMode() {
    System.out.print("\u001B[4h");
  }

  /**
   * Define a cor do texto utilizando a paleta estendida de 256 cores.
   *
   * @param colorCode O código da cor (0-255).
   */
  public static void setExtendedTextColor(int colorCode) {
    System.out.print(String.format("\u001B[38;5;%dm", colorCode));
  }

  /**
   * Define a cor de fundo utilizando a paleta estendida de 256 cores.
   *
   * @param colorCode O código da cor (0-255).
   */
  public static void setExtendedBackgroundColor(int colorCode) {
    System.out.print(String.format("\u001B[48;5;%dm", colorCode));
  }

  /**
   * Define o texto para piscar lentamente.
   */
  public static void setTextBlinkSlow() {
    System.out.print("\u001B[5m"); // Piscar lento
  }

  /**
   * Define o texto para piscar rapidamente.
   */
  public static void setTextBlinkFast() {
    System.out.print("\u001B[6m"); // Piscar rápido
  }

  /**
   * Desativa o piscar do cursor no terminal, caso suportado.
   */
  public static void disableCursorBlinking() {
    System.out.print("\u001B[?12l");
  }

  /**
   * Ativa o piscar do cursor no terminal, caso suportado.
   */
  public static void enableCursorBlinking() {
    System.out.print("\u001B[?12h");
  }

  /**
   * Define o texto como subscript (subscrito), se suportado.
   */
  public static void setSubscript() {
    System.out.print("\u001B[8m"); // Subscrito
  }

  /**
   * Define o texto como superscript (sobrescrito), se suportado.
   */
  public static void setSuperscript() {
    System.out.print("\u001B[73m"); // Sobrescrito
  }

  /**
   * Define caracteres com largura dupla, se suportado.
   */
  public static void setDoubleWidth() {
    System.out.print("\u001B#6");
  }

  /**
   * Define caracteres com altura dupla, se suportado.
   */
  public static void setDoubleHeight() {
    System.out.print("\u001B#3");
  }

  /**
   * Restaura a altura e largura padrão dos caracteres.
   */
  public static void resetCharSize() {
    System.out.print("\u001B#5");
  }

  /**
   * Alterna para o modo gráfico, se suportado.<br>
   * Para mais informação pesquise sobre os terminais:
   * <li>VT100 Manual: VT100 User Guide
   * <li>VT220 Manual: VT220 Programmer Reference Manual
   */
  public static void enableGraphicMode() {
    System.out.print("\u001B(0");
  }

  /**
   * Retorna ao modo de caracteres normal.
   */
  public static void disableGraphicMode() {
    System.out.print("\u001B(B");
  }

  /**
   * Lista os caracteres e seus equivalentes no modo gráfico.<Br>
   * Veja mais em {@link #enableGraphicMode()}.
   */
  public static void listGraphicChars() {
    // Título
    System.out.println("+-----------------------------------------------------------------+----------------------------------------------------------------+");
    System.out.println("| " + TextFormat.UNDERLINE.getActivationCode() + "Less Support" + TextFormat.UNDERLINE.getDeactivationCode() + "                                                    | " + TextFormat.UNDERLINE.getActivationCode() + "More Support" + TextFormat.UNDERLINE.getDeactivationCode() + "                                                   |");

    // Elementos Gráficos
    System.out.print("| ");
    enableGraphicMode();
    for (char c = 64; c <= 95; c++) {
      System.out.print(c + " ");
    }
    disableGraphicMode();
    System.out.print("| ");
    enableGraphicMode();
    for (char c = 96; c <= 127; c++) {
      System.out.print(c + " ");
    }
    disableGraphicMode();
    System.out.println("|");

    // Texto de Referência
    System.out.print("| ");
    for (char c = 64; c <= 95; c++) {
      System.out.print(c + " ");
    }
    System.out.print("| ");
    for (char c = 96; c <= 127; c++) {
      System.out.print(c + " ");
    }
    System.out.print("|");
    System.out.println();
    System.out.println("+-----------------------------------------------------------------+----------------------------------------------------------------+");
  }

}
