package edu.uark.uarkregisterapp.models.api;

import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.fields.CartFieldName;
import edu.uark.uarkregisterapp.models.api.fields.EmployeeFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.uarkregisterapp.models.transition.CartTransition;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;

public class Cart implements Serializable, ConvertToJsonInterface, LoadFromJsonInterface<Cart>{
    private String lookupCode;
    public String getLookupCode() {
        return this.lookupCode;
    }
    public Cart setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
        return this;
    }


    private int quantity;
    public int getQuantity() {
        return this.quantity;
    }
    public Cart setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public Cart loadFromJson(JSONObject rawJsonObject) {
         this.lookupCode = rawJsonObject.optString(CartFieldName.LOOKUP_CODE.getFieldName());
        this.quantity = rawJsonObject.optInt(CartFieldName.QUANTITY.getFieldName());

        return this;
    }
    @Override
    public JSONObject convertToJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(CartFieldName.LOOKUP_CODE.getFieldName(), this.lookupCode);
            jsonObject.put(CartFieldName.QUANTITY.getFieldName(), this.quantity);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
    public Cart(){
        this.quantity = 0;
        this.lookupCode = "";
    }

    public Cart(CartTransition cartTransition) {
        this.quantity = cartTransition.getQuantity();
        this.lookupCode = cartTransition.getLookupCode();

    }
}
