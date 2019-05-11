//Rahul Gite

package com.example.android.s4s;

public class Book {

    private String BRANCH;

    private String Book_Name;

    private String Author_Name;

    private String Price;

    private String Book_Id;

    private String User_Id;

    public Book(String branch, String Bookname, String Authorname, String Price, String book_Id, String user_Id) {
        BRANCH = branch;
        Book_Name = Bookname;
        Author_Name = Authorname;
        this.Price = Price;
        Book_Id = book_Id;
        User_Id = user_Id;
    }


    public String getmBookname() {
        return Book_Name;
    }

    public String getmAuthorname() {
        return Author_Name;
    }

    public String getmPrice() {
        return Price;
    }

    public String getBook_Id() {
        return Book_Id;
    }

    public void setBook_Id(String book_Id) {
        Book_Id = book_Id;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getBRANCH() {
        return BRANCH;
    }

    public void setBRANCH(String BRANCH) {
        this.BRANCH = BRANCH;
    }
}


//Rahul Gite