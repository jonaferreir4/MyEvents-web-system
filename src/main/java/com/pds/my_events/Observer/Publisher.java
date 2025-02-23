package com.pds.my_events.Observer;


import com.pds.my_events.Model.User;

public interface Publisher {
    void addObserver(User observer);
    void removeObserver(User observer);
    void notifyObservers(User observerTarget, String message);

}



