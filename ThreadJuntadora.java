public class ThreadJuntadora extends Thread {
    private byte[] pedacoA;
    private byte[] pedacoB;
    private byte[] resultado;

    public ThreadJuntadora(byte[] pedacoA, byte[] pedacoB) throws Exception {
        if (pedacoA == null) {
            throw new Exception("O pedaço A não pode ser nulo!");
        }
        if (pedacoB == null) {
            throw new Exception("O pedaço B não pode ser nulo!");
        }

        this.pedacoA = pedacoA;
        this.pedacoB = pedacoB;
    }

    @Override
    public void run() {
        this.resultado = Merge(this.pedacoA, this.pedacoB);
    }

    public byte[] getResultado() {
        return this.resultado;
    }

    public static byte[] Merge(byte[] a, byte[] b) {
        byte[] novoVetor = new byte[a.length + b.length];
        int idxA = 0, idxB = 0, idxNovo = 0;

        while (idxA < a.length && idxB < b.length) {
            if (a[idxA] < b[idxB]) {
                novoVetor[idxNovo] = a[idxA];
                idxA++;
            } else {
                novoVetor[idxNovo] = b[idxB];
                idxB++;
            }
            idxNovo++;
        }

        while (idxA < a.length) {
            novoVetor[idxNovo] = a[idxA];
            idxA++;
            idxNovo++;
        }

        while (idxB < b.length) {
            novoVetor[idxNovo] = b[idxB];
            idxB++;
            idxNovo++;
        }
        return novoVetor;
    }
}