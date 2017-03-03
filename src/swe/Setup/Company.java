/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.Setup;

/**
 *
 * @author Fin
 */
class Company {
    String Name;
    String Type;
    int price;

    public Company(String Name, String Type, int price) {
        this.Name = Name;
        this.Type = Type;
        this.price = price;
    }

    public String getName() {
        return Name;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return Type;
    }

    void printName() {
        System.out.println(this.Name);
    }
}
