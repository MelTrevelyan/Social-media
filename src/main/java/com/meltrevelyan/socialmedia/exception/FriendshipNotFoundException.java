package com.meltrevelyan.socialmedia.exception;

public class FriendshipNotFoundException extends RuntimeException {

    public FriendshipNotFoundException(String message) {
        super(message);
    }
}
