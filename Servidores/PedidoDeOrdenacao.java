package Servidores;

public class PedidoDeOrdenacao extends Comunicado
{
    private static final long serialVersionUID = 1L;
    private byte[] vetor;

    public PedidoDeOrdenacao(byte[] vetor) throws Exception
    {
        if (vetor == null)
            throw new Exception("Vetor ausente");

        this.vetor = vetor;
    }

    public byte[] getVetor()
    {
        return this.vetor;
    }
}