package edu.uark.uarkregisterapp.models.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import edu.uark.uarkregisterapp.models.api.fields.CartFieldName;
import edu.uark.uarkregisterapp.models.api.fields.EmployeeFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.uarkregisterapp.models.transition.CartTransition;

public class Transaction implements Serializable, ConvertToJsonInterface{
    private String employee_id;
    public String getEmployee_Id() {
        return this.employee_id;
    }

    public Transaction setEmployee_Id(String employee_id) {
        this.employee_id = employee_id;
        return this;
    }

    private ArrayList<Cart> cart;
    public ArrayList<Cart> getCart() {return this.cart;}

    public Transaction setCart(ArrayList<Cart> cart){
        this.cart = cart;
        return this;
    }
//
//    @Override
//    public Transaction loadFromJson(JSONObject rawJsonObject) {
//
//        this.employee_id = rawJsonObject.optString(EmployeeFieldName.EMPLOYEE_ID.getFieldName());
//        this.cart = rawJsonObject.optJSONArray(CartFieldName.LOOKUP_CODE.getFieldName());
//
//        return this;
//    }
    @Override
    public JSONObject convertToJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(EmployeeFieldName.EMPLOYEE_ID.getFieldName(), this.employee_id);
            jsonObject.put(CartFieldName.CART.getFieldName(), this.cart);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
    public Transaction(){
        this.employee_id = "";
        this.cart = getCart();

    }

    public Transaction(CartTransition cartTransition) {
        this.employee_id = cartTransition.getEmployee_Id();
        this.cart = getCart();
    }
}
