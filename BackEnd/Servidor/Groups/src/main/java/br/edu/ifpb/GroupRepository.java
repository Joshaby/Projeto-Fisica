package br.edu.ifpb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.rmi.RemoteException;
import java.util.*;

/*
    Classe responsavel por receber, organizar e distribuir os usuarios em grupo, e regir-los corretamente.

    groups: HashSet que irá conter todos os grupos;
    maxGroup: Variavel que determina o número maximo de grupos;

    public boolean generateGroups(User[] Users): Função responsavel por gerar os grupos e alocalos na lista de grupos;
 */

public class GroupRepository implements User_IF {
    private Set<Group> groups;
    private int year;
    private int maxGroup;

//CONSTRUCTORS

    /*
        Construtor Padrão da classe que inicializa o HashSet para posterior armazenamento de grupos e
        utiliza o valor padrão de número de grupos no inicio do jogo. Valor padrão = 5
    */
    public GroupRepository() {
        this.groups = new HashSet<>();
        this.setMaxGroup(5);
        this.setYear(-1);
    }
    /*
        Construtor alternativo que inicializa o HashSet para posterior armazenamento de grupos e
        utiliza um valor recebido para o número total de grupos possiveis.
    */
    public GroupRepository(int maxGroup) {
        this();
        this.setMaxGroup(maxGroup);
    }

//REALOCATE GROUP METHODS

    /*
    Função Private responsável por calcular baseado nos critérios aderidos pelo projeto,
    o grupo de menor pontuação ou tempo e realocar os membros do mesmo nos grupos devidamente,
    além de retornar uma lista de grupos caso haja um empate que atenda todos os critérios.
    */
    public List<String> realocateGroup(int round) throws RemoteException {

        ArrayList<Group> LAO = lessAmountOf(round);
        if (LAO.size() > 1) {
            ArrayList<String> aux = new ArrayList<>();
            LAO.iterator().forEachRemaining(group -> {
                aux.add(group.getName());
            });
            return aux;
        }
        Group removido = LAO.get(0);
        this.removeGroup(removido);
        LAO = lessAmountOf(round);
        Group fraco = LAO.get(0);
        if(groups.size() == 1) return null;
        realocateMembers(fraco, removido.getMembers());
        return null;
    }

    //Função privada responsável por remover um grupo recebido como parametro.
    private void removeGroup(Group group) {
        if (group.validateGroup()) return;
        this.groups.remove(group);
    }

    //Função privada responsável pela transação de membro entre grupos, posteriormente aplicada na função de remoção de grupos.
    private void realocateMembers(Group fraco, List<User> removido) throws RemoteException {

        int quantidade = removido.size();
        int usersFracos = (int) Math.ceil((quantidade - (quantidade * 60.0) / 100.0));

        List<User> aux = new ArrayList<>(List.copyOf(removido));
        for (int i = 0; i < usersFracos; i++) {
            fraco.addMember(aux.remove(i));
        }
        ArrayList<Group> groups = new ArrayList<>(List.copyOf(getGroups()));
        groups.remove(fraco);

        for (int i = 0; i < aux.size(); i++) {
            groups.get(i % groups.size()).addMember(aux.get(i));
        }
    }

// LESSAMOUNTOFS

    //Função privada auxiliar responsável por retornar o array que vai coletar o grupo com menor total de pontos.
    private ArrayList<Group> lessAmountOf(int round) throws RemoteException {
        ArrayList<Group> LAOPS = lessAmountOfPoints();
        if (LAOPS.size() == 1) return LAOPS;
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
        ArrayList<Group> LAOT = new ArrayList<>();
        for (Group group : laops) {
            if (group.getAnswers().size() == 0) return laops;
            int groupTime = group.getAnswersByRound(round).getTime();
            if (groupTime >= time) {
                if (groupTime != time) LAOT.clear();
                LAOT.add(group);
                time = groupTime;
            }
        }
        return LAOT;
    }

//GROUP REGISTRATOR

    /*
        Função responsável por registrar os grupos recebidos do FrontEnd, na qual utiliza uma exceção
        'GroupException' que será gerada caso uma condição não seja confirmada pela lista recebida.
        Essa função foi construida como o intuito de gravar um nome para os grupos, e utilizar usuários
        predeterminados na criação do grupos no FrontEnd.
     */
    public void registerGroups(Map<String, List<String>> groups, int year) throws RemoteException {
        groups.keySet().iterator().forEachRemaining(group -> {
            if (groups.get(group).size() < 1) {
                try {
                    throw new GroupException(
                            "Número de usuários no grupo" + group +" inválido: " + groups.get(group).size());
                } catch (GroupException e) {
                    e.printStackTrace();
                }
            }
        });

        for (String group : groups.keySet()) {
            Group newGroup = new Group(group, year, groups.get(group).size());
            for (String user : groups.get(group)) {
                User novo = new User(user, year);
                newGroup.addMember(novo);
            }
            this.addGroup(newGroup);
        }
        if(getYear() == -1) setYear(year);
    }

