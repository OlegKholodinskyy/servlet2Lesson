package repository;

import exception.RepoAccessEcxeption;
import model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleg on 12.06.2019.
 */
public interface DAOInterface {
    public ArrayList<Item> getAllItems() throws RepoAccessEcxeption;

    public Item getItemById(long id) throws RepoAccessEcxeption;

    public void deleteItem(long id) throws RepoAccessEcxeption;

    public Item saveItem(Item item) throws RepoAccessEcxeption;

    public Item updateItem(Item item) throws RepoAccessEcxeption;

  //  public boolean isExist(String name);
}
