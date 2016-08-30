package com.OnCafe.Waiter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.OnCafe.Management.CategorySelectedDBAdapter;
import com.OnCafe.Management.ProductClickedDialog;
import com.OnCafe.R;


public class OrderItemFragment extends Fragment implements orderTabs.YourFragmentInterface {
    TextView total;
    TextView customerOrderList;
OrderedProducts op;
    OrderedDBAdapter odbAdapter;
    TableNoFrontDB frontDB;
    String fileName;
    String tableNum;
    public void setName(OrderTable tableNo){
        fileName="Table"+tableNo.get_tableNo() + ".db";
    }

    ServerFileTransfer serv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.order_item_main, container, false);
        SharedPreferences sTableNo = this.getActivity().getSharedPreferences("Table Number", Context.MODE_PRIVATE);
        tableNum= sTableNo.getString("hello","table");

        customerOrderList = (TextView) view.findViewById(R.id.customerOrderList);
        customerOrderList.setText("Customer Order List");
        odbAdapter= new OrderedDBAdapter(view.getContext());
        printDatabase(view);
        Button btnCancelAll=(Button)view.findViewById(R.id.btnCancelAll);
        btnCancelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                odbAdapter=new OrderedDBAdapter(view.getContext());
                odbAdapter.clear();
                printDatabase(view);
                frontDB=new TableNoFrontDB(view.getContext());
                frontDB.deleteProduct(tableNum);
            }
        });
        return view;
    }




   /* public void ToDeleteString(int selectedId,View view)
    {
        odbAdapter.deleteProduct(selectedId);
        printDatabase(view);
    }*/



    public void printDatabase(final View view) {

        Cursor cursor = odbAdapter.getAllRows();
        final String[] fromFieldNames = new String[]{

                 OrderedDBAdapter.COLUMN_PRODUCTNAME,
                 OrderedDBAdapter.COLUMN_AMOUNT,
                 OrderedDBAdapter.COLUMN_AMOUNT,
                OrderedDBAdapter.COLUMN_PERPRICE,
                 OrderedDBAdapter.COLUMN_COST,
                OrderedDBAdapter.COLUMN_ID};
        int[] toViewIDs = new int[]{
                R.id.selectedTextViewItemName,
                R.id.selectedTextViewItemAmount,
                R.id.selectedTextViewItemCalcAmt,
                R.id.selectedTextViewItemPerPrice,
                R.id.selectedTextViewPrice};
        final SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(view.getContext(), R.layout.order_item_layout, cursor, fromFieldNames, toViewIDs, 0);
        final ListView myList = (ListView) view.findViewById(R.id.selectedItemListView);
        myList.setAdapter(myCursorAdapter);

        odbAdapter= new OrderedDBAdapter(view.getContext());
/*       Cursor cur =odbAdapter.get_sum();
        int amount=0;
        if(cur.moveToFirst())
        {
           amount = cur.getInt(0);
        }

        TextView total= (TextView)view.findViewById(R.id.total);
       total.setText("" + amount);

*/
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Cursor cursor = (Cursor) myList.getItemAtPosition(position);
                final String SelectedItem = cursor.getString(cursor.getColumnIndexOrThrow(fromFieldNames[0]));
                final int Amount = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[1]));
                final int calcAmt = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[2]));
                final int perPrice = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[3]));

                final int selectedCost=cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[4]));
                final int SelectedItemID = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[5]));

                OrderFragmentDialog ofd = new OrderFragmentDialog(view.getContext(),
                        SelectedItemID, SelectedItem, Amount,calcAmt,perPrice,selectedCost);
                ofd.show();



                ofd.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        // myCursorAdapter.notifyDataSetChanged();
                            printDatabase(view);
                    }
                });
                //make a Toast
                Toast.makeText(view.getContext(),
                        SelectedItem, Toast.LENGTH_SHORT).show();
            }
        });



    }
    @Override
    public void fragmentBecameVisible() {

    }


    }
