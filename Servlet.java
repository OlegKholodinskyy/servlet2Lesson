/**
 * Created by oleg on 02.07.2019.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.ItemController;
import exception.ItemExistException;
import exception.RepoAccessEcxeption;
import model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(urlPatterns = "/items")
public class Servlet extends HttpServlet {
    ItemController itemController = new ItemController();
    Item item = new Item();

    //          http://localhost:8080/items?id=3011
    //          http://localhost:8080/items?id=all

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id").equals("all")) {

            try {
                resp.getWriter().print(itemController.getAllItems());
            } catch (RepoAccessEcxeption e) {
                resp.setStatus(500);
                resp.getWriter().print("Error" + e.getMessage());
            }
        } else {
            try {
                resp.getWriter().print(itemController.getItemById(Long.parseLong(req.getParameter("id"))));
            } catch (ItemExistException e) {
                resp.getWriter().print("Item with id " + req.getParameter("id") + " not found");
            } catch (RepoAccessEcxeption e) {
                resp.setStatus(500);
                resp.getWriter().print("Error" + e.getMessage());
            }
        }
    }
    /*
    "id":"3065",
    "name":"3065",
    "dateCreated":"2019-07-02",
    "lastUpdatedDate":"2019-07-02",
    "description":"3065"
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        item = mappingItem (req);
        try {
            itemController.updateItem(item);
            resp.getWriter().println("Item  updated.");
        } catch (ItemExistException e) {
            resp.getWriter().print("Item with id " + item.getId() + " is not found  in Data Base.");
        } catch (RepoAccessEcxeption e) {
            resp.setStatus(500);
            resp.getWriter().print("Error" + e.getMessage());
        }
    }

    /*
    "name":"Olko5333updated",
    "dateCreated":"2019-07-02",
    "lastUpdatedDate":"2019-07-02",
    "description":"TestOlko2updated"
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        item = mappingItem (req);
        try {
            itemController.saveItem(item);
            resp.getWriter().println("Item  saved.");
        } catch (ItemExistException e) {
            resp.getWriter().print("Item with name " + item.getName() + " is already present in Data Base.");
        } catch (RepoAccessEcxeption e) {
            resp.setStatus(500);
            resp.getWriter().print("Error" + e.getMessage());
        }
    }

    /*
http://localhost:8080/items?itemId=3074
    */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        try {
            itemController.deleteItem(Long.parseLong(req.getParameter("itemId")));
        } catch (ItemExistException e) {
            resp.getWriter().print("Item with id " + req.getParameter("id") + " not found");
        } catch (RepoAccessEcxeption e) {
            resp.setStatus(500);
            resp.getWriter().print("Error" + e.getMessage());
        }
    }

    private Item mappingItem (HttpServletRequest req) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        String line;

        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return item = objectMapper.readValue(stringBuilder.toString(), Item.class);
    }
}
