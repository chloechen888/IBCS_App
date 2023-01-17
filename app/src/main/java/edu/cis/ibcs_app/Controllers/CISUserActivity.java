package edu.cis.ibcs_app.Controllers;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.cis.ibcs_app.Models.Request;
import edu.cis.ibcs_app.Models.SimpleClient;
import edu.cis.ibcs_app.R;
import edu.cis.ibcs_app.Utils.CISConstants;


public class CISUserActivity extends AppCompatActivity {

    EditText nameInput;
    String userName;
    EditText IDInput;
    String userID;
    EditText yearInput;
    String userYear;
    EditText itemInput;
    String itemId;
    EditText priceInput;
    String price;
    EditText typeInput;
    String type;
    EditText descriptionInput;
    String description;
    EditText foodNameInput;
    String foodName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

    }
    // pinging server
    public void pingTheServer(View v)
    {
        try
        {
            //create a request with commnad ping
            Request exampleRequest = new Request("ping");

            //sending the request to a url

            String resultFromServer = SimpleClient.makeRequest(CISConstants.HOST,exampleRequest);
            System.out.println("results from server for ping command" + resultFromServer);
        }
        catch (Exception err)
        {
            err.printStackTrace();
        }
    }

    // set content view/ switching screens
    public void goCreateUser(View v)
    {
        setContentView(R.layout.activity_cisuser);
    }
    public void goAddItem(View V)
    {
        setContentView(R.layout.activity_addmenuitem);
    }
    public void goToAdminMenu(View v)
    {
        setContentView(R.layout.activity_admin_menu);
    }


    //creating user
    public void createUser(View v)
    {
        nameInput = (EditText) findViewById(R.id.nameInput);
        IDInput = (EditText) findViewById(R.id.userId);
        yearInput =(EditText) findViewById(R.id.yearInput);
        userName = nameInput.getText().toString();
        userID = IDInput.getText().toString();
        userYear = yearInput.getText().toString();

        Request newRequest =  new Request(CISConstants.CREATE_USER);
        newRequest.addParam(CISConstants.USER_NAME_PARAM,userName);
        newRequest.addParam(CISConstants.USER_ID_PARAM,userID);
        newRequest.addParam(CISConstants.YEAR_LEVEL_PARAM,userYear);

        System.out.println("works till here1");
        try
        {
            String resultsFromRequest = SimpleClient.makeRequest(CISConstants.HOST,newRequest);
            System.out.println("RESULTS"+resultsFromRequest);
            Toast message = Toast.makeText(this,"YAY YOU MADE A USER",Toast.LENGTH_SHORT);
            message.show();

        }
        catch(Exception err)
        {
            Toast mesanger = Toast.makeText(this,"oh no",Toast.LENGTH_SHORT);
            mesanger.show();
        }


    }
//adding menu items
    public void addMenuItem(View v)
    {
        typeInput = (EditText) findViewById(R.id.typeInput);
        descriptionInput = (EditText) findViewById(R.id.description);
        priceInput=(EditText) findViewById(R.id.priceInput);
        itemInput = (EditText) findViewById(R.id.itemIdInput);
        foodNameInput =(EditText) findViewById(R.id.itemNameInput);

        type = typeInput.getText().toString();
        description = descriptionInput.getText().toString();
        price = priceInput.getText().toString();
        itemId = itemInput.getText().toString();
        foodName= foodNameInput.getText().toString();

        Request newRequest = new Request(CISConstants.ADD_MENU_ITEM);
        newRequest.addParam(CISConstants.ITEM_TYPE_PARAM,type);
        newRequest.addParam(CISConstants.ITEM_ID_PARAM,itemId);
        newRequest.addParam(CISConstants.PRICE_PARAM,price);
        newRequest.addParam(CISConstants.DESC_PARAM,description);
        newRequest.addParam(CISConstants.ITEM_NAME_PARAM,foodName);

        try{
            String resultsFromRequest =SimpleClient.makeRequest(CISConstants.HOST,newRequest);
            if(resultsFromRequest.equals(CISConstants.SUCCESS))
            {
                Toast message = Toast.makeText(this,"New menu item has been added",Toast.LENGTH_SHORT);
                message.show();
            }
            else
            {
                Toast badMessage = Toast.makeText(this,"parameter missing, please make sure you've filled in all the questions",Toast.LENGTH_SHORT);
                badMessage.show();
            }

            }
        catch (Exception err)
        {
            Toast mesanger = Toast.makeText(this,"hmm it seems like something went wrong, re run the program and try again",Toast.LENGTH_SHORT);
            mesanger.show();
        }
    }








}