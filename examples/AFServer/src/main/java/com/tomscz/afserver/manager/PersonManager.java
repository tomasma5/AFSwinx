package com.tomscz.afserver.manager;

public interface PersonManager<T> extends Manager<T> {

    public T findUser(String login);

    public T findUser(String login, String password);

}
