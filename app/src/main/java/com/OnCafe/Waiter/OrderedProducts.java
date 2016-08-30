package com.OnCafe.Waiter;



public class OrderedProducts {
    private int _id;
    private    String _productname;
    private String _tableNumber;


    public OrderedProducts() {

    }



    public OrderedProducts(String productname) {
        this._productname = productname;
    }



    public String get_productname() {
        return _productname;
    }



}

