package model;

import java.util.ArrayList;

public class ContactList extends Message{
    private ArrayList<String> contactList;

    public ContactList(){
        contactList = new ArrayList<>();
    }

    public void addContact(String username){
        contactList.add(username);
    }

    public void removeContact(String username){

    }

    public void readFromFile(String filename){

    }

    public void saveToFile(String filename){

    }

    public void setContactList(ArrayList<String> contactList) {
        this.contactList = contactList;
    }

    public ArrayList<String> getContacts() {
        return contactList;
    }
}
