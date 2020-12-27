package dev.atharvakulkarni.messenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FriendRequestAdapter extends RecyclerView.Adapter
{
    RecyclerView recyclerView;
    Context context;
   // ArrayList<String> name = new ArrayList<>();
    ArrayList<String> photo = new ArrayList<>();
    // int flag;
    private ArrayList<Users_FriendRequest_Model>dataSet;

    public void update(ArrayList<Users_FriendRequest_Model> dataSet)
    {
        this.dataSet = dataSet;

       // this.flag = flag;

        //if(getItemCount() == 0)
        //    Toast.makeText(context, "No Results Found", Toast.LENGTH_SHORT).show();
        // else
        notifyDataSetChanged();  // refreshes the recycler view automatically
        // Toast.makeText(context, getItemCount()+"", Toast.LENGTH_SHORT).show();
    }

    public FriendRequestAdapter(RecyclerView recyclerView,Context context,ArrayList<Users_FriendRequest_Model>data)
    {
        this.recyclerView = recyclerView;
        this.context = context;
        this.dataSet = data;
    }

   /* @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)   // to create view for recycler view item
    {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_request_item,parent,false);
        return new ViewHolder(view);
    }*/

   /* @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        /*Glide.with(context)
                .load(userphotouris.get(position))
                .into(holder.circleImageView);*/

      /*     holder.name.setText(name.get(position));
           holder.photo.setText(photo.get(position));
        //  holder.count.setText(count.get(position));
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        switch (viewType)
        {
            case Users_FriendRequest_Model.USERS_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item, parent, false);
                return new UsersViewHolder(view);
            case Users_FriendRequest_Model.FRIENDREQUEST_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_item, parent, false);
                return new FriendRequestViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position)
    {
        switch (dataSet.get(position).type)
        {
            case 0:
                return Users_FriendRequest_Model.USERS_TYPE;
            case 1:
                return Users_FriendRequest_Model.FRIENDREQUEST_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition)
    {
        Users_FriendRequest_Model object = dataSet.get(listPosition);
        if (object != null)
        {
            switch (object.type)
            {
                case Users_FriendRequest_Model.USERS_TYPE:
                    ((UsersViewHolder) holder).name.setText(object.names);

                    break;
                case Users_FriendRequest_Model.FRIENDREQUEST_TYPE:
                    ((FriendRequestViewHolder) holder).name.setText(object.names);
                    break;
            }
        }
    }

    @Override
    public int getItemCount()       // return the no. of items
    {
        return dataSet.size();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;

        public UsersViewHolder(View itemView)
        {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    public class FriendRequestViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,photo;

        public FriendRequestViewHolder(View itemView)        // represents indiv list items
        {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            photo = itemView.findViewById(R.id.photo);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int position = recyclerView.getChildLayoutPosition(view);

                   /* Intent intent = new Intent(context, download_each.class);
                    intent.putExtra("title",title.get(position));
                    intent.putExtra("description",description.get(position));
                    intent.putExtra("duedate",duedates.get(position));
                    intent.putExtra("givendate",givendates.get(position));
                    intent.putExtra("teachername",usernames.get(position));
                    intent.putExtra("url",urls.get(position));
                    intent.putExtra("position",position);

                    context.startActivity(intent);*/

                  //  Intent intent = new Intent(context,chat_person.class);
                  //  context.startActivity(intent);


                    // denotes that we are going to view something
                    // intent.setData(Uri.parse(urls.get(position)));
                    //intent.setType(Intent.ACTION_VIEW);

                   /* intent.setDataAndType(Uri.parse((urls.get(position))),Intent.ACTION_VIEW);
                    context.startActivity(intent);*/
                }
            });
        }
    }
}