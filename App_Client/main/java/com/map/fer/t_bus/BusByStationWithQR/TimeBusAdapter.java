package com.map.fer.t_bus.BusByStationWithQR;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;
import com.map.fer.t_bus.Bus.ItemClickListenerBus;
import com.map.fer.t_bus.Bus.MyBus;
import com.map.fer.t_bus.R;
import java.util.ArrayList;

public class TimeBusAdapter extends RecyclerView.Adapter<TimeBusAdapter.ViewHolder> implements Filterable {
    ArrayList<MyBus> android;
    ArrayList<MyBus> filterListbusT;
    CustomFilterBusTime filterbusT;
    Context context;

    public TimeBusAdapter(Context context, ArrayList<MyBus> android) {
        this.context=context;
        this.android = android;
        filterListbusT = android;
    }

    @Override
    public TimeBusAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bus_by_station, viewGroup, false);
        return new TimeBusAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TimeBusAdapter.ViewHolder viewHolder, int i) {//set view and text to card view
        final MyBus busTm = android.get(i);
        String str2 = busTm.getArrivalTime();
        String[] arrOfStr1 = str2.split(" ");
        String part11 = arrOfStr1[0];
        final String part2 = arrOfStr1[1];

        viewHolder.LineTv.setText("Line:"+busTm.getLinename());
        viewHolder.Nbr.setText("Bus Nbr:"+String.valueOf(busTm.getNbrBus()));
        viewHolder.time.setText(part2);
        viewHolder.Fare.setText(String.valueOf(busTm.getFar())+"Km");




        viewHolder.setItemClickListener(new ItemClickListenerBus() {
            @Override
            public void onItemClick(View v, int pos) {
                Snackbar.make(v,busTm.getBus(),Snackbar.LENGTH_SHORT).show();
                //when click on item pass to next ativity
//                Intent i = new Intent(context, StepView.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("nbr_bus", busTm.getNbrBus());
//                bundle.putString("name", busTm.getLinename());
//                bundle.putString("departure_date", busTm.getStartTime());
//                bundle.putString("finish_date", busTm.getArrivalTime());
//                i.putExtras(bundle);
//                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return android.size();
    }


    @Override
    public android.widget.Filter getFilter() {
        if(filterbusT ==null)
        {
            filterbusT =new CustomFilterBusTime(filterListbusT,this);
        }
        return filterbusT;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { // define components of item
        private TextView LineTv, Nbr ,time,Fare;
        ItemClickListenerBus itemClickListener;
        public ViewHolder(View view) {
            super(view);

            LineTv =  itemView.findViewById(R.id.TVlinenm);
            Nbr =  itemView.findViewById(R.id.textVnbr);
            time=itemView.findViewById(R.id.textVTime);
            Fare = itemView.findViewById(R.id.textVfare);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListenerBus ic)
        {
            this.itemClickListener=ic;
        }}


}

