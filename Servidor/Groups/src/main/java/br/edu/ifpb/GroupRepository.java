package br.edu.ifpb;

import java.rmi.RemoteException;
import java.util.*;

/*
    Classe responsavel por receber, organizar e distribuir os usuarios em grupo, e regir-los corretamente.

    groups: HashSet que irá conter todos os grupos;
    maxGroup: Variavel que determina o número maximo de grupos;

    public boolean generateGroups(User[] Users): Função responsavel por gerar os grupos e alocalos na lista de grupos;
 */

public class GroupRepository implements User_IF{
    private Set<Group> groups;
    private int maxGroup;

    /*
        Construtor Padrão da classe que inicializa o HashSet para posterior armazenamento de grupos e
        utiliza o valor padrão de número de grupos no inicio do jogo. Valor padrão = 5
    */
    public GroupRepository() {
        groups = new HashSet<>();
        this.setMaxGroup(5);
    }

    /*
        Construtor alternativo que inicializa o HashSet para posterior armazenamento de grupos e
        utiliza um valor recebido para o número total de grupos possiveis.
    */
    public GroupRepository(int maxGroup) {
        groups = new HashSet<>();
        this.setMaxGroup(maxGroup);
    }


    //Função privada responsável por remover um grupo recebido como parametro.
    private void removeGroup(Group group) {
        if (group.validateGroup()) return;
        this.groups.remove(group);
    }

    //Função privada responsável pela transação de membro entre grupos, que posteriormente será aplicada na função de remoção de grupos.
    private void realocateMember(User user, String groupName) throws RemoteException {
        Group aux = null;
        for (Group group : getGroups()) {
            if (group.getName().equals(groupName)) {
                aux = group;
                break;
            }
        }
        if (aux != null) aux.addMember(user);
    }

    /*
    Função Publica responsável por calcular baseado nos critérios aderidos pelo projeto,
    o grupo de menor pontuação ou tempo e realocar os membros do mesmo nos grupos devidamente,
    além de retornar uma lista de grupos caso haja um empate que atenda todos os critérios.
    */
    public List<String> realocateGroup(int round) throws RemoteException {

        ArrayList<Group> LAO = lessAmountOf(round);
        if(LAO.size() > 1){
            ArrayList<String> aux = new ArrayList<>();
            LAO.iterator().forEachRemaining(group -> { aux.add(group.getName()); });
            return aux;
        }
        this.removeGroup(LAO.get(0));

        return null;
    }

    //Função privada auxiliar responsável por retornar o array que vai coletar o grupo com menor total de pontos.
    private ArrayList<Group> lessAmountOf(int round) throws RemoteException {
        ArrayList<Group> LAOPS = lessAmountOfPoints();
        if(LAOPS.size() == 1) return LAOPS;
        return lessAmountOfTime(LAOPS, round);
    }

    //Função privada auxiliar responsável por retornar um array com os grupos ou o grupo com menor pontuação total no momento.
    private ArrayList<Group> lessAmountOfPoints() throws RemoteException {
        ArrayList<Group> LAOPS = new ArrayList<>();
        int menor = (int) Double.POSITIVE_INFINITY;

        for (Group group : this.getGroups()) {
            if (group.getPoints() <= menor) {
                if (group.getPoints() != menor) LAOPS.clear();
                LAOPS.add(group);
                menor = group.getPoints();
            }
        }
        return LAOPS;
    }

    /*
        Função privada auxiliar responsável por retornar um array com os grupos ou o grupo com maior tempo
        decorrido no round especificado. Essa função tem o critério menor que a pontuação total dos grupos,
        por isso, ela faz uma busca pelo tempo apenas dos grupos com menor pontuação, caso haja mais de um.
     */
    private ArrayList<Group> lessAmountOfTime(ArrayList<Group> laops, int round) {
        int time = 0;
        ArrayList<Group> aux = new ArrayList<>();
        for (Group laop : laops) {
            int groupTime = laop.getAnswers().get(round).getTime();
            if(groupTime >= time){
                if(groupTime != time) aux.clear();
                aux.add(laop);
                time = groupTime;
            }
        }
        return aux;
    }

    /*
        Função responsável por registrar os grupos recebidos do FrontEnd, na qual utiliza uma exceção
        'GroupException' que será gerada caso uma condição não seja confirmada pela lista recebida.
        Essa função foi construida como o intuito de gravar um nome para os grupos, e utilizar usuários
        predeterminados na criação do grupos no FrontEnd.
     */
    public void registerGroups(Map<String, List<String>> groups, int year) throws RemoteException {
        groups.keySet().iterator().forEachRemaining(group -> {
            if(groups.get(group).size() == 0) {
                try { throw new GroupException("Número de usuários inválido: 0"); }
                catch (GroupException e) { e.printStackTrace(); }
            }
        });

        for (String group : groups.keySet()) {
            Group newGroup = new Group(group, year);
            for (String user: groups.get(group)) {
                User novo = new User(user, year);
                newGroup.addMember(novo);
            }
            this.addGroup(newGroup);
        }
    }


//    /*
//        Função publica responsável por criar grupos dinamicamente de acordo com a inserção de usuários
//        disponibilizando assim a possibilidade de ter até um grupo com uma pessoa, até 5 grupos com n pessoas.
//        Essa função atribui nomes genericos aos grupos, sendo assim impossivel aderir nomes personalizados.
//        todo Precisa de uma atualização para os novos paramêtros do corpo do projeto.
//     */

//    public boolean generateGroupsWithOutName(List<User> users) throws RemoteException {
//
//        if (users.size() == 0) { return false; }
//
//        int comparador = users.get(0).getYear();
//
//        for (User user : users) { if (!user.validateUser() || user.getYear() != comparador) return false; }
//
//        ArrayList<Group> aux = new ArrayList<>();
//        Collections.shuffle(users);
//        for (int i = 1; i <= getMaxGroup(); i++) { aux.add(new Group(i)); }
//
//        for (int i = 0; i < users.size(); i++) { aux.get(i % 5).addMember(users.get(i)); }
//
//        aux.forEach(this::addGroup);
//
//        this.groups.removeIf(group -> group.getMembers().size() == 0);
//
//        return true;
//    }

    //Função privada auxiliar para adição de um grupo ao set de grupos principal.
    private void addGroup(Group group) {
        if (group.validateGroup()) { return; }
        this.groups.add(group);
    }

    //Função publica que disponibiliza uma busca pelo nome do grupo e retorna o mesmo, caso exista.
    public Group getGroupByName(String name)  throws RemoteException {
        for (Group group : List.copyOf(this.getGroups())) { if (group.getName().equals(name)) return group; }
        return null;
    }

    //Função publica que retorna o set de grupos cadastrados.
    public Set<Group> getGroups()  throws RemoteException { return this.groups; }

    //função privada auxiliar que retorna o valor maximo de grupos.
    private int getMaxGroup() { return maxGroup; }

    //função privada auxiliar que disponibiliza a troca de numero maximo de grupos.
    private boolean setMaxGroup(int maxGroup) {
        if (maxGroup <= 0) { return false; }
        this.maxGroup = maxGroup;
        return true;
    }
}
