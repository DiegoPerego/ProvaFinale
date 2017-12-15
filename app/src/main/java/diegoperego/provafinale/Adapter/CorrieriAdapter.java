package diegoperego.provafinale.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import diegoperego.provafinale.DettaglioCorriereActivity;
import diegoperego.provafinale.Model.Corriere;
import diegoperego.provafinale.R;

/**
 * Created by utente3.academy on 15-Dec-17.
 */

public class CorrieriAdapter extends RecyclerView.Adapter<CorrieriAdapter.ViewCorrieri> {

    private Context context;
    private List<Corriere> corrieri;

    public CorrieriAdapter(Context context, List<Corriere> corrieri) {
        this.context = context;
        this.corrieri = corrieri;
    }

    @Override
    public CorrieriAdapter.ViewCorrieri onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_corrieri, parent, false);
        ViewCorrieri vc = new ViewCorrieri(v);
        return vc;
    }

    @Override
    public void onBindViewHolder(CorrieriAdapter.ViewCorrieri holder, int position) {

        Corriere corriere = corrieri.get(position);
        holder.corriere.setText(corriere.getUsername());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DettaglioCorriereActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return corrieri.size();
    }

    public class ViewCorrieri extends RecyclerView.ViewHolder{

        public TextView corriere;
        public CardView cardView;

        public ViewCorrieri(View itemView) {
            super(itemView);

            corriere = itemView.findViewById(R.id.tFattorinoVal);
            cardView = itemView.findViewById(R.id.cardCorrieri);
        }
    }
}
