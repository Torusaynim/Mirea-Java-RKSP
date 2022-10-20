package edu.mirea.rksp.pr3.task3;

import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    private static final int MAX_COUNT_USER_FRIENDS = 100;
    private static final int MIN_USER_ID = 0;
    private static final int MAX_USER_ID = 9;

    private static ArrayList<UserFriend> userFriends = new ArrayList<>();

    public static void main(String[] args) {
        initUserFriends();

        getObservableUserId()
                .map(userId -> {
                    System.out.println("UserId: " + userId);
                    return getFriends(userId);
                })
                .subscribe(userFriendsObservable -> {
                    userFriendsObservable.forEach(System.out::println);
                });
    }

    private static Observable<Integer> getObservableUserId() {
        Set<Integer> userIdsSet = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            userIdsSet.add(getRandomUserId());
        }

        return Observable.fromArray(userIdsSet.toArray(Integer[]::new));
    }

    private static Observable<UserFriend> getFriends(int userId) {
        UserFriend[] arrFilteredUserFriend = userFriends
                .stream()
                .filter(userFriend -> userFriend.getUserId() == userId)
                .toArray(UserFriend[]::new);

        return Observable.fromArray(arrFilteredUserFriend);
    }

    private static void initUserFriends() {
        Set<UserFriend> userFriendSet = new HashSet<>();

        for (int i = 0; i < MAX_COUNT_USER_FRIENDS; i++) {
            int newUserId = getRandomUserId();
            int newFriendId = getRandomUserId();

            if (newFriendId != newUserId) {
                userFriendSet.add(new UserFriend(newUserId, newFriendId));
            }
        }

        userFriends = new ArrayList<>(userFriendSet);
    }

    private static int getRandomUserId() {
        return getRandomNumber(MIN_USER_ID, MAX_USER_ID);
    }

    private static int getRandomNumber(int min, int max) {
        return (int) Math.round(Math.random() * (max - min) + min);
    }
}
