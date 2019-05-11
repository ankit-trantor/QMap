package qmap.amoralprog.com.qmap.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import qmap.amoralprog.com.qmap.R;
import qmap.amoralprog.com.qmap.ui.MapFragment;

public class QuestsAdapter extends RecyclerView.Adapter<QuestsAdapter.QuestViewHolder> {

    private int numOfQuests;
    private FragmentManager fragmentManager;

    public QuestsAdapter(int numOfQuests, FragmentManager fragmentManager)
    {
        this.numOfQuests = numOfQuests;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public QuestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context =viewGroup.getContext();
        int layoutIdListItem = R.layout.item_quest;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(layoutIdListItem, viewGroup, false);

        QuestViewHolder questViewHolder = new QuestViewHolder(view);


        return questViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestViewHolder questViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return numOfQuests;
    }

    class QuestViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;

        public QuestViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            description = itemView.findViewById(R.id.tv_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction().replace(R.id.container, new MapFragment()).commit();
                }
            });

        }

    }
}
