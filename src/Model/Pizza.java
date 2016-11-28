/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author daniel
 */
public class Pizza {
    private int id;
    private String name;
    private char size;
    private double price;
    
    public Pizza(int id, String name, char size, double price){
        this.id = id;
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public char getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }
    
    
}
