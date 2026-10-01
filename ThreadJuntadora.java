public class ThreadJuntadora extends Thread
{
    private Vector<Byte> pedacoA;
    private Vector<Byte> pedacoB;
    private Vector<Byte> resultado;

    public ThreadJuntadora(Vector<Byte> pedacoA, Vector<Byte> pedacoB) throws Exception
    {
        if(pedacoA == null){throw new Exception("O pedaço A não pode ser nulo!");}
        if(pedacoB == null){throw new Exception("O pedaço B não pode ser nulo!");}

        this.pedacoA = pedacoA;
        this.pedacoB = pedacoB;
    }

    @Override
    public void run()
    {
        this.resultado = Merge(this.pedacoA, this.pedacoB);
    }

    public Vector<Byte> getResultado()
    {
        return this.resultado;
    }

    public Vector<Byte> Merge (Vector<Byte> a, Vector<Byte> b)
    {
        Vector<Byte> novoVetor = new Vector<Byte>();
        int idxA = 0, idxB = 0;

        while (idxA < a.size() && idxB < b.size())
        {
            if(a.get(idxA) < b.get(idxB))
            {
                novoVetor.add(a.get(idxA));
                idxA++;
            }

            else
            {
                novoVetor.add(b.get(idxB));
                idxB++;
            }
        }

        while(idxA < a.size())
        {
            novoVetor.add(a.get(idxA));
            idxA++;
        }

        while(idxB < b.size())
        {
            novoVetor.add(b.get(idxB));
            idxB++;
        }
    }
}
