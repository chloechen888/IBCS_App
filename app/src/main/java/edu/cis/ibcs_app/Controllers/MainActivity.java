package edu.cis.ibcs_app.Controllers;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Arrays;

import edu.cis.ibcs_app.Models.Request;
import edu.cis.ibcs_app.Models.SimpleClient;
import edu.cis.ibcs_app.R;
import edu.cis.ibcs_app.Utils.CISConstants;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText IDInput;
    String userID;
    ArrayList<String> cartItems = new ArrayList<String>();
    ArrayList<String>menuItems = new ArrayList<String>();
    RecyclerView recView;
    TextView accountUserName;
    TextView money;
    EditText topUpInput;
    String topUp;
    EditText itemInput;
    String itemId;
    String price;
    EditText typeInput;
    String type;
    String description;
    String foodName;
    EditText orderIDinput;
    String orderID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        //This is not great, for extra credit you can fix this so that network calls happen on a different thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        addCake();
        addBrownie();
        addChocolateBar();
        addCookie();
        addDonut();
    }

//set content views/ switch screens
    public void goAdminPage(View v)
    {
        Intent adminPage = new Intent(MainActivity.this, CISUserActivity.class);
        startActivity(adminPage);
    }
    public void pleaseUserPage(View v)
    {
        setContentView(R.layout.activity_get_user);
    }
    public void goToMainMenu(View v)
    {
        setContentView(R.layout.activity_main_menu);
    }
    public void goDeleteOrder(View v)
    {
        setContentView(R.layout.activity_delete_order);
    }
    public void goPlaceOrder(View v)
    {
        setContentView(R.layout.activity_placeorder);
    }
    public void goToMainPage(View v)
    {
        setContentView(R.layout.activity_main);
    }
    public void goToDessertMenu(View v)
    {
        setContentView(R.layout.activity_dessert_menu);
    }


    //get user
    public void getUser(View v)
    {
        IDInput = (EditText) findViewById(R.id.usergetID);
       userID =  IDInput.getText().toString();
        Request newRequest = new Request(CISConstants.GET_USER);
        newRequest.addParam(CISConstants.USER_ID_PARAM,userID);

        try
        {
            String resultsFromRequest = SimpleClient.makeRequest(CISConstants.HOST,newRequest);
            System.out.println("RESULTS"+resultsFromRequest);
            if(!resultsFromRequest.equals(CISConstants.USER_INVALID_ERR)) {
                Toast message = Toast.makeText(this, "OH WE FOUND YOU", Toast.LENGTH_SHORT);
                message.show();
                setContentView(R.layout.activity_main_menu);

            }
            else{

                Toast badMessage = Toast.makeText(this,"Seems like we can't find you, do you have an account?",Toast.LENGTH_SHORT);
                badMessage.show();
            }
        }
        catch(Exception err)
        {
            Toast message = Toast.makeText(this,"hmm seems like something went wrong, re run the program to check",Toast.LENGTH_SHORT);
            message.show();
        }

    }

//making the array list for things in cart
    public void cartArrayList()
    {
            String resultFromRequest ="";
        Request newRequest = new Request(CISConstants.GET_CART);
        newRequest.addParam(CISConstants.USER_ID_PARAM,userID);
            try
            {
                resultFromRequest = SimpleClient.makeRequest(CISConstants.HOST,newRequest);
                Toast message = Toast.makeText(this,"Your cart",Toast.LENGTH_SHORT);
                message.show();

            }
            catch (Exception err)
            {
                Toast mesanger = Toast.makeText(this,"hmm seems like something went wrong, re run the program to check",Toast.LENGTH_SHORT);
                mesanger.show();
            }
        String [] getCartItems = resultFromRequest.split("<");
        cartItems.clear();
        cartItems.addAll(Arrays.asList(getCartItems));
        cartItems.remove(0);

    }

    //putting information in recycler view
    public void cart (View V)
    {
        setContentView(R.layout.activity_getallcart);
        CISUserCart myAdapter = new CISUserCart(cartItems) ;
        cartArrayList();
        recView = findViewById(R.id.getCartRecyclerView);
        recView.setAdapter(myAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setItemAnimator(new DefaultItemAnimator());
        recView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));


    }
