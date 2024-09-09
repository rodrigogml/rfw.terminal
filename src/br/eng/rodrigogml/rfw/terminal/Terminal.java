package br.eng.rodrigogml.rfw.terminal;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import br.eng.rodrigogml.rfw.kernel.exceptions.RFWCriticalException;
import br.eng.rodrigogml.rfw.kernel.exceptions.RFWException;
import br.eng.rodrigogml.rfw.kernel.preprocess.PreProcess;
import br.eng.rodrigogml.rfw.kernel.utils.RUString;
import br.eng.rodrigogml.rfw.terminal.utils.Figlet;
import br.eng.rodrigogml.rfw.terminal.utils.Kernel32;

/**
 * Description: Classe est�tica com os comandos e constantes necess�rios para manipula��o do terminal.<br>
 * O objetivo dessa classe � manter todos os comandos e m�todos auxiliares par r�pida importa��o, sugerindo que a importa��o seja est�tica dos m�todos para mais clareza do c�digo.
 *
 * @author Rodrigo Leit�o
 * @since (8 de set. de 2024)
 */
public class Terminal {

  /**
   * C�digo ASCII para limpar o restante da linha a partir do cursor.<br>
   * Limpa os caracteres mas deixa o fundo pintado com a cor de fundo vigente.
   */
  public static final String ASCII_CLEAR_REMAINING_LINE = "\u001B[0K";
  /**
   * Define o tamanho padr�o do terminal para quando n�o for poss�vel obter o tamanho correto do terminal.<Br>
   */
  private static int terminalDefaultColumns = 120;

  /**
   * Acumulador de texto parcial escrito na linha, utilizado por exmeplo pelo comando {@link #writePart(String)}.<br>
   * Deve ser reiniciado em zero por todos os m�todos que quebrarem a linha e somado por todos que escrevem parciamente na linha.
   */
  private static int totalPartialLineWrote = 0;

  /**
   * Enumera��o para definir as cores de texto no terminal.
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
     * Construtor da enumera��o, atribuindo os c�digos de ativa��o e desativa��o ANSI.
     *
     * @param activationCode O c�digo ANSI para ativar a cor de texto.
     */
    TextColor(String activationCode) {
      this.activationCode = activationCode;
    }

