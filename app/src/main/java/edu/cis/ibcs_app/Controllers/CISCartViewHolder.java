package edu.cis.ibcs_app.Controllers;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import edu.cis.ibcs_app.R;

public class CISCartViewHolder extends RecyclerView.ViewHolder
{
    protected TextView cartText;

    public CISCartViewHolder(@NonNull View itemView) {
        super(itemView);
        cartText = itemView.findViewById(R.id.cartView);

    }
}