// placing order
    public void placeOrder(View v)
    {
        typeInput = (EditText) findViewById(R.id.orderType);
        orderIDinput = (EditText) findViewById(R.id.orderIdInput);
        itemInput = (EditText) findViewById(R.id.itemIdInput1);

        type = typeInput.getText().toString();
        orderID = orderIDinput.getText().toString();
        itemId = itemInput.getText().toString();

        Request newRequest = new Request(CISConstants.PLACE_ORDER);
        newRequest.addParam(CISConstants.ORDER_ID_PARAM,orderID);
        newRequest.addParam(CISConstants.USER_ID_PARAM,userID);
        newRequest.addParam(CISConstants.ORDER_TYPE_PARAM,type);
        newRequest.addParam(CISConstants.ITEM_ID_PARAM,itemId);

        try {
            String resultsFromRequest =SimpleClient.makeRequest(CISConstants.HOST,newRequest);
            if(!resultsFromRequest.equals(CISConstants.SUCCESS))
            {
                Toast badMessage = Toast.makeText(this,resultsFromRequest,Toast.LENGTH_SHORT);
                badMessage.show();

            }
            else
            {
                Toast message = Toast.makeText(this,"Order placed",Toast.LENGTH_SHORT);
                message.show();
            }
        }
        catch (Exception err)
        {
            Toast mesanger = Toast.makeText(this,"hmm seems like something went wrong, re run the program to check",Toast.LENGTH_SHORT);
            mesanger.show();
        }


    }

    //deleting order
    public void deleteOrder (View v)
    {

        orderIDinput = (EditText) findViewById(R.id.deleteId);
        orderID = orderIDinput.getText().toString();

        Request newRequest = new Request(CISConstants.DELETE_ORDER);
        newRequest.addParam(CISConstants.ORDER_ID_PARAM,orderID);
        newRequest.addParam(CISConstants.USER_ID_PARAM,userID);

        try
        {
            String resultFromRequest = SimpleClient.makeRequest(CISConstants.HOST,newRequest);
            if(!resultFromRequest.equals(CISConstants.SUCCESS))
            {
                Toast badMessage = Toast.makeText(this,resultFromRequest,Toast.LENGTH_SHORT);
                badMessage.show();
            }
            else
            {
                Toast message = Toast.makeText(this,"Item deleted",Toast.LENGTH_SHORT);
                message.show();
            }
        }
        catch (Exception err)
        {
            Toast mesanger = Toast.makeText(this,"hmm seems like something went wrong, re run the program to check",Toast.LENGTH_SHORT);
            mesanger.show();
        }


    }
//getting menu items in an array list from server
    public void getMenu()
    {
        String resultFromRequest = "";
        Request newRequest = new Request(CISConstants.GET_ALL_ITEMS);
        try
        {
            resultFromRequest = SimpleClient.makeRequest(CISConstants.HOST,newRequest);
            Toast message = Toast.makeText(this," Menu Items",Toast.LENGTH_SHORT);
            message.show();
        }
        catch(Exception err)
        {
            Toast mesanger = Toast.makeText(this,"hmm seems like something went wrong, re run the program to check",Toast.LENGTH_SHORT);
            mesanger.show();
        }
        String [] getAllMenuItems = resultFromRequest.split("<");

        for(int i = 0; i< getAllMenuItems.length-1;i++)
        {
            menuItems.add(getAllMenuItems[i+1]);
            System.out.println(getAllMenuItems[i+1]);
        }


    }
// putting menu items in recycler view
    public void wholeMenu (View v)
    {
        setContentView(R.layout.activity_menuitems);
        getMenu();
        for(int i = 0; i<menuItems.size();i++)
        {
            System.out.println(menuItems.get(i));
        }
        recView = findViewById(R.id.getMenuRecyclerView);
        CISGetMenu myAdapter = new CISGetMenu(menuItems) ;
        recView.setAdapter(myAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setItemAnimator(new DefaultItemAnimator());
        recView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));


    }
