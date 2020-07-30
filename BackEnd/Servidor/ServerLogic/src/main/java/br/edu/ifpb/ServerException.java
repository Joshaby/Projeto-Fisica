package br.edu.ifpb;

public class ServerException extends Exception{

    public ServerException() {
        super("Erro ocorrido na gestão do servidor!");
    }
    public ServerException(String msg){
        super(msg);
    }
}
