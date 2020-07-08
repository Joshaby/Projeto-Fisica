package br.edu.ifpb;

public class GroupException extends Exception {
    public GroupException() { super("Erro no repositório de grupos!"); }
    public GroupException(String message) { super(message); }
}
