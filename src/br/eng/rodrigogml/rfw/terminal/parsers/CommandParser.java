package br.eng.rodrigogml.rfw.terminal.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.eng.rodrigogml.rfw.kernel.exceptions.RFWException;
import br.eng.rodrigogml.rfw.kernel.exceptions.RFWValidationException;

/**
 * Description: Parser para linhas de comando.<br>
 * Capaz de quebrar linhas de comando como:<bR>
 * <li>arg1 arg2 arg3 -param1 -param2=valor --param3 = "Esse é um \"Valor\" do param3" arg4 -param5= 'Este é outro \'tipo de valor com Scape\' de aspas e outras " no conteúdo interno do texto'</li>
 *
 * @author Rodrigo Leitão
 * @since (8 de set. de 2024)
 */
public class CommandParser {

  /**
   * Classe interna utilizada no retorno do parser
   */
  public static class ParsedCommand {
    public List<String> arguments = new ArrayList<>();
    public Map<String, String> parameters = new HashMap<>();
  }

  /**
   * Realiza o parser da linha de comando.
   *
   * @param commandLine linha de comando para realizar o parser
   * @return Instância de {@link ParsedCommand} com os tokens
   * @throws RFWValidationException Lançado em caso de falha de estrutura do comando detectado.
   */
  public static ParsedCommand parse(String commandLine) throws RFWException {
    ParsedCommand result = new ParsedCommand();
    StringBuilder currentArgument = new StringBuilder();
    StringBuilder currentParamName = new StringBuilder();
    StringBuilder currentParamValue = new StringBuilder();
    String penddingParam = null;
    boolean nextDataIsValue = false;

    boolean insideQuotes = false;
    char quoteChar = 0; // Para lidar com " ou '
    boolean escapedQuotes = false;

    // arg1 arg2 arg3 -param1 -param2=valor --param3 = "Esse é um \"Valor\" do param3" arg4 -param5= 'Este é outro \'tipo de valor com Scape\' de aspas e outras " no conteúdo interno do texto'
    for (int i = 0; i < commandLine.length(); i++) {
      char c = commandLine.charAt(i);

      if (Character.isWhitespace(c) && !insideQuotes) {
        // Se é um espaço, finalizamos os buffers
        if (currentArgument.length() > 0) {
          result.arguments.add(currentArgument.toString());
          currentArgument.setLength(0);
        } else if (currentParamName.length() > 0) {
          penddingParam = currentParamName.toString();
          currentParamName.setLength(0);
        } else if (currentParamValue.length() > 0) {
          result.parameters.put(penddingParam, currentParamValue.toString());
          currentParamValue.setLength(0);
          penddingParam = null;
          nextDataIsValue = false;
        }
      } else if (c == '-' && !insideQuotes && currentParamValue.length() == 0 && currentParamName.length() == 0 && currentArgument.length() == 0) {
        if (penddingParam != null) {
          result.parameters.put(penddingParam, "true");
          penddingParam = null;
          nextDataIsValue = false;
        }
        currentParamName.append(c);
      } else if (c == '\\' && insideQuotes) {
        if (commandLine.length() <= i + 1) throw new RFWValidationException("RFW_000046", new String[] { "" + c, "" + i, commandLine.substring(Math.max(0, i - 10), Math.min(i + 10, commandLine.length() - 1)) });
        char cn = commandLine.charAt(i + 1);
        if (cn == quoteChar) {
          escapedQuotes = true;
        }
      } else if ((c == '\'' || c == '"') && !escapedQuotes) {
        if (insideQuotes) {
          if (c == quoteChar) {
            if (commandLine.length() > i + 1) {
              char cn = commandLine.charAt(i + 1);
              if (!Character.isWhitespace(cn)) throw new RFWValidationException("RFW_000046", new String[] { "" + c, "" + i, commandLine.substring(Math.max(0, i - 10), Math.min(i + 10, commandLine.length() - 1)) });
            }
            quoteChar = 0;
            insideQuotes = false;
          } else {
            if (nextDataIsValue || currentParamValue.length() > 0) { // Estamos escrevendo o valor do parâmetro
              currentParamValue.append(c);
            } else {
              currentArgument.append(c);
            }
          }
        } else {
          if (currentArgument.length() > 0 || currentParamName.length() > 0 || currentParamValue.length() > 0) {
            throw new RFWValidationException("RFW_000046", new String[] { "" + c, "" + i, commandLine.substring(Math.max(0, i - 10), Math.min(i + 10, commandLine.length() - 1)) }); // Não é esperado iniciar aspas depois que algum bloco já começou a ser escrito pode ser um argumento ou um parâmetro
          }
          insideQuotes = true;
          quoteChar = c;
        }
      } else if (c == '=' && !insideQuotes) {
        if (nextDataIsValue) {
          throw new RFWValidationException("RFW_000046", new String[] { "" + c, "" + i, commandLine.substring(Math.max(0, i - 10), Math.min(i + 10, commandLine.length() - 1)) });
        }
        if (currentParamName.length() > 0) {
          penddingParam = currentParamName.toString();
          currentParamName.setLength(0);
        }
        if (penddingParam == null || "".equals(penddingParam.replaceAll("[^\\w]", ""))) {
          throw new RFWValidationException("RFW_000046", new String[] { "" + c, "" + i, commandLine.substring(Math.max(0, i - 10), Math.min(i + 10, commandLine.length() - 1)) });
        }
        nextDataIsValue = true;
      } else {
        // Anexamos o caracter no buffer adequado
        if (nextDataIsValue || currentParamValue.length() > 0) { // Estamos escrevendo o valor do parâmetro
          currentParamValue.append(c);
        } else if (currentParamName.length() > 0) { // Estamos escrevendo um parâmetro
          currentParamName.append(c);
        } else {
          currentArgument.append(c);
        }
        escapedQuotes = false;
      }
    }

    if (insideQuotes) throw new RFWValidationException("RFW_000045");

    // Ao ancerrar faz o flush dos valores pendentes
    if (currentArgument.length() > 0) {
      result.arguments.add(currentArgument.toString());
      currentArgument.setLength(0);
    } else if (currentParamName.length() > 0) {
      penddingParam = currentParamName.toString();
      currentParamName.setLength(0);
    } else if (currentParamValue.length() > 0) {
      result.parameters.put(penddingParam, currentParamValue.toString());
      currentParamValue.setLength(0);
      penddingParam = null;
      nextDataIsValue = false;
    }

    return result;
  }
}
