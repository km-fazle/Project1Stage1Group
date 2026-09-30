import java.util.*;

public class ClientList {
  private List<Client> clients;
  private static ClientList clientList;

  private ClientList() {
    clients = new LinkedList<Client>();
  }

  public static ClientList instance() {
    if (clientList == null) {
      return (clientList = new ClientList());
    } else {
      return clientList;
    }
  }

  public boolean insertClient(Client client) {
    clients.add(client);
    return true;
  }

  public Client searchClient(String clientID) {
    Iterator<Client> iterator = clients.iterator();
    while (iterator.hasNext()) {
      Client client = iterator.next();
      if (client.equals(clientID)) {
        return client;
      }
    }
    return null;
  }

  public Iterator<Client> getClients() {
    return clients.iterator();
  }

  public String toString() {
    return clients.toString();
  }
}
