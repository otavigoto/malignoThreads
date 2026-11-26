import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Coordenador {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Uso esperado: java Coordenador N host:porta [host:porta ...]");
      System.err.println("Exemplo:      java Coordenador 1000 host1:3001 host2:3002\n");
      return;
    }

    long n;

    try {
      n = Long.parseLong(args[0]);

      if (n <= 0) {
        throw new Exception();
      }

    } catch (Exception e) {
      System.err.println("N deve ser um numero inteiro positivo!\n");
      return;
    }

    Scanner sc = new Scanner(System.in);

    byte[] vetorInicial = new byte[(int) n];
    System.out.println("Quer gerar numero aleatorios (1) ou digitar manualmente (2)");
    int resp = sc.nextInt();

    if (resp == 2) {
      for (int i = 0; i < (int) n; i++) {
        System.out.println("escolha um numero:");
        vetorInicial[i] = sc.nextByte();
      }
    } else if (resp == 1) {
      Random rand = new Random();
      for (int i = 0; i < (int) n; i++) {
        vetorInicial[i] = (byte) rand.nextInt(128);
      }
      System.out.println("Numeros adicionados aleatoriamente");
    } else {
      System.out.println("Resposta inválida! Finalizando...");
      sc.close();
      return;
    }

    // ordenação inicial

    int qtdMaquinas = args.length - 1;

    ArrayList<ConversaComTrabalhador> conversas = new ArrayList<ConversaComTrabalhador>();

    try {

      System.out.println("Dividindo o vetor em " + n + " partes...");

      Vector<byte[]> vetorDivs = new Vector<>();
      int tamanhoDivs = (int) (n / (long) qtdMaquinas);
      int resto = (int) (n % (long) qtdMaquinas);
      int inicio = 0;

      for (int i = 0; i < qtdMaquinas; i++) {
        int qntEls = tamanhoDivs + (resto > 0 ? 1 : 0);
        resto--;

        byte[] divs = new byte[qntEls];
        for (int j = 0; j < qntEls; j++) {
          divs[j] = vetorInicial[inicio + j];
        }

        vetorDivs.add(divs);
        inicio += qntEls;
      }

    } catch (Exception e) {
      System.err.println("Erro ao criar conversas com os trabalhadores!");
      sc.close();
      return;
    }

    sc.close();

  }

}