// Creative task 1- adding desserts to menu
    public void addCake()
    {
        System.out.println("HELLO HELLO I'M WORKING");
        type = "dessert";
        description = "Yummy Yummy Cake";
        price = "5";
        itemId = "chloeCake123";
        foodName= "Chococlate Cake";

        Request newRequest = new Request(CISConstants.ADD_MENU_ITEM);
        newRequest.addParam(CISConstants.ITEM_TYPE_PARAM,type);
        newRequest.addParam(CISConstants.ITEM_ID_PARAM,itemId);
        newRequest.addParam(CISConstants.PRICE_PARAM,price);
        newRequest.addParam(CISConstants.DESC_PARAM,description);
        newRequest.addParam(CISConstants.ITEM_NAME_PARAM,foodName);

        try{
            String resultsFromRequest =SimpleClient.makeRequest(CISConstants.HOST,newRequest);

        }
        catch (Exception err)
        {
            System.out.println("dessert not added"); //check run
        }
    }

    public void addChocolateBar()
    {

        System.out.println("HELLO HELLO I'M WORKING");
        type = "dessert";
        description = "The best Chocolate Bar";
        price = "5";
        itemId = "chloeChocolate123";
        foodName= "Chococlate Bar";

        Request newRequest = new Request(CISConstants.ADD_MENU_ITEM);
        newRequest.addParam(CISConstants.ITEM_TYPE_PARAM,type);
        newRequest.addParam(CISConstants.ITEM_ID_PARAM,itemId);
        newRequest.addParam(CISConstants.PRICE_PARAM,price);
        newRequest.addParam(CISConstants.DESC_PARAM,description);
        newRequest.addParam(CISConstants.ITEM_NAME_PARAM,foodName);

        try{
            String resultsFromRequest =SimpleClient.makeRequest(CISConstants.HOST,newRequest);

        }
        catch (Exception err)
        {
            System.out.println("dessert not added"); //check run
        }
    }

    public void addDonut()
    {

        System.out.println("HELLO HELLO I'M WORKING");
        type = "dessert";
        description = "Very yumy chocolate covered donut (with sprinkles)";
        price = "5";
        itemId = "chloeDonut123";
        foodName= "Donut";

        Request newRequest = new Request(CISConstants.ADD_MENU_ITEM);
        newRequest.addParam(CISConstants.ITEM_TYPE_PARAM,type);
        newRequest.addParam(CISConstants.ITEM_ID_PARAM,itemId);
        newRequest.addParam(CISConstants.PRICE_PARAM,price);
        newRequest.addParam(CISConstants.DESC_PARAM,description);
        newRequest.addParam(CISConstants.ITEM_NAME_PARAM,foodName);

        try{
            String resultsFromRequest =SimpleClient.makeRequest(CISConstants.HOST,newRequest);

        }
        catch (Exception err)
        {
            System.out.println("dessert not added"); //check run
        }
    }

    public void addCookie()
    {

        System.out.println("HELLO HELLO I'M WORKING");
        type = "dessert";
        description = "Chewy sweet cookies";
        price = "5";
        itemId = "chloeCookies123";
        foodName= "Chococlate Chip Cookie";

        Request newRequest = new Request(CISConstants.ADD_MENU_ITEM);
        newRequest.addParam(CISConstants.ITEM_TYPE_PARAM,type);
        newRequest.addParam(CISConstants.ITEM_ID_PARAM,itemId);
        newRequest.addParam(CISConstants.PRICE_PARAM,price);
        newRequest.addParam(CISConstants.DESC_PARAM,description);
        newRequest.addParam(CISConstants.ITEM_NAME_PARAM,foodName);

        try{
            String resultsFromRequest =SimpleClient.makeRequest(CISConstants.HOST,newRequest);

        }
        catch (Exception err)
        {
            System.out.println("dessert not added"); //check run
        }
    }

    public void addBrownie()
    {

        System.out.println("HELLO HELLO I'M WORKING");
        type = "dessert";
        description = "Chocolate soft Brownie";
        price = "5";
        itemId = "chloeBrownie123";
        foodName= "Brownie";

        Request newRequest = new Request(CISConstants.ADD_MENU_ITEM);
        newRequest.addParam(CISConstants.ITEM_TYPE_PARAM,type);
        newRequest.addParam(CISConstants.ITEM_ID_PARAM,itemId);
        newRequest.addParam(CISConstants.PRICE_PARAM,price);
        newRequest.addParam(CISConstants.DESC_PARAM,description);
        newRequest.addParam(CISConstants.ITEM_NAME_PARAM,foodName);

        try{
            String resultsFromRequest =SimpleClient.makeRequest(CISConstants.HOST,newRequest);

        }
        catch (Exception err)
        {
            System.out.println("dessert not added"); //check run
        }
    }

    //ordering dessert according to image pressed
    public void placeOrderCake(View v)
    {

        System.out.println(userID);

        orderID = "tooCoolforSchool123";
        type = "dessert";
        itemId = "chloeCake123";

        Request newRequest = new Request(CISConstants.PLACE_ORDER);
        newRequest.addParam(CISConstants.ORDER_ID_PARAM,orderID);
        newRequest.addParam(CISConstants.USER_ID_PARAM,userID);
        newRequest.addParam(CISConstants.ORDER_TYPE_PARAM,type);
        newRequest.addParam(CISConstants.ITEM_ID_PARAM,itemId);

        try {
            String resultsFromRequest =SimpleClient.makeRequest(CISConstants.HOST,newRequest);
            System.out.println("Runs till here");
            if(!resultsFromRequest.equals(CISConstants.SUCCESS))
            {
                Toast badMessage = Toast.makeText(this,resultsFromRequest,Toast.LENGTH_SHORT);
                badMessage.show();

            }
            else
            {
                Toast message = Toast.makeText(this,"Order placed",Toast.LENGTH_SHORT);
                message.show();
            }
        }
        catch (Exception err)
        {
            Toast mesanger = Toast.makeText(this,"hmm seems like something went wrong, re run the program to check",Toast.LENGTH_SHORT);
            mesanger.show();
        }


    }

    public void placeOrderCookie(View v)
    {

        System.out.println(userID);

        orderID = "cookie1345";
        type = "dessert";
        itemId = "chloeCookies123";

        Request newRequest = new Request(CISConstants.PLACE_ORDER);
        newRequest.addParam(CISConstants.ORDER_ID_PARAM,orderID);
        newRequest.addParam(CISConstants.USER_ID_PARAM,userID);
        newRequest.addParam(CISConstants.ORDER_TYPE_PARAM,type);
        newRequest.addParam(CISConstants.ITEM_ID_PARAM,itemId);

        try {
            String resultsFromRequest =SimpleClient.makeRequest(CISConstants.HOST,newRequest);
            if(!resultsFromRequest.equals(CISConstants.SUCCESS))
            {
                Toast badMessage = Toast.makeText(this,resultsFromRequest,Toast.LENGTH_SHORT);
                badMessage.show();

            }
            else
            {
                Toast message = Toast.makeText(this,"Order placed",Toast.LENGTH_SHORT);
                message.show();
            }
        }
        catch (Exception err)
        {
            Toast mesanger = Toast.makeText(this,"hmm seems like something went wrong, re run the program to check",Toast.LENGTH_SHORT);
            mesanger.show();
        }


    }

    public void placeOrderDonut(View v)
    {
//        setContentView(R.layout.activity_placeOrder);
        System.out.println(userID);

        orderID = "donutComplain123";
        type = "dessert";
        itemId = "chloeDonut123";

        Request newRequest = new Request(CISConstants.PLACE_ORDER);
        newRequest.addParam(CISConstants.ORDER_ID_PARAM,orderID);
        newRequest.addParam(CISConstants.USER_ID_PARAM,userID);
        newRequest.addParam(CISConstants.ORDER_TYPE_PARAM,type);
        newRequest.addParam(CISConstants.ITEM_ID_PARAM,itemId);

        try {
            String resultsFromRequest =SimpleClient.makeRequest(CISConstants.HOST,newRequest);
            System.out.println("Runs till here");
            if(!resultsFromRequest.equals(CISConstants.SUCCESS))
            {
                Toast badMessage = Toast.makeText(this,resultsFromRequest,Toast.LENGTH_SHORT);
                badMessage.show();

            }
            else
            {
                Toast message = Toast.makeText(this,"Order placed",Toast.LENGTH_SHORT);
                message.show();
            }
        }
        catch (Exception err)
        {
            Toast mesanger = Toast.makeText(this,"hmm seems like something went wrong, re run the program to check",Toast.LENGTH_SHORT);
            mesanger.show();
        }


    }

    public void placeOrderBrownie(View v)
    {
//        setContentView(R.layout.activity_placeOrder);
        System.out.println(userID);

        orderID = "Brownie1234";
        type = "dessert";
        itemId = "chloeBrownie123";

        Request newRequest = new Request(CISConstants.PLACE_ORDER);
        newRequest.addParam(CISConstants.ORDER_ID_PARAM,orderID);
        newRequest.addParam(CISConstants.USER_ID_PARAM,userID);
        newRequest.addParam(CISConstants.ORDER_TYPE_PARAM,type);
        newRequest.addParam(CISConstants.ITEM_ID_PARAM,itemId);

        try {
            String resultsFromRequest =SimpleClient.makeRequest(CISConstants.HOST,newRequest);
            System.out.println("Runs till here");
            if(!resultsFromRequest.equals(CISConstants.SUCCESS))
            {
                Toast badMessage = Toast.makeText(this,resultsFromRequest,Toast.LENGTH_SHORT);
                badMessage.show();

            }
            else
            {
                Toast message = Toast.makeText(this,"Order placed",Toast.LENGTH_SHORT);
                message.show();
            }
        }
        catch (Exception err)
        {
            Toast mesanger = Toast.makeText(this,"hmm seems like something went wrong, re run the program to check",Toast.LENGTH_SHORT);
            mesanger.show();
        }


    }

    public void placeOrderChocolate(View v)
    {
//        setContentView(R.layout.activity_placeOrder);
        System.out.println(userID);

        orderID = "chocolate1345";
        type = "dessert";
        itemId = "chloeChocolate123";

        Request newRequest = new Request(CISConstants.PLACE_ORDER);
        newRequest.addParam(CISConstants.ORDER_ID_PARAM,orderID);
        newRequest.addParam(CISConstants.USER_ID_PARAM,userID);
        newRequest.addParam(CISConstants.ORDER_TYPE_PARAM,type);
        newRequest.addParam(CISConstants.ITEM_ID_PARAM,itemId);

        try {
            String resultsFromRequest =SimpleClient.makeRequest(CISConstants.HOST,newRequest);
            if(!resultsFromRequest.equals(CISConstants.SUCCESS))
            {
                Toast badMessage = Toast.makeText(this,resultsFromRequest,Toast.LENGTH_SHORT);
                badMessage.show();

            }
            else
            {
                Toast message = Toast.makeText(this,"Order placed",Toast.LENGTH_SHORT);
                message.show();
            }
        }
        catch (Exception err)
        {
            Toast mesanger = Toast.makeText(this,"hmm seems like something went wrong, re run the program to check",Toast.LENGTH_SHORT);
            mesanger.show();
        }


    }

