package com.OnCafe.Waiter;





public class OrderTable{
    private int _id;
    private String _tableNo;
    private String _tableName;

    public OrderTable() {

    }

    public OrderTable(String table) {
        this._tableNo = table;
    }
    public OrderTable(String jpt,String table) {
        this._tableName = table;
    }

    public String get_tableNo() {
        return _tableNo;
    }
    public String get_name() {
        return _tableName;
    }
}

