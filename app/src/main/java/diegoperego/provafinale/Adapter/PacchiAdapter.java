package diegoperego.provafinale.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import diegoperego.provafinale.Model.Pacco;
import diegoperego.provafinale.R;
import diegoperego.provafinale.DettaglioPacchiActivity;

/**
 * Created by utente3.academy on 15-Dec-17.
 */

public class PacchiAdapter extends RecyclerView.Adapter<PacchiAdapter.ViewPacchi>{

    private Context context;
    private List<Pacco> pacchi;

    public PacchiAdapter(Context context, List<Pacco> pacchi) {
        this.context = context;
        this.pacchi = pacchi;
    }

    @Override
    public PacchiAdapter.ViewPacchi onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pacchi, parent, false);
        ViewPacchi vp = new ViewPacchi(v);
        return vp;
    }

    @Override
    public void onBindViewHolder(PacchiAdapter.ViewPacchi holder, int position) {

        Pacco pacco = pacchi.get(position);
        holder.destinatario.setText(pacco.getDestinatario());
        holder.indirizzo.setText(pacco.getIndirizzo());
        holder.dataConsegna.setText(pacco.getDataConsegna());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DettaglioPacchiActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return pacchi.size();
    }

    public class ViewPacchi extends RecyclerView.ViewHolder{

        public TextView destinatario;
        public TextView indirizzo;
        public TextView dataConsegna;
        public CardView cardView;

        public ViewPacchi(View itemView) {
            super(itemView);

            destinatario = itemView.findViewById(R.id.tDestVal);
            indirizzo = itemView.findViewById(R.id.tIndirizzoVal);
            dataConsegna = itemView.findViewById(R.id.tDataVal);
            cardView = itemView.findViewById(R.id.cardPacchi);
        }

    }
}
