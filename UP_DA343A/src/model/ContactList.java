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

    public void setContactList(ArrayList<String> contactList) {
        this.contactList = contactList;
    }

    public ArrayList<String> getContacts() {
        return contactList;
    }

    public String getAddedContact(){
        return contactList.get(1);
    }
}
