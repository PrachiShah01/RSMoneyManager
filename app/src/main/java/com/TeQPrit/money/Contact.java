package com.TeQPrit.money;

public class Contact {
    int _id;
    String _name;
    String _total;
    String _contact;
    String _discription;

    public String get_discription() {
        return _discription;
    }

    public void set_discription(String _discription) {
        this._discription = _discription;
    }

    public Contact(){   }

    public Contact(String _name, String _total, String _contact, String _discription) {
        this._name = _name;
        this._total = _total;
        this._contact = _contact;
        this._discription = _discription;
    }

    public Contact(int _id, String _name, String _total, String _contact, String _discription) {
        this._id = _id;
        this._name = _name;
        this._total = _total;
        this._contact = _contact;
        this._discription = _discription;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String get_total() {
        return _total;
    }

    public void set_total(String _total) {
        this._total = _total;
    }

    public String get_contact() {
        return _contact;
    }

    public void set_contact(String _contact) {
        this._contact = _contact;
    }
    
}


