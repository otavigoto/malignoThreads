import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Escolha o tamanho do vetor (se igual a 0, sera o tamanho maximo)");
        int tamanho = sc.nextInt();

        // todo tamanho maximo = 0 fazer maximo do sistema

        Vector<Integer> vetorInicial = new Vector<Integer>(tamanho);
        System.out.println("Quer gerar numero aleatorios (1) ou digitar manualmente (2)");
        int resp = sc.nextInt();

        if (resp == 2) {
            for (int i = 0; i < tamanho; i++) {
                System.out.println("escolha um numero:");
                vetorInicial.add(sc.nextInt());
            }
        } else if (resp == 1) {
            Random rand = new Random();
            for (int i = 0; i < tamanho; i++) {
                vetorInicial.add(rand.nextInt(100000));
            }
            System.out.println("Numeros adicionados aleatóriamente");
        }

        Vector<Integer> vetorCopia = new Vector<Integer>(vetorInicial);

        int n = Runtime.getRuntime().availableProcessors() - 1;
        if (tamanho < n)
            n = tamanho;

        long tempoInicio = System.currentTimeMillis();

        Vector<Vector<Integer>> vetorDivs = new Vector<>();
        int tamanhoDivs = tamanho / n;
        int resto = tamanho % n;
        int inicio = 0;

        for (int i = 0; i < n; i++) {
            int qntEls = tamanhoDivs + (resto > 0 ? 1 : 0);
            resto--;

            Vector<Integer> divs = new Vector<>();
            for (int j = 0; j < qntEls; j++) {
                divs.add(vetorInicial.get(inicio + j));
            }

            vetorDivs.add(divs);
            inicio += qntEls;
        }

        Vector<Ordenadora> ordenadoras = new Vector<>();

        for (int i = 0; i < vetorDivs.size(); i++) {
            ordenadoras.add(new Ordenadora(vetorDivs.get(i)));
            ordenadoras.get(i).start();
        }

        vetorDivs.clear();
        try {
            for (int i = 0; i < n; i++) {
                ordenadoras.get(i).join();
                vetorDivs.add(ordenadoras.get(i).getResultado());
            }
        } catch (Exception e) {
        }

        Vector<Integer> vetorFinal = new Vector<>();
        int qntJuntadoras = 0;

        while (vetorDivs.size() > 1) {
            qntJuntadoras = (vetorDivs.size() % 2 == 0) ? (vetorDivs.size() / 2) : (vetorDivs.size() / 2 + 1);

            Vector<ThreadJuntadora> juntadoras = new Vector<>();
            try {
                for (int i = 0; i < qntJuntadoras; i++) {
                    juntadoras.add(new ThreadJuntadora(vetorDivs.get(i * 2), vetorDivs.get(i * 2 + 1)));
                    juntadoras.get(i).start();
                }
            } catch (Exception e) {
            }

            vetorDivs.clear();

            try {
                for (int i = 0; i < qntJuntadoras; i++) {
                    juntadoras.get(i).join();
                    vetorDivs.add(juntadoras.get(i).getResultado());
                }
            } catch (Exception e) {
            }
        }
        vetorFinal = vetorDivs.get(0);

        long tempoFim = System.currentTimeMillis();

        long tempoInicioNormal = System.currentTimeMillis();

        Vector<Integer> vetorOrdenadoNormal = sort(vetorCopia);

        long tempoFimNormal = System.currentTimeMillis();

        System.out.println("Tempo do Merge Sort: " + (tempoFim - tempoInicio));
        // System.out.println("Vetor ordenado:" + vetorFinal);

        System.out.println("Tempo do sort normal:" + (tempoFimNormal - tempoInicioNormal));
        // System.out.println("Vetor ordenado:" + vetorOrdenadoNormal);

        // todo ordenação normal sem threads

        // todo escolha do usuario de ver tempo e comparar com o padrão(sem threads),
        // ver numeros ordenados num limite ou todos

    }

    public static Vector<Integer> sort(Vector<Integer> vetor) {
        for (int i = 0; i < vetor.size(); i++) {
            for (int j = i + 1; j < vetor.size(); j++) {
                if (vetor.get(i) > vetor.get(j)) {
                    int temp = vetor.get(i);
                    vetor.set(i, vetor.get(j));
                    vetor.set(j, temp);
                }
            }
        }
        return vetor;
    }

}