package com.example.recyclerviewexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.Toast;

import com.example.recyclerviewexample.adapter.ItemAdapter;
import com.example.recyclerviewexample.adapter.RecyclerItemClick;
import com.example.recyclerviewexample.modal.Modal;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements RecyclerItemClick {
    SwipeRefreshLayout swipeRefreshLayout;
    List<Modal> modalList = new ArrayList<>();
    List<Modal> archiveItem = new ArrayList<>();
    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    Modal deletedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getList();

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout=findViewById(R.id.swiperefresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this,modalList,this);
        recyclerView.setAdapter(itemAdapter);

        //add divider to recyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //swipe down & refresh list
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                modalList.add(new Modal("Movie 11","number 11"));
                modalList.add(new Modal("Movie 12","number 12"));
                modalList.add(new Modal("Movie 13","number 13"));
                modalList.add(new Modal("Movie 14","number 14"));
                modalList.add(new Modal("Movie 15","number 15"));
                modalList.add(new Modal("Movie 16","number 16"));

                
                itemAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //connect itemTouchHelper to recyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }


    //swipe left to delete & swipe right to archive
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    deletedItem = modalList.get(position);
                    modalList.remove(position);
                    itemAdapter.notifyItemRemoved(position);

                    //add snackbar with undo btn
                    Snackbar.make(recyclerView, deletedItem.getName(),Snackbar.LENGTH_LONG)
                            .setAction("Undo", v -> {
                                modalList.add(position, deletedItem);
                                itemAdapter.notifyItemInserted(position);
                            })
                            .show();
                    break;
                case ItemTouchHelper.RIGHT:
                    Modal itemName = modalList.get(position);
                    archiveItem.add(itemName);
                    modalList.remove(position);
                    itemAdapter.notifyItemRemoved(position);

                    //add snackbar with undo btn
                    Snackbar.make(recyclerView, itemName.getName()+" Archive",Snackbar.LENGTH_LONG)
                            .setAction("Undo", v -> {
                                archiveItem.remove(archiveItem.lastIndexOf(itemName));
                                modalList.add(position, itemName);
                                itemAdapter.notifyItemInserted(position);
                            })
                            .show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.green))
                    .addSwipeRightActionIcon(R.drawable.archive)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };



    private void getList() {
        modalList.add(new Modal("Movie 1","number 1"));
        modalList.add(new Modal("Movie 2","number 2"));
        modalList.add(new Modal("Movie 3","number 3"));
        modalList.add(new Modal("Movie 4","number 4"));
        modalList.add(new Modal("Movie 5","number 5"));
        modalList.add(new Modal("Movie 6","number 6"));
        modalList.add(new Modal("Movie 7","number 7"));
        modalList.add(new Modal("Movie 8","number 8"));
        modalList.add(new Modal("Movie 9","number 9"));
        modalList.add(new Modal("Movie 10","number 10"));
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "pos " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(int position) {
        modalList.remove(position);
        itemAdapter.notifyItemRemoved(position);
    }
}