package com.pds.my_events.Observer;


import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.User;

public interface Publisher {
    void addObserver(User observer);
    void notifyObservers(String message, Event event);

}



