import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Main{

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Escolha o tamanho do vetor (se igual a 0, sera o tamanho maximo)");
        int tamanho = sc.nextInt();


        //todo tamanho maximo = 0 fazer maximo do sistema


        Vector<Integer> vetorInicial = new Vector<Integer>(tamanho);
        System.out.println("Quer gerar numero aleatorios (1) ou digitar manualmente (2)");
        int resp = sc.nextInt();

        if (resp == 2){
            for (int i = 0; i<tamanho;i++){
                System.out.println("escolha um numero:");
                vetorInicial.add(sc.nextInt());
            }
        } else if(resp == 1){
            Random rand = new Random();
            for (int i = 0; i<tamanho; i++){
                vetorInicial.add(rand.nextInt(100000));
            }
            System.out.println("Numeros adicionados aleatóriamente");
        }

        int n = Runtime.getRuntime().availableProcessors() - 1;
        if (tamanho < n) n = tamanho;


        long tempoInicio = System.currentTimeMillis();


        Vector<Vector<Integer>> vetorDivs = new Vector<>();
        int tamanhoDivs = tamanho / n;
        int resto = tamanho % n;
        int inicio = 0;

        for (int i = 0; i < n; i++){
            int els = tamanhoDivs + (resto > 0 ? 1 : 0);
            resto--;

            Vector<Integer> divs = new Vector<>();
            for (int j = 0; j<els;j++){
                divs.add(vetorInicial.get(inicio + j));
            }

            vetorDivs.add(divs);
            inicio += els;
        }


        //todo start das ordenadoras e finalizar adicionando no vetor final


        //todo start das juntadoras pós vetorDividido e ordenado


        //todo ordenação normal sem threads


        //todo escolha do usuario de ver tempo e comparar com o padrão(sem threads), ver numeros ordenados num limite ou todos



    }
}