    @Override
    public boolean registerSingleGroup(String groupName, List<String> members, int year) throws RemoteException {
        if(groupName.isEmpty() || members.size() == 0 || year <= 0 || year >= 4 || (getYear() > 0 && year != getYear())) return false;

        Group newGroup = new Group(groupName, year);

        for (String user : members) {
            if(!user.equals("")){
                newGroup.addMember(new User(user, year));
            }
        }
        this.addGroup(newGroup);
        if(getYear() < 0) setYear(year);
        return true;
    }

    //Função privada auxiliar para adição de um grupo ao set de grupos principal.
    private void addGroup(Group group) {
        if (group.validateGroup()) {
            return;
        }
        this.groups.add(group);
    }

// GROUP REMOVER
    public void removeGroupByName(String name) throws RemoteException {
        this.groups.remove(this.getGroupByName(name));
    }

// RESET ALL GROUPS POINTS
    public void resetAllPoints(){
        this.getGroups().iterator().forEachRemaining(group -> {
            group.setAnswers(new HashMap<>());
        });
    }


// Reset a single round group points
    public void resetPoints(int round){
        this.groups.iterator().forEachRemaining(group -> {
            group.getAnswersByRound(round).setAnswers(new HashSet<>());
        });
    }

// MONGO CONNECTION METHOD
    public void initializeGroupsSet() {
        MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("Grupos");
        MongoCollection<Document> collection = database.getCollection(String.format("%d ano", year));
        FindIterable<Document> documentAggregateIterable = collection.find();
        Iterator iterator = documentAggregateIterable.iterator();
        iterator.forEachRemaining(
                doc -> {
                    Document document = (Document) doc;
                    String groupName = (String) document.get("nome");
                    Set<String> members = new HashSet<>((List<String>) document.get("integrantes"));
                    int points = (int) document.get("pontos");
                    HashSet<User> users = new HashSet<>();
                    members.forEach(
                            member -> {
                                User user = new User(member, year);
                                user.addPoints(points);
                                users.add(user);
                            });
                    groups.add(new Group(groupName, year, users, points));
                    System.out.println(groups);
                });
}

//GETTERS

    @Override
    public Map<String, List<String>> getGroupsMAP() throws RemoteException {
        HashSet<Group> aux = (HashSet<Group>) getGroups();
        HashMap<String, List<String>> retorno = new HashMap<>();
        for (Group group : aux) {
            Map<String, List<String>> novo = this.stringifyGroup(group);
            for (String s : novo.keySet()) {
                retorno.put(s, novo.get(s));
            }
        }
        return retorno;
    }

    //Função publica que disponibiliza uma busca pelo nome do grupo e retorna o mesmo, caso exista em modo de mapa.
    public Map<String, List<String>> getGroupByNameMap(String name) throws RemoteException {
        for (Group group : List.copyOf(this.getGroups())) {
            if (group.getName().equals(name)) {
                return this.stringifyGroup(group);
            }
        }
        return null;
    }

    //Função publica que disponibiliza uma busca pelo nome do grupo e retorna o mesmo, caso exista.
    public Group getGroupByName(String name) throws RemoteException {
        for (Group group : List.copyOf(this.getGroups())) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    //Função publica que retorna o set de grupos cadastrados.
    public Set<Group> getGroups() {
        return this.groups;
    }


    //função privada auxiliar que retorna o valor maximo de grupos.
    private int getMaxGroup() {
        return maxGroup;
    }

    public int getYear() {
        return year;
    }


//SETTERS

    public void setYear(int year) {
        this.year = year;
    }

    //função privada auxiliar que disponibiliza a troca de numero maximo de grupos.
    private boolean setMaxGroup(int maxGroup) {
        if (maxGroup <= 0) {
            return false;
        }
        this.maxGroup = maxGroup;
        return true;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    //STRINGIFIERS

    //Função Privada que stringuifica um grupo, e retorna o mesmo em formato de mapa.
    public Map<String, List<String>> stringifyGroup(Group group) {
        HashMap<String, List<String>> aux = new HashMap<>();
        ArrayList<String> users = new ArrayList<>();
        for (User user : group.getMembers()) {
            users.add(user.getName());
        }
        aux.put(group.getName(), users);
        return aux;
    }

    @Override
    public String toString() {
        return "GroupRepository{" +
                "groups=" + groups +
                ", year=" + year +
                ", maxGroup=" + maxGroup +
                '}';
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
}
