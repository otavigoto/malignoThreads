package Servidores;

public class TempoLevado extends Comunicado
{
    private static final long serialVersionUID = 1L;
    private long tempo;

    public TempoLevado(long tempo) throws Exception
    {
        if (tempo < 0)
            throw new Exception("Tempo invalido");

        this.tempo = tempo;
    }

    public long getTempo()
    {
        return this.tempo;
    }
}