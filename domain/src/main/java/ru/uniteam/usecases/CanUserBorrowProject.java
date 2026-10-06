package ru.uniteam.usecases;

public class CanUserBorrowProject {
    public static boolean canBorrow(int userAmountProject){
        return userAmountProject < 5;
    }
}
