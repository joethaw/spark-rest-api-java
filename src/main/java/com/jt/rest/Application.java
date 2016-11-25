package com.jt.rest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jt.rest.model.ErrorResponse;
import com.jt.rest.model.User;
import com.jt.rest.service.UserService;
import com.jt.rest.service.UserServiceImpl;
import com.jt.rest.util.JsonUtil;

import java.util.NoSuchElementException;

import static spark.Spark.*;

public class Application {
    public static void main( String[] args) {
        UserService userService = new UserServiceImpl();

        port(8081);

        get("/api/users", (req, res) -> {
            return userService.getUsers();
        }, JsonUtil.json());

        get("/api/users/:id", (req, res) -> {
            return  userService.getUser(req.params(":id"));
        }, JsonUtil.json());

        post("/api/users", (req, res) -> {
            User user = new Gson().fromJson(req.body(), User.class);

            String id = userService.createUser(user);
            String url = req.url() + "/" + id;

            res.header("Location", url);
            res.status(201);
            return "{\"id\": \" + id + \", \"url\": \" + url + \"}";
        });

        put("/api/users/:id", (req, res) -> {
            User user = new Gson().fromJson(req.body(), User.class);
            user.setId(req.params(":id"));
            userService.updateUser(user);
            res.status(204);
            return "";
        });

        delete("/api/users/:id", (req, res) -> {
            userService.deleteUser(req.params(":id"));
            res.status(204);
            return "";
        });

        after((req, res) -> {
            res.type("application/json");
        });

        exception(NoSuchElementException.class, (e, req, res) -> {
            res.status(404);
            res.body(JsonUtil.toJson(new ErrorResponse("user not found")));
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(JsonUtil.toJson(new ErrorResponse(e)));
        });

        exception(JsonSyntaxException.class, (e, req, res) -> {
            res.status(400);
            res.body(JsonUtil.toJson(new ErrorResponse(e)));
        });
    }
}
