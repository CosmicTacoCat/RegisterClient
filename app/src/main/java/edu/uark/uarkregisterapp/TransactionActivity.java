package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.uark.uarkregisterapp.adapters.ProductListAdapter;
import edu.uark.uarkregisterapp.adapters.TransactionListAdapter;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;
import edu.uark.uarkregisterapp.models.api.Cart;
import edu.uark.uarkregisterapp.models.api.services.ProductService;

public class TransactionActivity extends AppCompatActivity {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_transaction_);
                setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

                ActionBar actionBar = this.getSupportActionBar();
                if (actionBar != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                this.products = new ArrayList<>();
                this.cartList = new ArrayList<>();
        this.transactionListAdapter = new TransactionListAdapter(this, this.products);

        this.getProductsListView().setAdapter(this.transactionListAdapter);
        this.getProductsListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TransactionActivity.this);
               LayoutInflater inflater = LayoutInflater.from(TransactionActivity.this);
               View dialogView = inflater.inflate(R.layout.content_confirm_product, null);
               dialogBuilder.setView(dialogView);

               Product product = transactionListAdapter.getItem(position);
               product_in_cart = product.getLookupCode();


              final EditText edt = dialogView.findViewById(R.id.edit_text);

               dialogBuilder.setTitle("How many " + product_in_cart + "s to add to cart?");
               dialogBuilder.setPositiveButton(R.string.button_confirm,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        cart.setLookupCode(product_in_cart);
                                       final int num = Integer.parseInt(edt.getText().toString());
                                       cart.setQuantity(num);
                                         cartList.add(cart);
                                       // System.out.println(cartList.size());
                                        dialog.dismiss();
                                    }
                                }
                        );
               dialogBuilder.setNegativeButton(R.string.button_cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        );
               dialogBuilder.create();
               dialogBuilder.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        (new RetrieveProductsTask()).execute();
    }

    private ListView getProductsListView() {
        return (ListView) this.findViewById(R.id.list_view_products);
    }

    public void BackOnClick(View view) {
            finish();
   }

    public void ToCheckout(View view) {
         System.out.println(cartList.size());
        this.startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
            }



    private class RetrieveProductsTask extends AsyncTask<Void, Void, ApiResponse<List<Product>>> {
        @Override
        protected void onPreExecute() {
            this.loadingProductsAlert.show();
        }

        @Override
        protected ApiResponse<List<Product>> doInBackground(Void... params) {
            ApiResponse<List<Product>> apiResponse = (new ProductService()).getProducts();

            if (apiResponse.isValidResponse()) {
                products.clear();
                products.addAll(apiResponse.getData());
            }

            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<List<Product>> apiResponse) {
            if (apiResponse.isValidResponse()) {
                transactionListAdapter.notifyDataSetChanged();
            }

            this.loadingProductsAlert.dismiss();

            if (!apiResponse.isValidResponse()) {
                new AlertDialog.Builder(TransactionActivity.this).
                        setMessage(R.string.alert_dialog_products_load_failure).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        ).
                        create().
                        show();
            }
        }

        private AlertDialog loadingProductsAlert;

        private RetrieveProductsTask() {
            this.loadingProductsAlert = new AlertDialog.Builder(TransactionActivity.this).
                    setMessage(R.string.alert_dialog_products_loading).
                    create();
        }
    }

    public String product_in_cart;
    private List<Product> products;
    private Cart cart = new Cart();
    private List<Cart> cartList;
    private TransactionListAdapter transactionListAdapter;
}