//going to account page
public void goToAccount(View v)
{
    setContentView(R.layout.activity_topup);
    updateMoneyInfo();


}
//creative task 2-  top up -  setting text
public void updateMoneyInfo()
{
    String resultsFromRequest ="";
    accountUserName = (TextView) findViewById(R.id.userName);
    accountUserName.setText(userID);
    Request newRequest = new Request(CISConstants.GET_MONEY);
    newRequest.addParam(CISConstants.USER_ID_PARAM,userID);
    try {
        resultsFromRequest = SimpleClient.makeRequest(CISConstants.HOST, newRequest);
    }
    catch (Exception err)
    {
        System.out.println("Something went wrong");
    }

    money = (TextView) findViewById(R.id.moneyAvalible);
    money.setText("Money: "+resultsFromRequest);
}

//getting info from editText to add to users money
    public void topUp(View v)
    {
        topUpInput =(EditText) findViewById(R.id.topUpAmount);
        topUp = topUpInput.getText().toString();
        Request newRequest = new Request(CISConstants.TOP_UP);
        newRequest.addParam(CISConstants.USER_ID_PARAM,userID);
        newRequest.addParam(CISConstants.TOP_UP_AMOUNT,topUp);

        try {
            String resultFromRequest = SimpleClient.makeRequest(CISConstants.HOST,newRequest);
            updateMoneyInfo();
            Toast message = Toast.makeText(this," Top Uped",Toast.LENGTH_SHORT);
            message.show();
        }
        catch(Exception err)
        {
            Toast mesanger = Toast.makeText(this,"hmm seems like something went wrong, re run the program to check",Toast.LENGTH_SHORT);
            mesanger.show();
        }


    }












}


