import java.util.Vector;

public class ThreadJuntadora extends Thread
{
    private Vector<Integer> pedacoA;
    private Vector<Integer> pedacoB;
    private Vector<Integer> resultado;

    public ThreadJuntadora(Vector<Integer> pedacoA, Vector<Integer> pedacoB) throws Exception
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

    public Vector<Integer> getResultado()
    {
        return this.resultado;
    }

    public static Vector<Integer> Merge (Vector<Integer> a, Vector<Integer> b)
    {
        Vector<Integer> novoVetor = new Vector<Integer>();
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
        return novoVetor;
    }
}
