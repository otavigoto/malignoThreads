package Servidores;

public class PedidoDeCalibragem extends Comunicado
{
    private static final long serialVersionUID = 1L;
    private byte[] vetor;

    public PedidoDeCalibragem(byte[] vetor) throws Exception
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