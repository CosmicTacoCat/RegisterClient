package edu.uark.uarkregisterapp;

import android.content.Context;
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

import edu.uark.uarkregisterapp.adapters.ProductListAdapter;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

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
        this.productListAdapter = new ProductListAdapter(this, this.products);

        this.getProductsListView().setAdapter(this.productListAdapter);
        this.getProductsListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TransactionActivity.this);
               LayoutInflater inflater = LayoutInflater.from(TransactionActivity.this);
               View dialogView = inflater.inflate(R.layout.content_transaction, null);
               dialogBuilder.setView(dialogView);

               final EditText edt = (EditText) dialogView.findViewById(R.id.edit_text);

               dialogBuilder.setTitle("Add to cart?");
               dialogBuilder.setMessage("ok");
               dialogBuilder.setPositiveButton(R.string.button_confirm,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        );
               dialogBuilder.setNegativeButton(R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        );
               dialogBuilder.create();
               dialogBuilder.show();
//                Intent intent = new Intent(getApplicationContext(), ProductViewActivity.class);
//
//                intent.putExtra(
//                        getString(R.string.intent_extra_product),
//                        new ProductTransition((Product) getProductsListView().getItemAtPosition(position))
//                );

//                startActivity(intent);
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
//        this.startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
            finish();
   }

    public void ToCheckout(View view) {
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
                productListAdapter.notifyDataSetChanged();
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


    private List<Product> products;
    private ProductListAdapter productListAdapter;
}
