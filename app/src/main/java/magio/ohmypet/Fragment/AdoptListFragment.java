package magio.ohmypet.Fragment;

//import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import magio.ohmypet.R;
import magio.ohmypet.activity.AdoptDetailActivity;
import magio.ohmypet.adapter.MyRecyclerAdapter;
import magio.ohmypet.model.ItemData;
import magio.ohmypet.model.Model_adopt;
import magio.ohmypet.util.Constants;
import magio.ohmypet.util.GetTask;

/**
 * Created by mini on 2016-07-06.
 */
public class AdoptListFragment extends Fragment{

    private Context context;
    private SwipeRefreshLayout swipeLayout;
    private MyRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    private ArrayList<ItemData> itemDatas = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_adopt_list, container, false);

        context = v.getContext();
        swipeLayout = (SwipeRefreshLayout)v.findViewById(R.id.adopt_list_swipe_layout);
        recyclerView = (RecyclerView)v.findViewById(R.id.adopt_list_recyclerView);

        initModel();
        initData();

        refresh();

        final GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });


        swipeLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO, 새로고침

                // for test
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });



        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                Log.d(Constants.TAG, "onInterceptTouchEvent");
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                /*Log.d(TAG, "getChildAdapterPosition=>" + rv.getChildAdapterPosition(child));
                Log.d(TAG,"getChildLayoutPosition=>"+rv.getChildLayoutPosition(child));
                Log.d(TAG,"getChildViewHolder=>" + rv.getChildViewHolder(child));*/
                    //Toast.makeText(getApplication(), count.get(rv.getChildAdapterPosition(child)).toString(), Toast.LENGTH_SHORT).show();
                    Log.d(Constants.TAG, "AdapterPosition=>" + rv.findViewHolderForAdapterPosition(rv.getChildLayoutPosition(child)));
                    Log.d(Constants.TAG, "LayoutPosition=>" + rv.findViewHolderForLayoutPosition(rv.getChildLayoutPosition(child)));
                    Log.d(Constants.TAG, "getChildViewHolder=>" + rv.getChildViewHolder(child).itemView);
                    //TextView tv = (TextView) rv.findViewHolderForAdapterPosition(rv.getChildLayoutPosition(child)).itemView.findViewById(R.id.tv);
                    //TextView tv = (TextView) rv.findViewHolderForLayoutPosition(rv.getChildLayoutPosition(child)).itemView.findViewById(R.id.tv);
                    TextView txt = (TextView) rv.getChildViewHolder(child).itemView.findViewById(R.id.txtTitle_item2);
                    TextView desc = (TextView) rv.getChildViewHolder(child).itemView.findViewById(R.id.txtDescription_item2);

                    Toast.makeText(getActivity(), txt.getText() + "\n" + desc.getText(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getContext(), AdoptDetailActivity.class);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                Log.d(Constants.TAG, "onTouchEvent");
            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                Log.d(Constants.TAG, "onRequestDisallowInterceptTouchEvent");
            }
        });

        return v;
    }

    private void initModel() {
        itemDatas = new ArrayList<ItemData>();

        int i;
        for(i = 0; i < 100; i++) {
            ItemData itemData_submit = new ItemData();
            itemData_submit.setTestImage((int)(Math.random() * 6));
            itemData_submit.setTitle("Test " + i);
            itemData_submit.setDesc("Desc " + i);

            itemDatas.add(0, itemData_submit);
        }
    }

    private void initData(){
        adapter = new MyRecyclerAdapter(itemDatas, R.layout.card_item);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void refresh() {
        try {
            GetTask getAdoption = new GetTask(context);
            getAdoption.execute(Constants.SERVER + "/adoption");
        } catch (Exception e) {
            Toast.makeText(context, "서버 접속에 실패 하였습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void addItem(Model_adopt obj) {
        Log.d(Constants.TAG, "AdoptListFragment addItem()");

        ItemData item = new ItemData();
        item.setTestImage((int)(Math.random() * 6));
        item.setTitle(obj.getTitle());
        item.setDesc(obj.getPrice());

        itemDatas.add(0, item);

        adapter.notifyDataSetChanged();
    }
}
