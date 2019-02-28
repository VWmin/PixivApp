package com.vwmin.min.sharedpreferencestest.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.activity.SearchActivity;
import com.vwmin.min.sharedpreferencestest.data.QueryHistory;
import com.vwmin.min.sharedpreferencestest.response.AutoCompleteResponse;

import java.util.List;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.ViewHolder> {

    private Context context;
    private List<QueryHistory> queryHistories;
    private List<String> autoCompleteResponses;
    public static final int MOD_QUERY_HISTORY = 1, MOD_AUTO_COMPLETE = 2;
    private int selectedMod;

    public QueryAdapter(Context context){
        this.context = context;
    }

    public void setMod(final int MOD){
        this.selectedMod = MOD;
    }

    public void setQueryHistories(List<QueryHistory> queryHistories) {
        this.queryHistories = queryHistories;
    }

    public void setAutoCompleteResponses(List<String> autoCompleteResponses) {
        this.autoCompleteResponses = autoCompleteResponses;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.query_edit);
            imageView = itemView.findViewById(R.id.query_delete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_query, viewGroup, false);

        return new QueryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if(selectedMod == MOD_QUERY_HISTORY) {
            viewHolder.textView.setText(queryHistories.get(i).getQueryContent());
            viewHolder.textView.setOnClickListener(v -> postSearch(queryHistories.get(i).getQueryContent()));
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.imageView.setOnClickListener(v -> {
                queryHistories.get(i).delete();
                postRefresh();
            });
        }else if(selectedMod == MOD_AUTO_COMPLETE){
            viewHolder.textView.setText(autoCompleteResponses.get(i));
            viewHolder.textView.setOnClickListener(v -> postSearch(autoCompleteResponses.get(i)));
            viewHolder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        switch (selectedMod){
            case MOD_QUERY_HISTORY:
                size = queryHistories.size();
                break;
            case MOD_AUTO_COMPLETE:
                size =  autoCompleteResponses.size();
                break;
        }
        return size;
    }

    private void postSearch(String word){
        Toast.makeText(context, "你点了一下这行字", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent("com.vwmin.min.sharedpreferencestest.START_SEARCH");
        intent.putExtra("word", word);
        context.sendBroadcast(intent);
    }

    private void postRefresh(){
        Intent intent = new Intent("com.vwmin.min.sharedpreferencestest.DELETE_PER_SEARCH_HISTORY");
        context.sendBroadcast(intent);
    }

}
