package edu.mirea.rksp.pr3.task3;

import java.util.Objects;

public class UserFriend {
    private int userId;
    private int friendId;

    public UserFriend(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public String toString() {
        return "UserFriend{" +
                "userId=" + userId +
                ", friendId=" + friendId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFriend that = (UserFriend) o;
        return userId == that.userId && friendId == that.friendId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendId);
    }
}
