import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Escolha o tamanho do vetor (se igual a 0, sera o tamanho maximo)");
        int tamanho = sc.nextInt();

        if (tamanho == 0) {
            System.out.println("Calculando o limite máximo de memória...");
            tamanho = descobrirTamanhoMaximo();
        }

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
            System.out.println("Erro ao juntar: " + e.getMessage());
        }

        Vector<Integer> vetorFinal = new Vector<>();
        Vector<ThreadJuntadora> juntadoras = new Vector<>();
        Vector<Vector<Integer>> vetorResto = new Vector<>();
        int qntJuntadoras = 0;

        while (vetorDivs.size() > 1) {

            qntJuntadoras = vetorDivs.size() / 2;

            juntadoras.clear();

            try {
                for (int i = 0; i < qntJuntadoras; i++) {
                    juntadoras.add(new ThreadJuntadora(vetorDivs.get(i * 2), vetorDivs.get(i * 2 + 1)));
                    juntadoras.get(i).start();
                }
            } catch (Exception e) {
                System.out.println("Erro ao criar thread: " + e.getMessage() + vetorDivs.size());
            }

            if (vetorDivs.size() % 2 != 0) {
                vetorResto.add(vetorDivs.get(vetorDivs.size() - 1));
            }

            vetorDivs.clear();
            vetorDivs.addAll(vetorResto);
            vetorResto.clear();

            try {
                for (int i = 0; i < qntJuntadoras; i++) {
                    juntadoras.get(i).join();
                    vetorDivs.add(juntadoras.get(i).getResultado());
                }
            } catch (Exception e) {
                System.out.println("Erro ao juntar: " + e.getMessage());
            }
        }
        vetorFinal = vetorDivs.get(0);

        long tempoFim = System.currentTimeMillis();

        long tempoInicioNormal = System.currentTimeMillis();

        Vector<Integer> vetorOrdenadoNormal = sort(vetorCopia);

        long tempoFimNormal = System.currentTimeMillis();

        // todo escolha do usuario de ver tempo e comparar com o padrão(sem threads),
        // ver numeros ordenados num limite ou todos

        int respFinal = -1;
        while (respFinal != 0) {
            System.out.println("1 - Ver tempo e comparar com o sort bubble");
            System.out.println("2 - Ver numeros ordenados");
            System.out.println("0 - Sair");
            respFinal = sc.nextInt();

            switch (respFinal) {
                case 1:
                    System.out.println("Tempo com threads: " + (tempoFim - tempoInicio) + "ms");
                    System.out.println("Tempo sem threads: " + (tempoFimNormal - tempoInicioNormal) + "ms");
                    break;
                case 2:
                    int idx = -2;
                    System.out.println(
                            "Vetor ordenado (escolha de 0 a " + (vetorFinal.size() - 1) + " ou -1 para ver todos):");
                    idx = sc.nextInt();

                    if (idx < -1 || idx >= vetorFinal.size()) {
                        System.out.println("Índice inválido!");
                        break;
                    }

                    if (idx == -1) {
                        System.out.println(vetorFinal);
                    } else {
                        for (int i = 0; i <= idx; i++) {
                            System.out.println(vetorFinal.get(i));
                        }
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

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

    public static int descobrirTamanhoMaximo()
    {
        int limiteInferior = 0;
        int limiteSuperior = Integer.MAX_VALUE;
        int maiorTamanhoSucesso = 0;

        while(limiteInferior <= limiteSuperior)
        {
            int tamanhoTentativa = limiteInferior + ((limiteSuperior - limiteInferior) / 2);
            System.out.println("Tentando tamanho: " + tamanhoTentativa);

            try
            {
                byte[] vetor = new byte[tamanhoTentativa];
                maiorTamanhoSucesso = tamanhoTentativa;
                limiteInferior = tamanhoTentativa + 1;
                vetor = null;
                System.gc();
            }
            catch (OutOfMemoryError e)
            {
                limiteSuperior = tamanhoTentativa - 1;
            }


        }
        System.out.printf("Memória estimada: %.2f MB%n", maiorTamanhoSucesso * 1.0 / (1024*1024));
        return maiorTamanhoSucesso;
    }

}