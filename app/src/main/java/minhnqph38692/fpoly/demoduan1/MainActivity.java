package minhnqph38692.fpoly.demoduan1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView invoiceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        invoiceTextView = findViewById(R.id.invoiceTextView);

        Invoice invoice = new Invoice(
                "Your Company",
                "123 Main St, Cityville",
                "Customer Name",
                "456 Oak St, Townsville"
        );

        invoice.addItem("Product A", 2, 25.50);
        invoice.addItem("Product B", 3, 50.00);
        invoice.addItem("Product C", 4, 60.00);

        String invoiceText = invoice.generateInvoice();
        invoiceTextView.setText(invoiceText);
    }

    private static class InvoiceItem {
        String itemName;
        int quantity;
        double price;

        public InvoiceItem(String itemName, int quantity, double price) {
            this.itemName = itemName;
            this.quantity = quantity;
            this.price = price;
        }

        public double getTotal() {
            return quantity * price;
        }
    }

    private static class Invoice {
        String companyName;
        String companyAddress;
        String customerName;
        String customerAddress;
        List<InvoiceItem> items;

        public Invoice(String companyName, String companyAddress, String customerName, String customerAddress) {
            this.companyName = companyName;
            this.companyAddress = companyAddress;
            this.customerName = customerName;
            this.customerAddress = customerAddress;
            this.items = new ArrayList<>();
        }

        public void addItem(String itemName, int quantity, double price) {
            items.add(new InvoiceItem(itemName, quantity, price));
        }

        public double calculateTotal() {
            return items.stream().mapToDouble(InvoiceItem::getTotal).sum();
        }

        public String generateInvoice() {
            StringBuilder invoice = new StringBuilder();
            invoice.append("-------------------------------------------------\n");
            invoice.append(String.format("%-20s%s\n", "Company:", companyName));
            invoice.append(String.format("%-20s%s\n", "Address:", companyAddress));
            invoice.append("\n");
            invoice.append(String.format("%-20s%s\n", "Customer:", customerName));
            invoice.append(String.format("%-20s%s\n", "Address:", customerAddress));
            invoice.append("-------------------------------------------------\n");
            invoice.append(String.format("%-20s%-10s%-15s%s\n", "Item", "Quantity", "Unit Price", "Total"));
            invoice.append("-------------------------------------------------\n");

            for (InvoiceItem item : items) {
                invoice.append(String.format("%-20s%-10d$%-15.2f$%.2f\n", item.itemName, item.quantity, item.price, item.getTotal()));
            }

            invoice.append("-------------------------------------------------\n");
            invoice.append(String.format("%-35s$%.2f\n", "Subtotal:", calculateTotal()));
            invoice.append(String.format("%-35s$%.2f\n", "Tax (10%):", calculateTotal() * 0.1));
            invoice.append("-------------------------------------------------\n");
            invoice.append(String.format("%-35s$%.2f\n", "Total:", calculateTotal() * 1.1));
            invoice.append("-------------------------------------------------\n");

            return invoice.toString();
        }

    }
}