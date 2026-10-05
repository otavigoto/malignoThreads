public class Ordenadora extends Thread {

    private byte[] vetor;

    public Ordenadora(byte[] v) throws NullPointerException {
        if (v == null) {
            throw new NullPointerException();
        }

        this.vetor = v;
    }

    @Override
    public void run() {
        this.vetor = mergeSort(this.vetor);
    }

    private byte[] mergeSort(byte[] v) {
        if (v.length < 2) {
            return v;
        }

        int meio = v.length / 2;

        byte[] esquerda = new byte[meio];
        byte[] direita = new byte[v.length - meio];

        for (int i = 0; i < meio; i++)
            esquerda[i] = v[i];
        for (int i = meio; i < v.length; i++)
            direita[i - meio] = v[i];

        esquerda = mergeSort(esquerda);
        direita = mergeSort(direita);

        return ThreadJuntadora.Merge(esquerda, direita);
    }

    public byte[] getResultado() {
        return this.vetor;
    }
}