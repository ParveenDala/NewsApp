package com.parveendala.newsapp.data;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NetworkState {

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR,
        INITIAL_ERROR,
        END_OF_THE_LIST,
        DATABASE
    }

    public static final NetworkState SUCCESS;
    public static final NetworkState LOADING;
    public static final NetworkState END_OF_THE_LIST;
    public static final NetworkState DATABASE;

    private final Status status;
    private final String message;

    public NetworkState(Status status, String msg) {
        this.status = status;
        this.message = msg;
    }

    static {
        SUCCESS = new NetworkState(Status.SUCCESS, "Success");
        LOADING = new NetworkState(Status.LOADING, "Loading");
        END_OF_THE_LIST = new NetworkState(Status.END_OF_THE_LIST, "You've eached the end of list.");
        DATABASE = new NetworkState(Status.DATABASE, "You're offline.");
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
