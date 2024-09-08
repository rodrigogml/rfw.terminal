package br.eng.rodrigogml.rfw.terminal.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.github.lalyos.jfiglet.FigletFont;

import br.eng.rodrigogml.rfw.kernel.utils.RUString;

/**
 * Description: Classe para gera��o de texto "art ASCII" ou Figlet Fonts sob demanda.<br>
 *
 * @author Rodrigo Leit�o
 * @since (8 de set. de 2024)
 */
public class Figlet {

  /**
   * Enumera��o para representar as fontes dispon�veis na biblioteca Figlet4J.
   */
  public enum FigletFontType {
    STANDARD("standard"), BLOCK("block"), SLANT("slant"), SHADOW("shadow"), BIG("big"), THREE_D("3-d"), THREE_X_FIVE("3x5"), FIVE_LINE_OBLIQUE("5lineoblique"), ACROBATIC("acrobatic"), ALLIGATOR("alligator"), ALPHABET("alphabet"), AVATAR("avatar"), BANNER("banner"), BANNER3_D("banner3-D"), BANNER3("banner3"), BANNER4("banner4"), BARBWIRE("barbwire"), BASIC("basic"), BELL("bell"), BIGCHIEF("bigchief"), BINARY("binary"), BUBBLE("bubble"), BULBHEAD("bulbhead"), CALGPHY2("calgphy2"), CALIGRAPHY("caligraphy"), CATWALK("catwalk"), CHUNKY("chunky"), COINSTAK("coinstak"), COLOSSAL("colossal"), COMPUTER("computer"), CONTESSA("contessa"), CONTRAST("contrast"), COSMIC("cosmic"), COSMIKE("cosmike"), CRICKET("cricket"), CURSIVE("cursive"), CYBERLARGE("cyberlarge"), CYBERMEDIUM("cybermedium"), CYBERSMALL("cybersmall"), DIAMOND("diamond"), DIGITAL("digital"), DOH("doh"), DOOM("doom"), DOTMATRIX("dotmatrix"), DRPEPPER("drpepper"), EFTICHESS("eftichess"), EFTIFONT("eftifont"), EFTIPITI("eftipiti"), EFTIROBOT("eftirobot"), EFTITALIC(
        "eftitalic"), EFTIWALL("eftiwall"), EFTIWATER("eftiwater"), EPIC("epic"), FENDER("fender"), FOURTOPS("fourtops"), FUZZY("fuzzy"), GOOFY("goofy"), GOTHIC("gothic"), GRAFFITI("graffiti"), HOLLYWOOD("hollywood"), INVITA("invita"), ISOMETRIC1("isometric1"), ISOMETRIC2("isometric2"), ISOMETRIC3("isometric3"), ISOMETRIC4("isometric4"), ITALIC("italic"), IVRIT("ivrit"), JAZMINE("jazmine"), JERUSALEM("jerusalem"), KATAKANA("katakana"), KBAN("kban"), LARRY3D("larry3d"), LCD("lcd"), LEAN("lean"), LETTERS("letters"), LINUX("linux"), LOCKERGNOME("lockergnome"), MADRID("madrid"), MARQUEE("marquee"), MAXFOUR("maxfour"), MIKE("mike"), MINI("mini"), MIRROR("mirror"), MNEMONIC("mnemonic"), MORSE("morse"), MOSCOW("moscow"), NANCYJ_FANCY("nancyj-fancy"), NANCYJ_UNDERLINED("nancyj-underlined"), NANCYJ("nancyj"), NIPPLES("nipples"), NTGREEK("ntgreek"), O8("o8"), OGRE("ogre"), PAWP("pawp"), PEAKS(
            "peaks"), PEBBLES("pebbles"), PEPPER("pepper"), POISON("poison"), PUFFY("puffy"), PYRAMID("pyramid"), RECTANGLES("rectangles"), RELIEF("relief"), RELIEF2("relief2"), REV("rev"), ROMAN("roman"), ROT13("rot13"), ROUNDED("rounded"), ROWANCAP("rowancap"), ROZZO("rozzo"), RUNIC("runic"), RUNYC("runyc"), SBLOOD("sblood"), SCRIPT("script"), SERIFCAP("serifcap"), SHORT("short"), SLIDE("slide"), SLSCRIPT("slscript"), SMALL("small"), SMISOME1("smisome1"), SMKEYBOARD("smkeyboard"), SMSCRIPT("smscript"), SMSHADOW("smshadow"), SMSLANT("smslant"), SMTENGWAR("smtengwar"), SPEED("speed"), STAMPATELLO("stampatello"), STARWARS("starwars"), STELLAR("stellar"), STOP("stop"), STRAIGHT("straight"), TANJA("tanja"), TENGWAR("tengwar"), TERM("term"), THICK("thick"), THIN("thin"), THREEPOINT("threepoint"), TICKS("ticks"), TICKSSLANT("ticksslant"), TINKER_TOY("tinker-toy"), TOMBSTONE("tombstone"), TREK("trek"), TSALAGI("tsalagi"), TWOPOINT("twopoint"), UNIVERS("univers"), USAFLAG("usaflag"), WAVY("wavy"), WEIRD("weird");

