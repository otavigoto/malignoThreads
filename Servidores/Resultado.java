package Servidores;

public class Resultado extends Comunicado
{
    private static final long serialVersionUID = 1L;
    private byte[] vetorResultante;

    public Resultado(byte[] vetorResultante) throws Exception
    {
        if (vetorResultante == null)
            throw new Exception("Vetor resultante ausente");

        this.vetorResultante = vetorResultante;
    }

    public byte[] getVetorResultante()
    {
        return this.vetorResultante;
    }
}