package edu.cis.ibcs_app.Controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.cis.ibcs_app.R;
//
public class CISGetMenu extends RecyclerView.Adapter<CISMenuViewHolder>
{
    ArrayList<String> menu;
    public CISGetMenu(ArrayList data)
    {
        menu = data;
    }
    @NonNull
    @Override
    public CISMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_recycler_view,parent,false);

        CISMenuViewHolder holder= new CISMenuViewHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CISMenuViewHolder holder, int position)
    {
        holder.menuText.setText(menu.get(position));
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }
}
