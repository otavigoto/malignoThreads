import java.util.Vector;

public class Ordenadora extends Thread{

    private Vector<Byte> vetor;

    public Ordenadora(Vector<Byte> v) throws NullPointerException{
        if (v == null){
            throw new NullPointerException();
        }

        this.vetor = v;
    }

    @Override
    public void run(){
        this.vetor = mergeSort(this.vetor);
    }

    private Vector<Byte> mergeSort(Vector<Byte> v){
        if(v.size() < 2)
        {
            return v;
        }
        Vector<Byte> esquerda = new Vector<>();
        Vector<Byte> direita = new Vector<>();

        int meio = v.size() / 2;

        for(int i = 0; i < meio; i++) esquerda.add(v.get(i));
        for(int i = meio; i < v.size(); i++) direita.add(v.get(i));

        esquerda = mergeSort(esquerda);
        direita = mergeSort(direita);

        return ThreadJuntadora.Merge(esquerda, direita);
    }

    public Vector<Byte> getResultado(){
        return this.vetor;
    }
}
