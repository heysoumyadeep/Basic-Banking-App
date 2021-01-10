package com.soumyadeeppradhan.basicbankingapp.Model;

public class BankHolders {
    private String holderPhoneNumber, holderName, holderAccbalance;
    private String fromUserName, toUserName, dateOfTransaction, transactionStatus, transactionID;


    public BankHolders(String holderPhoneNumber, String holderName) {
        this.holderPhoneNumber = holderPhoneNumber;
        this.holderName = holderName;
    }

    public BankHolders(String holderPhoneNumber, String holderName, String holderAccbalance) {
        this.holderPhoneNumber = holderPhoneNumber;
        this.holderName = holderName;
        this.holderAccbalance = holderAccbalance;
    }

    public BankHolders(String fromUserName, String toUserName, String holderAccbalance, String dateOfTransaction, String transactionStatus, String transactionID) {
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
        this.holderAccbalance = holderAccbalance;
        this.dateOfTransaction = dateOfTransaction;
        this.transactionStatus = transactionStatus;
        this.transactionID = transactionID;
    }

    public String getHolderPhoneNumber() {
        return holderPhoneNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getHolderAccbalance() {
        return holderAccbalance;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public String getTransactionID() {
        return transactionID;
    }

}
