package edu.uark.uarkregisterapp.models.api.services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uark.uarkregisterapp.CheckoutActivity;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Cart;
import edu.uark.uarkregisterapp.models.api.enums.ApiObject;
import edu.uark.uarkregisterapp.models.api.enums.CartApiMethod;
import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;


public class CartService extends BaseRemoteService {


	public ApiResponse<Cart> getCartByLookupCode(String cartLookupCode) {
		return this.readCartDetailsFromResponse(
			this.<Cart>performGetRequest(
				this.buildPath(
					(new PathElementInterface[] { CartApiMethod.BY_LOOKUP_CODE })
					, cartLookupCode
				)
			)
		);
	}

	public ApiResponse<List<Cart>> getCart() {
		ApiResponse<List<Cart>> apiResponse = this.performGetRequest(
			this.buildPath()
		);

		JSONArray rawJsonArray = this.rawResponseToJSONArray(apiResponse.getRawResponse());
		if (rawJsonArray != null) {
			ArrayList<Cart> cart = new ArrayList<>(rawJsonArray.length());
			for (int i = 0; i < rawJsonArray.length(); i++) {
				try {
					cart.add((new Cart()).loadFromJson(rawJsonArray.getJSONObject(i)));
				} catch (JSONException e) {
					Log.d("GET CART", e.getMessage());
				}
			}

			apiResponse.setData(cart);
		} else {
			apiResponse.setData(new ArrayList<Cart>(0));
		}

		return apiResponse;
	}

	public ApiResponse<Cart> updateCart(Cart cart) {
		return this.readCartDetailsFromResponse(
			this.<Cart>performPutRequest(
				this.buildPath(cart.getLookupCode())
				, cart.convertToJson()
			)
		);
	}

	public ApiResponse<Cart> createCart(Cart cart) {
		return this.readCartDetailsFromResponse(
			this.<Cart>performPostRequest(
				this.buildPath()
				, cart.convertToJson()
			)
		);
	}



	private ApiResponse<Cart> readCartDetailsFromResponse(ApiResponse<Cart> apiResponse) {
		JSONObject rawJsonObject = this.rawResponseToJSONObject(
			apiResponse.getRawResponse()
		);

		if (rawJsonObject != null) {
			apiResponse.setData(
				(new Cart()).loadFromJson(rawJsonObject)
			);
		}

		return apiResponse;
	}

	public CartService() { super(ApiObject.CART); }
}
