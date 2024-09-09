package br.eng.rodrigogml.rfw.terminal.utils;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

/**
 * Description: Classe para chamada direta da biblioteca kernel32 do windows e obter melhor controle do terminal e outras funções do SO.<br>
 *
 * @author Rodrigo Leitão
 * @since (8 de set. de 2024)
 */
public interface Kernel32 extends StdCallLibrary {
  Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);

  // Estrutura para armazenar as informações do console
  @Structure.FieldOrder({ "dwSize", "dwCursorPosition", "wAttributes", "srWindow", "dwMaximumWindowSize" })
  class CONSOLE_SCREEN_BUFFER_INFO extends Structure {
    public COORD dwSize;
    public COORD dwCursorPosition;
    public short wAttributes;
    public SMALL_RECT srWindow;
    public COORD dwMaximumWindowSize;

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList("dwSize", "dwCursorPosition", "wAttributes", "srWindow", "dwMaximumWindowSize");
    }
  }

  @Structure.FieldOrder({ "X", "Y" })
  class COORD extends Structure {
    public short X;
    public short Y;

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList("X", "Y");
    }
  }

  @Structure.FieldOrder({ "Left", "Top", "Right", "Bottom" })
  class SMALL_RECT extends Structure {
    public short Left;
    public short Top;
    public short Right;
    public short Bottom;

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList("Left", "Top", "Right", "Bottom");
    }
  }

  boolean GetConsoleScreenBufferInfo(int hConsoleOutput, CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);

  int GetStdHandle(int nStdHandle);

  int STD_OUTPUT_HANDLE = -11;
}