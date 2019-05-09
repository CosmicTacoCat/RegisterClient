package edu.uark.uarkregisterapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.UUID;

import edu.uark.uarkregisterapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.uarkregisterapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.Cart;

public class CartTransition implements Parcelable {

	private String lookupCode;
	public String getLookupCode() {
		return this.lookupCode;
	}
	public CartTransition setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
		return this;
	}

	private int quantity;
	public int getQuantity() {
		return this.quantity;
	}
	public CartTransition setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	private String employee_id;
	public String getEmployee_Id() {
		return this.employee_id;
	}

	public CartTransition setEmployee_Id(String employee_id) {
		this.employee_id = employee_id;
		return this;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeString(this.lookupCode);
		destination.writeInt(this.quantity);
		destination.writeString(this.employee_id);


	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<CartTransition> CREATOR = new Creator<CartTransition>() {
		public CartTransition createFromParcel(Parcel productTransitionParcel) {
			return new CartTransition(productTransitionParcel);
		}

		public CartTransition[] newArray(int size) {
			return new CartTransition[size];
		}
	};

	public CartTransition() {
		this.quantity = -1;
		this.lookupCode = StringUtils.EMPTY;
		this.employee_id = "";
	}

	public CartTransition(Cart cart) {
		this.quantity = cart.getQuantity();
		this.lookupCode = cart.getLookupCode();
	//	this.employee_id = cart.getEmployee_Id();
	}

	private CartTransition(Parcel productTransitionParcel) {
		this.lookupCode = productTransitionParcel.readString();
		this.quantity = productTransitionParcel.readInt();
		this.employee_id = productTransitionParcel.readString();


	}
}
