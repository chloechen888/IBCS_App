package edu.cis.ibcs_app.Controllers;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import edu.cis.ibcs_app.R;

public class CISUserCart extends RecyclerView.Adapter<CISCartViewHolder>
{
    ArrayList<String> cart;
    public CISUserCart(ArrayList data)
    {
        cart = data;
    }
    @NonNull
    @Override
    public CISCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_recycler_view,parent,false);

        CISCartViewHolder holder = new CISCartViewHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CISCartViewHolder holder, int position)
    {
        holder.cartText.setText(cart.get(position));
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }
}
