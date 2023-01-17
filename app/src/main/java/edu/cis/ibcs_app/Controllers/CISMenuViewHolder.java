package edu.cis.ibcs_app.Controllers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.cis.ibcs_app.R;

public class CISMenuViewHolder extends RecyclerView.ViewHolder
{
    protected TextView menuText;

    public CISMenuViewHolder(@NonNull View itemView) {
        super(itemView);
        menuText = itemView.findViewById(R.id.menuView);

    }
}
