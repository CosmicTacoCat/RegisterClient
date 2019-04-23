package edu.uark.uarkregisterapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import edu.uark.uarkregisterapp.R;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.Cart;

public class CheckoutListAdapter extends ArrayAdapter<Cart> {
	@NonNull
	@Override
	public View getView(int position, View convertView, @NonNull ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			view = inflater.inflate(R.layout.list_view_item_cart, parent, false);
		}

		Cart cart = this.getItem(position);

		if (cart != null) {
			TextView lookupCodeTextView = (TextView) view.findViewById(R.id.list_view_item_lookup_code);
			if (lookupCodeTextView != null) {
				lookupCodeTextView.setText(cart.getLookupCode());
			}

			TextView countTextView = (TextView) view.findViewById(R.id.list_view_item_count);
			if (countTextView != null) {
				countTextView.setText(String.format(Locale.getDefault(), "%d", cart.getQuantity()));
			}


		}

		return view;
	}

	public CheckoutListAdapter(Context context, List<Cart> cart) {
		super(context, R.layout.list_view_item_cart, cart);
	}
}
