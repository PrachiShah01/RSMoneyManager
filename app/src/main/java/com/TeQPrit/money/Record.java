package com.TeQPrit.money;

public class Record {
    int _id;
    String _name;
    String _contact;
    String _date;
    String _total;
    String _total2;
    String discription;

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Record(){   }

    public Record(String _name, String _contact, String _date, String _total, String _total2, String discription) {
        this._name = _name;
        this._contact = _contact;
        this._date = _date;
        this._total = _total;
        this._total2 = _total2;
        this.discription = discription;
    }

    public Record(int _id, String _name, String _contact, String _date, String _total, String _total2, String discription) {
        this._id = _id;
        this._name = _name;
        this._contact = _contact;
        this._date = _date;
        this._total = _total;
        this._total2 = _total2;
        this.discription = discription;
    }

    public String get_contact() {
        return _contact;
    }

    public void set_contact(String _contact) {
        this._contact = _contact;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_total2() {
        return _total2;
    }

    public void set_total2(String _total2) {
        this._total2 = _total2;
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
}