    /**
     * Retorna o c�digo ANSI para ativar a cor de texto.
     *
     * @return O c�digo ANSI para ativar a cor de texto.
     */
    public String getActivationCode() {
      return this.activationCode;
    }

  }

  /**
   * Enumera��o para definir as cores de fundo no terminal.
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
     * Construtor da enumera��o, atribuindo os c�digos de ativa��o e desativa��o ANSI.
     *
     * @param activationCode O c�digo ANSI para ativar a cor de fundo.
     */
    TextBackgroundColor(String activationCode) {
      this.activationCode = activationCode;
    }

    /**
     * Retorna o c�digo ANSI para ativar a cor de fundo.
     *
     * @return O c�digo ANSI para ativar a cor de fundo.
     */
    public String getActivationCode() {
      return this.activationCode;
    }
  }

  /**
   * Enumera��o para definir formata��es de texto no terminal.
   */
  public enum TextFormat {

    /** Formata��o de texto em negrito. */
    BOLD("\u001B[1m", "\u001B[22m"),

    /** Formata��o de texto sublinhado. */
    UNDERLINE("\u001B[4m", "\u001B[24m"),

    /** Formata��o de texto piscante. */
    BLINK("\u001B[5m", "\u001B[25m"),

    /** Inverte as cores do texto e do fundo. */
    INVERT("\u001B[7m", "\u001B[27m");

    private final String activationCode;
    private final String deactivationCode;

    /**
     * Construtor da enumera��o, atribuindo os c�digos de ativa��o e desativa��o ANSI.
     *
     * @param activationCode O c�digo ANSI para ativar a formata��o.
     * @param deactivationCode O c�digo ANSI para desativar a formata��o.
     */
    TextFormat(String activationCode, String deactivationCode) {
      this.activationCode = activationCode;
      this.deactivationCode = deactivationCode;
    }

    /**
     * Retorna o c�digo ANSI para ativar a formata��o.
     *
     * @return O c�digo ANSI para ativar a formata��o.
     */
    public String getActivationCode() {
      return this.activationCode;
    }

    /**
     * Retorna o c�digo ANSI para desativar a formata��o.
     *
     * @return O c�digo ANSI para desativar a formata��o.
     */
    public String getDeactivationCode() {
      return this.deactivationCode;
    }
  }

  /**
   * Define a cor do texto no terminal.
   *
   * @param color A cor do texto a ser definida, baseada na enumera��o TextColor.
   */
  public static void setTextColor(TextColor color) {
    System.out.print(color.getActivationCode());
  }

  /**
   * Define a cor de fundo no terminal.
   *
   * @param backgroundColor A cor de fundo a ser definida, baseada na enumera��o TextBackgroundColor.
   */
  public static void setTextBackgroundColor(TextBackgroundColor backgroundColor) {
    System.out.print(backgroundColor.getActivationCode());
  }

  /**
   * Define m�ltiplas formata��es de texto no terminal.
   *
   * @param formats Um ou mais valores da enumera��o TextFormat que devem ser aplicados ao texto.
   */
  public static void setTextFormat(TextFormat... formats) {
    StringBuilder formatCodes = new StringBuilder();
    for (TextFormat format : formats) {
      formatCodes.append(format.getActivationCode());
    }
    System.out.print(formatCodes.toString());
  }

  /**
   * Remove m�ltiplas formata��es de texto no terminal.
   *
   * @param formats Um ou mais valores da enumera��o TextFormat que devem ser removidos do texto.
   */
  public static void removeTextFormat(TextFormat... formats) {
    StringBuilder formatCodes = new StringBuilder();
    for (TextFormat format : formats) {
      formatCodes.append(format.getDeactivationCode());
    }
    System.out.print(formatCodes.toString());
  }

  /**
   * Remove todas as formata��es de texto aplicadas, retornando ao formato padr�o do terminal.
   */
  public static void resetTextFormat() {
    System.out.print(TextFormat.BOLD.getDeactivationCode() +
        TextFormat.UNDERLINE.getDeactivationCode() +
        TextFormat.BLINK.getDeactivationCode() +
        TextFormat.INVERT.getDeactivationCode());
  }

  /**
   * Remove a cor atual do texto, retornando � cor padr�o do terminal.
   */
  public static void resetTextColor() {
    System.out.print("\u001B[39m");
  }

  /**
   * Remove a cor atual de fundo, retornando ao fundo padr�o do terminal.
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
   * Pode ser utilizado para limpar o restante da linha quando estamos sobreescrevendo algum conte�do, ou para for�ar o preenchimento com a cor de fundo em todo o restante da linha, inependente do tamanho do terminal.
   */
  public static void clearRemainingLine() {
    System.out.print("\u001B[K"); // Limpa at� o final da linha (preenchendo com o background vermelho)
  }

  /**
   * Reseta o terminal para o estado padr�o, removendo qualquer formata��o de texto ou cor.
   */
  public static void reset() {
    System.out.print("\u001B[0m");
  }

  /**
   * Move o cursor para uma posi��o espec�fica no terminal.
   *
   * @param row A linha para onde o cursor deve ser movido (1 � a primeira linha).
   * @param col A coluna para onde o cursor deve ser movido (1 � a primeira coluna).
   */
  public static void moveCursor(int row, int col) {
    System.out.print(String.format("\u001B[%d;%dH", row, col));
  }

  /**
   * Move o cursor para cima em um n�mero espec�fico de linhas.
   *
   * @param lines O n�mero de linhas para mover o cursor para cima.
   */
  public static void moveCursorUp(int lines) {
    System.out.print(String.format("\u001B[%dA", lines));
  }

  /**
   * Move o cursor para baixo em um n�mero espec�fico de linhas.
   *
   * @param lines O n�mero de linhas para mover o cursor para baixo.
   */
  public static void moveCursorDown(int lines) {
    System.out.print(String.format("\u001B[%dB", lines));
  }

  /**
   * Move o cursor para a direita em um n�mero espec�fico de colunas.
   *
   * @param columns O n�mero de colunas para mover o cursor para a direita.
   */
  public static void moveCursorRight(int columns) {
    System.out.print(String.format("\u001B[%dC", columns));
  }

  /**
   * Move o cursor para a esquerda em um n�mero espec�fico de colunas.
   *
   * @param columns O n�mero de colunas para mover o cursor para a esquerda.
   */
  public static void moveCursorLeft(int columns) {
    System.out.print(String.format("\u001B[%dD", columns));
  }

  /**
   * Este m�todo faz a limpeza do restante da linha atual ({@link #clearRemainingLine()} e imprime uma quebra de linha para garantir que passar� para a pr�xima.<br>
   * Se utilizado no come�o de uma linha, far� uma linha em branco com a cor de fundo definida. Se utilizada em uma linha j� com conte�do terminar� de incluir essa linha.
   */
  public static void emptyLine() {
    System.out.println(ASCII_CLEAR_REMAINING_LINE);
    totalPartialLineWrote = 0;
  }

  /**
   * Este m�todo faz a limpeza do restante da linha atual e imprime uma quebra de linha para garantir que passar� para a pr�xima. Se utilizado no come�o de uma linha, far� uma linha em branco com a cor de fundo definida. Se utilizado em uma linha j� com conte�do, terminar� de incluir essa linha.
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
   * Limpa a linha atual do in�cio at� a posi��o do cursor.
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
   * Limpa a tela a partir da posi��o do cursor at� o final da tela.
   */
  public static void clearScreenFromCursor() {
    System.out.print("\u001B[0J");
  }

  /**
   * Limpa a tela do in�cio at� a posi��o atual do cursor.
   */
  public static void clearScreenToCursor() {
    System.out.print("\u001B[1J");
  }

  /**
   * Salva a posi��o atual do cursor no terminal.
   * <p>
   * Nota: Apenas uma posi��o pode ser salva. Se o m�todo for chamado novamente, a posi��o salva anteriormente ser� sobrescrita.
   * </p>
   */
  public static void saveCursorPosition() {
    System.out.print("\u001B[s");
  }

  /**
   * Restaura a posi��o do cursor previamente salva no terminal.
   * <p>
   * Nota: Se a posi��o do cursor n�o tiver sido salva anteriormente, este comando n�o ter� efeito.
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
   * Move o cursor para o in�cio da linha atual, sem mudar de linha.
   */
  public static void returnToStartOfLine() {
    System.out.print("\u001B[G");
  }

  /**
   * Rola o conte�do da tela para cima em um n�mero espec�fico de linhas.
   *
   * @param lines O n�mero de linhas para rolar a tela para cima.
   */
  public static void scrollUp(int lines) {
    System.out.print(String.format("\u001B[%dS", lines));
  }

  /**
   * Rola o conte�do da tela para baixo em um n�mero espec�fico de linhas.
   *
   * @param lines O n�mero de linhas para rolar a tela para baixo.
   */
  public static void scrollDown(int lines) {
    System.out.print(String.format("\u001B[%dT", lines));
  }

  /**
   * Define uma regi�o de rolagem no terminal, restringindo a rolagem entre uma linha superior e inferior.
   *
   * @param top A linha superior da regi�o de rolagem.
   * @param bottom A linha inferior da regi�o de rolagem.
   */
  public static void setScrollRegion(int top, int bottom) {
    System.out.print(String.format("\u001B[%d;%dr", top, bottom));
  }

  /**
   * Reseta a regi�o de rolagem, permitindo rolagem em toda a tela.
   */
  public static void resetScrollRegion() {
    System.out.print("\u001B[r");
  }

  /**
   * Define o t�tulo da janela do terminal, caso o terminal suporte esta funcionalidade.
   *
   * @param title O t�tulo a ser definido para a janela do terminal.
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
   * Desabilita o echo no terminal para ocultar a entrada de texto do usu�rio.
   */
  public static void disableEcho() {
    System.out.print("\u001B[12l");
  }

  /**
   * Habilita o echo no terminal para mostrar a entrada de texto do usu�rio.
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
   * Ativa o modo de inser��o no terminal.
   */
  public static void enableInsertMode() {
    System.out.print("\u001B[4h");
  }

  /**
   * Define a cor do texto utilizando a paleta estendida de 256 cores.
   *
   * @param colorCode O c�digo da cor (0-255).
   */
  public static void setExtendedTextColor(int colorCode) {
    System.out.print(String.format("\u001B[38;5;%dm", colorCode));
  }

  /**
   * Define a cor de fundo utilizando a paleta estendida de 256 cores.
   *
   * @param colorCode O c�digo da cor (0-255).
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
    System.out.print("\u001B[6m"); // Piscar r�pido
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
   * Restaura a altura e largura padr�o dos caracteres.
   */
  public static void resetCharSize() {
    System.out.print("\u001B#5");
  }

  /**
   * Alterna para o modo gr�fico, se suportado.<br>
   * Para mais informa��o pesquise sobre os terminais:
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
   * Lista os caracteres e seus equivalentes no modo gr�fico.<Br>
   * Veja mais em {@link #enableGraphicMode()}.
   */
  public static void listGraphicChars() {
    // T�tulo
    disableGraphicMode();
    System.out.println("+-----------------------------------------------------------------+----------------------------------------------------------------+");
    System.out.println("| " + TextFormat.UNDERLINE.getActivationCode() + "Less Support" + TextFormat.UNDERLINE.getDeactivationCode() + "                                                    | " + TextFormat.UNDERLINE.getActivationCode() + "More Support" + TextFormat.UNDERLINE.getDeactivationCode() + "                                                   |");

    // Elementos Gr�ficos
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

    // Texto de Refer�ncia
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
    totalPartialLineWrote = 0;
  }

  /**
   * L� uma linha de entrada do console.
   * <p>
   * Este m�todo utiliza {@link System#console()} para capturar a entrada do usu�rio diretamente do console e retorna o texto digitado. � necess�rio que o programa esteja rodando em um terminal ou prompt de comando, pois {@code System.console()} pode retornar {@code null} em alguns ambientes, como IDEs.
   *
   * @return Uma {@link String} representando a linha de texto digitada pelo usu�rio.
   * @throws IllegalStateException se o {@link System#console()} n�o estiver dispon�vel.
   */
  public static String readLine() {
    Console console = System.console();
    if (console == null) {
      throw new IllegalStateException("Console n�o dispon�vel. Execute em um terminal.");
    }
    return console.readLine();
  }

  /**
   * L� uma linha de entrada do console, exibindo um prompt personalizado.
   * <p>
   * Este m�todo utiliza {@link System#console()} para capturar a entrada do usu�rio e exibe um prompt personalizado antes de capturar o texto. O programa deve ser executado em um terminal ou prompt de comando, pois {@code System.console()} pode retornar {@code null} em alguns ambientes, como IDEs.
   *
   * @param prompt O texto a ser exibido antes da captura da entrada.
   * @return Uma {@link String} representando a linha de texto digitada pelo usu�rio.
   * @throws IllegalStateException se o {@link System#console()} n�o estiver dispon�vel.
   */
  public static String readLine(String prompt) {
    Console console = System.console();
    if (console == null) {
      throw new IllegalStateException("Console n�o dispon�vel. Execute em um terminal.");
    }
    return console.readLine(prompt);
  }

  /**
   * L� uma senha do console sem exibir os caracteres digitados.
   * <p>
   * Este m�todo utiliza {@link System#console()} para capturar a senha sem ecoar os caracteres digitados no terminal. A senha � retornada como uma string, mas recomenda-se tratar a senha como um array de caracteres para maior seguran�a.
   *
   * @return Uma {@link String} representando a senha digitada pelo usu�rio.
   * @throws IllegalStateException se o {@link System#console()} n�o estiver dispon�vel.
   */
  public static String readPassword() {
    Console console = System.console();
    if (console == null) {
      throw new IllegalStateException("Console n�o dispon�vel. Execute em um terminal.");
    }
    char[] passwordChars = console.readPassword();
    return new String(passwordChars);
  }

  /**
   * L� uma senha do console sem exibir os caracteres digitados, exibindo um prompt personalizado.
   * <p>
   * Este m�todo utiliza {@link System#console()} para capturar a senha sem ecoar os caracteres digitados no terminal e exibe um prompt personalizado antes da captura. A senha � retornada como uma string, mas recomenda-se tratar a senha como um array de caracteres para maior seguran�a.
   *
   * @param prompt O texto a ser exibido antes da captura da senha.
   * @return Uma {@link String} representando a senha digitada pelo usu�rio.
   * @throws IllegalStateException se o {@link System#console()} n�o estiver dispon�vel.
   */
  public static String readPassword(String prompt) {
    Console console = System.console();
    if (console == null) {
      throw new IllegalStateException("Console n�o dispon�vel. Execute em um terminal.");
    }
    char[] passwordChars = console.readPassword(prompt);
    return new String(passwordChars);
  }

  /**
   * Obt�m o n�mero de colunas do terminal, de acordo com o sistema operacional.
   * <p>
   * Para sistemas Unix/Linux, utiliza o comando "stty size". Para sistemas Windows, utiliza uma chamada JNA na DLL Kernel32. Caso n�o seja poss�vel determinar o valor, o m�todo retornar� o valor de {@link Terminal#getTerminalDefaultColumns()}.
   *
   * @return O n�mero de colunas do terminal, ou valor de colunas padr�o se n�o for poss�vel determinar.
   */
  public static Integer getTerminalColumns() {
    Integer cols = null;
    try {
      String os = System.getProperty("os.name").toLowerCase();
      if (os.contains("win")) {
        cols = getWindowsTerminalCols();
      } else {
        cols = getUnixTerminalCols();
      }
    } catch (Exception e) {
    }
    return PreProcess.coalesce(cols, getTerminalDefaultColumns());
  }

  /**
   * Obt�m o n�mero de colunas do terminal em sistemas Unix/Linux.
   *
   * @return O n�mero de colunas do terminal, ou null se n�o for poss�vel determinar.
   */
  private static Integer getUnixTerminalCols() {
    try {
      Process process = Runtime.getRuntime().exec(new String[] { "sh", "-c", "stty size 2>/dev/null || echo 80 24" });
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String[] size = reader.readLine().split(" ");
        return Integer.parseInt(size[1]); // Retorna o n�mero de colunas (segundo valor)
      }
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Obt�m o n�mero de colunas do terminal usando JNA para acessar o Windows API.
   *
   * @return O n�mero de colunas do terminal, ou null se n�o for poss�vel determinar.
   */
  public static Integer getWindowsTerminalCols() {
    Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
    int hConsole = Kernel32.INSTANCE.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);

    if (Kernel32.INSTANCE.GetConsoleScreenBufferInfo(hConsole, info)) {
      return (int) info.dwSize.X; // Retorna o n�mero de colunas
    }
    return null; // Retorna null em caso de falha
  }

  /**
   * # define o tamanho padr�o do terminal para quando n�o for poss�vel obter o tamanho correto do terminal.<Br>
   * .
   *
   * @return the define o tamanho padr�o do terminal para quando n�o for poss�vel obter o tamanho correto do terminal
   */
  public static int getTerminalDefaultColumns() {
    return terminalDefaultColumns;
  }

  /**
   * # define o tamanho padr�o do terminal para quando n�o for poss�vel obter o tamanho correto do terminal.<Br>
   * .
   *
   * @param terminalDefaultColumns the new define o tamanho padr�o do terminal para quando n�o for poss�vel obter o tamanho correto do terminal
   */
  public static void setTerminalDefaultColumns(int terminalDefaultColumns) {
    Terminal.terminalDefaultColumns = terminalDefaultColumns;
  }

  /**
   * Escreve o texto gerado pelo Figlet centralizado no terminal.
   *
   * @param text O texto a ser convertido em arte ASCII.
   * @param fontType A fonte do Figlet a ser utilizada.
   * @throws IOException Caso ocorra um erro durante a gera��o da arte.
   * @throws RFWException
   */
  public static void writeFigletCentralized(String text, Figlet.FigletFontType fontType) throws RFWException {
    try {
      // Gera a arte ASCII usando o Figlet
      String asciiArt = Figlet.generateWithFont(text, fontType);

      // Obt�m o n�mero de colunas do terminal
      Integer terminalCols = getTerminalColumns();

      // Se n�o for poss�vel obter o n�mero de colunas, usa o valor padr�o
      if (terminalCols == null) {
        terminalCols = getTerminalDefaultColumns();
      }

      // Centraliza a arte ASCII com base no n�mero de colunas
      String centeredArt = Figlet.centralize(asciiArt, terminalCols);

      // Imprime o resultado no console linha por linha com o comando para completar o restante da linha para garantir as cores de fundo quanto utilizadas
      for (String line : centeredArt.split("\n")) {
        System.out.println(line + ASCII_CLEAR_REMAINING_LINE);
      }
      totalPartialLineWrote = 0;
    } catch (Exception e) {
      throw new RFWCriticalException("Erro ao gerar ou centralizar a arte ASCII.", e);
    }
  }

  /**
   * Escreve um conte�do alinhado a direita na posi��o definida.
   *
   * @param text Texto a ser escrito a linhado a direita.
   * @param col Coluna de posi��o do texto.
   */
  public static void writeAlignedRight(String text, int col) {
    System.out.println(RUString.completeUntilLengthLeft(" ", text, col - totalPartialLineWrote) + ASCII_CLEAR_REMAINING_LINE);
    totalPartialLineWrote = 0;
  }

  /**
   * Escreve um padr�o de texto repetido de acordo com o total de colunas do console.
   *
   * @param pattern
   */
  public static void writeFullLine(String pattern) {
    System.out.println(RUString.completeOrTruncateUntilLengthLeft(pattern, "", getTerminalColumns()) + ASCII_CLEAR_REMAINING_LINE);
    totalPartialLineWrote = 0;
  }

  /**
   * Simplesmente faz a impress�o no console, mas acrescenta o c�digo {@link #ASCII_CLEAR_REMAINING_LINE}Ipara garantir a cor at� o final do console.
   *
   * @param text
   */
  public static void write(String text) {
    System.out.println(text + ASCII_CLEAR_REMAINING_LINE);
    totalPartialLineWrote = 0;
  }

  /**
   * Este m�todo tem a simples fun��o de escrever parte do texto da linha, escreve com o print() ao inv�s do println(). No entanto adiciona o caracter ASCII para colorir a linha at� o final, e armazena internamente a quantidade de texto que j� foi escrito na linha. Permitindo assim que outros m�todos como {@link #writeAlignedRight(String, int)} consigam descontar o texto j� escrito.
   *
   * @param text
   */
  public static void writePart(String text) {
    System.out.print(text + ASCII_CLEAR_REMAINING_LINE);
    totalPartialLineWrote += text.length();
  }

  /**
   * Escreve um texto alinhado ao lado direito (final) do console.
   *
   * @param text Texto a ser escrito.
   */
  public static void writeAlignedRight(String text) {
    writeAlignedRight(text, getTerminalColumns());
  }
}