    private final String fontName;

    /**
     * Construtor da enumera��o.
     *
     * @param fontName O nome da fonte inclu�da na biblioteca Figlet4J.
     */
    FigletFontType(String fontName) {
      this.fontName = fontName;
    }

    /**
     * Retorna o nome da fonte correspondente.
     *
     * @return O nome da fonte como uma string.
     */
    public String getFontName() {
      return fontName;
    }
  }

  /**
   * Gera texto em ASCII Art usando a fonte padr�o.
   *
   * @param text O texto a ser convertido em ASCII Art.
   * @return String contendo o texto convertido em ASCII Art.
   * @throws Exception Caso ocorra algum erro durante a convers�o.
   */
  public static String generate(String text) throws Exception {
    return FigletFont.convertOneLine(text); // Usa a fonte padr�o inclu�da na depend�ncia
  }

  /**
   * Gera texto em ASCII Art com uma fonte espec�fica definida pela enumera��o FigletFontType.
   *
   * @param text O texto a ser convertido em ASCII Art.
   * @param fontType A fonte a ser usada, representada pela enumera��o FigletFontType.
   * @return String contendo o texto convertido em ASCII Art.
   * @throws Exception Caso ocorra algum erro durante a convers�o.
   */
  public static String generateWithFont(String text, FigletFontType fontType) throws Exception {
    // Carrega a fonte diretamente do classpath usando o fontName da enumera��o
    String fontPath = "/flf/" + fontType.getFontName() + ".flf";
    return FigletFont.convertOneLine(Figlet.class.getResourceAsStream(fontPath), text);
  }

  /**
   * Gera um arquivo HTML com exemplos de todas as fontes Figlet dispon�veis.
   *
   * @param exampleText O texto de exemplo a ser utilizado em todas as fontes.
   * @param outputPath O caminho para o arquivo .html ou pasta onde o arquivo ser� salvo.
   * @throws IOException Caso ocorra algum erro ao escrever o arquivo.
   */
  public static void exportFigletPortfolioHTML(String exampleText, String outputPath) throws IOException {
    File file;
    if (outputPath.endsWith(".html")) {
      file = new File(outputPath);
    } else {
      file = new File(outputPath + "/figlet_portfolio.html");
    }

    try (FileWriter writer = new FileWriter(file)) {
      writer.write("<!DOCTYPE html>\n");
      writer.write("<html lang='en'>\n<head>\n<meta charset='UTF-8'>\n");
      writer.write("<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n");
      writer.write("<title>Figlet Fonts</title>\n");
      writer.write("<style>\n");
      writer.write("body { background-color: white; color: black; font-family: monospace; }\n");
      writer.write("pre { font-size: 12px; }\n");
      writer.write("</style>\n</head>\n<body>\n");
      writer.write("<h1>Figlet Fonts</h1>\n");

      // Percorre todas as fontes da enumera��o e escreve o texto de exemplo
      for (FigletFontType fontType : FigletFontType.values()) {
        writer.write("<h2>" + fontType.name() + "</h2>\n");
        writer.write("<pre>\n");

        try {
          // Gera o texto com a fonte atual
          String asciiArt = Figlet.generateWithFont(exampleText, fontType);
          writer.write(asciiArt);
        } catch (Exception e) {
          writer.write("[Erro ao gerar ASCII art com a fonte " + fontType.name() + "]\n");
        }

        writer.write("</pre>\n");
        writer.write("<hr>\n");
      }

      writer.write("</body>\n</html>");
    }
  }

  /**
   * Centraliza a arte ASCII em um n�mero espec�fico de colunas.
   * <p>
   * O m�todo analisa o comprimento da linha mais longa na arte ASCII e insere espa�os antes de cada linha para fazer com que a arte pare�a centralizada no n�mero de colunas fornecido. Se a linha mais longa j� ocupar o total de colunas ou for maior, a arte � retornada sem modifica��o.
   *
   * @param asciiArt A arte ASCII a ser centralizada.
   * @param totalColumns O n�mero total de colunas dispon�veis para centralizar o texto.
   * @return A arte ASCII centralizada, ou a arte original se j� for maior que o n�mero de colunas.
   */
  public static String centralize(String asciiArt, int totalColumns) {
    String[] lines = asciiArt.split("\n");

    int maxLength = 0;
    for (String line : lines) {
      if (line.length() > maxLength) {
        maxLength = line.length();
      }
    }

    if (maxLength >= totalColumns) {
      return asciiArt;
    }

    // Calcula o n�mero de espa�os necess�rios para centralizar a arte como um todo
    int padding = (totalColumns - maxLength) / 2;
    String paddingBlock = RUString.completeUntilLengthRight(" ", "", padding);
    StringBuilder centeredArt = new StringBuilder();
    for (String line : lines) {
      centeredArt.append(paddingBlock).append(line).append("\n");
    }

    return centeredArt.toString();
  }

}
