package com.example.jonathan.prepcookinventory.utils;

import android.content.Context;

import com.example.jonathan.prepcookinventory.data.Item;
import com.example.jonathan.prepcookinventory.data.Order;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.siegmar.fastcsv.writer.CsvWriter;

public class CVShelper {
    public CVShelper(Context context, List<Item> items, List<Order> orders) {
        mItems = items;
        mOrders = orders;
        this.context = context;
    }

    private List<Item> mItems;
    private List<Order> mOrders;
    private File cvsExport;
    private Context context;

    public File getCvsExport() {

        String filePath = context.getFilesDir().getPath() + "/databaseExport.csv";
        cvsExport = new File(filePath);
        CsvWriter writer = new CsvWriter();

        Collection<String[]> exportData = new ArrayList<>();


        // Add Orders
        exportData.add(new String[] { "orderID", "orderDate", "orderName"});
        for ( Order order : mOrders) {
            exportData.add(new String[] {
                    Integer.toString(order.getOrderID()),
                    order.getOrderDate(),
                    order.getOrderName() });
        }

        // Add Items
        exportData.add(new String[] { "id,orderID,name,location,category,vendor,quantity"});
        for ( Item item : mItems ) {
            exportData.add(new String[]{
                    Integer.toString(item.getId()),
                    Integer.toString(item.getOrderID()),
                    item.getName(),
                    item.getCategory(),
                    item.getVendor(),
                    Integer.toString(item.getQuantity()) });
        }

        // Build file
        try {
            writer.write(cvsExport, StandardCharsets.UTF_8, exportData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cvsExport;
    }
}
