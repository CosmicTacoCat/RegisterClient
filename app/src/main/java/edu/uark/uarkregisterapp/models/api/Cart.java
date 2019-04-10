package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.fields.CartFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
//import edu.uark.uarkregisterapp.models.transition.CartTransition;


public class Cart implements ConvertToJsonInterface, LoadFromJsonInterface<Cart>{


    private String lookupCode;
    public String getLookupCode() {
        return this.lookupCode;
    }
    public Cart setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
        return this;
    }

    private int count;
    public int getCount() {
        return this.count;
    }
    public Cart setCount(int count) {
        this.count = count;
        return this;
    }

    @Override
    public Cart loadFromJson(JSONObject rawJsonObject) {
         this.lookupCode = rawJsonObject.optString(CartFieldName.LOOKUP_CODE.getFieldName());
        this.count = rawJsonObject.optInt(CartFieldName.COUNT.getFieldName());

        return this;
    }
    @Override
    public JSONObject convertToJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(CartFieldName.LOOKUP_CODE.getFieldName(), this.lookupCode);
            jsonObject.put(CartFieldName.COUNT.getFieldName(), this.count);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
    public Cart(){
        this.count = 0;
        this.lookupCode = "";
    //    this.id = cartTransition.getID();

    }
}
