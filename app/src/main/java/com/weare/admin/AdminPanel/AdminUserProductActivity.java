package com.weare.admin.AdminPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weare.admin.Model.CartModel;
import com.weare.admin.R;
import com.weare.admin.ViewHolder.CartViewHolder;

public class AdminUserProductActivity extends AppCompatActivity {

    private RecyclerView productsListRv;
    DatabaseReference cartListRef;
    private String uid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_product);
        uid=getIntent().getStringExtra("uid");

        productsListRv=findViewById(R.id.admin_user_product_recyclerview);
        productsListRv.setHasFixedSize(true);
        productsListRv.setLayoutManager(new LinearLayoutManager(this));

        loadData();


    }

    private void loadData() {
        cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("Admin View")
                .child(uid)
                .child("Products");
        FirebaseRecyclerOptions<CartModel> options=new FirebaseRecyclerOptions.Builder<CartModel>().setQuery(cartListRef, CartModel.class)
                .build();
        FirebaseRecyclerAdapter<CartModel, CartViewHolder> adapter=new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull CartModel model) {
                holder.titleTextView.setText(model.getPname());
                holder.quantityTextView.setText("Quantity: "+model.getQuantity());
                holder.priceTextView.setText("Price: Rs"+model.getPrice());

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item,parent,false));
            }
        };
        productsListRv.setAdapter(adapter);
        adapter.startListening();
    }

}
